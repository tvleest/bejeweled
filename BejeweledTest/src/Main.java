import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Timo
 *
 */
public class Main extends JFrame {
	JLabel highscores;
	JLabel score;
	
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
		add(new Game());
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
