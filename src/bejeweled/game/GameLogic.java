package bejeweled.game;

import javafx.scene.canvas.GraphicsContext;

import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.state.HighScores;
import bejeweled.state.Logger;
import bejeweled.state.Time;

/**
 * @author Timo
 *
 */
public final class GameLogic {

	private Board board;
	private static Time time;
	private HighScores highscores;

	/**
	 * @param offsetx
	 *            the offset on the x-axis
	 * @param offsety
	 *            the offset on the y-axis
	 */
	public GameLogic(boolean i) {
		time = new Time(60);
		board = new Board(8, i);
		highscores = new HighScores();
	}

	/**
	 * @return - The board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param gc
	 *            - the GraphicsContext.
	 */
	public void draw(final GraphicsContext gc) {
		board.draw(gc);
		drawScore(gc);
		drawHighscores(gc);
	}

	/**
	 * @param row
	 *            - the row index.
	 * @param col
	 *            - the col index.
	 */
	public void handleMouseClicked(final int row, final int col) {
		if (board.getHintedgem() != null) {
			board.getHintedgem().setHinted(false);
		}
		Logger.getInstance().writeLineToLogger("Mouse clicked on row " + row + " and col " + col);
		Sounds.getInstance().playSelectSound();
		if (board.getSelectedgem() == null) {
			board.setSelectedgem(board.getGems()[row][col]);
			board.getGems()[row][col].setSelected(true);
		} else {
			board.setSecondGem(board.getGems()[row][col]);
			int firstgemrow = board.getSelectedgem().getRow();
			int firstgemcol = board.getSelectedgem().getCol();
			if (board.swap(firstgemrow, firstgemcol, row, col)) {
				int first = board.deleteRows(board.getSelectedgem());
				int second = board.deleteRows(board.getSecondGem());
				if (first + second > 0) {
					Logger.getInstance()
							.writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + ","
									+ board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + ","
									+ board.getSecondGem().getRow() + ") are switched. This switch was succesfull.");
					for (int i = 0; i < first + second; i++) {
						time.updateTime();
						Sounds.getInstance().playCombinationSound();
					}
				} else { // if there are no combinations found after the move
					Logger.getInstance()
							.writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + ","
									+ board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + ","
									+ board.getSecondGem().getRow() + ") are switched. This switch was unsuccesfull.");
					// switches the two switched gems back
					board.swap(firstgemrow, firstgemcol, row, col);
					// play error sound
					Sounds.getInstance().playErrorSound();
				}
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
		gc.fillText(s, 60, 190);
	}

	/**
	 * @return - Time object in use.
	 */
	public Time getTime() {
		return time;
	}

	public void setTime(Time t) {
		time = t;
	}

	/**
	 * @param gc
	 *            GraphicsContext Draws the highscore next to the board
	 */
	public void drawHighscores(final GraphicsContext gc) {
		String hs = "Highscores:\n";
		for (int score : highscores.getAllScores()) {
			hs += score + "\n";
		}
		gc.fillText(hs, 60, 210);
	}

	public HighScores getHighScores() {
		return highscores;
	}
}
