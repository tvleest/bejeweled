package bejeweled.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bejeweled.Difficulties;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.Combination;
import bejeweled.board.CombinationIterator;
import bejeweled.board.DeleteRowGem;
import bejeweled.board.DoublePointsGem;
import bejeweled.board.EasyGameFactory;
import bejeweled.board.Gem;
import bejeweled.board.HardGameFactory;
import bejeweled.board.Iterator;
import bejeweled.board.GameFactory;
import bejeweled.board.MediumGameFactory;
import bejeweled.state.HighScores;
import bejeweled.state.Logger;
import bejeweled.state.Score;
import bejeweled.state.Time;

/**
 * @author Timo
 *
 */
public final class GameLogic{
	private boolean isanimating = false;
	private Board board;
	private static Time time;
	private static Score score;
	private HighScores highscores;
	private AnimationHandler animationhandler;
	private boolean combinationsFormed = false;
	private GameFactory gemFactory;
	private Difficulties dif;

	/**
	 * @param offsetx
	 *            the offset on the x-axis
	 * @param offsety
	 *            the offset on the y-axis
	 */
	public GameLogic(Difficulties dif) {
		this.dif = dif;
		gemFactory = dif.getFactory(); //TODO: switch this around
		time = new Time(60);
		board = new Board(8, gemFactory);
		score = gemFactory.getScoreObject();
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
		time.drawTime(gc);
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
		if(board.handleMouseClickedBoard(row,col)){
			isanimating = true;
			animationhandler.animate();
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
		ArrayList<Combination> combinations = board.checkForCombinations();
		boolean br = false;
		for (Combination b : combinations) {
			br = false;
			
			Iterator gemIterator = new CombinationIterator(b);
			while(gemIterator.hasNext()){
				Gem g = (Gem) gemIterator.next(); 
			//for (Gem g : b.getGems()) {
				Combination combi = g.makeCross(board, combinations);
				if(combi != null) {
					combinations.add(combi);
					br = true;
				}
			}
			if (br) {
				break;
			}
		}
		while(br) {
			br = false;
			
			Iterator gemIterator = new CombinationIterator(combinations.get(combinations.size()-1));
			//for(Gem g : combinations.get(combinations.size()-1).getGems()) {
			while(gemIterator.hasNext()){
				Gem g = (Gem) gemIterator.next();
				Combination combi = g.makeCross(board, combinations);
				if(combi != null) {
					combinations.add(combi);
					br = true;
				}
				if(br) {
					break;
				}
			}
			
		}
		// combinations found
		if(combinations.size()>0){
			int gems = 0;
			for(int i = 0; i < combinations.size(); i++) {
				gems += combinations.get(i).getSize();
			}
			combinationsFormed = true;
			Sounds.getInstance().playCombinationSound();
			int timesScore = 1;
			for(int i = 0; i < combinations.size(); i++) {
				Iterator gemIterator = new CombinationIterator(combinations.get(i));
				while(gemIterator.hasNext()){
				//for(Gem gem : combinations.get(i).getGems()) {
					Gem g = (Gem) gemIterator.next();
					timesScore *= g.timesPoints();
				}
			}
			for(int i = 0; i < timesScore; i++) {
				score.updateScore(gems);
			}
			time.updateTime(gems); //update time
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

	public HighScores getHighScores() {
		return highscores;
	}
	
	public int getScore() {
		return score.getScore();	
				}
	
	public Score getScoreObject() {
		return score;
	}
	
	public void setScore(int s) {
		score.setScore(s);
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time t) {
		time = t;
	}

	public Difficulties getDif() {
		return dif;
	}
}
	
	

