package bejeweled.state.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import bejeweled.state.Logger;

/**
 * Tests for the Logger class.
 * 
 * @author group30
 */
public class LoggerTest {
	Logger logger;
	private final int STARTINDEX = 13;
	
	/**
	 * Set up before every test.
	 */
	@Before
	public final void setUp() {
		logger = new Logger();
	}
	
	/**
	 * Tests the updateLogger method for 1 call, for 1 line.
	 */
	@Test
	public final void updateLoggerTest() {
		File f = logger.getFile();
		logger.writeLineToLogger("Hello World!");
		String line = null;
		try {
			BufferedReader readfile = new BufferedReader(new FileReader(f));
			line = readfile.readLine();
			line = line.substring(STARTINDEX);
			readfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("Hello World!", line);
	}
	
	/**
	 * Tests the updateLogger method for 2 calls, for 2 lines.
	 */
	@Test
	public final void updateLoggerTest2() {
		File f = logger.getFile();
		logger.writeLineToLogger("Hello World!");
		logger.writeLineToLogger("Second test");
		String line = null;
		String line2 = null;
		try {
			BufferedReader readfile = new BufferedReader(new FileReader(f));
			line = readfile.readLine();
			line = line.substring(STARTINDEX);
			line2 = readfile.readLine();
			line2 = line2.substring(STARTINDEX);
			readfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals("Hello World!", line);
		assertEquals("Second test", line2);
	}

}
