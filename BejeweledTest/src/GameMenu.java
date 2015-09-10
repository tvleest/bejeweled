import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * @author Hugo
 * Class to make the game menu
 */


public class GameMenu extends Main {
	
	protected void start (Pane mainMenuRoot) {
		
		Font font = Font.font(72);
		
		// load background
		Image background = new Image("Images/Background.png");
		ImageView imgView = new ImageView(background);
		imgView.setFitHeight(600);
		imgView.setFitWidth(800);
		
		// make two buttons for the menu
		Button startGameButton = new Button("START GAME");
		startGameButton.setFont(font);
		startGameButton.setOnAction(Main.start()); //start the game
		
		Button exitButton = new Button("EXIT");
		exitButton.setFont(font);
		exitButton.setOnAction(); //exit the menu

    
}
}
