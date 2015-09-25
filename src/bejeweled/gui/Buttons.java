package bejeweled.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A class dedicated to making standard buttons for the GUI.
 * @author group30
 *
 */
public class Buttons {
	
	/**
	 * Makes a standard button for the main menu.
	 * @param label - The text to put on the button (String).
	 * @param x - The x-coordinate to put the button on.
	 * @param y - The y-coordinate to put the button on.
	 * @return - The main menu button.
	 */
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
	
	/**
	 * Makes a standard button for the sub menu in the game screen.
	 * @param label - The text to put on the button(String).
	 * @param image - The image to put on the button.
	 * @param x - the x-coordinate to put the button on.
	 * @param y - the y-coordinate to put the button on.
	 * @return - The sub menu button
	 */
	public static Button subMenuButton(String label, Image image, int x, int y) {
		ImageView imgView = new ImageView(image);
		
   		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		Button button = new Button(label, imgView);
		Background buttonBack1 = new Background(new BackgroundFill(Color.CHOCOLATE, null, null));
		Background buttonBack2 = new Background(new BackgroundFill(Color.BROWN, null, null));
		button.setBackground(buttonBack1);
		button.setLayoutX(x);
		button.setLayoutY(y);
		
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
	
	/**
	 * Makes a standard pause menu button.
	 * @param label - Text to put on the button (String).
	 * @param x - x-coordinate to put the button on.
	 * @param y - y-coordinate to put the button on.
	 * @return - The pause menu button.
	 */
	public static Button pauseMenuButton(String label, int x, int y) {
		Font font = new Font("Helvetica", 22);
		Button button = new Button(label);
		button.setFont(font);
		button.setLayoutX(x);
		button.setLayoutY(y);
		Background buttonBack = new Background(new BackgroundFill(Color.ALICEBLUE, null, null));
		button.setBackground(buttonBack);
		
		//Darker color when hovering over button
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
//				button.setBackground(buttonBack2);
			}
		});
		
		//Restore original color when mouse exits
		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent m) {
//				button.setBackground(buttonBack1);
			}
		});
		return button;
	}
}
