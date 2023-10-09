import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Kevin Abeykoon 
 * (Previously worked on by Nisaaj and I for a different project, but it has been edited since then)

 * Date: June 2023
 * 
 * Description: This class reads and write to a file. It only works with LeaderboardLists 
 * 				LeaderboardRecords
 * Methods:
 * 		1. public static  LeaderboardList loadFile(String fileName)
 * 			-> Method to load data from a file and insert it into a leaderboard list
 *  	1. public static  void saveFile(String fileName, LeaderboardList outputList)
 * 			-> This method writes a LeaderboardList to a new file
 */

public class FileAccessForLeaderBoards {


	/*
	 * Method to load data from a file 
	 * and insert it into a leaderboard list
	 */
	public static  LeaderboardList loadFile(String fileName) throws IOException {
		// open the file to read from
		FileReader fr = new FileReader(fileName);
		BufferedReader input = new BufferedReader(fr);

		// read the size of file located on the first line and save it in size
		int sizeOfFile = Integer.parseInt(input.readLine());

		LeaderboardRecord  record = new LeaderboardRecord();
		LeaderboardList  list = new LeaderboardList();

		list.increaseMaxSize(sizeOfFile);

		// read the rest of the files
		for (int i = 0; i < sizeOfFile; i++) {
			// read each line into the array
			record = new LeaderboardRecord(input.readLine());
			list.insert(record);
		}

		input.close(); // close not file but its file string

		return list; // return String array
	}

	/**
	 * This method  writes a LeaderboardList to a new file
	 */
	public static  void saveFile(String fileName, LeaderboardList outputList) throws IOException {


		FileWriter fw = new FileWriter(fileName); // Creates a new file writer for the given fileName
		BufferedWriter output = new BufferedWriter(fw); // writes data into fileName provided

		// Reads the # of items in the file
		output.write(outputList.getSize()+"\n"); //Write the length of the phrases array to the outputfile. This is the first line 

		// Loop through the phrases array followed by a new line each time to the file.
		for (int i = 0; i < outputList.getSize(); i++) {
			System.out.println(outputList.getList()[i].getName());
			output.write(outputList.getList()[i].getName()+"/"
					+outputList.getList()[i].getRank() +"/"
					+outputList.getList()[i].getScore() +"/"
					+outputList.getList()[i].getTimeTaken() +"/"
					+outputList.getList()[i].getDifficulty());

			output.newLine();
		}

		output.close(); //close the output writer. If this is not closes this may cause generation issues to the new file
	}





}

//END OF PROGRAM
