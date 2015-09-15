
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;



/**
 * @author Timo
 *
 */
public final class GameLogic {

	/**
	 * board attribute.
	 */
	private Board board;
	/**
	 * 
	 */
	private int time;
	/**
	 * 
	 */
	private final int timePerGem = 1;
	/**
	 * 
	 */
	private HighScores highscores;
	/**
	 * 
	 */
	private Main main;


	/**
	 * @param offsetx the offset on the x-axis
	 * @param offsety the offset on the y-axis
	 */
	public GameLogic(final int offsetx, final int offsety, Main m, boolean i) {
		time = 30;
		board = new Board(8, offsetx, offsety, i);
		highscores = new HighScores();
		main = m;
	}

	/**
	 * @return - The board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @param gc - the graphicscontext.
	 */
	public void draw(final GraphicsContext gc) {
		board.draw(gc);
		drawScore(gc);
		drawTime(gc);
		drawHighscores(gc);
	}

	/**
	 * @param row - the row index.
	 * @param col - the col index.
	 */
	public void handleMouseClicked(final int row, final int col) {
		try {
			Media m = new Media(new File("src/Sounds/select.mp3").toURI().toString());
			new MediaPlayer(m).setAutoPlay(true);
		}
		catch (Exception e) {
		    System.err.println("Caught Exception: " + e.getMessage() + "\n Are you running a test?");
		}
		if (board.getSelectedgem() == null) {
			board.setSelectedgem(board.getGems()[row][col]);
			board.getGems()[row][col].setSelected(true);
		} else {
			board.setSecondGem(board.getGems()[row][col]);
			int firstgemrow = board.getSelectedgem().getRow();
			int firstgemcol = board.getSelectedgem().getCol();
			if (board.swap(firstgemrow, firstgemcol, row, col)) {
				int first = board.deleteRows(board.getSelectedgem());
				int second = board.deleteRows(board.getSecondGem());
				if (first + second > 0) {
					for (int i = 0; i < first + second; i++) {
						updateTime();
					}
				} else {			// if there are no combinations found after the move

					// switches the two switched gems back
					board.swap(firstgemrow, firstgemcol, row, col);
					// TODO: error sound
				}
			} // else {
				// TODO: error sound;
			//}
			board.getSelectedgem().setSelected(false);
			board.setSelectedgem(null);
			board.setSecondGem(null);
		}
	}

	/**
	 * @param gc
	 *            GraphicsContext to draw to
	 */
	public void drawScore(final GraphicsContext gc) {
		String s = "Score: ";
		s += board.getScore();
		gc.fillText(s, 240, 460);
	}

	/**
	 * update the time.
	 */
	public void updateTime() {
		time += timePerGem;
	}
	
	/**
	 * Decreases the timer by 1.
	 * This is  called every second.
	 */
	public void decrementTime() {
		time--;
	}

	/**
	 * @return - the current time left
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param gc
	 *            GraphicsContext to draw to Draws the time to the scene
	 */
	public void drawTime(final GraphicsContext gc) {
        
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if (seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		if (time < 5000) {
			gc.fillText(s, 240, 480);
		} else {
			gc.fillText("Time left: 0", 240, 480);
		}
		
		if (time < 1) {
			main.gameOver(highscores, board.getScore());
			time = Integer.MAX_VALUE;
		}
	}
	
	/**
	 * @param gc GraphicsContext
	 * Draws the highscore next to the board
	 */
	public void drawHighscores(final GraphicsContext gc) {
		String hs = "Highscores:\n";
		for (int score : highscores.getAllScores()) {
			hs += score + "\n";
		}
		gc.fillText(hs, 100, 200);
	}
}
