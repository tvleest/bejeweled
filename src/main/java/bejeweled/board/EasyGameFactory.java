package bejeweled.board;

import bejeweled.state.Score;

public class EasyGameFactory extends GameFactory {
	private final int amountOfGems=5;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}

	@Override
	public Score getScoreObject() {
		return new Score(5);
	}
}
