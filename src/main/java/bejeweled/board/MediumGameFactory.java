package bejeweled.board;

import bejeweled.state.Score;

public class MediumGameFactory extends GameFactory {

	private final int AmountOfTypes=6;
	private final int scorePerGem=10;
	
	@Override
	public Score getScoreObject() {
		return new Score(scorePerGem);
	}
	
	@Override
	public GemFactory getGemFactory() {
		return new GemFactory(AmountOfTypes);
	}
}
