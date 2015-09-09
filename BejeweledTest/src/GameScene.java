import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class GameScene extends Scene{
//TODO: public static Sounds GameSounds = new Sounds();
//to use the sounds in this class
	GraphicsContext gc;
	private GameLogic gamelogic;
	public final int OFFSETX = 235;
	public final int OFFSETY = 115;
	
	/**
	 * Constructor
	 */
	public GameScene(Group root){
		super(root);
		Canvas canvas = new Canvas(800, 600);
   		root.getChildren().add( canvas );
   		gc = canvas.getGraphicsContext2D();
   		gamelogic = new GameLogic(OFFSETX, OFFSETY);
   		
   		//this will handle mouseclicks
   		this.setOnMousePressed(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                    	//get the coordinates of the mouse pressed event
                		int xvar = (int) (e.getX() - OFFSETX);
                        int yvar = (int) (e.getY() - OFFSETY);
                        //calculate which column and row are clicked, integers will be rounded down by default
                        int col = xvar/40;
                        int row = yvar/40;
                        //check if the col and row are inside the board
                        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
                        	gamelogic.handleMouseClicked(row, col);
                        }
                    }
                });
        draw();
	}
	
	public void draw() {
			gamelogic.draw(gc);
	    }
}
