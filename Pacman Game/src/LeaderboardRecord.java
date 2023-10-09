/**
 * 
 */

/**
 * @author Aleeza
 * Date: 05/31/23
 * Description: Class that takes in name, rank, score, the time they took, 
 * and the difficulty they completed the task
 *
 */
public class LeaderboardRecord {

	/**
	 * Private instance Data
	 */
	
	private String name, rank;
	private int score, timeTaken;
	private char difficulty;
	
	/**
	 * default constructor
	 */
	public LeaderboardRecord() {
		// initialize data
		this.name = "";
		this.rank = "";
		this.score = 0;
		this.timeTaken = 0;
		this.difficulty = 'a';
	}
	public LeaderboardRecord(String record) {
		// initialize data
		this.name = "";
		this.rank = "";
		this.score = 0;
		this.timeTaken = 0;
		this.difficulty = 'a';
		
		processRecords(record);
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}



	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}



	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}



	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}



	/**
	 * @return the timeTaken
	 */
	public int getTimeTaken() {
		return timeTaken;
	}



	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}



	/**
	 * @return the difficulty
	 */
	public char getDifficulty() {
		return difficulty; 
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(char difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Method to populate the data. Assumes the format
	 * name, rank, score, time taken, difficulty
	 */
	public void processRecords(String record) {
		String word[];
		word = record.split("/");

		this.name = word[0];
		this.rank = word[1];
		this.score = Integer.parseInt(word[2]);
		this.timeTaken = Integer.parseInt(word[3]);
		this.difficulty = word[4].charAt(0);
	}

	/**
	 * Method to represent object as a string
	 */
	@Override
	public String toString() {
		
		if (difficulty == 'h') {
			System.out.println("Hard");
		}
		
		
		else if (difficulty == 'e') {
			System.out.println("Easy");
		}
		
		return "LeaderboardRecord [name= " + name + ", rank= " + rank + ", score= " + score 
				+ ", timeTaken= " + timeTaken + ", difficulty= " + difficulty + "]";
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//declare a test string with a record
		String myRecord = "Aleeza/4/2159/118/h";
		
		//create an object
		LeaderboardRecord leaderInfo = new LeaderboardRecord();
		
		//display object with toString method
		System.out.println(leaderInfo.toString());
		
		//test the processor method with string variable and object
		leaderInfo.processRecords(myRecord);
		 
		//display object with toString method again
		System.out.println(leaderInfo.toString());
		
		//test the setters
		leaderInfo.setName("Kevin");
		leaderInfo.setRank("82");
		leaderInfo.setScore(12);
		leaderInfo.setTimeTaken(120);
		leaderInfo.setDifficulty('e');
		
		//test the getters
		System.out.println(leaderInfo.getName());
		System.out.println(leaderInfo.getRank());
		System.out.println(leaderInfo.getScore());
		System.out.println(leaderInfo.getTimeTaken());
		System.out.println(leaderInfo.getDifficulty());

	}

}
