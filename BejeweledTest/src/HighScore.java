import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HighScore {
	
	public void getHighScore() {
		int highscore = Board.getScore();
	}
	
	public static void readScoreFile() throws IOException{
		
		// create token1
	    String token1 = "";
	    // read the file
	    Scanner readfile = new Scanner(new File("highscores.txt")).useDelimiter(",\\s*");
	    // make the array to store the scores in
	    List<String> allScores = new ArrayList<String>();

	    // while loop for reading the file
	    while (readfile.hasNext()) {
	      // find next line
	      token1 = readfile.next();
	      allScores.add(token1);
	    }
	    // close file when done
	    readfile.close();

	    String[] scoreArray = allScores.toArray(new String[0]);

	    for (String s : scoreArray) {
	      System.out.println(s);
			
		}
	}
		    
	public void writeScoreFile() {
		// get high score from the board class
		int highscore = Board.getScore();
		
		// try to write it to highscore.txt and close when done
		try {
			PrintWriter out= new PrintWriter(new FileWriter("highscores.txt"));
			out.println(highscore + ",");
			out.close();
		}
		// catch errors
		catch(IOException e) {
	        System.out.println("Error!");
	   }
	}
	
}
		
	