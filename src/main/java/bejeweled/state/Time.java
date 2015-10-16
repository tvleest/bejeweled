package bejeweled.state;
import bejeweled.Main;
import javafx.scene.canvas.GraphicsContext;


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
			//gc.fillText(toString(), 240, 480);
			gc.fillText("Time left:", 240, 480);
			gc.fillRect(310, 465, time, 20);
		} else {
			gc.fillText("Time left: 0:00", 240, 480);
		}
	}
	
	public void timeCheck() {
		if (time < 1) {
			time = Integer.MAX_VALUE;
			Main.gameOver();
		}
	}
}
