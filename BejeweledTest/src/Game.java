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
		int xvar = e.getX() - 235;
        int yvar = e.getY() - 115;
        //calc which diamond is clicked
        int col = xvar/40;
        int row = yvar/40;
        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
        	if(board.selectedgem==null)
        		board.selectedgem=board.gems[row][col];
        	else
        	{
        		board.swap(board.selectedgem.row, board.selectedgem.col, row, col);
        		board.selectedgem=null;
        	}
            repaint();
        }
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
