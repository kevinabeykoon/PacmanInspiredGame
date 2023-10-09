import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * Author: Kevin Abeykoon
 * Date: June 2023
 * Description:
 * 				This class is used for both enemy and player
 * 				objects, with player using a child class of 
 * 				this class. It gives the objects the information
 * 				they need to move around 
 * Methods:
 * 		1. public int getSpeed() -> Getter method to get speed
 * 		2. public void setSpeed(int speed) ->Setter method to set speed
 * 		3. public int getMovingDirectionX() -> Setter method to set MovingDirectionX
 *		4. public void setMovingDirectionY(int movingDirectionY) -> Setter method to set MovingDirectionY
 * 		5. public Character(ImageIcon characterImage, int x, int y) -> Constructor to specify position.
 * 		6. public Character(ImageIcon characterImage)  -> Default Constructor 
 * 		7. public static void main(String args[]) - > Main method for self testing
 */
public class Character extends ImagePicture {
	 
	//Declaring attributes
	private int speed, maximumSpeed;
	private int movingDirectionX, movingDirectionY;
	

	/*
	 * Getter method to get speed
	 */
	public int getSpeed() {
		return speed;
	}

	/*
	 * Setter method to set speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/*
	 * Getter method to get MovingDirectionX
	 */
	public int getMovingDirectionX() {
		return movingDirectionX;
	}

	/*
	 * Setter method to set MovingDirectionX
	 */
	public void setMovingDirectionX(int movingDirectionX) {
		this.movingDirectionX = movingDirectionX;
	}

	/*
	 * Getter method to get MovingDirectionY
	 */
	public int getMovingDirectionY() {
		return movingDirectionY;
	}

	/*
	 * Setter method to set MovingDirectionY
	 */
	public void setMovingDirectionY(int movingDirectionY) {
		this.movingDirectionY = movingDirectionY;
	}

	/*
	 * Constructor to specify position.
	 * This constructor initializes all attributes
	 */
	public Character(ImageIcon characterImage, int x, int y) {  
		super(characterImage);
		setxPos(x);
		setyPos(y); 
		setMyWidth(characterImage.getIconWidth());
		setMyHeight(characterImage.getIconHeight());
		speed = 1;
		movingDirectionX = 1;
		movingDirectionY = 1;
		maximumSpeed=0;
		repaint();
	} 
	
	/*
	 * Default Constructor 
	 * This constructor initializes most attributes
	 */
	public Character(ImageIcon characterImage) {  
		super(characterImage);
		setMyWidth(characterImage.getIconWidth());
		setMyHeight(characterImage.getIconHeight());
		speed = 1;
		movingDirectionX = 1;
		movingDirectionY = 1;
		maximumSpeed=0;
		repaint();
	} 
	
	/*
	 * Main method for self testing
	 */
	public static void main(String args[]) {
		// self testing main method;            
		JFrame f = new JFrame("Testing");
		Character p = new Character(new ImageIcon("Redson.png"), 300, 250);
		f.setSize(400,350);  // size for graphics
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		JOptionPane.showMessageDialog(null,"Wait");
		p.setxPos(30);
		p.repaint();  // repaint picture
		JOptionPane.showMessageDialog(null,"Wait");
		p.setyPos(30);
		p.repaint();  // repaint picture
		JOptionPane.showMessageDialog(null,"Wait");
		
		p.setImage(new ImageIcon("Mk.png"));
		p.repaint();
		
		System.out.println("character image = " + p.getImage().toString() );
		
		p.setMovingDirectionX(1);
		p.setMovingDirectionY(-1);
		System.out.println("Moving Dir X = " + p.getMovingDirectionX() );
		System.out.println("Moving Dir Y = " + p.getMovingDirectionY() );
		
		p.setSpeed(100);
		System.out.println("Speed " + p.getSpeed());
		
	}
}
