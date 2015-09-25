package bejeweled.gui;

import java.io.FileWriter;
import java.io.IOException;

import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.game.GameLogic;
import bejeweled.state.Time;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * A class dedicated to making popups.
 * @author group30
 *
 */
public class Popups {
	
	/**
	 * Makes a pause menu as a Popup.
	 * @param root - The gamescene which needs to be reenabled.
	 * @return - The pause menu.
	 */
	public static Popup pausePopup(Group root) {
		Popup popup = new Popup();
		popup.setWidth(330);
		popup.setHeight(460);
		Rectangle rect = new Rectangle(330, 460, Color.MAROON);
		rect.setArcHeight(30);
		rect.setArcWidth(30);
		Label bejeweled = new Label("BEJEWELED");
		bejeweled.setFont(new Font("Comic Sans MS", 45));
		bejeweled.setTextFill(Color.WHITE);
		bejeweled.setLayoutX(30);
		bejeweled.setLayoutY(20);
		
		Label paused = new Label("-GAME PAUSED-");
		paused.setFont(new Font("Comic Sans MS", 20));
		paused.setTextFill(Color.WHITESMOKE);
		paused.setLayoutX(82);
		paused.setLayoutY(75);
		
		Label saved = new Label("Game saved!");
		saved.setFont(new Font("Comic Sans MS", 18));
		saved.setTextFill(Color.WHITE);
		saved.setLayoutX(115);
		saved.setLayoutY(420);
		
		Button resume = Buttons.pauseMenuButton("RESUME", 107, 110);
		
		/* Pressing resume will continue the timer 
		 * and reenable the bejeweled playing field
		 * as well as hiding the pause menu. 
		 */
		resume.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				root.setDisable(false);
				popup.hide();
				Main.getTimeline().play();
			}
		});
		
		Button quit = Buttons.pauseMenuButton("QUIT", 123, 165);
		
		/* Pressing quit will remove the pause menu
		 * and bring the player back to the main menu.
		 */
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.switchMenu();
				Sounds.getInstance().stopBackgroundSound();
				popup.hide();
			}
		});
		
		Button save = Buttons.pauseMenuButton("SAVE", 123, 220);
		
		/* Pressing the save button will save the current state
		 * of the game to a savefile.
		 */
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Time time = GameLogic.getTime();
				String stime = time.toString().substring(11, time.toString().length());
				int score = Board.getScore();
				Gem[][] board = Board.getGems();
				String save = stime + "\n" + score + "\n";
				System.out.println(board.length);
				for(int row = 0; row < board.length; row++) {
					for(int col = 0; col < board.length; col++) {
						save += board[row][col].getType() + "\n";
					}
				}
				try {
					FileWriter fw = new FileWriter("savefile.txt");
					fw.write(save);
					fw.close();
				} catch (IOException e1) {
					System.out.println("Something went wrong with the FileWriter in GameScene");
				}
				popup.getContent().add(saved);
			}
		});
		
		popup.getContent().addAll(rect, bejeweled, paused, resume, quit, save);
		return popup;
	}
	
	/**
	 * Makes the game over popup when the time is up.
	 * @param score - Achieved score this game.
	 * @return - The game over popup.
	 */
	public static Popup gameOverPopup(int score) {
		Popup popup = new Popup();
		popup.centerOnScreen();
		popup.setWidth(200);
		popup.setHeight(500);
		Rectangle rect = new Rectangle(500, 300, Color.GOLD);
		rect.setArcHeight(30);
		rect.setArcWidth(30);
		Text text = new Text("GAME OVER!");
		Text name = new Text("You've scored " + score + " points!");
		text.setLayoutX(200);
		text.setLayoutY(30);
		name.setLayoutX(30);
		name.setLayoutY(150);
		text.setFill(Color.BLACK);
		Button confirm = new Button("Continue");
		confirm.setLayoutX(205);
		confirm.setLayoutY(255);
		popup.getContent().addAll(rect, text, confirm, name);
		return popup;

	}
}
