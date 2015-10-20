package bejeweled.board;

import java.util.Random;

public class GemFactory {
	
	private Random random = new Random();
	private GemType[] allgems = GemType.values();
	private int amountOfGems = 6;//MAX 7
	
	public Gem createGem(int row, int col, GemType type, SpecialType special) {
		if(type==null)
			type=getRandomGemType();
		switch(special) {
			case DOUBLE: return new DoublePointsGem(row, col, type);
			case CROSS: return new DeleteRowGem(row, col, type);
			default: return new Gem(row, col, type);
		}
	}
	
	private GemType getRandomGemType(){
			return allgems[random.nextInt(amountOfGems)];
	}
}
