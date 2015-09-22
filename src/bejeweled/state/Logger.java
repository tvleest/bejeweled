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


/**
 * @author Job, Timo
 *
 */
public final class Logger {
	
	private FileWriter filewriter;
	private BufferedWriter writer;
	private File file;
	private static Logger logger = null;
	private int flushCounter = 0;
	private int amountOfLinesPerFlush = 5;
	
	private Logger() {
		Date d = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(d);
		file = new File("Logs/" + date +".txt");
		try {
			filewriter= new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Something went wrong with the FileWriter in Logger");
		}
		writer = new BufferedWriter(filewriter);
	}
	
	//This method is part of the singleton pattern
	public static Logger getInstance() {
	      if(logger == null) {
	         logger = new Logger();
	      }
	      return logger;
	}
	
	public void writeLineToLogger(String s){
		flushCounter++;
		Date d = new Date();
		String date = new SimpleDateFormat("HH:mm:ss").format(d);
		String logline = date + "  -  " + s + "\r\n";
		try {
			writer.write(logline);
			if(flushCounter>amountOfLinesPerFlush){
				writer.flush();
				flushCounter=0;
			}
		} catch (IOException e) {
			System.out.println("Something went wrong while writing to the BufferedWriter in Logger");
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void disposeLogger() {
		try {
			writer.flush();
			writer.close();
			logger = null;
		} catch (IOException e) {
			System.out.println("Something went wrong while closing the BufferedWriter in Logger");
		}
	}
	
	public File getFile() {
		return file;
	}
	
}
