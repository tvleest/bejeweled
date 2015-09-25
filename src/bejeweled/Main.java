package bejeweled;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Scanner;

import bejeweled.board.Board;
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
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		scene = new GameScene(new Group(), stage);
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
					Sounds.getInstance().playSelectSound();
					switchGame(true);
					Logger.getInstance().writeLineToLogger("The saved game has been loaded.");
					
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

   		scene = new GameScene(root, stage);
		
   		if(savedGame) {
	    	loadFile();
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
	
	/**
	 * Reads the savefile File and changes the value of score, time and
	 * all the Gems in the Game so it matches the saved Game.
	 */
	public static void loadFile() {
		int x = Board.getOffsetX();
		int y = Board.getOffsetY();
		Scanner sc;
    	int score2 = 0;
		try {
			sc = new Scanner(new File("savefile.txt"));
	    	String time = sc.nextLine();
	    	System.out.println(time);
	    	if(sc.hasNext()) {
	    		String score = sc.nextLine();
	    		score2 = Integer.parseInt(score);
	    		System.out.println(score);
	    	}
	    	Gem[][] board = new Gem[8][8];
	    	for(int row = 0; row < board.length; row++) {
	    		for(int col = 0; col < board.length; col++) {
	    			if(sc.hasNext()) {
	    				String type = sc.nextLine();
	    				System.out.println(type);
	    				GemType gtype = GemType.typeFromString(type);
	    				board[row][col] = new Gem(row, col, x, y, gtype, true);
	    			}
	    		}
	    	}
	
	    	String minutes = time.substring(0, time.indexOf(":"));
	    	String seconds = time.substring(time.length() - 2, time.length());
	    	System.out.println(minutes + " - " + seconds);
	    	int m = Integer.parseInt(minutes);
	    	int s = Integer.parseInt(seconds);
	    	int t = m*60 + s;
	    	bejeweled.state.Time time2 = new bejeweled.state.Time(t);
	    	
			GameLogic.setTime(time2);
			Board.setScore(score2);
			Board.setGems(board);
	    	
	    } catch (FileNotFoundException e) {
			System.out.println("Savefile was not found!");
	    }
	}
	
	public static final void switchContinueGame() {
		
	}
	
	/**
	 * Show a GameOver popup.
	 */
	public static final void gameOver() {
		int score = scene.getGameLogic().getBoard().getScore(); // get score
		
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
	
	public static Timeline getTimeline() {
		return timeline;
	}
}