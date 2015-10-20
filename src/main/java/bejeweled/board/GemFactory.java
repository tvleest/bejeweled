package bejeweled.board;

import java.util.Random;

/**
 * @author Timo
 *
 */
public abstract class GemFactory {
	
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

	public final Random getRandom() {
		return random;
	}

	public final GemType[] getAllgems() {
		return allgems;
	}
}
