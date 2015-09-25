package bejeweled.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A class dedicated to making standard buttons for the GUI.
 * @author group30
 *
 */
public class Buttons {
	
	public static Button menuButton(String label, int x, int y) {
		Font font = new Font(72);
		Button button = new Button(label);
		button.setFont(font);
		button.setLayoutX(x);
		button.setLayoutY(y);
		CornerRadii corners = new CornerRadii(10);
		Insets insets = new Insets(10);
		Background buttonBack1 = new Background(new BackgroundFill(Color.GOLD, corners, insets));
		Background buttonBack2 = new Background(new BackgroundFill(Color.GOLDENROD, corners, insets));
		button.setBackground(buttonBack1);
		
		//Darker color when hovering over button
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				button.setBackground(buttonBack2);
			}
		});
		
		//Restore original color when mouse exits
		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				button.setBackground(buttonBack1);
			}
		});
		
		return button;
	}
	
	public static Button subMenuButton(String label, Image image, int x, int y) {
		ImageView imgView = new ImageView(image);
		
   		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		Button button = new Button(label, imgView);
		Background buttonBack1 = new Background(new BackgroundFill(Color.WHITE, null, null));
		Background buttonBack2 = new Background(new BackgroundFill(Color.GREY, null, null));
		button.setBackground(buttonBack1);
		button.setLayoutX(x);
		button.setLayoutY(y);
		
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				button.setBackground(buttonBack2);
			}
			
		});
		
		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
				button.setBackground(buttonBack1);
			}
		});
		return button;
	}
}
