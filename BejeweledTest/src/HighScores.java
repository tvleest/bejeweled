import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Class that deals with the highscores of our game.
 * @author - Group 30.
 *
 */
public class HighScores {
	
	/**
	 * Define the highscore file.
	 */
	private static String filename = "highscores.txt";
	
	/**
	 *  Define the array for the highscores.
	 */
	private ArrayList<Integer> allScores;
	
	/**
	 *  Read high score file and catch exceptions.
	 */
	public HighScores() {
		try {
			allScores = readScoreFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	
	/**
	 * Add highscores from file to array allScores.
	 * @param score - score to add to the highscores list.
	 */
	public void addHighScore(int score) {
		allScores.add(score);
		Collections.sort(allScores);
		Collections.reverse(allScores);
		while (allScores.size() > 10) {
			allScores.remove(10);
		}
	}
	
	/**
	 * @return - An arraylist of highscores.
	 * @throws IOException
	 */
	// read high score file and catch exceptions
	public ArrayList<Integer> readScoreFile() throws IOException {
	    ArrayList<Integer> allScores = new ArrayList<Integer>();
	    // read the file
	    BufferedReader readfile = new BufferedReader(new FileReader(filename));
	    String line = null;
	    // read line per line and add to allScores array
		while ((line = readfile.readLine()) != null) {
			allScores.add(Integer.parseInt(line));
		}
		// close the file when done
		readfile.close();
	    return allScores;
	}
		    
	/**
	 * Write scores to the highscore file.
	 * @throws IOException when file cannot be written
	 */
	public void writeScoreFile() throws IOException {
		// try to write it to highscore.txt and close when done
		try {
			FileWriter out = new FileWriter(filename);
			for (int score : allScores) {
				out.write(score + "\n");
			}
			out.close();
		}
		// catch errors
		catch (IOException e) {
	        System.out.println("The application wasn't able to write the scores to " + filename);
	   }
	}

	/**
	 * @param file - String of the filename.
	 * Overwrite file name so we can write to a testfile
	 */
	public void setFilename(String file) {
		this.filename = file;
	}

	
	/**
	 * @return - Arraylist of the highscores.
	 */
	public ArrayList<Integer> getAllScores() {
		return allScores;
	}
	
	/**
	 * Set ArrayList of highscores.
	 * @param allScores - ArrayList of highscores to set allscore to.
	 */
	public void setAllScores(ArrayList<Integer> allScores) {
		this.allScores = allScores;
	}
	
	
}
		
	