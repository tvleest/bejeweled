package bejeweled.game;

import javafx.scene.canvas.GraphicsContext;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.state.HighScores;
import bejeweled.state.Logger;
import bejeweled.state.Score;
import bejeweled.state.Time;

/**
 * @author Timo
 *
 */
public final class GameLogic {

	private Board board;
	private static Time time;
	private static Score score;
	private HighScores highscores;
	private AnimationHandler animationhandler;
	private boolean animating = false;

	/**
	 * @param offsetx
	 *            the offset on the x-axis
	 * @param offsety
	 *            the offset on the y-axis
	 */
	public GameLogic() {
		time = new Time(60);
		score = new Score(0);
		board = new Board(8);
		highscores = new HighScores();
		animationhandler = new AnimationHandler(this);
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
		
		//no mouseclicks handled during animations
		if(animating) 
			return;
		
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
				//swap animation
				animating = true;
				animationhandler.setIsAnimating(false);
				//swap animation
				int first = board.deleteRows(board.getSelectedgem());
				int second = board.deleteRows(board.getSecondGem());
				score.updateScore(first+second);
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
		s += score.getScore();
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
	
	public Score getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score.setScore(s);
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public AnimationHandler getAnimationhandler() {
		return animationhandler;
	}
	
	
}
