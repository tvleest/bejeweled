import java.awt.Graphics;

import javax.swing.ImageIcon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
		overlay = new Image("overlay.png");
	}
	
	
	
	/**
	 * Loads the correct image according to the type of the gem
	 * Should be called when type changes
	 * Type 0 will return a null image, creating an empty cell
	 */
	private void loadImage() {
		switch (type) {
		case 1:
			image = new Image("blue.png");
			break;
		case 2:
			image = new Image("green.png");
			break;
		case 3:
			image = new Image("orange.png");
			break;
		case 4:
			image = new Image("pink.png");
			break;
		case 5:
			image = new Image("red.png");
			break;
		case 6:
			image = new Image("yellow.png");
			break;
		default:
			image=null;
			break;
		}
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
	void draw(GraphicsContext gc){
		gc.drawImage(image, 235 + col*dimension, 115 + row*dimension, dimension, dimension);
		if (selected)
			gc.drawImage(overlay, 235 + col*dimension, 115 + row*dimension, dimension, dimension);
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
	
	/**
	 * @return row of gem
	 */
	
	public int getRow(){
		return this.row;
	}
	
	/**
	 * @return colom of gem
	 */
	public int getCol(){
		return this.col;
	}
	
	/**
	 * @return type of gem
	 */
	public int getType(){
		return this.type;
	}
	
	
}
