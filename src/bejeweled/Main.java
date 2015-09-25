package bejeweled;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import bejeweled.board.Board;
import bejeweled.game.GameScene;
import bejeweled.gui.Buttons;
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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private static File file;

	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public final void start(Stage primaryStage) throws Exception {
		file = new File("saveFile.txt");
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
				
		// load background
		Image background = new Image("Images/MenuBackground.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		
		// make two buttons for the menu
		Button newGameButton = Buttons.menuButton("NEW GAME", 170, 270);
		
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Sounds.getInstance().playSelectSound();
				switchGame(false);
				Logger.getInstance().writeLineToLogger("The game has started.");
			}
		}); //start the game
		
		Button exitButton = Buttons.menuButton("EXIT", 290, 430);

		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		
		Button continueButton = Buttons.menuButton("CONTINUE", 183, 110);
		continueButton.setOpacity(0.35);
		continueButton.setDisable(true);
		
		if(file.exists()) {
			continueButton.setDisable(false);
			continueButton.setOpacity(1);
			continueButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					//Load the save file if there is one
					//Go to the loaded game screen
					//switchGame(true);
				}
			});
			
		}

		root.getChildren().addAll(imgView, newGameButton, exitButton, continueButton);
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
	public static final void switchGame(boolean savedGame) {
		timeline.playFromStart();
		root = new Group();
	    Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		

		Rectangle rect = new Rectangle(210, 35, Color.CHOCOLATE);
		rect.setLayoutX(555);
		rect.setLayoutY(565);
		
		
		
		root.getChildren().addAll(imgView, rect);
   		Sounds.getInstance().playBackgroundSound();
   		
   		if(!savedGame) {
   			scene = new GameScene(root);
   		}
		
		  new AnimationTimer()
		    {
		        public void handle(long currentNanoTime) {
		        	scene.getGraphicsContext().clearRect(0, 0, 800, 600);
		            scene.draw();
		        }
		    }.start();
		    
	    stage.setScene(scene);
	    
	}
	
	public static final void switchContinueGame() {
		
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
	
	public final static void shoutOut() {
							
		// Create a popup for showing text on the board
		Popup popup = new Popup();
		popup.centerOnScreen();
		popup.setWidth(600);
		popup.setHeight(800);
		
		// Add various shouts to Array
		String[] shouts = new String[] {"Good job!", "Keep on going!", "Nice work!"}; 
		
		// Pick a random shout
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(shouts.length);
		String item = shouts[index];
		
		// Include dropshadow
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.0f, 0.0f, 0.0f));
		
		// Format the text
		Text t = new Text();
		t.setEffect(ds);
		t.setCache(true);
		t.setX(60);
		t.setY(200);
		t.setFill(Color.RED);
		t.setText(item);
		t.setFont(Font.font("Helvetica", FontWeight.BOLD,60));
		
		// Put it all together in the pop up
		popup.getContent().addAll(t);
		popup.show(stage);
		root.setDisable(true);
		
		// Show it for 1 second
		int showtime = 1 * 1000;
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(showtime),
		        ae -> popup.hide()));
		timeline.play();
				
	}
			
}