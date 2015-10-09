package bejeweled.board;

import java.util.EnumMap;
import java.util.Random;

import javafx.scene.image.Image;

/**
 * @author Timo This class defines the different types of gems.
 */
public enum GemType {
	BLUE, GREEN, ORANGE, PINK, RED, YELLOW;

	private static GemType[] allgems = {BLUE, GREEN, ORANGE, PINK, RED, YELLOW};
	private static Random random = new Random();
	private static EnumMap<GemType, Image> typeToImage;
	private static EnumMap<GemType, Image> specialTypeToImage1;
	private static EnumMap<GemType, Image> specialTypeToImage2;
	
	public static Image getImage(GemType gt, int s) {
        if (typeToImage == null)
            initMapping();
        if(s == 0) {
        	return typeToImage.get(gt);
        }
        else if(s == 1) {
        	return specialTypeToImage1.get(gt);
        }
        else {
        	return specialTypeToImage2.get(gt);
        }
    }
 
    private static void initMapping() {
    	typeToImage = new EnumMap<>(GemType.class);
    	specialTypeToImage1 = new EnumMap<>(GemType.class);
    	specialTypeToImage2 = new EnumMap<>(GemType.class);
    	
    	typeToImage.put(GemType.BLUE, new Image("Images/blue.png"));
    	typeToImage.put(GemType.GREEN, new Image("Images/green.png"));
    	typeToImage.put(GemType.ORANGE, new Image("Images/orange.png"));
    	typeToImage.put(GemType.PINK, new Image("Images/pink.png"));
    	typeToImage.put(GemType.RED, new Image("Images/red.png"));
    	typeToImage.put(GemType.YELLOW, new Image("Images/yellow.png"));
    	
    	specialTypeToImage1.put(GemType.BLUE, new Image("Images/blue.png"));
    	specialTypeToImage1.put(GemType.GREEN, new Image("Images/green.png"));
    	specialTypeToImage1.put(GemType.ORANGE, new Image("Images/orange.png"));
    	specialTypeToImage1.put(GemType.PINK, new Image("Images/pink.png"));
    	specialTypeToImage1.put(GemType.RED, new Image("Images/red.png"));
    	specialTypeToImage1.put(GemType.YELLOW, new Image("Images/yellow.png"));
    	
    	specialTypeToImage2.put(GemType.BLUE, new Image("Images/blue.png"));
    	specialTypeToImage2.put(GemType.GREEN, new Image("Images/green.png"));
    	specialTypeToImage2.put(GemType.ORANGE, new Image("Images/orange.png"));
    	specialTypeToImage2.put(GemType.PINK, new Image("Images/pink.png"));
    	specialTypeToImage2.put(GemType.RED, new Image("Images/red.png"));
    	specialTypeToImage2.put(GemType.YELLOW, new Image("Images/yellow.png"));
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
