import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 */

/**
 * @author Aleeza
 * Date: 06/05/23
 * Description: GUI Screen that shows instructions on how to play
 * and how to pick your features to for user to pick
 *
 */
public class HelpScreen extends JFrame implements ActionListener{

	/**
	 * Private Instance Variables 
	 */
	private JPanel buttonPanel;
	private JButton btnReturn;
	private JTextArea area;
	private JFrame frame;
	private TextPicture myTitle;
	
	/**
	 * Default constructor
	 */
	public HelpScreen() {
		// Initialize private variables 
		super("Help Screen"); 
		
		//set the size and the location of the window
		frame = new JFrame(); //Create a new frame
		frame.setSize(800,500);
		frame.setLocation(100,10);

		setContentPane(new JLabel(new ImageIcon("Backround.jpg")));
		
		frame.setLayout(null); //make layout flexible
		
		//>>>>>>>>>> Button Panel >>>>>>>>>>
		buttonPanel = new JPanel();
		buttonPanel.setBounds(135, 400, 500, 100);
		
		frame.add(buttonPanel);
		//leave the layout as the default FlowLayout
		
		//>>>>>>>>>> Buttons >>>>>>>>>>
		btnReturn = new JButton("Go Back");
		buttonPanel.add(btnReturn);
				
		//add buttons as listeners
		btnReturn.addActionListener(this);
		
		//>>>>>>>>>> Title Panel >>>>>>>>>>
		myTitle = new TextPicture("How to play!", 308, 30);
		myTitle.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 28));
		//set the location
		myTitle.setBounds(0,0,500,40);
		frame.add(myTitle);
		
		//>>>>>>>>>> Text Area Panel >>>>>>>>>>
		area = new JTextArea("You are a lost traveler that is trying to escape a group of ghosts" 
				+ " as soon as \npossible. Collect as many orbs as you can as you run through the "
				+ "maze to \nescape and win! For every 50 orbs you collect, you gain a temporary speed "
				+ "boost, \nso be sure to use that! Use your keyboard arrows to move around the maze, "
				+ "and \navoid coming into contact with the ghosts! You only have 3 lives, so be careful!"
				+ "\n\nWhen you finish a game, your name will appear on the leaderboard! If you want to"
				+ "\nsee a rank past the top 3 visible ranks, use the 'Show all' button to see all the "
				+ "\nranks up to 30. If your rank is not visible on the top 3 ranks, use the search "
				+ "\nbutton to find your name and rank on the leaderboard. If you want to save the "
				+ "\nleaderboard ranks, use the 'save file' to download the top 5 ranks into a text \nfile."
				+ "\n\nPress the 'Go Back' button when you are done."); 
		
		
		area.setBounds(38, 40, 710, 350); //boundary for text box
		frame.add(area);
		area.setEditable(false);
		area.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		frame.setResizable(false); //set it so can not be resized and make it visible
		frame.setVisible(true);
		//Only used for self testing
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //ends when x is clicked
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		//when user is done with screen
		if(evt.getSource()==btnReturn) {
			//System.exit(0);//Only used for self testing\
			frame.dispose();
		} 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Test class
		HelpScreen help = new HelpScreen();

	}

}
