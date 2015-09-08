import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author Timo
 * Gem class
 */
public class Gem {

	private int dimension = 40; //the dimension (width and height) of the gems on the board
	private int row;
	private int col;
	private int type; //which type of six gems
	private Image image; //The image of the gem
	private Image overlay; //The overlay image
	private boolean selected = false;
	
	/**
	 * @param row
	 * @param col
	 * @param type
	 * Constructor
	 */
	public Gem(int row, int col, int type){
		this.row = row;
		this.col = col;
		this.type = type;
		loadImage();
		ImageIcon overlayicon = new ImageIcon("overlay.png");
		overlay = overlayicon.getImage();
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public int getType(){
		return this.type;
	}
	
	/**
	 * Loads the correct image according to the type of the gem
	 * Should be called when type changes
	 * Type 0 will return a null image, creating an empty cell
	 */
	private void loadImage() {
        ImageIcon ii = null;

		switch (type) {
		case 1:
			ii = new ImageIcon("blue.png");
			break;
		case 2:
			ii = new ImageIcon("green.png");
			break;
		case 3:
			ii = new ImageIcon("orange.png");
			break;
		case 4:
			ii = new ImageIcon("pink.png");
			break;
		case 5:
			ii = new ImageIcon("red.png");
			break;
		case 6:
			ii = new ImageIcon("yellow.png");
			break;
		default:
			break;
		}
		if(ii==null)
			this.image = null; //creates an empty cell
		else
			this.image = ii.getImage();
    }	

	/**
	 * @param type
	 */
	void setType(int type){
		this.type = type;
		loadImage();
	}
	
	/**
	 * @param g
	 * 	this method draws a gem, should be called from the paintcomponent
	 */
	void draw(Graphics g){
		g.drawImage(image, 235 + col*dimension, 115 + row*dimension, dimension, dimension, null);
		if (selected)
			g.drawImage(overlay, 235 + col*dimension, 115 + row*dimension, dimension, dimension, null);
	}

	/**
	 * Deletes a gem by changing the type to 0
	 */
	public void delete() {
		setType(0);
	}

	/**
	 * @param row
	 * @param col
	 * Sets the gem to a new position
	 * 
	 */
	public void setPosition(int row, int col) {
		// TODO Auto-generated method stub
		this.row = row;
		this.col = col;
	}

	/**
	 * @return true if the gem is currently selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 * Set the selected variable
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
