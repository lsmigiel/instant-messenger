import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Interaction between ClientModel and ClientView classes takes place here. Defines
 * actions taken on an event like button pressing or 'Enter' key hitting.
 * @author Lukasz Smigielski
 *
 */
public class ClientController {
	ClientModel model;
	ClientView view;
	
	/**
	 * Constructs a ClientController object that manages model and view attributes.
	 * Adds action listeners to the view, sets the title of the main window and displays
	 * the log window at the launch of the Client application.
	 * @param m ClientModel object
	 * @param v ClientView object
	 */
	public ClientController(ClientModel m, ClientView v) {
		model = m;
		view = v;	
		view.addListener(new MyListener());
		view.showLogWindow();
		view.setTitle("Instant Messenger");
		keepRefreshingTextArea();
	}
	
	/**
	 * Receives a message and adds it to the text area. Plays the notification sound,
	 * provided that the message added to the text area was not created by the current user.
	 * Only messages from other peers are indicated with a sound.
	 */
	public void keepRefreshingTextArea(){
		while(true){
			String tmp = model.receiveMessage();
			if(tmp != null){
				view.setTextArea(tmp);

				if(!getStringUpToChar(tmp, 11, ':').equals(model.getNickname()))
					playSound("alert.wav");
			}
		}
	}
	
	/**
	 * Sets the nickname by a text typed by the user in the log window. Sets the label
	 * that informs what is the user's nickname
	 */
	public void setNickname(){
		String nickname = view.getNickFromTextField();
		model.setNickname(nickname);
		view.getLogWindow().dispose();
		view.setNicknameLabel("Logged as: "+nickname);
	}
	
	/**
	 * Plays a sound after receiving a message
	 * @param soundName Name of the audio file
	 */
	public void playSound(String soundName){
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception ex){
			System.out.println("Error with playing sound.");
			ex.printStackTrace( );
		}
	}
	
	/**
	 * Class needed for adding action listeners
	 * @author Lukasz Smigielski
	 *
	 */
	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object src = arg0.getSource();
			
			if(src == view.getSendButton() || src == view.getTextField()){
				String tmp = view.getMessageFromTextField();
				if(tmp.equals("")){
					view.showErrorBoxMessage();
				}
				else{
					model.sendMessage(model.getNickname()+": "+tmp);
					view.setTextField("");
					view.getTextField().requestFocusInWindow();
				}
			}//if
			else if(src == view.getEmojiButton()){
				view.showEmojiWindow();
			}
			else if(src == view.getSmileButton() ){
				view.appendTextField(":)");
			}
			else if(src == view.getSadButton() ){
				view.appendTextField(":(");
			}
			else if(src == view.getStraightLineButton() ){
				view.appendTextField(":|");
			}
			else if(src == view.getIksDeButton() ){
				view.appendTextField("xd");
			}
			else if(src == view.getTongueButton()){
				view.appendTextField(":P");
			}
			else if(src == view.getAnnoyedButton() ){
				view.appendTextField(";_;");
			}
			else if(src == view.getLennyButton() ){
				view.appendTextField("( ͡° ͜ʖ ͡°)");
			}
			else if(src == view.getLogButton() || src == view.getNickTextField() ){
				setNickname();
			}
		}//actionPerformed
		
	}//class MyListener
	
	/**
	 * Needed for comparing the current user's nickname and the nickname built
	 * into the message in order to avoid playing sound in the user's app 
	 * if the message was written by this user. The method takes a string as an argument and returns
	 * the part between the character at [a] index (including this char) and the c character
	 * (excluding this char). For example, given a String: example = "(12:34:01) Lukasz: Example message"
	 * the call getStringUpToChar(example, 11, ':') returns "Lukasz". 
	 * 
	 * @param str Original string
	 * @param a index of the first character to be included in the return string
	 * @param c the character indicating end of the part to be returned
	 * @return Part of the str parameter
	 */
	public static String getStringUpToChar(String str, int a, Character c){
		String tmp = null;
		int i = a+1;
		if(str.charAt(0) != '(')
			return "smotheing";
		tmp = Character.toString(str.charAt(a));
		while(str.charAt(i) != c){
			tmp += Character.toString(str.charAt(i));
			i++;
		}
		
		return tmp;
	}
}//Class ClientController
