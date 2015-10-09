package bejeweled.board;

public class GemFactory {
	
	public Gem createGem(int row, int col, GemType type, SpecialType special) {
		Gem gem = null;
		switch(special) {
			case DOUBLE: gem = new DoublePointsGem(row, col, type);
						 break;
			case CROSS: gem = new DeleteRowGem(row, col, type);
			 			 break;
			default: gem = new Gem(row, col, type);
					 break;
		}
		return gem;
	}
}
