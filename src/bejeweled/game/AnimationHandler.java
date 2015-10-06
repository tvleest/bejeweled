package bejeweled.game;

import java.util.ArrayList;

import bejeweled.board.Board;
import bejeweled.board.Gem;

public class AnimationHandler {

	private boolean running = false;
	private GameLogic gamelogic;
	private Board board;
	
	public AnimationHandler(GameLogic gamelogic){
		this.gamelogic = gamelogic;
		this.board = gamelogic.getBoard();
	}
	
	public void runAnimation(){
		ArrayList<Gem> animatedgems = getAnimatedGems();
		for(Gem g : animatedgems){
			while(g.isMoving()){
				//TODO:
			}
		}
	}

	private ArrayList<Gem> getAnimatedGems() {
		ArrayList<Gem> animatedgems = new ArrayList<Gem>();
		for (Gem[] gemss : board.getGems()) {
			for (Gem gem : gemss) {
				if(gem.isMoving()==true){
					animatedgems.add(gem);
				}
			}
		}
		return animatedgems;
	}
}
