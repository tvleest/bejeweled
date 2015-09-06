import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Timo
 *
 */
public class Main extends JFrame {
	JLabel highscores;
	JLabel score;
	Game game;
	
	/**
	 * Constructor
	 */
	public Main(){
		initializeGame();	
	}
	
	/**
	 * Set up the game
	 */
	private void initializeGame() {		
		highscores = new JLabel("Highscores");
		game = new Game();
		add(game);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args) {		
				Main m = new Main();
				m.setVisible(true);
	}
}
