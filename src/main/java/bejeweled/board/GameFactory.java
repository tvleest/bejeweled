package bejeweled.board;
import bejeweled.state.Score;

/**
 * @author Timo
 *
 */
public abstract class GameFactory {
	
	public abstract Score getScoreObject();
	
	public abstract GemFactory getGemFactory();
}
