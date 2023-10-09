import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 */

/**
 * @author Aleeza and Kevin
 * Date: 06/09/23
 * Description: GUI for leaderboard to show user the ranks in the game
 *
 */
public class LeaderboardUI extends JFrame implements ActionListener{

	//private instance data
	private LeaderboardList ranks;
	private JPanel buttonPanel;
	private JButton btnReturn, btnSave, btnShow, btnSearch;
	private JTextArea area, area2, area3;
	private String output1, output2, output3;
	private JFrame frame;
	private TextPicture myTitle, subTitle;

	/**
	 * 
	 */
	public LeaderboardUI(LeaderboardList ranksInput) {
		//initialize private variables
		super("Leaderboard Ranks");

		ranks = ranksInput;

		//set the size and the location of the window
		frame = new JFrame(); //Create a new frame
		frame.setSize(800,630);
		frame.setLocation(100,10);

		frame.setLayout(null); //make layout flexible

		//>>>>>>>>>> Button Panel >>>>>>>>>>
		buttonPanel = new JPanel();
		buttonPanel.setBounds(155, 530, 500, 100);

		frame.add(buttonPanel);
		//leave the layout as the default FlowLayout

		//>>>>>>>>>> Buttons >>>>>>>>>>
		btnReturn = new JButton("Go Back");
		btnSave = new JButton("Save file");
		btnShow = new JButton("Show all");
		btnSearch = new JButton("Search Leaderboard");
		buttonPanel.add(btnReturn);
		buttonPanel.add(btnSave);
		buttonPanel.add(btnShow);
		buttonPanel.add(btnSearch);

		//add buttons as listeners
		btnReturn.addActionListener(this);
		btnSave.addActionListener(this);
		btnShow.addActionListener(this);
		btnSearch.addActionListener(this);

		//>>>>>>>>>> Title and subtitle Panel >>>>>>>>>>
		myTitle = new TextPicture("Leaderboard", 250, 60);
		myTitle.setFont(new Font("serif", Font.BOLD, 48));
		//set the location
		myTitle.setBounds(0,0,550,100);
		frame.add(myTitle);

		subTitle = new TextPicture("Rank        Name        Score       Time Left   "
				+ "   Difficulty", 25, 95);
		subTitle.setFont(new Font("dialogInput", Font.BOLD, 18));
		subTitle.setBounds(20, 50, 700, 100);
		frame.add(subTitle);

		//>>>>>>>>>> Text Area Panel >>>>>>>>>>
		area = new JTextArea("No data yet");
		area.setBounds(38, 160, 700, 100); //boundary for text box
		frame.add(area);
		area.setEditable(false);
		area.setFont(new Font("monospace", Font.BOLD, 16));

		area2 = new JTextArea("No data yet");
		area2.setBounds(38, 280, 700, 100);
		frame.add(area2);
		area2.setEditable(false);
		area2.setFont(new Font("monospace", Font.BOLD, 16));

		area3 = new JTextArea("No data yet");
		area3.setBounds(38, 400, 700, 100);
		frame.add(area3);
		area3.setEditable(false);
		area3.setFont(new Font("monospace", Font.BOLD, 16));

		ranks.heapSort(); // Ensuring they are sorted

		String output;

		for(int i =0; i < ranks.getSize(); i++) {
			output = (i+1)+"                             "
					+ranks.getList()[i].getName()+"                         "
					+ranks.getList()[i].getScore()+"                                "
					+ranks.getList()[i].getTimeTaken()+"                                ";

			if(ranks.getList()[i].getDifficulty() =='e') {
				output+=" easy mode.";
			}
			else if(ranks.getList()[i].getDifficulty() =='h') {
				output+=" hard mode." +"                                ";
			}

			if(i == 0) {
				area.setText(output);
			} 
			else if(i == 1) {
				area2.setText(output);
			}
			else if(i == 2) {
				area3.setText(output);

			}
		}



		frame.setResizable(false); //set it so can not be resized and make it visible
		frame.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent evt) {

		//when user is done with screen
		if(evt.getSource()==btnReturn) {
			//System.exit(0);
			frame.dispose();
		}
		if(evt.getSource()==btnSave) {
			String fileName = JOptionPane.showInputDialog(null,"File name to save to?");
			try {
				FileAccessForLeaderBoards.saveFile(fileName, ranks);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(evt.getSource()==btnShow) {
			String output = "";
			JTextArea area = new JTextArea();
			ranks.heapSort();
			for(int i =0; i< ranks.getSize(); i++) {
				System.out.println(ranks.getList()[i].getName());
				output+= ranks.getList()[i].getName() +" achieved "+ ranks.getList()[i].getScore() +" with " 
						+ ranks.getList()[i].getTimeTaken() +" time left on ";
				if(ranks.getList()[i].getDifficulty() =='e') {
					output+=" easy mode \n";
				}
				else if(ranks.getList()[i].getDifficulty() =='h') {
					output+=" hard mode\n";
				}
			}
			area.setText(output);
			
			 JScrollPane scrollableTextArea = new JScrollPane(area);  
			JOptionPane.showMessageDialog(null, scrollableTextArea);

		}
		if(evt.getSource()==btnSearch) {
			String searchTerm="", results;
			int index = -1;
			searchTerm = JOptionPane.showInputDialog(null, "Who would you like to search for?");
			index= ranks.binarySearch(searchTerm);

			if(index >= 0) {
				results = searchTerm + " found!\n"
						+searchTerm+" achieved "+ ranks.getList()[index].getScore() +" with " 
						+ ranks.getList()[index].getTimeTaken() +" time left on ";
				if(ranks.getList()[index].getDifficulty() =='e') {
					results+=" easy mode.";
				}
				else if(ranks.getList()[index].getDifficulty() =='h') {
					results+=" hard mode.";
				}
				JOptionPane.showMessageDialog(null, results );
			}
			else {
				JOptionPane.showMessageDialog(null, searchTerm + " not found!");
			}


		}

	}

	/*
	 * Self-testing main method
	 */
	public static void main(String[] args) {
		new LeaderboardUI(new LeaderboardList());
	}
	
}
