package bejeweled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	private static Sounds sound;

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
				Sounds.getInstance().playSelectSound();
				switchGame();
				Logger.getInstance().writeLineToLogger("The game has started.");
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
   		Sounds.getInstance().playBackgroundSound();
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
		int score = scene.getGameLogic().getBoard().getScore(); // get score
		
		Sounds.getInstance().stopBackgroundSound();// Stop background sound
		Sounds.getInstance().playGameOverSound(); 
		
		HighScores highscores = scene.getGameLogic().getHighScores();
		
		Logger.getInstance().writeLineToLogger("The game is over. The final score is " + score + ".");
		Logger.getInstance().disposeLogger();
		
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
	
	public static void drawPopup() { 
		long startTime = System.currentTimeMillis();
		long showtime = 2*1000;
		
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
			shout.getContent().addAll(t, ds);
			shout.show(stage);
			root.setDisable(true);
			}
		
		}
	}
}