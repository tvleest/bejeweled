package bejeweled.state;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


public class Logger {
	
	FileWriter writer;
	File file;
	String allLogs;
	
	public Logger() {
		Date d = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(d);
		file = new File("Logs/" + date);
		allLogs = "";
	}
	
	public void printText() {
		try {
			writer = new FileWriter(file);
			writer.write("Er gebeurde iets!");
			writer.close();
		} catch (IOException e) {
			
		}
	}
	
	public void updateLogger(String s) {
		Date d = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(d);
		allLogs += date + "  -  " + s + "\n";
		try {
			writer = new FileWriter(file);
			writer.write(allLogs);
			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with the FileWriter in Logger");
		}
	}
	
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong with the FileWriter in Logger");
		}
	}
	
}
