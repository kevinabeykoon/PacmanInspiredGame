import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * Author: Kevin
 * Date: March  2023
 * Description: Template for a simple TextPicture component
 */
public class TextPicture extends Picture{

	/*
	 * Declaring private data
	 */
	private String text;
	private Font font;

	/*
	 * Constructor
	 */
	public TextPicture(String text, int x, int y) {
		super();
		this.setFont(new Font("Georgia", Font.ITALIC +Font.BOLD, 50)); // Setting font
		this.setText(text); //Setting text
		setyPos(y);
		setxPos(x);
	}

	/*
	 * Override the paint method
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor (this.getColor()); // Sets the text colour
		g.setFont(this.font); // Sets the text font
		g.drawString(this.text, getxPos(), getyPos()); // draws the text
	}

	/**
	 * @param text the title to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	public static void main(String[] args) {
	
		//self test main method
		JFrame f =new JFrame("Just for testing");
		TextPicture p = new TextPicture("My title", 45,45); // create a picture object
		f.setSize(400,600); // set size to frame
		f.add(p); //add the object to frame
		f.setVisible(true); //set visible
		
		JOptionPane.showMessageDialog(null,"Wait"); //wait command
		p.setFont(new Font("Serif", Font.ITALIC, 54)); // testing setFont
		
		p.setColor(Color.magenta); // testing setColor
		p.repaint();  // repaint picture
		
		JOptionPane.showMessageDialog(null,"Wait"); //wait command
		p.setColor(50,50,120); // testing setColor
		p.repaint();  // repaint picture
		
		JOptionPane.showMessageDialog(null,"Wait"); //wait command
		p.setxPos(100); // testing setxPos
		p.setyPos(100); // testing setyPos
		p.setText("New Title"); //testing setText
		p.repaint();  // repaint picture
		
	}

}
