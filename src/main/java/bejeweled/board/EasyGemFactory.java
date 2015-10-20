package bejeweled.board;

public class EasyGemFactory extends GemFactory {
	private final int amountOfGems=5;
	
	@Override
	GemType getRandomGemType() {
		return getAllgems()[getRandom().nextInt(amountOfGems)];
	}
}
