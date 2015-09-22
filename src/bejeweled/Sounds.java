package bejeweled;

import java.io.File;

import bejeweled.state.Logger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class used for playing sound effects.
 * @author - group 30
 *
 */
public final class Sounds {
		private static AudioClip selectSound;
		private static AudioClip combinationSound;
		private static AudioClip backgroundSound;
		private static AudioClip errorSound;
		private static AudioClip gameOverSound;
		
		private static Sounds sounds = null;
    
		/**
		 * Assigns the sound to be played.
		 */
		private Sounds() {
			selectSound = new AudioClip(new File("src/Sounds/select.wav").toURI().toString());
			 combinationSound = new AudioClip(new File("src/Sounds/combination.wav").toURI().toString());
			 combinationSound.setVolume(0.5);
			 backgroundSound = new AudioClip(new File("src/Sounds/bejeweled.wav").toURI().toString());
			 backgroundSound.setVolume(0.5);
			 backgroundSound.setCycleCount(AudioClip.INDEFINITE);
			 errorSound = new AudioClip(new File("src/Sounds/error.wav").toURI().toString());
			 gameOverSound = new AudioClip(new File("src/Sounds/gameover.wav").toURI().toString());
		}
		
		//This method is part of the singleton pattern
		public static Sounds getInstance() {
		      if(sounds == null) {
		         sounds = new Sounds();
		      }
		      return sounds;
		}
		
		/**
		 * Plays the select sound effect.
		 */
		public void playSelectSound() {
			 selectSound.play();
		}
		
		/**
		 * Plays the combination sound effect.
		 */
		public void playCombinationSound() {
			 combinationSound.play();
		}
		
		/**
		 * Plays the background sound effect.
		 */
		public void playBackgroundSound() {
			  backgroundSound.play();
		}
		
		/**
		 * Stops the background sound effect.
		 */
		public void stopBackgroundSound() {
			  backgroundSound.stop();
		}
		
		/**
		 * Plays the error sound effect.
		 */
		public void playErrorSound() {
			 errorSound.play();
		}
		
		/**
		 * Plays the game over sound effect.
		 */
		public void playGameOverSound() {
			 gameOverSound.play();
		}


}

