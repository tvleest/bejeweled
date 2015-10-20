package bejeweled.board;

public class MediumGemFactory extends GemFactory {
	private final int amountOfGems=6;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}
}
