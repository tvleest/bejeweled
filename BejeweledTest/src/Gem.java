import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Gem {

	int dimension = 40;
	int row;
	int col;
	int type; //which of the 6 gems
	Image image;
	
	public Gem(int row, int col, int type){
		this.row = row;
		this.col = col;
		this.type = type;
		loadImage();
	}
	
	private void loadImage() {
		//depending on the type, a different image should be loaded
		//might wanna handle type 0 as well
        ImageIcon ii = null;

		switch (type) {
		case 1:
			ii = new ImageIcon("blue.png");
			break;
		case 2:
			ii = new ImageIcon("green.png");
			break;
		case 3:
			ii = new ImageIcon("orange.png");
			break;
		case 4:
			ii = new ImageIcon("pink.png");
			break;
		case 5:
			ii = new ImageIcon("red.png");
			break;
		case 6:
			ii = new ImageIcon("yellow.png");
			break;
		default:
			break;
		}
		if(ii==null)
			this.image = null; //creates an empty cell
		else
			this.image = ii.getImage();        
    }	

	void setType(int type){
		this.type = type;
		loadImage();
	}
	
	//this method draws a gem, should be called from the paintcomponent
	void draw(Graphics g){
		g.drawImage(image, 235 + col*dimension, 115 + row*dimension, dimension, dimension, null);	
	}

	public void delete() {
		setType(0);
	}

	public void setPosition(int row, int col) {
		// TODO Auto-generated method stub
		this.row = row;
		this.col = col;
	}
}
