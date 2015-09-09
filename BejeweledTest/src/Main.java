import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
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
