import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author Timo
 *
 */
public class Main extends Application {
	GameScene game;

	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Bejeweled group 30");
	    Group root = new Group();
	    
	    Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		root.getChildren().add(imgView);
	   
		GameScene scene = new GameScene(root);
	    primaryStage.setScene(scene);
	    
	    
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	scene.gc.clearRect(0, 0, 800, 600);
	            scene.draw();
	        }
	    }.start();
	    primaryStage.show();
	}
}
