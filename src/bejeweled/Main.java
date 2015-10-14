package bejeweled;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import bejeweled.board.Board;
import java.util.Scanner;
import bejeweled.board.Gem;
import bejeweled.board.GemType;
import bejeweled.game.GameLogic;
import bejeweled.game.GameScene;
import bejeweled.gui.Buttons;
import bejeweled.gui.Popups;
import bejeweled.state.HighScores;
import bejeweled.state.Logger;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main of our Bejeweled game.
 * 
 * @author Group 30
 *
 */
public final class Main extends Application {
	private static GameScene scene;
	private static Stage stage;
	private static Timeline timeline;
	private static Group root;
	private static File file;

	/**
	 * @param args
	 *            Main method
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		file = new File("saveFile.txt");
		stage = primaryStage;
		scene = new GameScene(new Group(), stage);
		primaryStage.setTitle("Bejeweled group 30");
		switchMenu();		
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> scene.decrementTime()));
		timeline.setCycleCount(Animation.INDEFINITE);
		primaryStage.show();
	}

	/**
	 * Switch the current screen to the main menu. TODO: This method is way too
	 * long
	 */
	public static void switchMenu() {
		root = new Group();

		// load background
		Image background = new Image("Images/MenuBackground.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);

		// make two buttons for the menu
		Button newGameButton = Buttons.menuButton("Start New Game", 275, 110);
		newGameButton.setStyle("-fx-font-size: 20px");

		newGameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Sounds.getInstance().playSelectSound();
				switchGame(false);
				Logger.getInstance().writeLineToLogger("The game has started.");
			}
		}); // start the game

		Button highScoresButton = Buttons.menuButton("View the Highscores", 275, 430);
		highScoresButton.setStyle("-fx-font-size: 20px");

		highScoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				highScoreMenu();
			}
		});

		Button continueButton = Buttons.menuButton("Continue Saved Game", 275, 270);
		continueButton.setStyle("-fx-font-size: 20px");
		continueButton.setOpacity(0.35);
		continueButton.setDisable(true);

		if (file.exists()) {
			continueButton.setDisable(false);
			continueButton.setOpacity(1);
			continueButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					Sounds.getInstance().playSelectSound();
					switchGame(true);
					Logger.getInstance().writeLineToLogger("The saved game has been loaded.");
				}
			});
		}
		root.getChildren().addAll(imgView, newGameButton, highScoresButton, continueButton);
		root.getStylesheets().add("Style.css");
		if (stage.getScene() == null) {
			Scene sc = new Scene(root);
			stage.setScene(sc);
			
		} else {
			stage.getScene().setRoot(root);
		}
	}
	
	
	/**
	 * Switch to the HighScores menu. TODO: Add the actual highscores to the menu
	 */
	public static void highScoreMenu() {
		root = new Group();

		// load background
		Image background = new Image("Images/MenuBackground.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);

		Button quit = Buttons.menuButton("Return to main Menu", 275, 430);
		quit.setStyle("-fx-font-size: 20px");
		
		Label HighScoreText = new Label ("Test");
		
		
		/*
		 * Pressing quit will remove the pause menu and bring the player back to
		 * the main menu.
		 */
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.switchMenu();
				}
		});
		
		// @TODO: add the draw highscores here
		
		root.getChildren().addAll(imgView, quit, HighScoreText);
		root.getStylesheets().add("Style.css");
		if (stage.getScene() == null) {
			Scene sc = new Scene(root);
			stage.setScene(sc);
			
		} else {
			stage.getScene().setRoot(root);
		}
	}

	/**
	 * Switch the current screen to the Bejeweled screen.
	 */
	public static void switchGame(boolean savedGame) {
		timeline.playFromStart();
		root = new Group();

		Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);

		root.getChildren().addAll(imgView);
		Sounds.getInstance().playBackgroundSound();

		scene = new GameScene(root, stage);

		if (savedGame) {
			loadFile();
		}
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				scene.getGraphicsContext().clearRect(0, 0, 800, 600);
				scene.draw();
			}
		}.start();
		stage.setScene(scene);
	}

	/**
	 * Reads the savefile File and changes the value of score, time and all the
	 * Gems in the Game so it matches the saved Game. 
	 * TODO: put this in a
	 * different save class
	 */
	public static void loadFile() {
		GameLogic gamelogic = scene.getGameLogic();
		Board boardinstance = gamelogic.getBoard();
		Scanner sc;
		int score2 = 0;
		try {
			sc = new Scanner(new File("savefile.txt"));
			String time = sc.nextLine();
			System.out.println(time);
			if (sc.hasNext()) {
				String score = sc.nextLine();
				score2 = Integer.parseInt(score);
				System.out.println(score);
			}
			Gem[][] board = new Gem[8][8];
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board.length; col++) {
					if (sc.hasNext()) {
						String type = sc.nextLine();
						System.out.println(type);
						GemType gtype = GemType.valueOf(type);
						board[row][col] = new Gem(row, col, gtype);
					}
				}
			}

			String minutes = time.substring(0, time.indexOf(":"));
			String seconds = time.substring(time.length() - 2, time.length());
			System.out.println(minutes + " - " + seconds);
			int m = Integer.parseInt(minutes);
			int s = Integer.parseInt(seconds);
			int t = m * 60 + s;
			bejeweled.state.Time time2 = new bejeweled.state.Time(t);

			gamelogic.setTime(time2);
			gamelogic.getScoreObject().setScore(score2);
			boardinstance.setGems(board);

		} catch (FileNotFoundException e) {
			System.out.println("Savefile was not found!");
		}
	}

	public static void switchContinueGame() {

	}

	/**
	 * Show a GameOver popup.
	 */
	public static void gameOver() {
		int score = scene.getGameLogic().getScore(); // get score
		Sounds.getInstance().stopBackgroundSound();// Stop background sound
		Sounds.getInstance().playGameOverSound();

		HighScores highscores = scene.getGameLogic().getHighScores();

		Logger.getInstance().writeLineToLogger("The game is over. The final score is " + score + ".");
		Logger.getInstance().disposeLogger();
		Popup popup = Popups.gameOverPopup(score);
		popup.show(stage);
		root.setDisable(true);
		Button confirm = (Button) popup.getContent().get(2);
		confirm.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				highscores.addHighScore(score);
				try {
					highscores.writeScoreFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				popup.hide();
				switchMenu();
			}
		});

	}

	public static void shoutOut() {

		// Create a popup for showing text on the board
		Popup popup = new Popup();
		popup.centerOnScreen();
		popup.setWidth(600);
		popup.setHeight(800);

		// Add various shouts to Array
		String[] shouts = new String[] { "Good job!", "Keep on going!", "Nice work!" };

		// Pick a random shout
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(shouts.length);
		String item = shouts[index];

		Text t = new Text();
		t.setCache(true);
		t.setX(60);
		t.setY(200);
		t.setText(item);
		t.setId("shoutout");

		// Put it all together in the pop up
		popup.getContent().addAll(t);
		popup.show(stage);
		root.setDisable(true);
		Sounds.getInstance().playShoutOutSound();

		// Show it for 1 second
		int showtime = 1 * 1000;
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(showtime), ae -> popup.hide()));
		timeline.play();
		root.setDisable(false);
	}

	public static Timeline getTimeline() {
		return timeline;
	}
}