package bejeweled.board;

import javafx.scene.canvas.GraphicsContext;

public class DoublePointsGem extends Gem {
	
	public DoublePointsGem(int row, int col, GemType type) {
		super(row,col,type);
	}
	
	@Override
	public void draw(final GraphicsContext gc) {
		if (moving) {
			gc.drawImage(GemType.getDoublePointsGemImage(type), animationx, animationy, dimension, dimension);
		} else {
			gc.drawImage(GemType.getDoublePointsGemImage(type), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
			if (selected) {
				gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
			} else if (hinted) {
				gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension,
						dimension);
			}
		}
	}
	
	/**
	 * Returns the factor to multiply the points with.
	 */
	@Override
	public int timesPoints() {
		return 2;
	}
}
