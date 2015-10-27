package bejeweled.board;
import java.util.ArrayList;

import bejeweled.game.GameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Timo
 * Gem class
 */
public class Gem implements DoublePoints, Cross {

	protected static int dimension = 40; //the dimension (width and height) of the gems on the board
	protected int row;
	protected int col;
	protected GemType type; //which type of six gems
	private static Image overlay; //The overlay image
	private static Image hintedoverlay;
	protected boolean selected = false;
	protected boolean hinted = false;
	protected boolean moving = false;
	protected int offsetx;
	protected int offsety;
	protected int animationx = 0;
	protected int animationy = 0;
	
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
		animationy=offsety-dimension+1;
	}	

	/**
	 * @param type - Type to change the gem to.
	 */
	public void setType(GemType type) {
		this.type = type;
	}
	
	public int timesPoints() {
		return 1;
	}
	
	public Combination makeCross(Board b, Iterator combinationIterator) {
		return null;
	}
	
	/**
	 * @param gc - The graphicscontext.
	 * 	this method draws a gem, should be called from the paintcomponent
	 */
	public void draw(final GraphicsContext gc) {
		if (moving) {
			gc.drawImage(GemType.getImage(type), animationx, animationy);
		} else {
			gc.drawImage(GemType.getImage(type), offsetx + col * dimension, offsety + row * dimension);
			if (selected) {
				gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension);
			} else if (hinted) {
				gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension,
						dimension);
			}
		}
	}

	protected static Image getOverlayImage(){
		if(overlay == null){
			initImages();
		}
		return overlay;
	}
	
	protected static Image getHintedImage(){
		if(hintedoverlay == null){
			initImages();
		}
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
	
	public boolean isHinted() {
		return hinted;
	}

	/**
	 * @param selected - boolean to select or deselect the gem.
	 * Set the selected variable
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setHinted(boolean hinted) {
		this.hinted = hinted;
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
	public GemType getType() {
		return this.type;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getAnimationx() {
		return animationx;
	}

	public void setAnimationx(int animationx) {
		this.animationx = animationx;
	}

	public int getAnimationy() {
		return animationy;
	}

	public void setAnimationy(int animationy) {
		this.animationy = animationy;
	}

	public static int getDimension() {
		return dimension;
	}
	
	public int getCurrentx(){
		return col*dimension+offsetx;
	}
	
	public int getCurrenty(){
		return row*dimension+offsety;
	}
	
	public void setCurrentPositionsAsAnimationPositions(){
		setAnimationx(getCurrentx());
		setAnimationy(getCurrenty());
	}

	@Override
	public Combination makeCross(Board b, ArrayList<Combination> c) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTypeString() {
		if(type == GemType.BLUE) {
			return "BLUE";
		}
		else if(type == GemType.GREEN) {
			return "GREEN";
		}
		else if(type == GemType.ORANGE) {
			return "ORANGE";
		}
		else if(type == GemType.PINK) {
			return "PINK";
		}
		else if(type == GemType.YELLOW) {
			return "YELLOW";
		}
		else if(type == GemType.RED) {
			return "RED";
		}
		else {
			return "WHITE";
		}
	}
}
