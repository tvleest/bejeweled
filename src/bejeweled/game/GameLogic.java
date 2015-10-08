package bejeweled.game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
public final class GameLogic implements Observer{
	private boolean isanimating = false;
	private Board board;
	private static Time time;
	private static int intscore;
	private static Score score;
	private HighScores highscores;
	private AnimationHandler animationhandler;
	private boolean combinationsFormed = false;
	/**
	 * @param offsetx
	 *            the offset on the x-axis
	 * @param offsety
	 *            the offset on the y-axis
	 */
	public GameLogic() {
		time = new Time(60);
		intscore = 0;
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
		//we won't handle mouseclicks while an animation is running
		if(isanimating) {
			return;
		}
		if (board.getHintedgem() != null) {
			board.getHintedgem().setHinted(false);
		}
		Logger.getInstance().writeLineToLogger("Mouse clicked on row " + row + " and col " + col);
		Sounds.getInstance().playSelectSound();
		//select if there is already a first selectedgem
		if (board.getSelectedgem() == null) {
			board.setSelectedgem(board.getGems()[row][col]);
			board.getGems()[row][col].setSelected(true);
		//apparently this is the second gem we select
		//we should swap these two gems and handle animations and combinations
		} else {
			board.setSecondGem(board.getGems()[row][col]);
			if (board.swap(board.getSelectedgem(), board.getSecondGem())) {
				Logger.getInstance()
				.writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + ","
						+ board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + ","
						+ board.getSecondGem().getRow() + ") are swapped.");
				//swap animation
				isanimating = true;
				animationhandler.animate();
			}
			else{
				board.getSelectedgem().setSelected(false);
				board.setSelectedgem(null);
			}
		}
	}
	
	/**
	 * this method gets called when an animation ends so the code can continue.
	 */
	public void returnFromAnimation() {
		if(isanimating) {
			checkForCombinations();
		}
	}
	
	/**
	 * this method checks for combinations on the board.
	 * handles animations
	 * when animations end returnfromanimation will get called again
	 * untill there are no more combinations then the animations will end
	 */
	private void checkForCombinations() {
		ArrayList<Gem> combinations = board.checkForCombinations();
		//combinations found
		if(combinations.size()>0){
			combinationsFormed = true;
			Sounds.getInstance().playCombinationSound();
			score.updateScore(combinations.size()); //update score
			time.updateTime(combinations.size()); //update time
			board.delete(combinations); //delete all the combinations we found
			animationhandler.animate(); //animate the falling of gems
		}
		//no combinations found
		else{
			isanimating=false;
			//if no combinations are formed we swap the gems back
			if (!combinationsFormed){
				swapBack();
			}
			//return board to normal state by resetting some variables
			board.getSelectedgem().setSelected(false);
			board.setSelectedgem(null);
			board.setSecondGem(null);
			combinationsFormed = false;
		}
	}
	
	/**
	 * swaps two gems back.
	 */
	private void swapBack(){
		Logger.getInstance()
		.writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + ","
				+ board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + ","
				+ board.getSecondGem().getRow() + ") are swapped back.");
		// swap the two switched gems back
		board.swap(board.getSelectedgem(), board.getSecondGem());
		animationhandler.animate();
		// play error sound
		Sounds.getInstance().playErrorSound();
	}

	/**
	 * @param gc
	 *            GraphicsContext to draw to
	 */
	public void drawScore(final GraphicsContext gc) {
		String s = "Score: ";
		s += intscore;
		gc.fillText(s, 60, 190);
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
	
	public int getScore() {
		return intscore;
	}
	
	public Score getScore2() {
		return score;
	}
	
	public void setScore(int s) {
		intscore = s;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time t) {
		time = t;
	}
	
	public void update(Observable obs, Object arg) {
		score = (Score) obs;
		intscore = score.getScore();
	}
}
	
	

