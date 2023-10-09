import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 *  Class to draw image icons
 *  Inherits from Picture
 */
public class ImagePicture extends Picture {
	 
	private ImageIcon image;  //private data for image
	
	// constructor with image only
	public ImagePicture(ImageIcon img) { 
		super();
		setxPos(0);
		setyPos(0);
		this.image = img;
		setMyWidth(img.getIconWidth());
		setMyWidth(img.getIconHeight());
		repaint();
	}
	
	// constructor for specifying location
	public ImagePicture(ImageIcon img, int x, int y) {  
		super();
		setxPos(x);
		setyPos(y); 
		this.image = img;
		this.image = img;
		setMyWidth(img.getIconWidth());
		setMyWidth(img.getIconHeight());
		repaint();
	} 
	
	// methods to get the width and height of the Icon
	public int getMyWidth() {
		return this.image.getIconWidth();
	}
	
	public int getMyHeight() {
		return this.image.getIconHeight();
	}
	
	// paint method
	public void paint (Graphics g) {
		 this.image.paintIcon (this, g, getxPos(), getyPos());
	}

	public static void main(String args[]) {
		// self testing main method;            
		JFrame f = new JFrame("Testing");
		ImagePicture p = new ImagePicture(new ImageIcon("minion.png"));
		
		f.setSize(400,350);  // size for graphics
		f.add(p);
		f.setVisible(true);
		
		JOptionPane.showMessageDialog(null,"Wait");
		//f.remove(p);
	
		ImagePicture p1 = new ImagePicture(new ImageIcon("minion.png"), 100, 100);
		f.add(p1);
		
		f.setVisible(true);  // repaint picture
		
		JOptionPane.showMessageDialog(null,"Wait");
		p1.setxPos(0);
		
		//p1.repaint();  // repaint picture
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
