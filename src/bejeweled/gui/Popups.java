package bejeweled.gui;

import java.io.FileWriter;
import java.io.IOException;

import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
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
		saved.setFont(new Font("Helvetica", 20));
		saved.setTextFill(Color.BROWN);
		saved.setLayoutX(115);
		saved.setLayoutY(420);
		
		CornerRadii corners = new CornerRadii(0);
		Insets insets = new Insets(0);
		Background buttonBack1 = new Background(new BackgroundFill(Color.GAINSBORO, corners, insets));
		Background buttonBack2 = new Background(new BackgroundFill(Color.ROSYBROWN, corners, insets));

		Button resume = Buttons.pauseMenuButton("RESUME", 115, 180);
		resume.setTextFill(Color.BLACK);
		resume.setBackground(buttonBack1);

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
		
		//Darker color when hovering over button
		resume.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				resume.setBackground(buttonBack2);
			}
		});
		
		//Restore original color when mouse exits
		resume.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				resume.setBackground(buttonBack1);
			}
		});

		Button quit = Buttons.pauseMenuButton("QUIT", 115, 260);
		quit.setTextFill(Color.BLACK);
		quit.setBackground(buttonBack1);
		
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
		
		//Darker color when hovering over button
		quit.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				quit.setBackground(buttonBack2);
			}
		});
				
		//Restore original color when mouse exits
		quit.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				quit.setBackground(buttonBack1);
			}
		});

		Button save = Buttons.pauseMenuButton("SAVE", 115, 300);
		save.setTextFill(Color.BLACK);
		save.setBackground(buttonBack1);

		/*
		 * Pressing the save button will save the current state of the game to a
		 * savefile.
		 */
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Time time = gamelogic.getTime();
				String stime = time.toString().substring(11, time.toString().length());
				int score = gamelogic.getScore().getScore();
				Gem[][] board = gamelogic.getBoard().getGems();
				String save = stime + "\n" + score + "\n";
				System.out.println(board.length);
				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board.length; col++) {
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
		
		//Darker color when hovering over button
		save.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				save.setBackground(buttonBack2);
			}
		});
				
		//Restore original color when mouse exits
		save.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				save.setBackground(buttonBack1);
			}
		});		
		
		resume.setMaxWidth(Double.MAX_VALUE);
		quit.setMaxWidth(Double.MAX_VALUE);
		save.setMaxWidth(Double.MAX_VALUE);

		VBox vbButtons = new VBox();
		vbButtons.setSpacing(15);
		vbButtons.setPadding(new Insets(20,20,20,20)); 
		vbButtons.getChildren().addAll(resume, save, quit);
		vbButtons.setLayoutX(90);
		vbButtons.setLayoutY(180);
		
		popup.getContent().addAll(imgView, vbButtons);
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
