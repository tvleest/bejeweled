package bejeweled.game;

import java.util.ArrayList;

import bejeweled.board.Board;
import bejeweled.board.Gem;

/**
 * @author Timo
 *
 */
public final class AnimationHandler implements Runnable{

	private boolean running = false; //this should stay true, might need to stop this when a game ends;
	private boolean isAnimating = false;
	private GameLogic gamelogic;
	private Board board;
	private Thread animationThread; // for the animation
	private int period = 20;
	private ArrayList<Gem> animatedgems;


	public AnimationHandler(GameLogic gamelogic){
		this.gamelogic = gamelogic;
		this.board = gamelogic.getBoard();
		animationThread = new Thread(this);
		animationThread.setDaemon(true); //thread will close when game closes.
		animationThread.start();
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
			isAnimating = true;
			gamelogic.setAnimating(false);
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

	public void start() {
		if (animationThread == null || !running) {
			animationThread = new Thread(this);
			animationThread.start();
		}
	}

	private void gameUpdate() {
		if (!isAnimating) {
			getAnimatedGems();
			runAnimation();
		}
	}

	public void stopAnimationThread() {
		// called by the user to stop execution
		running = false;
	}
	
	public void run()
	{
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.currentTimeMillis();
		running = true;
		while (running) {
			gameUpdate();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleepTime = period - timeDiff; // time left in this loop
			if (sleepTime <= 0) // update/render took longer than period
				sleepTime = 5; // sleep a bit anyway
			try {
				Thread.sleep(sleepTime); // in ms
			} catch (InterruptedException ex) {
			}
			beforeTime = System.currentTimeMillis();
		}
	}

	public void setIsAnimating(boolean b) {
		isAnimating=b;
	}
}
