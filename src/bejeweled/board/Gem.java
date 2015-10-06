package bejeweled.board;
import bejeweled.game.GameScene;
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
	private static Image overlay; //The overlay image
	private static Image hintedoverlay;
	private boolean selected = false;
	private boolean hinted = false;
	private int offsetx;
	private int offsety;
	
	/**
	 * @param row - Row index of the gem.
	 * @param col - Column index of the gem.
	 * @param type - Type of the gem.
	 * Constructor
	 */
	public Gem(int row, int col, GemType type) {
		this.row = row;
		this.col = col;
		offsetx = GameScene.getOffsetx();
		offsety = GameScene.getOffsety();
		this.type = type;
	}	

	/**
	 * @param type - Type to change the gem to.
	 */
	public final void setType(GemType type) {
		this.type = type;
	}
	
	/**
	 * @param gc - The graphicscontext.
	 * 	this method draws a gem, should be called from the paintcomponent
	 */
	final void draw(final GraphicsContext gc) {
		gc.drawImage(GemType.getImage(type), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		if (selected) {
			gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		} else if(hinted){
			gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
		}
	}

	private static Image getOverlayImage(){
		if(overlay == null)
			initImages();
		return overlay;
	}
	
	private static Image getHintedImage(){
		if(hintedoverlay == null)
			initImages();
		return hintedoverlay;
	}
	
	private static void initImages(){
		overlay = new Image("Images/overlay.png");
		hintedoverlay = new Image("Images/hint.png");
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
