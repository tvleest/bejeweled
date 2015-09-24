package bejeweled.game;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.state.HighScores;
import bejeweled.state.Logger;
import bejeweled.state.Time;



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
	private Time time;
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
	private Sounds sounds;


	/**
	 * @param offsetx the offset on the x-axis
	 * @param offsety the offset on the y-axis
	 */
	public GameLogic(final int offsetx, final int offsety, boolean i) {
		time = new Time(60); 		
		board = new Board(8, offsetx, offsety, i);
		highscores = new HighScores();
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
		drawHighscores(gc);
		drawPopup(gc);
	}

	/**
	 * @param row - the row index.
	 * @param col - the col index.
	 */
	public void handleMouseClicked(final int row, final int col) {
		Logger.getInstance().writeLineToLogger("Mouse clicked on row " + row + " and col " + col);
		Sounds.getInstance().playSelectSound();
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
					Logger.getInstance().writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + "," + 
							board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + "," + 
							board.getSecondGem().getRow() + ") are switched. This switch was succesfull.");
					for (int i = 0; i < first + second; i++) {
						time.updateTime();
						Sounds.getInstance().playCombinationSound();
					}
				} else {			// if there are no combinations found after the move
					Logger.getInstance().writeLineToLogger("The Gems on (" + board.getSelectedgem().getCol() + "," + 
							board.getSelectedgem().getRow() + ") and (" + board.getSecondGem().getCol() + "," + 
							board.getSecondGem().getRow() + ") are switched. This switch was unsuccesfull.");
					// switches the two switched gems back
					board.swap(firstgemrow, firstgemcol, row, col);
					// play error sound
					Sounds.getInstance().playErrorSound();
				}
			}
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
	 * @return - Time object in use.
	 */
	public Time getTime() {
		return time;
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
	
	public HighScores getHighScores() {
		return highscores;
	}
	
	public static void drawPopup(final GraphicsContext gc) { 
		long startTime = System.currentTimeMillis();
		long showtime = 8*1000;
		
		while (startTime+showtime <= System.currentTimeMillis()){ 
			
		ArrayList<String> shouts = new ArrayList<>();
		shouts.add(1, "Good Job!");
		shouts.add(2, "Keep on going!");
		shouts.add(3, "Nice Work!");
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(shouts.size());
		String item = shouts.get(index);
		
		if (startTime+showtime <= System.currentTimeMillis()) {
			Popup shout = new Popup();
			shout.centerOnScreen();
			shout.setWidth(100);
			shout.setHeight(200);
			DropShadow ds = new DropShadow();
			ds.setOffsetY(3.0f);
			ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
			 
			Text t = new Text();
			t.setEffect(ds);
			t.setCache(true);
			t.setX(150);
			t.setY(270);
			t.setFill(Color.GOLD);
			t.setText(item);
			t.setFont(Font.font("Arial", FontWeight.BOLD, 72));
//			shout.getContent().addAll(t, ds);
//			shout.show(stage);
//			root.setDisable(true);
			gc.fillText("test", 240, 460);
			
			}
		
		}
	}
}
