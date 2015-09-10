import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Timo
 *
 */
public class Main extends Application {
	GameScene scene;
	Stage stage;
	Timeline timeline;

	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		scene = new GameScene(new Group(), this);
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
	public void switchMenu() {
		Group root = new Group();
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
			}
		}); //start the game
		
		Button exitButton = new Button("EXIT");
		exitButton.setFont(font);
//		exitButton.setOnAction(); //exit the menu
		
		root.getChildren().addAll(imgView, startGameButton);
		if(stage.getScene() == null) {
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(root);
		}
	}
	
	/**
	 * Switch the current screen to the Bejeweled screen.
	 */
	public void switchGame() {
		timeline.playFromStart();
		Group root = new Group();
	    Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		root.getChildren().add(imgView);
		scene = new GameScene(root, this);
		
		  new AnimationTimer()
		    {
		        public void handle(long currentNanoTime)
		        {
		        	scene.gc.clearRect(0, 0, 800, 600);
		            scene.draw();
		        }
		    }.start();
	    stage.setScene(scene);
	    
	}
}