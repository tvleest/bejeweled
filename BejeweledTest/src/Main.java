import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Timo
 *
 */
public class Main extends Application {
	Game game;

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
	    Game scene = new Game(root);
	    primaryStage.setScene( scene );
	    
	    final long startNanoTime = System.nanoTime();
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
