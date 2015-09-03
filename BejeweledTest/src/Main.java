import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	JLabel highscores;
	
	public Main(){
		
		initializeGame();	
		
	}
	
	private void initializeGame() {
		// TODO Auto-generated method stub
		
		highscores = new JLabel("Highscores");
		add(new Game());
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
				Main m = new Main();
				m.setVisible(true);
	}
}
