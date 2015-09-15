
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class used for playing sound effects.
 * @author - group 30
 *
 */
public class Sounds {
		/**
		 * The media player.
		 */
		private MediaPlayer player;
    
		/**
		 * Assigns the sound to be played.
		 */
		public Sounds() {
			Media sounds = new Media("Sounds/select.mp3");
			player = new MediaPlayer(sounds);
		}
		
		/**
		 * Plays the sound effect.
		 */
		public final void playSounds() {
			player.play();
		}
	
    
}

