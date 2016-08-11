import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clients connect to Server and every single message send by any client is stored
 * here. It allows to send these messages to a newly connected client that was unavailable
 * when these messages were sent. It's a multithread application that simultaneously
 * waits for new clients and handles messages from those who are connected.
 * 
 * @author Lukasz Smigielski
 *
 */
public class Server implements Runnable {
	
	/**
	 * Port that clients connect to
	 */
	private static final int port = 9999;
	private static ServerSocket serverSocket;
	
	/**
	 * Number specifies what a Server object is responsible for. See the Server constructor 
	 * description for more information.
	 */
	private int number;
	
	/**
	 * Every client connected to the Server is represented by an element
	 * in the array of sockets.
	 */
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	
	/**
	 * Every message sent from any client is stored in this array in order to send those 
	 * 'old' messages immediately to a just-connected client.
	 */
	public static ArrayList<String> messages = new ArrayList<String>();	
	
	/**
	 * The threads[0] is responsible for waiting for clients and adding new threads (with
	 * indices greater than 0) to this array. These non-zero threads receive and send
	 * messages, so that the threads[1] thread handles connection with the client in the
	 * sockets[0] socket, threads[2] corresponds to sockets[1] and so on. 
	 *  See the Server constructor description for more info.
	 */
	public static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	/**
	 * Program waits until the first client requests a connection.
	 * After that, thread[0] is started which waits for the second and further clients. 
	 * Threads[1] handles connection with the client represented by sockets[0].
	 * @param args
	 */
	public static void main(String[] args) {
		Server server0 = new Server(0);
		
		try {
			server0.waitForClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		threads.add(new Thread(server0));
		threads.add(new Thread(new Server(1)));

		threads.get(0).start();
		threads.get(1).start();

	}//main()
	
	/**
	 * Constructs a Server object with a specified number.
	 * If the number equals zero, then it means that this object is responsible
	 * for waiting for clients. It's going to be running
	 * along with other Server objects (also running as threads), but those other objects 
	 * (with numbers different than zero) are responsible for receiving messages
	 * from clients and sending them to all clients connected. Once the 'zero' Server object
	 * detects a new client connection, it adds corresponding thread to handle this client.
	 * Things described above happen in the overridden run() method.
	 * 
	 * @param newNumber Identification number which says what an object is responsible for
	 */
	public Server(int newNumber) {
		this.number = newNumber;
		if(number == 0){
			System.out.println("Waiting for clients...");
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void run() {	
		try {
			if(number == 0){
				while(true){
					this.waitForClient();
					threads.add(new Thread(new Server(threads.size())));
					threads.get(threads.size()-1).start();
				}
			}
			else if(number > 0){
				this.handleMessages(number-1);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void waitForClient() {	
		try {
			Socket tmp = serverSocket.accept();
			System.out.println("Client conected");
			sockets.add(tmp);
			String message = "Client no "+sockets.size()+" connected.";
			sendAllMessagesToNewbie(sockets.size()-1);
			messages.add(message);
			sendToAll(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//waitForClients
	
	
	/**
	 * Handles messages traffic from the client represented by sockets[i]. Receives a single
	 * message, adds current time and then sends it to all connected clients.
	 * @param i Index of a socket in the sockets[] array.
	 */
	public void handleMessages(int i)  {
		String line;
		String tempMessage;
		Scanner input = null;
		while(true) {		
			try {
				input = new Scanner(sockets.get(i).getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(input.hasNext()){
				line = input.nextLine();
				tempMessage = "("+getTime()+") "+line;
				try {
					this.sendToAll(tempMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				messages.add(tempMessage);
				System.out.println(messages.get(messages.size()-1));
			}//if
	
		}//while(true)
	}//handleMessages()
	
	/**
	 * Sends a single message to all connected clients
	 * @param msg A message to send
	 */
	public void sendToAll(String msg)  {
		PrintStream output = null;
		for(int i = 0; i < sockets.size(); i++) {
			try {
				output = new PrintStream(sockets.get(i).getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			output.println(msg);
		}
	}
	
	/**
	 * Sends all messages stored in the messages[] array to a newly connected client
	 * @param newbieIndex Index of a newly connected client
	 */
	public void sendAllMessagesToNewbie(int newbieIndex) {
		PrintStream tempOutput = null;
		try {
			tempOutput = new PrintStream(sockets.get(newbieIndex).getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < messages.size(); i++){
			tempOutput.println(messages.get(i));
		}
	}
	
	/**
	 * Returns the current time which is added at the beginning of a message
	 * @return A string with the current time
	 */
	public static String getTime(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
	}
	
	
}//class Server
