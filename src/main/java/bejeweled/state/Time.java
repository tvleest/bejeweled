package bejeweled.state;
import bejeweled.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * @author Tim
 * This will keep track of time.
 */
public final class Time {
	private int time;
	
	public Time(int t) {
		time = t;
	}
	
	/**
	 * update the time.
	 */
	public void updateTime(int amount) {
		time += amount;
	}
	
	/**
	 * Decreases the timer by 1.
	 * This is  called every second.
	 */
	public void decrementTime() {
		time--;
		timeCheck();
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	public int getTime() {
		return time;
	}
	
	public String toString() {
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if (seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		return s;
	}
	
	/**
	 * @param gc
	 *            GraphicsContext to draw to Draws the time to the scene
	 */
	public void drawTime(final GraphicsContext gc) {
		if (time < 5000) {
			gc.setFill(Color.BLACK);
			gc.strokeRect(100, 150, 40, 330);	
			gc.setFill(Color.YELLOW);
			if(time>330)
				time=330;
			gc.fillRect(100, 480-time, 40, time);
			gc.fillText(time+"", 110, 480-time);
			gc.setFill(Color.BLACK);
			}
	}
	
	public void timeCheck() {
		if (time < 1) {
			time = Integer.MAX_VALUE;
			Main.gameOver();
		}
	}
}
