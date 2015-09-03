import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener{
	
	Board board = new Board(8);
	
	public Game(){
		addMouseListener(this);
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        	board.draw(g);
    }

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int xvar = e.getX();
        int yvar = e.getY();
        //calc which diamond is clicked
        int col = xvar/40;
        int row = yvar/40;
        if (col < 8 && row < 8) {
        	board.delete(row, col);
            repaint();
        }
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
