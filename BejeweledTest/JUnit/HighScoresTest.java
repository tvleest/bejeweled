import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class HighScoresTest {


	@Test
	public void testAddScore(){
		ArrayList<Integer> allScorees = new ArrayList<Integer>();
		HighScores hs = new HighScores();
		hs.addHighScore(34);
		hs.addHighScore(200);
		hs.addHighScore(333);
		hs.addHighScore(2);
		hs.addHighScore(80);
		hs.addHighScore(100);
		hs.addHighScore(100);
		hs.addHighScore(100);
		hs.addHighScore(100);
		hs.addHighScore(100);
		try {
			hs.writeScoreFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


