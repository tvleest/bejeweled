package bejeweled.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.gui.Buttons;
import bejeweled.gui.Popups;
import bejeweled.state.Time;

/**
 * @author Timo This class is our Panel, handling mouse events and drawing the
 *         game
 */
public final class GameScene extends Scene {
	// TODO: public static Sounds GameSounds = new Sounds();

	// to use the sounds in this class
	private static GraphicsContext gc;
	private static GameLogic gamelogic;
	private final int offsetx = 235;
	private final int offsety = 115;

	/**
	 * GameScene Constructor. Prepares the UI of the root and mouseclick
	 * handlers.
	 */
	public GameScene(Group root, Stage stage) {
		super(root);
		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);

		Image saveIcon = new Image("Images/save.png");
		Button saveButton = Buttons.subMenuButton(null, saveIcon, 700, 565);

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Time time = GameLogic.getTime();
				String stime = time.toString().substring(11, time.toString().length());
				int score = Board.getScore();
				Gem[][] board = Board.getGems();
				String save = stime + "\n" + score + "\n";
				System.out.println(board.length);
				for (int row = 0; row < board.length; row++) {
					for (int col = 0; col < board.length; col++) {
						save += board[row][col].getType() + "\n";
					}
				}
				try {
					FileWriter fw = new FileWriter("savefile.txt");
					fw.write(save);
					fw.close();
				} catch (IOException e1) {
					System.out.println("Something went wrong with the FileWriter in GameScene");
				}
			}
		});

		Image hintIcon = new Image("Images/hintbutton.png");
		Button hintButton = Buttons.subMenuButton(null, hintIcon, 655, 565);

		hintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gamelogic.getBoard().hintCheck(0, 0);
			}
		});

		Image pauseIcon = new Image("Images/pause.png");
		Button pauseButton = Buttons.subMenuButton(null, pauseIcon, 610, 565);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.getTimeline().pause();
				Popup popup = Popups.pausePopup(root);
				popup.show(stage);
				root.setDisable(true);

			}
		});

		Image musicIcon = new Image("Images/music2.png");
		Button musicButton = Buttons.subMenuButton(null, musicIcon, 565, 565);

		musicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (Sounds.getInstance().backgroundSoundPlaying()) {
					Sounds.getInstance().stopBackgroundSound();
				} else {
					Sounds.getInstance().playBackgroundSound();
				}
			}
		});

		root.getChildren().addAll(hintButton, saveButton, pauseButton, musicButton);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(new Font("Helvetica", 15));
		gamelogic = new GameLogic(offsetx, offsety, true);

		// this will handle mouse clicks
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				// get the coordinates of the mouse pressed event
				int xvar = (int) (e.getX() - offsetx);
				int yvar = (int) (e.getY() - offsety);
				// calculate which column and row are clicked
				int col = xvar / 40;
				int row = yvar / 40;
				// check if the col and row are inside the board
				if (col < 8 && row < 8 && col >= 0 && row >= 0) {
					gamelogic.handleMouseClicked(row, col);
				}
			}
		});
		draw();
	}

	/**
	 * requests the game logic to draw the UI.
	 */
	public void draw() {
		gamelogic.draw(gc);
		gamelogic.getTime().drawTime(gc);
	}

	/**
	 * requests to decrement the timer (each second).
	 */
	public void decrementTime() {
		gamelogic.getTime().decrementTime();
	}

	public GameLogic getGameLogic() {
		return gamelogic;
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}

}
