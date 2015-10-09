package bejeweled.board;

import javafx.scene.canvas.GraphicsContext;

public class DeleteRowGem extends Gem {
	
	public DeleteRowGem(int row, int col, GemType type) {
		super(row,col,type);
	}
	
	public void draw(final GraphicsContext gc) {
		if (moving) {
			gc.drawImage(GemType.getImage(type, 2), animationx, animationy);
		} else {
			gc.drawImage(GemType.getImage(type, 2), offsetx + col * dimension, offsety + row * dimension);
			if (selected) {
				gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension);
			} else if (hinted) {
				gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension,
						dimension);
			}
		}
	}

}
