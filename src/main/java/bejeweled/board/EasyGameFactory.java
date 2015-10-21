package bejeweled.board;

import bejeweled.state.Score;

public class EasyGameFactory extends GameFactory {

	private final int AmountOfTypes=5;
	private final int scorePerGem=5;
	private GemFactory gemFact;
	
	public EasyGameFactory() {
		gemFact = new GemFactory(AmountOfTypes);
	}

	@Override
	public Score getScoreObject() {
		return new Score(scorePerGem);
	}

	@Override
	public GemFactory getGemFactory() {
		return gemFact;
	}
}
