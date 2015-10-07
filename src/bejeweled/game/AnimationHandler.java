package bejeweled.game;

import java.util.ArrayList;

import bejeweled.board.Board;
import bejeweled.board.Gem;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * @author Timo
 *
 */
public final class AnimationHandler{

	private boolean running = false; //this should stay true, might need to stop this when a game ends;
	private boolean isAnimating = false;
	private GameLogic gamelogic;
	private Board board;
	private int period = 20;
	private ArrayList<Gem> animatedgems;
	private Timeline timeline;


	public AnimationHandler(GameLogic gamelogic){
		this.gamelogic = gamelogic;
		this.board = gamelogic.getBoard();
	}
	
	//this will do one animation move
	public void runAnimation(){
		for(Gem g : animatedgems){
			if(g.isMoving()){
				if (g.getAnimationx() < g.getCurrentx())
					g.setAnimationx(g.getAnimationx()+1);
				else if (g.getAnimationx() > g.getCurrentx())
					g.setAnimationx(g.getAnimationx()-1);
				else
					if (g.getAnimationy() < g.getCurrenty())
						g.setAnimationy(g.getAnimationy()+1);
					else if (g.getAnimationy() > g.getCurrenty())
						g.setAnimationy(g.getAnimationy()-1);
					else{
						g.setMoving(false);
					}
			}
		}
		if(animatedgems.size()<1){
			isAnimating = false;
			timeline.stop();
			gamelogic.returnFromAnimation();
		}
	}
	
	//this will return the current animatedgems
	private void getAnimatedGems() {
		animatedgems = new ArrayList<Gem>();
		for (Gem[] gemss : board.getGems()) {
			for (Gem gem : gemss) {
				if(gem.isMoving()==true){
					animatedgems.add(gem);
				}
			}
		}
	}

	public void animate(){
		isAnimating=true;
		timeline = new Timeline(new KeyFrame(Duration.millis(10), ae -> gameUpdate()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	private void gameUpdate() {
		if (isAnimating) {
			getAnimatedGems();
			runAnimation();
		}
	}
}
