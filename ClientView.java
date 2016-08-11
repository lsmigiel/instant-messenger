import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * GUI for Client application. Creates three windows present in the application:
 * log window, main window and emoji window.
 * @author Lukasz Smigielski
 *
 */
public class ClientView extends JFrame {
	
	//emoji window's elements
	JButton smileButton = new JButton(":)");
	JButton sadButton = new JButton(":(");
	JButton straightLineButton = new JButton(":|");
	JButton iksdeButton = new JButton("xd");
	JButton tongueButton = new JButton(":P");
	JButton annoyedButton = new JButton(";_;");
	JButton lennyButton = new JButton("( ͡° ͜ʖ ͡°)");
	
	//log window's elements
	JTextField nickTextField = new JTextField();
	JButton logButton = new JButton("OK");
	JFrame logWindow = new JFrame();
	
	//main window's elements
	private JLabel nicknameLabel = new JLabel("[nickname]");
	private JTextField textField = new JTextField();
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JButton sendButton = new JButton(new ImageIcon(getEnvelopeImage()));
	private JButton emojiButton = new JButton(new ImageIcon(getEmojiImage()));
	
	/**
	 * Creates the main window of the program. Forces the focus on the text field
	 * so that you don't have to click on it every time you send a message.
	 * Makes the text area to show the latest message on the bottom, so that you always see it
	 * and don't have to scroll down. And many more tasks.
	 */
	public ClientView(){
		this.setSize(400, 320);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);

		nicknameLabel.setBounds(25, 0, 300, 25);
		textField.setBounds(25, 260, 260, 25);
		scrollPane.setBounds(25,20,350,220);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sendButton.setBounds(330, 255, 60, 35);
		emojiButton.setBounds(290, 255, 40, 35);
		
		sendButton.setBorder(BorderFactory.createEmptyBorder());
		sendButton.setContentAreaFilled(false);
		emojiButton.setBorder(BorderFactory.createEmptyBorder());
		emojiButton.setContentAreaFilled(false);
		
		textField.setToolTipText("Enter your message here");
		sendButton.setToolTipText("Click to send your message");
		emojiButton.setToolTipText("Insert an emoji");
		textArea.setToolTipText("Messaging story");
		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		this.add(textField);
		this.add(sendButton);
		this.add(emojiButton);
		this.add(nicknameLabel);
		getContentPane().add(scrollPane);
		textField.requestFocusInWindow();
		
	}
	
	//Below: getters of any object that can be the source of the event. They are used in the 
	//ClientController class to determine what is the source.

	public JButton getSendButton(){
		return sendButton;
	}
	public JButton getEmojiButton(){
		return emojiButton;
	}
	public JButton getSmileButton(){
		return smileButton;
	}
	public JButton getSadButton(){
		return sadButton;
	}
	public JButton getStraightLineButton(){
		return straightLineButton;
	}
	public JButton getIksDeButton(){
		return iksdeButton;
	}
	public JButton getTongueButton(){
		return tongueButton;
	}
	public JButton getAnnoyedButton(){
		return annoyedButton;
	}
	public JButton getLennyButton(){
		return lennyButton;
	}
	public JButton getLogButton(){
		return logButton;
	}
	public JTextField getTextField(){
		return textField;
	}
	public JTextField getNickTextField(){
		return nickTextField;
	}
	public JFrame getLogWindow(){
		return logWindow;
	}
	public String getMessageFromTextField(){
		return textField.getText();
	}
	public void setTextArea(String line){
		textArea.append(line+"\n");
	}
	public void setTextField(String e){
		textField.setText(e);
	}
	public void setNicknameLabel(String par){
		nicknameLabel.setText(par);
	}
	/**
	 * Adds text to the existing text in the textField
	 * @param e The text you might want to add
	 */
	public void appendTextField(String e){
		textField.setText(getMessageFromTextField()+" "+e);
	}
	
	/**
	 * Adds action listener to every object that might be the source of the event
	 * @param a
	 */
	public void addListener(ActionListener a){
		//main window
		sendButton.addActionListener(a);
		emojiButton.addActionListener(a);
		textField.addActionListener(a);
		
		//emoji window
		smileButton.addActionListener(a);
		sadButton.addActionListener(a);
		straightLineButton.addActionListener(a);
		iksdeButton.addActionListener(a);
		tongueButton.addActionListener(a);
		annoyedButton.addActionListener(a);
		lennyButton.addActionListener(a);
		
		//log window
		logButton.addActionListener(a);
		
	}

	/**
	 * Shows the tip if the message field is empty before attempting to end a message
	 */
	public void showErrorBoxMessage(){
		JOptionPane.showMessageDialog(null, "Type a message! :D");	
	}
	
	/**
	 * Shows the windows with emojis next to the main windows of the program.
	 */
	public void showEmojiWindow(){
		JFrame emojiWindow = new JFrame();
		emojiWindow.setBounds(this.getX()+100, this.getY()+320, 170, 170);
		emojiWindow.setTitle("Emojis");
		emojiWindow.setLayout(null);
		emojiWindow.setResizable(false);
		emojiWindow.setVisible(true);
		
		smileButton.setBounds(5, 5, 50,50);
		sadButton.setBounds(60, 5, 50,50);
		straightLineButton.setBounds(115, 5, 50,50);
		iksdeButton.setBounds(5, 60, 50,50);
		tongueButton.setBounds(60, 60, 50,50);
		annoyedButton.setBounds(115, 60, 50,50);
		lennyButton.setBounds(5, 115, 160,50);
		
		emojiWindow.add(smileButton);
		emojiWindow.add(sadButton);
		emojiWindow.add(straightLineButton);
		emojiWindow.add(iksdeButton);
		emojiWindow.add(tongueButton);
		emojiWindow.add(annoyedButton);
		emojiWindow.add(lennyButton);
	}
	
	/**
	 * Shows the log window where you can type your nickname just after launching the app.
	 */
	public void showLogWindow(){
		logWindow.setBounds(this.getX()+100, this.getY()+50, 200, 150);
		logWindow.setTitle("Log in");
		logWindow.setLayout(null);
		logWindow.setResizable(false);
		logWindow.setVisible(true);

		nickTextField.setBounds(25, 25, 150, 30);
		logButton.setBounds(65, 65, 70, 40);
		logWindow.add(nickTextField);
		logWindow.add(logButton);
	}
	
	public String getNickFromTextField(){
		return nickTextField.getText();
	}
	
	/**
	 * Needed for setting the envelope image on the send button
	 * @return BufferedImage with the envelope image
	 */
	public BufferedImage getEnvelopeImage(){
		BufferedImage buttonIcon = null;
		try {
			//buttonIcon = ImageIO.read(new File("/home/luksmi/Desktop/proz2/resources/images/icon2.png"));
			buttonIcon = ImageIO.read(new File("icon2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buttonIcon;
	}
	
	/**
	 * Needed for setting the emoji image on the emoji button
	 * @return BufferedImage with the emoji image
	 */
	public BufferedImage getEmojiImage(){
		BufferedImage buttonIcon = null;
		try {
			//buttonIcon = ImageIO.read(new File("/home/luksmi/Desktop/proz2/resources/images/emoji22.png"));
			buttonIcon = ImageIO.read(new File("emoji22.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buttonIcon;
	}
}