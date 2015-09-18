package bejeweled;

import java.io.IOException;

import bejeweled.game.GameScene;
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
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main of our Bejeweled game.
 * @author Group 30
 *
 */
public class Main extends Application {
	private static GameScene scene;
	private static Stage stage;
	private static Timeline timeline;
	private static Group root;
	private Sounds sound;
	static Logger logger;

	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public final void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		scene = new GameScene(new Group());
		primaryStage.setTitle("Bejeweled group 30");
	    switchMenu();
	    
	    timeline = new Timeline(new KeyFrame(
		        Duration.millis(1000),
		        ae -> scene.decrementTime()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
	    primaryStage.show();
   		sound = new Sounds();
   		sound.playBackgroundSound();
	}
	
	/**
	 * Switch the current screen to the main menu.
	 */
	public final static void switchMenu() {
		root = new Group();
		Font font = Font.font(72);
		
		// load background
		Image background = new Image("Images/MenuBackground.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		
		// make two buttons for the menu
		Button startGameButton = new Button("START GAME");
		startGameButton.setFont(font);
		startGameButton.setLayoutX(150);
		startGameButton.setLayoutY(270);
		CornerRadii r = new CornerRadii(10);
		Insets insets = new Insets(10);
		Background buttonBack = new Background(new BackgroundFill(Color.GOLD, r, insets));
		startGameButton.setBackground(buttonBack);
		startGameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				switchGame();
				logger = new Logger();
				logger.writeLineToLogger("The game has started.");
			}
		}); //start the game
		
		Button exitButton = new Button("EXIT");
		exitButton.setFont(font);
		exitButton.setLayoutX(290);
		exitButton.setLayoutY(430);
		exitButton.setBackground(buttonBack);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		
		root.getChildren().addAll(imgView, startGameButton, exitButton);
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
	public static final void switchGame() {
		timeline.playFromStart();
		root = new Group();
	    Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		root.getChildren().add(imgView);
		scene = new GameScene(root);
		
		  new AnimationTimer()
		    {
		        public void handle(long currentNanoTime) {
		        	scene.getGraphicsContext().clearRect(0, 0, 800, 600);
		            scene.draw();
		        }
		    }.start();
		    
	    stage.setScene(scene);
	    
	}
	
	/**
	 * Show a GameOver popup.
	 * @param highscores - highscores.
	 * @param score - Achieved score.
	 */
	public final static void gameOver() {
		int score = scene.getGameLogic().getBoard().getScore();
		HighScores highscores = scene.getGameLogic().getHighScores();
		
		logger.writeLineToLogger("The game is over. The final score is " + score + ".");
		
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
		popup.show(stage);
		root.setDisable(true);
		
		confirm.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				highscores.addHighScore(score);
				try {
					highscores.writeScoreFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				popup.hide();
				switchMenu();
			}
		});
		
	}
	
	public static Logger getLogger() {
		return logger;
	}
}