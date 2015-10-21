package bejeweled.board;
import bejeweled.state.Score;

/**
 * @author Timo
 *
 */
public abstract class GameFactory {
			
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
}
