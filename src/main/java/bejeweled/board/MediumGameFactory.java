package bejeweled.board;

import bejeweled.state.Score;

public class MediumGameFactory extends GameFactory {
	private final int amountOfGems=6;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}
	
	@Override
	public Score getScoreObject() {
		return new Score(10);
	}
}
