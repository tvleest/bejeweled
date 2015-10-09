package bejeweled.board;

import java.util.EnumMap;
import java.util.Random;

import javafx.scene.image.Image;

/**
 * @author Timo This class defines the different types of gems.
 */
public enum SpecialType {
	NORMAL, DOUBLE, CROSS;

	private static SpecialType[] allspecialgems = {NORMAL, DOUBLE, CROSS};

	/**
	 * @param s,
	 *            the String that is used.
	 * @return The GemType for a certain String.
	 */
	public static SpecialType typeFromString(String s) {
		if (s.equals("DOUBLE")) {
			return DOUBLE;
		} else if (s.equals("CROSS")) {
			return CROSS;
		} else {
			return NORMAL;
		}
	}
}
