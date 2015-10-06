package bejeweled.board;

import java.util.Random;

/**
 * @author Timo This class defines the different types of gems.
 */
public enum GemType {
	BLUE, GREEN, ORANGE, PINK, RED, YELLOW;

	private static GemType[] allgems = values();
	private static Random random = new Random();

	public static GemType getRandomGemType() {
		return allgems[random.nextInt(allgems.length)];
	}

	/**
	 * @param s,
	 *            the String that is used.
	 * @return The GemType for a certain String.
	 */
	public static GemType typeFromString(String s) {
		if (s.equals("BLUE")) {
			return BLUE;
		} else if (s.equals("GREEN")) {
			return GREEN;
		} else if (s.equals("ORANGE")) {
			return ORANGE;
		} else if (s.equals("PINK")) {
			return PINK;
		} else if (s.equals("RED")) {
			return RED;
		} else if (s.equals("YELLOW")) {
			return YELLOW;
		}
		return null;
	}
}
