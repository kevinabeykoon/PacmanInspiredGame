import javax.swing.ImageIcon;
/*
 * Author: Kevin Abeykoon
 * Date: June 2023
 * Description:
 * 				This is for a player object
 * Methods:
 * 		1. public Player(ImageIcon characterImage, int x, int y) - Default Constructor
 * 		2. public void checkAmountOfObjectsEaten() -  Checks how many orbs the player has eaten
 * 		3. public int getObjectsEaten() - Getter for objectsEaten
 *		4. public void setObjectsEaten(int objectsEaten) - Setter for objectsEaten
 * 		5. public int getBonusSpeed() - Getter for bonus speed
 * 		6. public void setBonusSpeed(int bonusSpeed) -  Setter for bonus speed
 * 		7. public boolean isBonusActive()  - Getter for isBonusActive
 * 		8. public void setBonusActive(boolean bonusActive) - Setter for isBonusActive
 */
public class Player extends Character{

	// Declaring attributes
	int objectsEaten;
	int bonusSpeed;
	boolean bonusActive;
	ImageIcon currentRainbowCharacterImage, originalCharacterImage;
	ImageIcon rainbowCharacterImage1, rainbowCharacterImage2,rainbowCharacterImage3;

	/*
	 * Default Constructor
	 */
	public Player(ImageIcon characterImage, int x, int y) {
		super(characterImage, x, y);
		objectsEaten =0;
		bonusSpeed = 0;
		bonusActive = false;
		if(characterImage.toString() == "Redson.png") {
			currentRainbowCharacterImage = new ImageIcon("RedsonRainbow.gif");
		}
		else if(characterImage.toString() == "Mei.png") {
			currentRainbowCharacterImage = new ImageIcon("MeiRainbow.gif");
		}
		else if(characterImage.toString() == "Mk.png") {
			currentRainbowCharacterImage = new ImageIcon("MKRainbow.gif");
		}

		originalCharacterImage = characterImage;
	}

	/*
	 * Checks how many orbs the player has eaten
	 */
	public void checkAmountOfObjectsEaten() {

		if(!bonusActive) {
			// If they've eaten at least 50, activate fast mode
			if(objectsEaten >= 50) {
				this.setSpeed(bonusSpeed+this.getSpeed());
				bonusActive = true;
				setImage(currentRainbowCharacterImage);
				objectsEaten = 0;
			}
		}
		else {
			// If they've eaten at least 30, while fast mode active
			// deactivate fast mode
			if(objectsEaten >= 30) {
				this.setSpeed(this.getSpeed()- bonusSpeed);
				bonusActive = false;
				setImage(originalCharacterImage);
				objectsEaten = 0;
			}
		}
	}


	/*
	 * Getter for objectsEaten
	 */
	public int getObjectsEaten() {
		return objectsEaten;
	}

	/*
	 * Setter for objectsEaten
	 */
	public void setObjectsEaten(int objectsEaten) {
		this.objectsEaten = objectsEaten;
	}

	/*
	 * Getter for bonus speed
	 */
	public int getBonusSpeed() {
		return bonusSpeed;
	}

	/*
	 * Setter for bonus speed
	 */
	public void setBonusSpeed(int bonusSpeed) {
		this.bonusSpeed = bonusSpeed;
	}

	/*
	 * Getter for isBonusActive
	 */
	public boolean isBonusActive() {
		return bonusActive;
	}

	/*
	 * Setter for isBonusActive
	 */
	public void setBonusActive(boolean bonusActive) {
		this.bonusActive = bonusActive;
	}
	
	/*
	 * Self testing main method
	 */
	public static void main(String[] args) {
		Player p = new Player(new ImageIcon("Mei.png"), 0, 0);
		p.setObjectsEaten(100);
		p.setBonusSpeed(10);
		p.setBonusActive(true);
		
		System.out.println(p.getObjectsEaten());
		System.out.println(p.getBonusSpeed());
		System.out.println(p.isBonusActive());
		
	}
}
