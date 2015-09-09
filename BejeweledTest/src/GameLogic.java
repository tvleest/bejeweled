import javafx.scene.canvas.GraphicsContext;

public class GameLogic {

	private Board board;
	private int score;
	private int scorePerGem = 10; //TODO: implement score per gem
	private int time;
	private final int TIMEPERGEM = 5;
	private final int DELAY = 1000;
	
	public GameLogic(int offsetx, int offsety){
		score = 0;
		time = 90;
		board = new Board(8, offsetx, offsety);
	}

	public Board getBoard() {
		return board;
	}

	public void draw(GraphicsContext gc) {
		board.draw(gc);
		drawScore(gc);
		drawTime(gc);
	}
	
	public void handleMouseClicked(int row, int col) {
//		TODO:Game.GameSounds.playAudio("select");
    	if(board.selectedgem==null){ //first click, select the gem that is clicked
    		board.selectedgem=board.gems[row][col];
    		board.gems[row][col].setSelected(true);
    	}
    	else //second click, check if the gems are neighbours and can be swapped
    	{	
    		board.secondGem = board.gems[row][col];
    		if (board.swap(board.selectedgem.getRow(), board.selectedgem.getCol(), row, col)){
    			boolean first = board.deleteRows(board.selectedgem); 
    			boolean second = board.deleteRows(board.secondGem);
        		if (first) {
        			updateScore();
        			updateTime();
        		}
    			if(first == false && second == false) { //if there are no combinations found after the move
    				board.swap(board.secondGem.getRow(), board.secondGem.getCol(), row, col); //switches the two switched gems back
    				//TODO: error sound
    			}
    		}
    		else{
    			//TODO: error sound;
    		}
    		board.selectedgem.setSelected(false);
    		board.selectedgem=null; //deselect gem
    		board.secondGem=null; 
    	}
	}
	
	/**
	 * This method will update the score
	 */
	public void updateScore() {
		score += scorePerGem;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @param gc GraphicsContext to draw to
	 */
	public void drawScore(GraphicsContext gc) {
		String s = "Score: ";
		s += score;
	    gc.fillText( s, 60, 80 );
	}
	
	/**
	 * update the time
	 */
	public void updateTime() {
		time += TIMEPERGEM;
	}
	
	/**
	 * @return the current time
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * @param gc GraphicsContext to draw to
	 * Draws the time to the scene
	 */
	public void drawTime(GraphicsContext gc) {
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if(seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		gc.fillText(s, 60, 60 );
	}
}
