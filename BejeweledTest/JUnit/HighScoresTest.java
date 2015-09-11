import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HighScoresTest {


	@Test
	public void testAddHighScore(){
		ArrayList<Integer> inputScores = new ArrayList<Integer>(Arrays.asList(15, 30, 150, 400, 4200, 510, 299, 100, 50, 1));
		ArrayList<Integer> expectedScores = new ArrayList<Integer>(Arrays.asList(4200, 3500, 510, 400, 299, 150, 100, 50, 30, 15));
		HighScores hs = new HighScores();
		hs.setAllScores(inputScores);
		hs.addHighScore(3500);
		assertEquals(expectedScores, hs.getAllScores());
	}
	
	@Test
	public void testReadWriteScoreFile(){
		ArrayList<Integer> allScoreswrite = new ArrayList<Integer>(Arrays.asList(999, 800, 700, 444, 333, 222, 123, 100, 80, 20));
		ArrayList<Integer> allScoresread = null;
		HighScores hs = new HighScores();
		hs.setFilename("testfile.txt");
		hs.setAllScores(allScoreswrite);
		try {
			hs.writeScoreFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			allScoresread = hs.readScoreFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(allScoreswrite, allScoresread);
	}
}

