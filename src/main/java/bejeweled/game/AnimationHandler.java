package bejeweled.game;

import java.util.ArrayList;

import bejeweled.board.Board;
import bejeweled.board.Gem;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * @author Timo
 *
 */
public final class AnimationHandler{

	private GameLogic gamelogic;
	private Board board;
	private ArrayList<Gem> animatedgems;
	private Timeline timeline;

	public AnimationHandler(GameLogic gamelogic){
		this.gamelogic = gamelogic;
		this.board = gamelogic.getBoard();
	}
	
	/**
	 * will do one move of the animation.
	 */
	public void runAnimation(){
		for(Gem g : animatedgems){
			if(g.isMoving()){
				if (g.getAnimationx() < g.getCurrentx()) {
					g.setAnimationx(g.getAnimationx()+1);
				} else if (g.getAnimationx() > g.getCurrentx()) {
					g.setAnimationx(g.getAnimationx()-1);
				} else
					if (g.getAnimationy() < g.getCurrenty()) {
						g.setAnimationy(g.getAnimationy()+1);
					} else if (g.getAnimationy() > g.getCurrenty()) {
						g.setAnimationy(g.getAnimationy()-1);
					} else{
						g.setMoving(false);
					}
			}
		}
		//will always do an extra loop before the size will be smaller, can be more efficient
		if(animatedgems.size()<1){
			timeline.stop();
			gamelogic.returnFromAnimation();
		}
	}
	
	/**
	 * will set the animatedgems to all gems that need animation.
	 */
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

	/**
	 * instantiates a new timeline for this animation and plays it.
	 * call gameUpdate for every 10 ms
	 */
	public void animate(){
		timeline = new Timeline(new KeyFrame(Duration.millis(10),
                new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	gameUpdate();
            }
        }));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	/**
	 * updates the game list.
	 * runs an animation
	 */
	private void gameUpdate() {
			getAnimatedGems();
			runAnimation();
	}
}
