package bejeweled;

import bejeweled.board.EasyGameFactory;
import bejeweled.board.GameFactory;
import bejeweled.board.HardGameFactory;
import bejeweled.board.MediumGameFactory;

public enum Difficulties {
	EASY, MEDIUM, HARD;
	
	public GameFactory getFactory(){
		switch (this) {
		case EASY:
			return new EasyGameFactory();
		case MEDIUM:
			return new MediumGameFactory();
		case HARD:
			return new HardGameFactory();
		default:
			return new MediumGameFactory();
		}
	}
}
