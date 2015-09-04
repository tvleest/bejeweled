import java.applet.*;
import java.util.HashMap;
import java.net.*;

/**
 * @author Hugo
 * @version 1.1
 * @This class makes it possible to play sound files during Bejeweled
 */

public class Sounds {
	HashMap<String,AudioClip> GameSounds;
	
	// method to locate the sounds
	public Sounds() {
		AudioClip select = null;
		// more sounds to come and insert here
		try {
			select = Applet.newAudioClip(new URL("file:select.wav"));
		} catch (Exception error) {System.out.println(error.getMessage());}
		GameSounds = new HashMap(1); //HashMap(n) n = number of sounds
		GameSounds.put("select", select);
		}
	
	// 	method to play the sounds
    public void playAudio(String name){
        AudioClip sound = GameSounds.get(name);
        sound.play();
	}
	
}
