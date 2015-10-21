package bejeweled.board;

public class GemFactory {
	private int gemAmount;
	
	public GemFactory(int gems) {
		gemAmount = gems;
	}
	
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
	
	GemType getRandomGemType() {
		return GemType.getRandomGemType(gemAmount);
	}
}
