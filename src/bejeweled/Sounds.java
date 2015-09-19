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
		static AudioClip selectSound;
		static AudioClip combinationSound;
		static AudioClip backgroundSound;
		static AudioClip errorSound;
		static AudioClip gameOverSound;
    
		/**
		 * Assigns the sound to be played.
		 */
		public Sounds() {
			selectSound = new AudioClip(new File("src/Sounds/select.wav").toURI().toString());
			 combinationSound = new AudioClip(new File("src/Sounds/combination.wav").toURI().toString());
			 combinationSound.setVolume(0.5);
			 backgroundSound = new AudioClip(new File("src/Sounds/bejeweled.wav").toURI().toString());
			 backgroundSound.setVolume(0.5);
			 errorSound = new AudioClip(new File("src/Sounds/error.wav").toURI().toString());
			 gameOverSound = new AudioClip(new File("src/Sounds/gameover.wav").toURI().toString());
		}
		
		/**
		 * Plays the select sound effect.
		 */
		public static final void playSelectSound() {
			 selectSound.play();
		}
		
		/**
		 * Plays the combination sound effect.
		 */
		public static final void playCombinationSound() {
			 combinationSound.play();
		}
		
		/**
		 * Plays the background sound effect.
		 */
		public static final void playBackgroundSound() {
			  backgroundSound.setCycleCount(AudioClip.INDEFINITE);
			  backgroundSound.play();
		}
		
		/**
		 * Plays the error sound effect.
		 */
		public static final void playErrorSound() {
			 errorSound.play();
		}
		
		/**
		 * Plays the game over sound effect.
		 */
		public static final void playGameOverSound() {
			 gameOverSound.play();
		}


}

