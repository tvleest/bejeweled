package bejeweled.board;

import bejeweled.state.Score;

public class HardGameFactory extends GameFactory {
	private final int amountOfGems=7;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}
	
	@Override
	public Score getScoreObject() {
		return new Score(15);
	}
}
