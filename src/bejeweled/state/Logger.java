package bejeweled.state;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
	
	FileWriter writer;
	File file;
	
	public Logger() {
		Date d = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(d);
		file = new File("Logs/" + date);
	}
	
	public void printText() {
		try {
			writer = new FileWriter(file);
			writer.write("Er gebeurde iets!");
			writer.close();
		} catch (IOException e) {
			
		}
	}
}
