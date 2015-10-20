package bejeweled.board;

import bejeweled.state.Score;

public class HardGameFactory extends GameFactory {
	private final int AmountOfTypes=7;
	private final int scorePerGem=15;
	
	@Override
	GemType getRandomGemType() {
		return GemType.getRandomGemType(AmountOfTypes);
	}
	
	@Override
	public Score getScoreObject() {
		return new Score(scorePerGem);
	}
}
