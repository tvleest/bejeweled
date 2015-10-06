package bejeweled.state;

import bejeweled.Main;

/**
 * Score class
 * @author Job
 *
 */
public final class Score {
	
	private int score;
	private int scorePerGem;
	
	public Score(int s) { 
		score = s;
		scorePerGem = 10;
	}
	
	/**
	 * Updates the score with number of gems * score per gem
	 * @param gems
	 */
	public void updateScore(int gems) {
		score += gems*scorePerGem;
		int goodscore = 30;
		if (gems*scorePerGem > goodscore) {
			Main.shoutOut();
		}

	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
}
