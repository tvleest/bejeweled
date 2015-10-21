package bejeweled.board;

import java.util.EnumMap;
import java.util.Random;

import javafx.scene.image.Image;

/**
 * @author Timo This class defines the different types of gems.
 */
public enum GemType {
	BLUE, GREEN, ORANGE, PINK, RED, YELLOW, PURPLE;

	private static Random random = new Random();
	private static GemType[] allgems = values();

	private static EnumMap<GemType, Image> typeToImage;
	private static EnumMap<GemType, Image> typeToDoublePointsGemImage;
	private static EnumMap<GemType, Image> typeToDeleteRowGemImage;
	
	public static Image getImage(GemType gt) {
        if (typeToImage == null){
        	initMapping();
        	}
        return typeToImage.get(gt);
    }
	
	public static Image getDoublePointsGemImage(GemType gt) {
        if (typeToDoublePointsGemImage == null){
        	initMapping();
        	}
        return typeToDoublePointsGemImage.get(gt);
    }
	
	public static Image getDeleteRowGemImage(GemType gt) {
        if (typeToDeleteRowGemImage == null){
        	initMapping();
        	}
        return typeToDeleteRowGemImage.get(gt);
    }

    private static void initMapping() {
    	typeToImage = new EnumMap<>(GemType.class);
    	typeToDoublePointsGemImage = new EnumMap<>(GemType.class);
    	typeToDeleteRowGemImage = new EnumMap<>(GemType.class);
    	
    	typeToImage.put(GemType.BLUE, new Image("Images/blue.png"));
    	typeToImage.put(GemType.GREEN, new Image("Images/green.png"));
    	typeToImage.put(GemType.ORANGE, new Image("Images/orange.png"));
    	typeToImage.put(GemType.PINK, new Image("Images/pink.png"));
    	typeToImage.put(GemType.RED, new Image("Images/red.png"));
    	typeToImage.put(GemType.YELLOW, new Image("Images/yellow.png"));
    	typeToImage.put(GemType.PURPLE, new Image("Images/purple.png"));

    	typeToDoublePointsGemImage.put(GemType.BLUE, new Image("Images/blue_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.GREEN, new Image("Images/green_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.ORANGE, new Image("Images/orange_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.PINK, new Image("Images/pink_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.RED, new Image("Images/red_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.YELLOW, new Image("Images/yellow_2x.png"));
    	typeToDoublePointsGemImage.put(GemType.PURPLE, new Image("Images/purple_2x.png"));

    	typeToDeleteRowGemImage.put(GemType.BLUE, new Image("Images/blue_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.GREEN, new Image("Images/green_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.ORANGE, new Image("Images/orange_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.PINK, new Image("Images/pink_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.RED, new Image("Images/red_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.YELLOW, new Image("Images/yellow_rk.png"));
    	typeToDeleteRowGemImage.put(GemType.PURPLE, new Image("Images/purple_rk.png"));
    }
    
	public static GemType getRandomGemType(int amountOfTypes) {
		return allgems[random.nextInt(amountOfTypes)];
	}
}
