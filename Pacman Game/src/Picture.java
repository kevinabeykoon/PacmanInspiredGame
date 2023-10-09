import javax.swing.*;
import java.awt.*;
/**
 * @(#)Picture.java
 * @Campos
 * @version 2.00 2017/7/30
 * @Description: Draws a picture on a JFrame.
 */
public class Picture extends JComponent {

	// private data for colour, location and size
	private Color c;
	private int xPos, yPos, width, height;

	// default constructor
	public Picture() {        
		this.c = Color.RED;
		this.xPos = 0;
		this.yPos = 0;
		this.width = 50;
		this.height = 25;
		repaint();
	}

	// constructor for specified location
	public Picture (int x, int y, int w, int h)		
	{
		this.c = Color.RED;
		this.xPos = x;
		this.yPos = y;
		this.width = w;
		this.height = h;
		repaint();
	}

	// setter and getter methods for color
	public void setColor (Color col)
	{
		this.c = col;
		repaint();
	}

	public void setColor (int r, int g, int b)
	{
		this.c = new Color (r,g,b);
		repaint();
	}

	public Color getColor() {
		return c;
	}

	// setter and getter methods for location
	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
		repaint();
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
		repaint();
	} 

	public int getMyWidth() {
		return width;
	}
	
	public void setMyWidth(int w) {
		this.width = w;
		repaint();
	}
	
	public int getMyHeight() {
		return height;
	}

	public void setMyHeight(int h) {
		this.height = h;
		repaint();
	}

	// paint method
	public void paint (Graphics g)  
	{   
		g.setColor(this.c);  // set the new colour
		g.drawRect(xPos,yPos,width,height);  // draw shape
		g.drawOval(xPos,yPos,width,height);  // draw shape
		g.fillOval(xPos+width/3,yPos,width/3,height);  // draw shape
	}

	// self testing main method   
	public static void main (String args[]){

		JFrame f = new JFrame("Testing");
		Picture p = new Picture();

		f.setSize(400,350);  // size for graphics
		f.add(p);
		f.setVisible(true);

		JOptionPane.showMessageDialog(null,"Wait");
		p.setColor(Color.BLUE);

		JOptionPane.showMessageDialog(null,"Wait");
		p.setColor(50,50,120);  
		JOptionPane.showMessageDialog(null,"Wait");

		Picture p1 = new Picture(200, 50, 100, 50);
		f.remove(p);

		f.add(p1);
		f.setVisible(true);  // repaint fame

		JOptionPane.showMessageDialog(null,"Wait");
		p1.setxPos(10);
		p1.setMyHeight(p1.getMyHeight()/2);
		p1.setMyWidth(p1.getMyWidth()*2);

		p1.repaint();  // repaint picture
	}
}