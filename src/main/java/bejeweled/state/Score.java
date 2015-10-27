package bejeweled.state;

import java.util.Observable;

import bejeweled.Main;

/**
 * Score class
 * @author Job
 *
 */
public final class Score extends Observable {
	
	private int score;
	private int scorePerGem;
	
	public Score(int scorepergem) { 
		this.score = score;
		this.scorePerGem = scorepergem;
	}
	
	/**
	 * Updates the score with number of gems * score per gem
	 * Notifies Observers
	 * @param gems
	 */
	public void updateScore(int gems) {
		score += gems*scorePerGem;
		int goodscore = 4 * scorePerGem;
		if (gems*scorePerGem > goodscore) {
			Main.shoutOut();
		}
		setChanged();
		notifyObservers();
	}
	
	public int getScore() {
		return score;
	}
	
	/**
	 * Notifies Observers.
	 * @param s
	 */
	public void setScore(int s) {
		score = s;
		setChanged();
		notifyObservers();
	}
	
}
