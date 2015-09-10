import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class HighScores {
	
	private static String filename = "highscores.txt";
	
	private ArrayList<Integer> allScores;
	
	public HighScores(){
		try {
			allScores = readScoreFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	
	public void addHighScore(int score){
		allScores.add(score);
		Collections.sort(allScores);
		Collections.reverse(allScores);
		while(allScores.size()>10){
			allScores.remove(10);
		}
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<Integer> readScoreFile() throws IOException{
	    ArrayList<Integer> allScores = new ArrayList<Integer>();
	    // read the file
	    BufferedReader readfile = new BufferedReader(new FileReader(filename));
	    String line = null;
		while ((line = readfile.readLine()) != null) {
			allScores.add(Integer.parseInt(line));
		}
		readfile.close();
	    return allScores;
	}
		    
	/**
	 * @throws IOException when file cannot be written
	 */
	public void writeScoreFile() throws IOException{
		// try to write it to highscore.txt and close when done
		try {
			FileWriter out= new FileWriter(filename);
			for(int score : allScores){
				out.write(score+"\n");
			}
			out.close();
		}
		// catch errors
		catch(IOException e) {
	        System.out.println("The application wasn't able to write the scores to "+filename);
	   }
	}

	/**
	 * @param filename
	 * Overwrite file name so we can write to a testfile
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	/**
	 * @return the highscores
	 */
	public ArrayList<Integer> getAllScores() {
		return allScores;
	}
}
		
	