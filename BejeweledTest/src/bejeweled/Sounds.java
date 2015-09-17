package bejeweled;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class used for playing sound effects.
 * @author - group 30
 *
 */
public class Sounds {
		AudioClip selectSound;
		AudioClip combinationSound;
		AudioClip backgroundSound;
    
		/**
		 * Assigns the sound to be played.
		 */
		public Sounds() {
			 selectSound = new AudioClip(new File("src/Sounds/select.mp3").toURI().toString());
			 combinationSound = new AudioClip(new File("src/Sounds/combination.mp3").toURI().toString());
			 backgroundSound = new AudioClip(new File("src/Sounds/bejeweled.mp3").toURI().toString());
			 backgroundSound.setVolume(0.5);
		}
		
		/**
		 * Plays the sound effect.
		 */
		public final void playSelectSound() {
			 selectSound.play();
		}
		
		/**
		 * Plays the sound effect.
		 */
		public final void playCombinationSound() {
			 combinationSound.play();
		}
		
		/**
		 * Plays the sound effect.
		 */
		public final void playBackgroundSound() {
			 backgroundSound.cycleCountProperty();
			 backgroundSound.play();
		}

}

