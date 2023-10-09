import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
/*
* Author: Kevin Abeykoon
* Date: June 2023
* Description:
* 				    This class uses the GameUI to play a game
* Methods:
*  1. public PacmanGame(ImageIcon characterIcon,  ImageIcon bgIcon, char difficulty)
*  2. public void disposePacmanGameScreen()
*  3. public GameUI getGameUI()
*  4. public void setGameUI(GameUI gameUI)
*/
public class PacmanGame extends JFrame {
	
	//Declaring attributes
	GameUI gameUI; 
	
	/*
	 * Constructor
	 */
	public PacmanGame(ImageIcon characterIcon,  ImageIcon bgIcon, char difficulty) {
	
		gameUI = new GameUI(characterIcon, bgIcon,difficulty);
		add(gameUI);
		repaint();
		setTitle("Play Pacman!"); 
		setSize(665,750);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/*
	 * Method to close this screen
	 */
	public void disposePacmanGameScreen() {
		this.dispose();
	}
	
	/*
	 * Getter for GameUI
	 */
	public GameUI getGameUI() {
		return gameUI;
	}

	/*
	 * Setter for GameUI
	 */
	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}

	/*
	 * Self testing main method
	 */
	public static void main(String[] args) {
		PacmanGame p = new PacmanGame(new ImageIcon("Mei.png"), new ImageIcon("bg.png"),'e');
		
		p.setGameUI(new GameUI(new ImageIcon("Mei.png"), new ImageIcon("bg.png"),'e'));
		
		System.out.println(p.getGameUI().getDifficulty());
		p.disposePacmanGameScreen();
	}
}
