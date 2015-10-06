package bejeweled.board;

import java.util.EnumMap;
import java.util.Random;

import javafx.scene.image.Image;

/**
 * @author Timo This class defines the different types of gems.
 */
public enum GemType {
	BLUE, GREEN, ORANGE, PINK, RED, YELLOW;

	private static GemType[] allgems = values();
	private static Random random = new Random();
	private static EnumMap<GemType, Image> typeToImage;
	
	public static Image getImage(GemType gt) {
        if (typeToImage == null)
            initMapping();
        return typeToImage.get(gt);
    }
 
    private static void initMapping() {
    	typeToImage = new EnumMap<>(GemType.class);
    	typeToImage.put(GemType.BLUE, new Image("Images/blue.png"));
    	typeToImage.put(GemType.GREEN, new Image("Images/green.png"));
    	typeToImage.put(GemType.ORANGE, new Image("Images/orange.png"));
    	typeToImage.put(GemType.PINK, new Image("Images/pink.png"));
    	typeToImage.put(GemType.RED, new Image("Images/red.png"));
    	typeToImage.put(GemType.YELLOW, new Image("Images/yellow.png"));
    }
	
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
