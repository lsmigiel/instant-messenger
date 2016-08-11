import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Includes all the data and methods needed to communicate with the Server
 * @author Lukasz Smigielski
 *
 */
public class ClientModel {
	
	private static final int port = 9999;
	private  Socket socket;
	
	/**
	 * For receiving messages
	 */
	private  Scanner input;
	
	/**
	 * For sending messages
	 */
	private  PrintStream ps;
	
	/**
	 * User's nickname set after launch of the application
	 */
	private String nickname;
	
	/**
	 * Creates connection between this Client and the Server
	 */
	public ClientModel() {
		try {
			socket = new Socket("127.0.0.1", port);
			input = new Scanner(socket.getInputStream());
			ps = new PrintStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives a single message from the Server
	 * @return String representing a received message
	 */
	public String receiveMessage(){
		String line = null;		
		if(input.hasNext()){
			line = input.nextLine();
		}
		return line;
	}
	
	/**
	 * Sends a single message
	 * @param msg The message to send
	 */
	public void sendMessage(String msg){
		ps.println(msg);
	}
	
	/**
	 * Sets the nickname
	 * @param par the new nickname
	 */
	public void setNickname(String par){
		nickname = par;
	}
	
	/**
	 * Gets the nickname of the Client
	 * @return Nickname String
	 */
	public String getNickname(){
		return nickname;
	}
}
