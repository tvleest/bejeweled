package bejeweled.board;

import bejeweled.state.Score;

public class EasyGameFactory extends GameFactory {
	private final int AmountOfTypes=5;
	private final int scorePerGem=5;
	
	@Override
	GemType getRandomGemType() {
		return GemType.getRandomGemType(AmountOfTypes);
	}

	@Override
	public Score getScoreObject() {
		return new Score(scorePerGem);
	}
}
