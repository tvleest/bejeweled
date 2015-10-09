package bejeweled.board;

public class GemFactory {
	
	public Gem createGem(int row, int col, GemType type, SpecialType special) {
		switch(special) {
			case DOUBLE: return new DoublePointsGem(row, col, type);
			case CROSS: return new DeleteRowGem(row, col, type);
			default: return new Gem(row, col, type);
		}
	}
}
