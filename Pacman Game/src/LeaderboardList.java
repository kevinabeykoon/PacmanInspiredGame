import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Aleeza
 * Date: 06/01/23
 * Description: Represents a list for leaderboard record objects
 *
 */
public class LeaderboardList {

	/**
	 * private instance data 
	 */
	private LeaderboardRecord list[];
	private int maxSize;
	private int size;

	/**
	 * Default constructor
	 */
	public LeaderboardList() {
		//initialize private data
		this.maxSize = 30;
		this.size = 0;
		this.list = new LeaderboardRecord[maxSize];	
	}

	/**
	 * The insert Method
	 * Checks if the size is below the maxSize
	 * If so, it increases the size by 1
	 * and adds the record to the location just below size
	 * returns true if successfully inserts
	 */
	public boolean insert(LeaderboardRecord record) {
		//if size is below maxSize
		if (size < maxSize) {
			size++; //increase by 1
			list[size-1] = record; //add the record 
			return true; 
		}
		return false; //record could not be inserted
	}

	/**
	 * The delete method
	 * Checks if the record exists 
	 * if so, it cheats & replaces record with the last one
	 * Decreases the size by 1
	 * returns true if successful
	 */
	public boolean delete(LeaderboardRecord record) {
		//loop through the list 
		for(int i =0; i < size; i++) {
			if(list[i].getName().equalsIgnoreCase(record.getName())) {
				list[i] = list[size-1]; //put last record in this record
				size--; //decrease size
				return true;
			}
		}
		return false; //record does not exist
	}

	/**
	 * Change a record
	 * Deletes the old record 
	 * Adds a new one
	 * Returns true if successful
	 */
	public boolean change(LeaderboardRecord oldR, LeaderboardRecord newR) {
		//delete the old record
		if (delete(oldR)) { //checks if it can delete old record
			insert(newR);
			return true; // was able to delete and insert new records
		}
		return false; 
	}

	/*
	 * Method that allows user to increase
	 * maximum size of the list. The parameters
	 * is an int which represents by how many indexes
	 * the current list will be
	 */
	public boolean increaseMaxSize(int newAmountOfIndexes) {

		//If the new # of indexes is greater than the current # of indexes
		if(newAmountOfIndexes>this.getMaxSize()) {
			LeaderboardRecord[] tempList = new LeaderboardRecord[newAmountOfIndexes];

			//Transports the old data to the new list
			for(int i =0; i< this.getList().length; i++) {
				tempList[i] = this.getList()[i];
			}

			this.setMaxSize(newAmountOfIndexes); // Sets the new max size
			this.setList(tempList); // sets the new list
			return true; // If increase was successful
		}
		return false; // If increase was not successful
	}


	/**
	 * Binary Search Method
	 */
	public int binarySearch(String searchKey) {
		int low = 0;
		int high = size-1;
		int middle;

		//while the low end of my list is below the high end
		while(low <= high) {
			middle = (low+high)/2;
			if(searchKey.compareToIgnoreCase(list[middle].getName())==0) {
				return middle; //found it
			}

			else if (searchKey.compareToIgnoreCase(list[middle].getName())<0) {
				//My search key is lower in the alphabet
				high = middle-1;
			}

			else {
				//my searchkey is higher in the alpha 
				low = middle + 1;
			}
		}
		return -1; //did not find it 
	}

	/**
	 * Heapify Method. Sorts from highest to lowest score
	 */	
	public void heapify(LeaderboardRecord[] array, int arraySize, int currentNode) {
		//Initializing variables
		int smallest = currentNode;
		int leftChildIndex = 2*currentNode+1;
		int rightChildIndex = 2*currentNode+2;

		// Checking to see if child nodes are larger than parent node
		if(leftChildIndex <arraySize && array[leftChildIndex].getScore() < array[smallest].getScore() ) {
			smallest=leftChildIndex;
		}

		if(rightChildIndex <arraySize && array[rightChildIndex].getScore() < array[smallest].getScore() ) {
			smallest=rightChildIndex;
		} 

		// If it isn't the smallest, swap them
		if(smallest != currentNode) {
			int swapItem = array[currentNode].getScore();
			array[currentNode].setScore(array[smallest].getScore());
			array[smallest].setScore( swapItem);
			heapify(array, arraySize, smallest); // recursively calls the heapify method
		}

	}

	/**
	 * Heap Sort Method below. Sorts from highest to lowest score by calling the heapify method
	 */	
	public void heapSort() {		
		int n = this.size; // Initializing variable to the current size

		// Sorts the list by score
		for(int i =n/2-1; i >=0; i--) {
			heapify(this.list, n, i);
		}
		for(int i = n-1; i >=0; i--) {
			LeaderboardRecord temp = this.list[0];
			this.list[0] = this.list[i];
			this.list[i] = temp;
			heapify(this.list,i,0);
		}
	}

	/*
	 * Testing method to display the list
	 */
	public void displayList() {
		int n = this.size;
		for(int i = 0; i < n; i++) {
			System.out.println(this.list[i].getScore() + "= index "+i);
		}
	}

	/**
	 * toString method to return the list
	 */
	@Override
	public String toString() {
		String theList = "";
		for(int i=0; i <size; i++) {
			theList = theList + "Record " + i + ": " + list[i].toString() + "\n";
		}
		return theList;
	} 

	public void displayArray() {
		int n = this.size;
		for(int i = 0; i < n; i++) {
			System.out.println(this.list[i].getScore() + "");
		}
	}

	/**
	 * @return the list
	 */
	public LeaderboardRecord[] getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(LeaderboardRecord[] list) {
		this.list = list;
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Create a leaderboard list object for testing
		LeaderboardList Llist = new LeaderboardList();

		//infinite while loop for methods
		while(true){
			char command = JOptionPane.showInputDialog(null, "i - insert \n" + "d - delete \n" + 
					"c - change \n" + "b- binary search \n" + "h - heap sort \n" 
					+ "p - print \n" + "q - quit", "i").charAt(0);

			//when user wants to quit using class
			if (command == 'q') {
				break;
			}

			//if user doesn't choose 'q'
			switch (command) {

			//if user wants to insert record into list
			case 'i': {
				String record = JOptionPane.showInputDialog(null, "Please enter Name, Rank, Score, "
						+ "Time taken, and Difficulty: ", "Aleeza/4/2159/118/h");
				//create a record
				LeaderboardRecord leaderInfo = new LeaderboardRecord();
				leaderInfo.processRecords(record);
				if(Llist.insert(leaderInfo)) {
					JOptionPane.showMessageDialog(null, "Insert successful.");
				} 
				else {
					JOptionPane.showMessageDialog(null, "Insert failed.");
				}
				break;
			}

			//if user wants to remove record from list
			case 'd': {
				String record = JOptionPane.showInputDialog(null, "Please enter Name, Rank, Score, "
						+ "Time taken, and Difficulty: ", "Aleeza/4/2159/118/h");
				//remove a record
				LeaderboardRecord leaderInfo = new LeaderboardRecord();
				leaderInfo.processRecords(record);


				if(Llist.delete(leaderInfo)) {
					JOptionPane.showMessageDialog(null, "Delete successful.");
				}
				else {
					JOptionPane.showMessageDialog(null, "Delete failed.");
					break;
				}
			}

			//if user wants to change records in list
			case 'c': {
				//remove old record
				String oldR = JOptionPane.showInputDialog(null, "Please enter Name, Rank, Score, "
						+ "Time taken, and Difficulty: ", "Aleeza/4/2159/118/h");
				LeaderboardRecord oldInfo = new LeaderboardRecord();
				oldInfo.processRecords(oldR);

				//add new record
				String newR = JOptionPane.showInputDialog(null, "Please enter Name, Rank, Score, "
						+ "Time taken, and Difficulty: ", "Aleeza/4/2159/118/h");
				LeaderboardRecord newInfo = new LeaderboardRecord();
				newInfo.processRecords(newR);

				if(Llist.change(oldInfo, newInfo)) {
					JOptionPane.showMessageDialog(null, "Change successful.");
				}
				else {
					JOptionPane.showMessageDialog(null, "Change failed.");
					break;
				}
			}

			//if user wants to search list
			case 'b': {
				String nametoFind = JOptionPane.showInputDialog(null, "Please enter a name: ");
				int location = Llist.binarySearch(nametoFind);

				if(location >= 0) {
					//found it
					JOptionPane.showMessageDialog(null, Llist.getList()[location].getName()
							+ " found.");
				}

				else {
					//not found
					JOptionPane.showMessageDialog(null, Llist.getList()[location].getName()
							+ " not found.");
				}
				break;
			}

			//if user wants to sort their list
			case 'h': {
				Llist.displayArray();
				System.out.println("\n normalheap");
				Llist.heapSort();
				Llist.displayArray();
			}

			//if user wants to print list
			case 'p': {
				JOptionPane.showMessageDialog(null, Llist.toString());
			}

			default: {
				break;				
			}
			}
		}
	}
}	