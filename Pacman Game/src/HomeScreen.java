import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Author: Kevin Abeykoon
 * Date: June 2023
 * Description:
 * 				    This is a JPanel class that is to be used in a JFrame. This class runs the core of 
 * 				the game. It allows you to control the pacman character using the arrow keys to eat orbs
 * 				orbs and avoid enemy ghosts. The lives left, the remaining time, and score is displayed 
 * 				to the user. When the player loses all 3 lives, runs out of time, or eats all the orbs
 * 				the game ends fully.
 * Method List:
 * 				1. GameUI(ImageIcon characterPicture, ImageIcon bgPicture, char difficulty)-> Default Constructor
 * 				2. public void calculateTotalPoints()......................................-> Method to calculate points
 * 				3. private void updateGame(Graphics2D g2D).................................-> Method to update the Game.
 * 				4. public void moveGhosts( Graphics2D g2D).................................-> Moves the ghost enemy
 * 				5. public void gameEnd()...................................................-> Completely ends the game
 * 				6. public void movePacman()................................................-> Moves the pacman character
 * 				7. public void paintComponent(Graphics g)..................................-> Paints the JPanel 
 * 				8. public void refreshGame()...............................................->  Sets the characters to the starting pos
 * 				9. public void keyPressed(KeyEvent e)......................................->  Method to handle key press events
 * 				10. public void keyReleased(KeyEvent e)....................................->  Method to handle key release events
 * 				11. public void keyTyped(KeyEvent e).......................................-> Method to handle key typed events
 * 				12. public void actionPerformed(ActionEvent e).............................-> Method to handle action events
 * 				13. public int getSecondsLeft()............................................-> Getter Method to get SecondsLeft
 *  			14. public void setSecondsLeft(int secondsLeft)............................-> Setter Method to set SecondsLeft
 *   			15. public int getMinutesLeft()............................................-> Getter Method to get MinutesLeft
 *    			16. public void setMinutesLeft(int minutesLeft)............................-> Setter Method to set MinutesLeft
 *     			17. public int getCalculatedScore()........................................->  Getter Method to get CalculatedScore
 *      		18. public void setCalculatedScore(int calculatedScore)....................-> Setter Method to set CalculatedScore
 *     			17. public char getDifficulty()............................................->  Getter Method to get Difficulty
 *      		18. public void setDifficulty(char difficulty).............................-> Setter Method to set Difficulty
 * Citations:
 *           -> https://www.youtube.com/watch?v=BGeOwlIGRGI - bitwise operators
 *           -> https://stackoverflow.com/questions/21568535/how-to-convert-imageicon-to-image
 *           -> https://stackoverflow.com/questions/17829098/how-to-close-specific-jframe-based-on-events-on-jpanel-without-exiting-application
 *           -> https://www.youtube.com/watch?v=ATz7bIqOjiA - main inspiration for the game
 */
public class HomeScreen extends JFrame implements ActionListener, WindowListener {

	//Declaring attributes
	private TextPicture title, nameInstructions, playerInstructions, 
	backgroundInstructions, levelInstructions, selectionDescription; 
	private JComboBox playerCombo, backgroundCombo, levelCombo; // combo boxes to choose from
	private JButton startBtn, exitBtn, helpBtn, leaderboardBtn, uploadLeaderboardBtn;
	private JTextField nameField;

	// To store images/data for the combo boxes
	private ImageIcon playerIconArray[], backgroundIconArray[];
	private String levelStringArray[];
	private int playerIconArrayLength, backgroundIconArrayLength, levelStringArrayLength;
	private String playerImage1, playerImage2, playerImage3, bgImage1, bgImage2, bgImage3;
	private String selectedPlayer, selectedBackground , enteredName, selectedDifficulty, output;

	private PacmanGame gameObject; // Creates a game object

	private LeaderboardList list =  new LeaderboardList(); // Creates a list to store data in

	/*
	 * Default Constructor
	 */
	public HomeScreen() {
		super("Home Screen");
		setSize(900,700);
		setLayout(null);

		// Initializing variables
		selectedPlayer = "";
		selectedBackground = "";
		enteredName = "";
		selectedDifficulty = "";
		output = "";

		setContentPane(new JLabel(new ImageIcon("HomeScreenBG.jpg"))); // Setting the background

		// The next few blocks create and format all the textPictures being used
		title = new TextPicture("Pacman Game Home Screen",0,50);
		title.setFont(new Font("Georgia", Font.BOLD, 50));
		title.setColor(Color.white);

		selectionDescription = new TextPicture("You have chosen Redson and the Fire Fortress background.",0,90);
		selectionDescription.setFont(new Font("Georgia", Font.BOLD, 15));
		selectionDescription.setColor(Color.white);
		nameInstructions = new TextPicture("Enter your name:",0,0);

		nameInstructions = new TextPicture("Enter your name:",0,40);
		nameInstructions.setFont(new Font("Georgia", Font.BOLD, 20));
		nameInstructions.setColor(Color.white);
		playerInstructions = new TextPicture("Select a player:",0,40);
		playerInstructions.setFont(new Font("Georgia", Font.BOLD, 20));
		playerInstructions.setColor(Color.white);
		backgroundInstructions = new TextPicture("Select a background",0,40);
		backgroundInstructions.setFont(new Font("Georgia", Font.BOLD, 20));
		backgroundInstructions.setColor(Color.white);
		levelInstructions = new TextPicture("Select a level",0,40);
		levelInstructions.setFont(new Font("Georgia", Font.BOLD, 20));
		levelInstructions.setColor(Color.white);


		// Settign up arrays
		playerIconArrayLength = 3;
		backgroundIconArrayLength = 3;
		levelStringArrayLength = 2;

		playerIconArray = new ImageIcon[playerIconArrayLength];
		backgroundIconArray = new ImageIcon[backgroundIconArrayLength];
		levelStringArray = new String[levelStringArrayLength];

		// This information is based on the files present
		playerImage1 = "RedsonBig.png";
		playerImage2 = "MeiBig.png";
		playerImage3 = "MkBig.png";
		bgImage1 = "FireFortressCombo.png";
		bgImage2 = "MountainVillageCombo.jpg";
		bgImage3 = "OldDojoCombo.png";

		//Putting information into the array
		playerIconArray[0] = new ImageIcon(playerImage1);
		playerIconArray[1] = new ImageIcon(playerImage2);
		playerIconArray[2] = new ImageIcon(playerImage3);
		backgroundIconArray[0] = new ImageIcon(bgImage1);
		backgroundIconArray[1] = new ImageIcon(bgImage2);
		backgroundIconArray[2] = new ImageIcon(bgImage3);
		levelStringArray[0] = "Easy Level";
		levelStringArray[1] = "Hard Level";

		playerCombo = new JComboBox(playerIconArray);
		backgroundCombo = new JComboBox(backgroundIconArray);
		levelCombo = new JComboBox(levelStringArray);

		// This selects one option for the user
		playerCombo.setSelectedIndex(0);
		backgroundCombo.setSelectedIndex(0);
		levelCombo.setSelectedIndex(0);

		//Adds ActionListener
		playerCombo.addActionListener(this);
		backgroundCombo.addActionListener(this);
		levelCombo.addActionListener(this);

		nameField = new JTextField("Enter your name here");

		//Setting up buttons
		startBtn = new JButton("Start");
		exitBtn = new JButton("Exit");
		helpBtn = new JButton("Help");
		leaderboardBtn = new JButton("Leaderboard");
		uploadLeaderboardBtn = new JButton("Upload Leaderboard Data");

		startBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		helpBtn.addActionListener(this);
		leaderboardBtn.addActionListener(this);
		uploadLeaderboardBtn.addActionListener(this);

		//Setting the bounds of components
		title.setBounds(75, 30, 800, 200);
		selectionDescription.setBounds(150, 430, 800, 100);

		playerInstructions.setBounds(60, 150, 800, 100);
		backgroundInstructions.setBounds(610, 150, 800, 100);
		levelInstructions.setBounds(355, 250, 800, 100);
		nameInstructions.setBounds(340, 150, 800, 100);

		nameField.setBounds(350, 200, 150,30);

		levelCombo.setBounds(350, 300, 150,30);
		startBtn.setBounds(50,550,180,40);
		helpBtn.setBounds(250,550,180,40);
		leaderboardBtn.setBounds(450,550,180,40);
		uploadLeaderboardBtn.setBounds(650,550,180,40);
		exitBtn.setBounds(350,600,180,40);

		playerCombo.setBounds(60, 200, 200, 200);
		backgroundCombo.setBounds(600, 200, 250, 200);

		//adding them to frame
		add(title);
		add(selectionDescription);
		add(playerInstructions);
		add(backgroundInstructions);
		add(levelInstructions);
		add(nameInstructions);
		add(nameField);

		add(startBtn);
		add(exitBtn);
		add(helpBtn);
		add(leaderboardBtn);
		add(uploadLeaderboardBtn);

		add(playerCombo);
		add(backgroundCombo);

		add(levelCombo);


		setResizable(false);
		updateSelectionDescription();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/*
	 * Method to update the text at the bottom that tells the user what they selected
	 */
	public void updateSelectionDescription() {
		//Getting info from user selections
		selectedPlayer = (playerCombo.getSelectedItem()).toString();
		selectedBackground = (backgroundCombo.getSelectedItem()).toString();
		enteredName = nameField.getText();
		selectedDifficulty = (levelCombo.getSelectedItem()).toString();

		//Default name
		if(enteredName.equalsIgnoreCase("Enter your name here")) {
			enteredName = "Traveler";
		}

		//Developing the output string
		output = "Hi "+ enteredName + "! You chose the character ";
		if(selectedPlayer ==playerImage1) {
			output+="Redson";
		}
		else if(selectedPlayer ==playerImage2) {
			output+="Mei";
		}
		else if(selectedPlayer ==playerImage3) {
			output+="Mk";
		}

		output+= " and the ";

		if(selectedBackground ==bgImage1) {
			output+="Fire Fortress";
		}
		else if(selectedBackground ==bgImage2) {
			output+="Mountain Village";
		}
		else if(selectedBackground ==bgImage3) {
			output+="Old Dojo";
		}
		output+= " background.";


		selectionDescription.setText(output); // sets the texts
		this.repaint(); // repaints the frame
	}

	/*
	 * Method to deal with action events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// for combo boxes
		if(e.getSource()==playerCombo) {
			updateSelectionDescription();
		}
		else if(e.getSource()==backgroundCombo) {
			updateSelectionDescription();
		}
		else if(e.getSource()==levelCombo) {
			updateSelectionDescription();
		}


		//For buttons
		if(e.getSource()==startBtn) { // for start button

			if(selectedDifficulty.equals("Easy Level")) {
				selectedDifficulty = "e";
			}
			else if(selectedDifficulty.equals("Hard Level")) {
				selectedDifficulty = "h";
			}

			if(selectedPlayer.equals("MeiBig.png")) {
				selectedPlayer = "Mei.png";
			}
			else if(selectedPlayer.equals("MkBig.png")) {
				selectedPlayer = "Mk.png";
			}
			else if(selectedPlayer.equals("RedsonBig.png")) {
				selectedPlayer = "Redson.png";
			}

			if(selectedBackground.equals("MountainVillageCombo.jpg")) {
				selectedBackground = "MountainVillage.jpg";
			}
			else if(selectedBackground.equals("OldDojoCombo.png")) {
				selectedBackground = "OldDojo.png";
			}
			else if(selectedBackground.equals("FireFortressCombo.png")) {
				selectedBackground = "FireFortress.png";
			}

			//Creates the gameObject so the user can play the game
			JOptionPane.showMessageDialog(null, output +"\n\nStarting Game...");
			gameObject= new PacmanGame(new ImageIcon(selectedPlayer),new ImageIcon(selectedBackground), selectedDifficulty.charAt(0) );
			gameObject.addWindowListener(this);
		}
		else if(e.getSource()==exitBtn) { // for exit button
			char option = 'a';
			do {
				option = (JOptionPane.showInputDialog(null, 
						"Are you sure you want to exit?"
								+"\nEnter 'y' to exit, 'b' to go back.")).charAt(0);
			}
			while(option != 'y' && option != 'b' );

			if(option=='y') {
				System.exit(0); // exits program
			}
		}
		else if(e.getSource()==helpBtn) { // for help button
			new HelpScreen(); // creates a new help screen
		}
		else if(e.getSource()==leaderboardBtn) {
			new LeaderboardUI(list);
		}
		else if(e.getSource()==uploadLeaderboardBtn) {
			char option = 'z';
			String fileName = "";
			do {
				option = (JOptionPane.showInputDialog(null,"Are you sure you woudl like to load data.\nEnter l for load, c for cancel.")).charAt(0);
			}while(option !='l' && option !='c' );

			if(option != 'c') {
				fileName = JOptionPane.showInputDialog(null,"File name to upload from?");
				try {
				
						list = FileAccessForLeaderBoards.loadFile(fileName);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		

	}

	public static void main(String[] args) {
		new HomeScreen(); // It was also tested here
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//	playerObject.
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		gameObject.getGameUI().calculateTotalPoints();
		updateSelectionDescription();
		list.insert(new LeaderboardRecord(enteredName+"/0/"+gameObject.getGameUI().getCalculatedScore()
				+"/"+(gameObject.getGameUI().getMinutesLeft()*60+ gameObject.getGameUI().getSecondsLeft()) 
				+"/"+gameObject.getGameUI().getDifficulty()));

	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
