import java.awt.Toolkit;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Sounds{
	
		private MediaPlayer player;
    
	
		public Sounds(){
			Media Sounds = new Media("Sounds/select.mp3");
			player = new MediaPlayer(Sounds);
		}
	
		public void PlaySounds(){
			player.play();
		}
	
    
}

