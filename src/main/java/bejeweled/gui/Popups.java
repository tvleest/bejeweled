package bejeweled.gui;

import java.io.FileWriter;
import java.io.IOException;

import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.DeleteRowGem;
import bejeweled.board.DoublePointsGem;
import bejeweled.board.Gem;
import bejeweled.game.GameLogic;
import bejeweled.game.GameScene;
import bejeweled.state.Time;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * A class dedicated to making popups.
 * 
 * @author group30
 *
 */
public class Popups {
	
	
	/**
	 * Makes a pause menu as a Popup.
	 * 
	 * @param root
	 *            - The gamescene which needs to be reenabled.
	 * @return - The pause menu.
	 */
	public static Popup pausePopup(Group root, GameLogic gamelogic) {
		Popup popup = new Popup();
		popup.setWidth(330);
		popup.setHeight(450);
		Image pausebackground = new Image("Images/pausemenu.png");
		ImageView imgView = new ImageView(pausebackground);
		
		BorderPane border = new BorderPane();
		border.setPadding(new Insets(20, 0, 20, 20));

		Label saved = new Label("Game saved!");
		saved.setLayoutX(115);
		saved.setLayoutY(420);
		
		Image musicIcon = new Image("Images/music2.png");
		Button musicButton = Buttons.subMenuButton(null, musicIcon, 0, 0);
		
		Line mute = new Line();
		mute.setStartX(150);
		mute.setStartY(205);
		mute.setEndX(170);
		mute.setEndY(220);
		mute.setStrokeWidth(1.0);
		mute.setVisible(false);

		musicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (Sounds.getInstance().backgroundSoundPlaying()) {
					Sounds.getInstance().stopBackgroundSound();
					mute.setVisible(true);
				} else {
					Sounds.getInstance().playBackgroundSound();
					mute.setVisible(false);
				}
			}
		});
		
		Button resume = Buttons.pauseMenuButton("Continue");

		/*
		 * Pressing resume will continue the timer and reenable the bejeweled
		 * playing field as well as hiding the pause menu.
		 */
		resume.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				root.setDisable(false);
				popup.hide();
				Main.getTimeline().play();
			}
		});

		Button quit = Buttons.pauseMenuButton("Main Menu");
		
		/*
		 * Pressing quit will remove the pause menu and bring the player back to
		 * the main menu.
		 */
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.switchMenu();
				Sounds.getInstance().stopBackgroundSound();
				popup.hide();
			}
		});

		Button save = Buttons.pauseMenuButton("Save");

		/*
		 * Pressing the save button will save the current state of the game to a
		 * savefile.
		 */
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Time time = gamelogic.getTime();
				String stime = time.toString().substring(11, time.toString().length());
				int score = gamelogic.getScore();
				Gem[][] board = gamelogic.getBoard().getGems();
				String dif = gamelogic.getDif().toString();
				String save = dif + "\n" + stime + "\n" + score + "\n";
				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board.length; col++) {
						int special = 0;
						if(board[row][col] instanceof DoublePointsGem) {
							special = 1;
						}
						else if(board[row][col] instanceof DeleteRowGem) {
							special = 2;
						}
						save += board[row][col].getTypeString() + special +"\n";
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
		
		resume.setMaxWidth(Double.MAX_VALUE);
		quit.setMaxWidth(Double.MAX_VALUE);
		save.setMaxWidth(Double.MAX_VALUE);
		musicButton.setMaxWidth(Double.MAX_VALUE);

		VBox vbButtons = new VBox();
		vbButtons.setSpacing(15);
		vbButtons.setPadding(new Insets(20,20,20,20)); 
		vbButtons.getChildren().addAll(musicButton, save, quit, resume);
		vbButtons.setLayoutX(90);
		vbButtons.setLayoutY(180);
		
		popup.getContent().addAll(imgView, vbButtons, mute);
		return popup;
	}

	/**
	 * Makes the game over popup when the time is up.
	 * 
	 * @param score
	 *            - Achieved score this game.
	 * @return - The game over popup.
	 */
	public static Popup gameOverPopup(int score) {
		Popup popup = new Popup();
		popup.centerOnScreen();
		popup.setWidth(180);
		popup.setHeight(400);
		Rectangle rect = new Rectangle(400, 300, Color.LIGHTGREY);
		Text text = new Text("GAME OVER!");
		text.getStyleClass().add("gameoverhead");
		Text name = new Text("You've scored " + score + " points!");
		name.getStyleClass().add("gameover");
		text.setLayoutX(180);
		text.setLayoutY(30);
		name.setLayoutX(30);
		name.setLayoutY(150);
		Button confirm = new Button("Continue");
		confirm.setLayoutX(180);
		confirm.setLayoutY(255);
		popup.getContent().addAll(rect, text, confirm, name);
		return popup;

	}
}
