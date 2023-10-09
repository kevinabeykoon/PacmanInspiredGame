import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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
public class GameUI extends JPanel implements ActionListener, KeyListener{

	//Declaring attributes
	private JLabel  scoreText, timeText; // For displaying the score and time left in the game
	private Image bg; // Image of the background
	private ImageIcon bgPicture;// Image of the background
	private char difficulty; // can be e for easy or h for hard
	private ImageIcon characterPicture; // ImageIcon of the user (This is the variable that stores the entered image)

	private ImageIcon heart, ghost1, ghost2, ghost3; // ImageIcons for the ghost enemies and hearts
	private int hardPlayerSpeed, easyPlayerSpeed, hardEnemySpeed, easyEnemySpeed; // for the different speeds for different levels
	private Character[] enemyArray; // Array to store the enemy objects
	private Player playerObject; 

	// Describes the "look" of the grid. 
	// blockSize = size of the cells , gridLengthInBlocks = how many cells in a row, gridSize = size in pixels of a row/column
	private int blockSize, gridLengthInBlocks, gridSize; 
	private int secondsLeft, minutesLeft; // Time left until the game is forcibly ended

	// currentScore is how many orbs has been eaten. calculatedScore is based on other factors and is the final score
	private int livesLeft, currentScore, calculatedScore; 
	private int inputDirX, inputDirY; // What direction the user entered from their keyboard
	private int[] dx, dy;                                                                             //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean inGame, playerIsDying, gameFinished; // Describes the states of the game

	private Random rnd = new Random(); // used for varying the enemy image
	int rndInt, lastInt;

	private DecimalFormat timeFormat; // A unformatted int is displayed as "0", two digits are needed for "00"
	private javax.swing.Timer gameTimer; // The timer that the game uses
	private javax.swing.Timer timeKeepingTimer; // The timer that the in game countdown timer uses

	// screenData describes what is actually on the screen, for example screenData records if an orb is eaten
	// levelData describes what is the original game like, orbs and borders.
	//Please review the pdf attached for more details on how it works 
	private  int screenData[]; 

	// Normally, this would be initialized in  the constructor, but then
	// the = { } cannot be used and it would be extremely tedious to do 730 individually
	private  int levelData[] = {
			19, 18, 18, 18, 18, 18, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,

			17, 16, 16, 16, 16, 20, 0,  17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
			17, 16, 16, 16, 16, 20, 0,  17, 16, 8,  8,  16, 16, 24, 16, 16, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 20,
			17, 16, 16, 24, 16, 20, 0,  17, 4,  0,  0,  1,  20, 0,  17, 20, 0,  0,  0,  0,  0,  0,  0,  17, 16, 16, 20,
			17, 16, 20, 0,  17, 16, 18, 16, 4,  0,  0,  1,  20, 0,  17, 16, 18, 18, 22, 0,  19, 18, 18, 16, 16, 16, 20,
			17, 16, 20, 0,  25, 24, 24, 24, 16, 2,  2,  16, 20, 0,  17, 16, 16, 16, 20, 0,  17, 16, 16, 24, 24, 24, 20,

			17, 16, 20, 0,  0,  0,  0,  0,  17, 16, 24, 24, 28, 0,  25, 24, 24, 16, 20, 0,  17, 16, 20, 0,  0,  0,  21,
			17, 16, 20, 0,  19, 18, 18, 18, 16, 20, 0,  0,  0,  0,  0,  0,  0,  17, 16, 18, 16, 16, 16, 18, 18, 18, 20,
			17, 16, 20, 0,  17, 16, 16, 24, 16, 16, 18, 18, 18, 18, 18, 18, 18, 16, 16, 16, 24, 24, 24, 24, 16, 16, 20,
			17, 16, 16, 18, 16, 24, 28, 0,  17, 16, 24, 24, 16, 16, 16, 16, 24, 24, 16, 20, 0,  0,  0,  0,  17, 16, 20,
			17, 16, 16, 16, 20, 0,  0,  0,  17, 20, 0,  0,  17, 16, 16, 20, 0,  0,  17, 16, 22, 0,  19, 18, 16, 16, 20,
			17, 16, 16, 24, 16, 18, 22, 0,  17, 20, 0,  19, 16, 16, 16, 16, 22, 0,  17, 16, 20, 0,  17, 16, 24, 24, 20,
			17, 16, 20, 0,  17, 16, 20, 0,  17, 20, 0,  25, 24, 24, 24, 24, 28, 0,  17, 16, 16, 18, 16, 20, 0,  0,  21,
			17, 16, 20, 0,  17, 16, 16, 18, 16, 20, 0,  0,  0,  0,  0,  0,  0,  0,  17, 16, 16, 24, 16, 20, 0,  0,  21,
			17, 16, 20, 0,  25, 24, 24, 16, 16, 16, 18, 18, 18, 18, 18, 18, 18, 18, 16, 16, 20, 0,  17, 16, 18, 18, 20,
			17, 16, 20, 0,  0,  0,  0,  17, 16, 16, 16, 16, 16, 24, 24, 24, 16, 16, 24, 24, 28, 0,  17, 16, 16, 16, 20,

			17, 16, 16, 18, 18, 18, 18, 16, 16, 16, 16, 16, 20, 0,  0,  0,  17, 20, 0,  0,  0,  0,  17, 16, 16, 16, 20,
			17, 16, 16, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 22, 0,  19, 16, 16, 18, 18, 18, 18, 16, 16, 16, 16, 20,
			17, 16, 20, 0,  0,  0,  0,  0,  0,  0,  17, 16, 16, 20, 0,  17, 16, 24, 24, 24, 16, 16, 24, 24, 16, 16, 20,
			17, 16, 20, 0,  19, 18, 18, 18, 22, 0,  17, 16, 16, 20, 0,  17, 20, 0,  0,  0,  17, 20, 0,  0,  17, 16, 20,
			17, 16, 20, 0,  17, 16, 24, 16, 20, 0,  17, 16, 16, 20, 0,  17, 16, 18, 18, 18, 16, 20, 0,  19, 16, 16, 20,
			17, 16, 16, 18, 16, 20, 0,  17, 16, 18, 16, 16, 16, 16, 18, 16, 16, 16, 24, 24, 24, 28, 0,  25, 16, 16, 20,
			17, 16, 16, 24, 24, 28, 0,  25, 24, 24, 16, 16, 24, 16, 16, 24, 16, 20, 0,  0,  0,  0,  0,  0,  17, 16, 20,
			17, 16, 20, 0,  0,  0,  0,  0,  0,  0,  17, 20, 0,  17, 20, 0,  17, 16, 18, 18, 18, 22, 0,  0,  17, 16, 20,
			17, 16, 16, 18, 18, 18, 18, 18, 18, 18, 16, 20, 0,  17, 20, 0,  17, 16, 16, 16, 16, 16, 18, 18, 16, 16, 20,
			17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  17, 20, 0,  17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,

			25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 26, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28,
	};

	/*
	 * Default Constructor
	 */
	public GameUI(ImageIcon characterPicture, ImageIcon bgPicture, char difficulty) {
		super();
		// Sets the inputted data to class variables
		this.characterPicture = characterPicture;
		this.bgPicture = bgPicture;
		this.difficulty = difficulty;



		// Initializing speeds
		hardPlayerSpeed =  2; 
		easyPlayerSpeed = 4;
		hardEnemySpeed = 5;
		easyEnemySpeed = 4;

		// Creating ImageIcon objects
		ghost1 =  new ImageIcon("ghost1.png");
		ghost2 =  new ImageIcon("ghost2.png");
		ghost3 =  new ImageIcon("ghost3.png");
		heart =  new ImageIcon("heart.png");

		// Initializing the sizing of the grid
		blockSize = 24;
		gridLengthInBlocks = 27;
		gridSize = gridLengthInBlocks * blockSize;

		// Creating and setting the screenData to levelData
		screenData = new int[gridLengthInBlocks * gridLengthInBlocks];
		for(int i = 0; i <gridLengthInBlocks*gridLengthInBlocks; i++) {
			screenData[i] = levelData[i];
		}

		enemyArray = new Character[10]; // Creating enemy array
		playerObject = new Player(this.characterPicture, 400, 500); // Creating player object

		// Creates the enemy objects
		rndInt =-1;
		lastInt = 0;
		for(int i = 0; i < enemyArray.length; i++) {

			//Ensures that theres a variety of enemy images
			do {
				rndInt = rnd.nextInt(3);
			}while(rndInt ==lastInt);
			lastInt = rndInt;

			ImageIcon randomGhost = new ImageIcon();

			if(rndInt== 0) {
				randomGhost = ghost1;
			}
			else if(rndInt== 1) {
				randomGhost = ghost2;
			}
			else if(rndInt== 2) {
				randomGhost = ghost3;
			}
			else {
				System.out.println("error"+ rndInt);
			}
			enemyArray[i] = new Character(randomGhost, 0, 0);

			//Sets speed based on difficulty
			if(difficulty == 'h') {
				enemyArray[i].setSpeed(hardEnemySpeed);
			}
			else if (difficulty == 'e') {
				enemyArray[i].setSpeed(easyEnemySpeed);
			}
		}

		// Based on difficulty, sets the speeds
		if(difficulty == 'h') {
			playerObject.setSpeed(hardPlayerSpeed);
			playerObject.setBonusSpeed(6);
		}
		else if(difficulty == 'e') {
			playerObject.setSpeed(easyPlayerSpeed);
			playerObject.setBonusSpeed(4);
		}

		//////////////////////////////////////////////////////////
		dx = new int[4];
		dy = new int[4];

		// Setting up
		secondsLeft = 0;
		minutesLeft = 3;

		scoreText = new JLabel("Score: 0");
		bg= this.bgPicture.getImage(); // Creating Image based on ImageIcon
		gameFinished = false;

		// Setting up things for the timer information
		timeText = new JLabel(minutesLeft+":00");
		timeText.setFont(new Font("Georgia", Font.ITALIC +Font.BOLD, 20));
		timeText.setBounds(0, 0, 200, 200);
		add(timeText);

		timeKeepingTimer =  new Timer(1000,this); // Creates timer 
		timeKeepingTimer.addActionListener(this);

		timeFormat =  new DecimalFormat("00"); 

		// 20 represents the speed at which the panel is repainted
		gameTimer = new Timer(20, this); 
		gameTimer.start();

		addKeyListener(this); // To listen for key events
		setFocusable(true); // Lets this panel get "focused" by the JFrame. This program does work without it
	}

	/*
	 * Method to calculate points
	 * It is based on the difficulty, time left, score, lives left.
	 */
	public void calculateTotalPoints() {
		calculatedScore= 0;

		calculatedScore = currentScore *1;
		if(difficulty == 'h') {
			calculatedScore  *=10;
		}
		calculatedScore += (minutesLeft * 60 + secondsLeft)*2 + livesLeft *10;
	}


	/*
	 * Method to update the Game.
	 * It checks the game status
	 * and tells the program to move the objects
	 */
	private void updateGame(Graphics2D g2D) {
		// If the player has been hit by a enemy, decrease lives by 1
		if(playerIsDying) {
			livesLeft--;
			// If not more lives left, end game
			if(livesLeft == 0) {
				inGame = false;
				gameFinished = true;
				gameEnd();
			}
			else {
				refreshGame(); // This sets the game back to a new starting positions
			}
		}

		// Keep moving and checking game status
		else {
			movePacman();
			g2D.drawImage(playerObject.getImage().getImage(), playerObject.getxPos()+1, playerObject.getyPos()+1, this);
			moveGhosts(g2D);
			boolean finished = true;
			int i = 0;

			while (i < gridLengthInBlocks * gridLengthInBlocks && finished) {
				if((screenData[i]) != 0) {
					finished = false;
				}
				i++;
			}

			if(finished) {
				gameEnd();
			}
		}
	}

	/*
	 *  Method to move ghost enemies
	 */
	public void moveGhosts( Graphics2D g2D) {
		int pos, count;

		//Loops through each enemy
		for(int i = 0; i < enemyArray.length;i++) {

			//If the enemy is in a valid palce to move
			if((enemyArray[i].getxPos() % blockSize == 0)  && (enemyArray[i].getyPos() % blockSize == 0 ) ) {
				pos = enemyArray[i].getxPos() / blockSize + gridLengthInBlocks * (int) (enemyArray[i].getyPos() / blockSize);
				count = 0;

				//Uses bitwise operator  to determine which type of cell the enemy is on
				// Then it determines if the enemy can continue moving

				//For example, if theres no obstacle on the left and the enemy is not already going to the right, continue going left
				if((screenData[pos] & 1) ==0 && enemyArray[i].getMovingDirectionX() != 1 ) {
					dx[count] = -1;
					dy[count] = 0;
					count++;
				}
				if((screenData[pos] & 2) ==0 && enemyArray[i].getMovingDirectionY() != 1 ) {
					dx[count] = 0;
					dy[count] = -1;
					count++;
				}
				if((screenData[pos] & 4) ==0 && enemyArray[i].getMovingDirectionX() != -1 ) {
					dx[count] = 1;
					dy[count] = 0;
					count++;
				}
				if((screenData[pos] & 8) ==0  && enemyArray[i].getMovingDirectionY() != -1  ) {
					dx[count] = 0;
					dy[count] = 1;
					count++;
				}

				//If all of the above conditions were not met, make the enemy go the opposite direction
				if (count == 0) {
					enemyArray[i].setMovingDirectionY(-enemyArray[i].getMovingDirectionY());
					enemyArray[i].setMovingDirectionX(-enemyArray[i].getMovingDirectionX());

				} else {

					//This randomizes the enemy movement based on the movement of the other ghosts
					//thats why at the start of the game, the ghosts stay in the center for a long time
					count = (int) (Math.random() * count);

					enemyArray[i].setMovingDirectionX(dx[count]);
					enemyArray[i].setMovingDirectionY(dy[count]);
				}


			}

			// Changes the the position of the enemy
			enemyArray[i].setxPos(enemyArray[i].getxPos() + enemyArray[i].getMovingDirectionX() * enemyArray[i].getSpeed());
			enemyArray[i].setyPos(enemyArray[i].getyPos() + enemyArray[i].getMovingDirectionY() * enemyArray[i].getSpeed());

			// Draws the enemy with the new position
			g2D.drawImage( enemyArray[i].getImage().getImage(), enemyArray[i].getxPos()+3, enemyArray[i].getyPos() +3,this); //diff from tutorail

			// Determine if the player intersects with one of the enemies.
			if (playerObject.getxPos() > (enemyArray[i].getxPos()- 12) && playerObject.getxPos() < (enemyArray[i].getxPos()+ 12)
					&& playerObject.getyPos() > (enemyArray[i].getyPos()- 12) && playerObject.getyPos() < (enemyArray[i].getyPos()+ 12)) {
				playerIsDying = true;
			}
		}
	}


	/*
	 * Method that ends the game and closes the JFrame this is attached to
	 */
	public void gameEnd() {
		timeKeepingTimer.stop();
		gameTimer.stop();
		calculateTotalPoints();
		JOptionPane.showMessageDialog(null, "Game complete! You scored a total of " + calculatedScore+" points!");

		// This finds what frame this jpanel is connected to and closes it
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.dispose();
	}
	/*
	 * Moves the pacman
	 */
	public void movePacman() {
		int pos;
		int data;
		//Determines if pacman is in a suitable position to move
		if(playerObject.getxPos() % blockSize == 0 && playerObject.getyPos() % blockSize == 0) {
			pos = playerObject.getxPos() / blockSize + gridLengthInBlocks * (int) (playerObject.getyPos()/blockSize);
			data = screenData[pos];

			//Records data about eaten orbs
			if((data & 16) != 0) {
				screenData[pos] = (int) (data & 15);
				currentScore++;
				playerObject.setObjectsEaten(playerObject.getObjectsEaten()+1);
				playerObject.checkAmountOfObjectsEaten(); // checks to see if the bonus speed can be used

			}

			// If the player can continue to a direction without an obstruction, then do so
			if(inputDirX != 0 || inputDirY != 0) {
				if(!((inputDirX == -1 && inputDirY == 0 && (data & 1) != 0)
						|| (inputDirX == 1 && inputDirY == 0 && (data & 4) != 0)
						|| (inputDirX == 0 && inputDirY == -1 && (data & 2) != 0)
						|| (inputDirX == 0 && inputDirY == 1 && (data & 8) != 0))) {
					playerObject.setMovingDirectionX(inputDirX);
					playerObject.setMovingDirectionY(inputDirY);
				}
			}

			// If the player cannoy continue to a direction due to an obstruction, then stop
			if((playerObject.getMovingDirectionX() == -1 && playerObject.getMovingDirectionY() == 0 && (data & 1) != 0)
					||(playerObject.getMovingDirectionX()  == 1 && playerObject.getMovingDirectionY() == 0 && (data & 4) != 0)
					||(playerObject.getMovingDirectionX()  == 0 && playerObject.getMovingDirectionY() == -1 && (data & 2) != 0)
					||(playerObject.getMovingDirectionX()  == 0 && playerObject.getMovingDirectionY() == 1 && (data & 8) != 0)) {
				playerObject.setMovingDirectionX(0);
				playerObject.setMovingDirectionY(0);
			}
		}

		// Sets the new position
		playerObject.setxPos( playerObject.getxPos() + playerObject.getSpeed() * playerObject.getMovingDirectionX()); 
		playerObject.setyPos( playerObject.getyPos() + playerObject.getSpeed() * playerObject.getMovingDirectionY()); 

	}

	/*
	 * Method to paint the JPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Graphics2D is used  because it allows for more 
		// complex tasks to be performed. For example, line weight wehen drawing lines
		Graphics2D g2D = (Graphics2D) g;

		g2D.drawImage(bg, 0,0,null); // Draws the background onto the Panel

		//paints the eintire maze using bitwise operations
		int j =0;
		for(int y = 0; y <gridSize; y += blockSize) {
			for(int x = 0; x <gridSize; x += blockSize) {
				g2D.setColor(Color.BLACK);

				// Changes the colour of the maze to become more readable on different bg
				if(bgPicture.toString().equals("FireFortress.png")) {
					g2D.setColor(Color.orange);
				}
				else if(bgPicture.toString().equals("MountainVillage.jpg")) {
					g2D.setColor(Color.PINK);
				}
				else if(bgPicture.toString().equals("OldDojo.png")) {
					g2D.setColor(Color.blue);
				}

				// This for example needs Graphics2D
				// This sets the line thickness when drawing
				g2D.setStroke(new BasicStroke(5)); 

				//Paints the actual maze
				if((levelData[j] == 0)) {
					g2D.fillRect(x, y, blockSize, blockSize);
				}
				if((screenData[j] & 1) != 0) {
					g2D.drawLine(x, y, x, y+ blockSize-1);
				}
				if((screenData[j] & 2) != 0) {
					g2D.drawLine(x, y, x+ blockSize-1, y);
				}
				if((screenData[j] & 4) != 0) {
					g2D.drawLine(x + blockSize-1, y, x + blockSize-1, y+ blockSize-1);
				}
				if((screenData[j] & 8) != 0) {
					g2D.drawLine(x , y + blockSize-1, x + blockSize-1, y+ blockSize-1);
				}
				//Paints the white dots
				if((screenData[j] & 16) != 0) {
					g2D.setColor(Color.WHITE);
					g2D.fillOval(x+10, y+10, 6, 6);
				}
				j++;
			}
		}

		// Draws the hearts
		for(int i = 0; i < livesLeft; i++) {
			g2D.drawImage(heart.getImage(), i *40+10, 665, this);
		}

		// Changes the colour of the strings to become more readable on different bg
		if(bgPicture.toString().equals("FireFortress.png")) {
			g2D.setColor(Color.LIGHT_GRAY);
		}
		else if(bgPicture.toString().equals("MountainVillage.jpg")) {
			g2D.setColor(Color.MAGENTA);
		}
		else if(bgPicture.toString().equals("OldDojo.png")) {
			g2D.setColor(Color.GREEN);
		}

		// Updates the score and time info on screen
		g2D.setFont(new Font("Georgia", Font.ITALIC +Font.BOLD, 30));
		g2D.drawString(timeText.getText(), 280, 685);
		scoreText.setText("Score: "+ currentScore);
		g2D.drawString(scoreText.getText(), 450, 685);

		if(inGame) {
			//continues the game
			updateGame(g2D);
		}
		else {
			// This is like a startup screen that waits for approval to start
			String text = "Ready? Press SPACE!";
			g2D.drawString(text, gridSize / 4, 300);
		}

		g2D.dispose(); // Frees up cache and memory, else the memory will be filled up
	}

	/*
	 * Method to refresh the game, but NOT reset the information
	 */
	public void refreshGame() {

		// Gives all the enemy objects the starting information
		for(int i = 0; i < enemyArray.length; i++) {
			enemyArray[i].setxPos( 13 * blockSize );
			enemyArray[i].setyPos( 11 * blockSize );

			enemyArray[i].setMovingDirectionY(0);
			enemyArray[i].setMovingDirectionX(1);

			enemyArray[i].setSpeed(1);
		}

		//puts the player back into the starting position
		playerObject.setxPos(13 * blockSize);
		playerObject.setyPos(1 * blockSize);
		playerObject.setMovingDirectionX(0);
		playerObject.setMovingDirectionY(0);
		inputDirX = 0;
		inputDirY = 0;
		playerIsDying = false;
	}



	/*
	 * Method to handle keyEvents, namely the arrow keys
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode  = e.getKeyCode();
		if(inGame) {


			if (keyCode == KeyEvent.VK_UP) { // Up arrow
				inputDirX = 0;
				inputDirY = -1;
			} else if (keyCode == KeyEvent.VK_DOWN) { // down arrow
				inputDirX = 0;
				inputDirY = 1;

			} else if (keyCode == KeyEvent.VK_LEFT) { // left arrow
				inputDirX = -1;
				inputDirY = 0;
			}
			else if (keyCode == KeyEvent.VK_RIGHT ) { // right arrow
				inputDirX = 1;
				inputDirY = 0;
			} 

		}
		else {
			// If they press space AND the game is not completely finished
			if(keyCode == KeyEvent.VK_SPACE && !gameFinished) {
				inGame = true;
				livesLeft = 3;
				currentScore = 0;
				refreshGame();
				if(inGame) {
					timeKeepingTimer.start();
				}
			}
		}
	}

	/*
	 * Method to deal with key release events
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		/*
		 * Method to deal with key typed events
		 */
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Method to deal with action events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// For countdown timer
		if(e.getSource()== timeKeepingTimer) {
			// formats text, for some reason it shows 1-2 seconds are left when the game ends by time, this fixes that
			String formattedSeconds =  timeFormat.format(secondsLeft-1);

			if(secondsLeft <=0 && minutesLeft >0 ) {
				secondsLeft =59;
				minutesLeft--;
			}
			secondsLeft--;

			// If no time remaining
			if(minutesLeft<=0 && secondsLeft <= 0) {
				gameEnd();
			}
			else {
				timeText.setText(minutesLeft+":"+(formattedSeconds));
			}
		}
		else {
			this.repaint(); // From GUITimer
		}
	}

	/*
	 *  Getter Method to get SecondsLeft
	 */
	public int getSecondsLeft() {
		return secondsLeft;
	}

	/*
	 *  Setter Method to set SecondsLeft
	 */
	public void setSecondsLeft(int secondsLeft) {
		this.secondsLeft = secondsLeft;
	}

	/*
	 *  Getter Method to get MinutesLeft
	 */
	public int getMinutesLeft() {
		return minutesLeft;
	}

	/*
	 *  Setter Method to set MinutesLeft
	 */
	public void setMinutesLeft(int minutesLeft) {
		this.minutesLeft = minutesLeft;
	}

	/*
	 *  Getter Method to get CalculatedScore
	 */
	public int getCalculatedScore() {
		return calculatedScore;
	}

	/*
	 *  Setter Method to set CalculatedScore
	 */
	public void setCalculatedScore(int calculatedScore) {
		this.calculatedScore = calculatedScore;
	}


	/*
	 *  Getter Method to get Difficulty
	 */
	public char getDifficulty() {
		return difficulty;
	}


	/*
	 *  Setter Method to set Difficulty
	 */
	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}



	/*
	 * Main method. This class does not need to be tested from within the class. PacmanGame tests this directly.
	 */
	public static void main(String[] args) {}
}
