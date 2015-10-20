package bejeweled.board;

import java.util.Random;

import bejeweled.state.Score;

/**
 * @author Timo
 *
 */
public abstract class GameFactory {
	
	private Random random = new Random();
	private GemType[] allgems = GemType.values();
		
	public final Gem createGem(int row, int col, GemType type, SpecialType special) {
		if(type==null){
			type=getRandomGemType();
		}
		switch(special) {
			case DOUBLE: return new DoublePointsGem(row, col, type);
			case CROSS: return new DeleteRowGem(row, col, type);
			default: return new Gem(row, col, type);
		}
	}
	
	abstract GemType getRandomGemType();
	
	public abstract Score getScoreObject();

	public final Random getRandom() {
		return random;
	}

	public final GemType[] getAllgems() {
		return allgems;
	}
}
