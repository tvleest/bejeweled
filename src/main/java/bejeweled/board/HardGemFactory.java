package bejeweled.board;

public class HardGemFactory extends GemFactory {
	private final int amountOfGems=7;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}
}
