package bejeweled.board;
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
	private GemType type; //which type of six gems
	private Image image; //The image of the gem
	private Image overlay; //The overlay image
	private boolean selected = false;
	private boolean hinted = false;
	private int offsetx;
	private int offsety;
	private boolean loadImage;
	
	/**
	 * @param row - Row index of the gem.
	 * @param col - Column index of the gem.
	 * @param type - Type of the gem.
	 * Constructor
	 */
	public Gem(int row, int col, int offsetx, int offsety, GemType type, boolean loadImages) {
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
		case BLUE:
			image = new Image("Images/blue.png");
			break;
		case GREEN:
			image = new Image("Images/green.png");
			break;
		case ORANGE:
			image = new Image("Images/orange.png");
			break;
		case PINK:
			image = new Image("Images/pink.png");
			break;
		case RED:
			image = new Image("Images/red.png");
			break;
		case YELLOW:
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
	public final void setType(GemType type) {
		this.type = type;
		if (loadImage) {
			loadImage();
		}
	}
	
	/**
	 * @param gc - The graphicscontext.
	 * 	this method draws a gem, should be called from the paintcomponent
	 */
	final void draw(final GraphicsContext gc) {
		gc.drawImage(image, offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		if (selected) {
			gc.drawImage(overlay, offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		} else if(hinted){
			gc.drawImage(overlay, offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		}
	}

	/**
	 * @param row - The row index to set this gem to.
	 * @param col - The column index to set this gem to.
	 * Sets the gem to a new position
	 * 
	 */
	public final void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * @return - true if the gem is currently selected
	 */
	public final boolean isSelected() {
		return selected;
	}
	
	public final boolean isHinted() {
		return hinted;
	}

	/**
	 * @param selected - boolean to select or deselect the gem.
	 * Set the selected variable
	 */
	public final void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public final void setHinted(boolean hinted) {
		this.hinted = hinted;
	}
	
	/**
	 * @return - Row of gem
	 */
	
	public final int getRow() {
		return this.row;
	}
	
	/**
	 * @return - Column of gem
	 */
	public final int getCol() {
		return this.col;
	}
	
	/**
	 * @return - Type of gem.
	 */
	public final GemType getType() {
		return this.type;
	}
	
	
}
