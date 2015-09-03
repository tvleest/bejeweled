import javax.swing.JFrame;

public class Main extends JFrame {

	public Main(){
		
		initializeGame();	
		
	}
	
	private void initializeGame() {
		// TODO Auto-generated method stub
		
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
