package bejeweled.board;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class DeleteRowGem extends Gem {
	
	public DeleteRowGem(int row, int col, GemType type) {
		super(row,col,type);
	}
	
	@Override
	public void draw(final GraphicsContext gc) {
		if (moving) {
			gc.drawImage(GemType.getDeleteRowGemImage(type), animationx, animationy, dimension, dimension);
		} else {
			gc.drawImage(GemType.getDeleteRowGemImage(type), offsetx + col * dimension, offsety + row * dimension, dimension, dimension);
			if (selected) {
				gc.drawImage(getOverlayImage(), offsetx + col * dimension, offsety + row * dimension);
			} else if (hinted) {
				gc.drawImage(getHintedImage(), offsetx + col * dimension, offsety + row * dimension, dimension,
						dimension);
			}
		}
	}
	
	/**
	 * Returns a Combination of all Gems in the cross of this Gem.
	 */
	@Override
	public Combination makeCross(Board b, ArrayList<Combination> c) {
		return b.deleteRowAndCol(this, c);
	}

}
