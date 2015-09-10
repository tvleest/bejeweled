import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Timo
 *
 */
public final class GameLogic {

	/**
	 * 
	 */
	private Board board;
	/**
	 * 
	 */
	private int time;
	/**
	 * 
	 */
	private final int timePerGem = 5;


	/**
	 * @param offsetx the offset on the x-axis
	 * @param offsety the offset on the y-axis
	 */
	public GameLogic(final int offsetx, final int offsety) {
		time = 90;
		board = new Board(8, offsetx, offsety, true);
	}

	/**
	 * @return the board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param gc the graphicscontext.
	 */
	public void draw(final GraphicsContext gc){
		board.draw(gc);
		drawScore(gc);
		drawTime(gc);
	}

	/**
	 * @param row the row.
	 * @param col the col.
	 */
	public void handleMouseClicked(final int row, final int col) {
		// TODO:Game.GameSounds.playAudio("select");
		if (board.getSelectedgem() == null) {
			board.setSelectedgem(board.getGems()[row][col]);
			board.getGems()[row][col].setSelected(true);
		} else {
			board.setSecondGem(board.getGems()[row][col]);
			int firstgemrow = board.getSelectedgem().getRow();
			int firstgemcol = board.getSelectedgem().getCol();
			if (board.swap(firstgemrow, firstgemcol, row, col)) {
				boolean first = board.deleteRows(board.getSelectedgem());
				boolean second = board.deleteRows(board.getSecondGem());
				if (first) {
					updateTime();
				}
				// if there are no combinations found after the move
				if (first == false && second == false) {
					// switches the two switched gems back

					board.swap(firstgemrow, firstgemcol, row, col);
					// TODO: error sound
				}
			} else {
				// TODO: error sound;
			}
			board.getSelectedgem().setSelected(false);
			board.setSelectedgem(null);
			board.setSecondGem(null);
		}
	}

	/**
	 * @param gc
	 *            GraphicsContext to draw to
	 */
	public void drawScore(final GraphicsContext gc) {
		String s = "Score: ";
		s += board.getScore();
		gc.fillText(s, 60, 80);
	}

	/**
	 * update the time.
	 */
	public void updateTime() {
		time += timePerGem;
	}
	
	public void decrementTime() {
		time--;
	}

	/**
	 * @return the current time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param gc
	 *            GraphicsContext to draw to Draws the time to the scene
	 */
	public void drawTime(final GraphicsContext gc) {
        
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if (seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		gc.fillText(s, 60, 60);
	}
}
