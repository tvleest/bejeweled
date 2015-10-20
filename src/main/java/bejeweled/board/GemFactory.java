package bejeweled.board;

import java.util.Random;

public class GemFactory {
	
	private static Random random = new Random();
	private static GemType[] allgems = GemType.values();
	
	public Gem createGem(int row, int col, GemType type, SpecialType special) {
		switch(special) {
			case DOUBLE: return new DoublePointsGem(row, col, type);
			case CROSS: return new DeleteRowGem(row, col, type);
			default: return new Gem(row, col, type);
		}
	}
	
	public GemType getRandomGemType(){
			return allgems[random.nextInt(allgems.length)];
	}
}
