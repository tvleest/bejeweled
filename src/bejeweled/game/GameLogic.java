package bejeweled.game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

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
	private boolean isanimating = false;
	private Board board;
	private static Time time;
	private static Score score;
	private HighScores highscores;
	private AnimationHandler animationhandler;
	private int firstgemrow;
	private int firstgemcol;
	private int secondgemrow;
	private int secondgemcol;
	private boolean combinationsFormed = false;
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
		if(isanimating)
			return;
		secondgemrow = row;
		secondgemcol = col;
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
			firstgemrow = board.getSelectedgem().getRow();
			firstgemcol = board.getSelectedgem().getCol();
			if (board.swap(board.getSelectedgem(), board.getSecondGem())) {
				//swap animation
				isanimating = true;
				animationhandler.animate();
				//swap animation
			}
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

	public AnimationHandler getAnimationhandler() {
		return animationhandler;
	}

	public void returnFromAnimation() {
		System.out.println("returned from animation");
		if(isanimating)
			checkForCombinations();
	}

	private void checkForCombinations() {
		ArrayList<Gem> combinations = board.checkForCombinations();
		
		if(combinations.size()>0){
			combinationsFormed = true;
			Sounds.getInstance().playCombinationSound();
			score.updateScore(combinations.size());
			time.updateTime(combinations.size());
			board.delete(combinations);
			animationhandler.animate();
		}
		else{
			isanimating=false;
			if (!combinationsFormed){
				swapBack();
			}
			board.getSelectedgem().setSelected(false);
			board.setSelectedgem(null);
			board.setSecondGem(null);
			combinationsFormed = false;
		}
	}
	
	private void swapBack(){
		Logger.getInstance()
		.writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + ","
				+ board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + ","
				+ board.getSecondGem().getRow() + ") are switched. This switch was unsuccesfull.");
		// switches the two switched gems back
		board.swap(board.getSelectedgem(), board.getSecondGem());
		animationhandler.animate();
		// play error sound
		Sounds.getInstance().playErrorSound();
	}

}
	
	

