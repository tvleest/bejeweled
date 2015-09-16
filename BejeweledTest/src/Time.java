
public class Time {
	private int time;
	
	public Time(int t) {
		time = t;
	}
	
	/**
	 * update the time.
	 */
	public void updateTime() {
		time++;
	}
	
	/**
	 * Decreases the timer by 1.
	 * This is  called every second.
	 */
	public void decrementTime() {
		time--;
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
}
