package bejeweled.board;

import bejeweled.state.Score;

public class HardGameFactory extends GameFactory {

	private final int AmountOfTypes=7;
	private final int scorePerGem=15;
	
	@Override
	public Score getScoreObject() {
		return new Score(scorePerGem);
	}
	
	@Override
	public GemFactory getGemFactory() {
		return new GemFactory(AmountOfTypes);
	}
}
