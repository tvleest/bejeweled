package bejeweled.board;

import javafx.scene.canvas.GraphicsContext;

public class DoublePointsGem extends Gem {
	
	public DoublePointsGem(int row, int col, GemType type) {
		super(row,col,type);
	}
	
	@Override
	public void draw(final GraphicsContext gc) {
		if (moving) {
			gc.drawImage(GemType.getDoublePointsGemImage(type), animationx, animationy);
		} else {
			gc.drawImage(GemType.getDoublePointsGemImage(type), offsetx + col * dimension, offsety + row * dimension);
			if (selected) {
				gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension);
			} else if (hinted) {
				gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension,
						dimension);
			}
		}
	}
	
	@Override
	public int timesPoints() {
		return 2;
	}
}
