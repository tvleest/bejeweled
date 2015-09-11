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
	private int offsetx;
	private int offsety;
	private boolean loadImage;
	
	/**
	 * @param row - Row index of the gem.
	 * @param col - Column index of the gem.
	 * @param type - Type of the gem.
	 * Constructor
	 */
	public Gem(int row, int col, int offsetx, int offsety, int type, boolean loadImages){
		this.row = row;
		this.col = col;
		this.offsetx = offsetx;
		this.offsety = offsety;
		this.type = type;
		this.loadImage = loadImages;
		if (loadImages) {
			loadImage();
			overlay = new Image("Images/overlay.png");
		}
	}
	
	/**
	 * Loads the correct image according to the type of the gem.
	 * Should be called when type changes.
	 * Type 0 will return a null image, creating an empty cell.
	 */
	private void loadImage() {
		switch (type) {
		case 1:
			image = new Image("Images/blue.png");
			break;
		case 2:
			image = new Image("Images/green.png");
			break;
		case 3:
			image = new Image("Images/orange.png");
			break;
		case 4:
			image = new Image("Images/pink.png");
			break;
		case 5:
			image = new Image("Images/red.png");
			break;
		case 6:
			image = new Image("Images/yellow.png");
			break;
		default:
			image = null;
			break;
		}
    }		

	/**
	 * @param type - Type to change the gem to.
	 */
	void setType(int type) {
		this.type = type;
		if (loadImage) {
			loadImage();
		}
	}
	
	/**
	 * @param gc - The graphicscontext.
	 * 	this method draws a gem, should be called from the paintcomponent
	 */
	void draw(GraphicsContext gc) {
		gc.drawImage(image, offsetx + col*dimension, offsety + row*dimension, dimension, dimension);
		if (selected) {
			gc.drawImage(overlay, offsetx + col*dimension, offsety + row*dimension, dimension, dimension);
		}
	}

	/**
	 * Deletes a gem by changing the type to 0.
	 */
	public void delete() {
		setType(0);
	}

	/**
	 * @param row - The row index to set this gem to.
	 * @param col - The column index to set this gem to.
	 * Sets the gem to a new position
	 * 
	 */
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * @return - true if the gem is currently selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected - boolean to select or deselect the gem.
	 * Set the selected variable
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	/**
	 * @return - Row of gem
	 */
	
	public int getRow() {
		return this.row;
	}
	
	/**
	 * @return - Column of gem
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * @return - Type of gem.
	 */
	public int getType() {
		return this.type;
	}
	
	
}
