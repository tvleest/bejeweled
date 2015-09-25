package bejeweled.board;

import java.util.Random;

public enum GemType { 
	BLUE, 
	GREEN, 
	ORANGE, 
	PINK,
    RED, 
    YELLOW;
    
	private static GemType[] allgems = values();
	private static Random random = new Random();
    public static GemType getRandomGemType()  {
      return allgems[random.nextInt(allgems.length)];
    }
}
