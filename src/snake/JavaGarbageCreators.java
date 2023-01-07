package snake;

import java.awt.event.KeyEvent;
import ledControl.BoardController;
import ledControl.LedConfiguration;
import ledControl.gui.KeyBuffer;
import java.util.ArrayList;

import javax.net.ssl.SNIServerName;

/**
 * This is the Game of Snake. <br>
 * It Works just like a typical Snake Game, but with few modifications. <br>
 * Your target is to collect as much Apples as possible, before eventually
 * crashing into something. In the end you will see your Score. <br>
 * <br>
 * Objects in the Game:<br>
 * Apple: you need to collect those for points<br>
 * Poop: Snake leaves trails of poop, when its done digesting the apple. Be
 * careful not to step on it<br>
 * Speed Boost: has a 50/50 chance of slowing or increase the game speed<br>
 * <br>
 * An apple counts as 1 point. When you eat an Apple while the Speed Boost is
 * Active you earn 2 Points
 * 
 * @author JavaGarbageCreators
 *
 */
public class JavaGarbageCreators {
	public static BoardController controller;
	public static KeyBuffer buffer;
	public static Pencil pencil;
	public static Apple apple;
	public static Snake snake;
	public static GameLogic gameLogic;
	public static SpeedBoost speedBoost;
	public static ArrayList<Poop> poopQueue;

	public static void init() {
		// Initialisierung der Objekte
		controller = BoardController.getBoardController();
		buffer = controller.getKeyBuffer();
		pencil = new Pencil(controller);
		snake = new Snake(5, 5);
		apple = new Apple(0, 0);
		speedBoost = new SpeedBoost(0, 0);
		poopQueue = new ArrayList<Poop>();
		gameLogic = new GameLogic(apple, snake, speedBoost, poopQueue);

		// Apfel wird plaziert
		int[][] availableCoords = gameLogic.getAvailableCoords();
		apple.relocate(availableCoords[(int) (Math.random() * availableCoords.length)]);

		// erstes zeichnen der Objekte
		pencil.drawSnake(snake);
		pencil.drawAnObject(apple);
		controller.updateBoard();
	}

	/**
	 * The Snake Game loop
	 */
	public static void Game() {
		while (true) {
			// Cooldown vor dem nachsten Frame
			controller.sleep(gameLogic.getFrameLength());

			KeyEvent event = buffer.pop();
			// Start Situation: Das spiel ist gefroren sonlange keine taste gedrueckt wurde
			if (!gameLogic.isGameStarted()) {
				if (event != null && event.getID() == java.awt.event.KeyEvent.KEY_PRESSED) {
					gameLogic.startGame();
				} else {
					continue;
				}
			}

			// ich brauche nur KEY_PRESSED Events oder null events; Alles andere wird
			// ignoriert
			// TO-DO: Der KeyBUffer kann ueberladen werden, so dass viele eingaben in einer
			// warteschlagen stehen und man keine kontrolle ueber die Schlange hat
			// Vielleicht kann man regulieren wie viele eingaben in dem buffer gleichzeitig
			// drin sind?
			while (event != null && event.getID() != java.awt.event.KeyEvent.KEY_PRESSED) {
				event = buffer.pop();
			}
			// SCHLANGE BEWEGUNG
			boolean snakeMovementSuccessful = gameLogic.gameSnakeMovement(event);
			// GAME STATE CHECK
			if (!snakeMovementSuccessful) {
				// ends Game
				gameLogic.stopGame();
				break;
			}
			// POOP
			gameLogic.gameCheckupPoop();
			// SPEED BOOST
			gameLogic.gameCheckupSpeedBoost();
			// APFEL COORDINATEN
			gameLogic.gameCheckupApple();

			// LED Board wird resetet
			controller.resetColors();
			// soll am ende des loops passieren
			// Alle Objekte werden auf dem board gemalt
			pencil.drawSnake(snake);
			pencil.drawAnObject(apple);
			if (speedBoost.isVisible()) {
				pencil.drawAnObject(speedBoost);
			}
			pencil.drawPoop(poopQueue);
			// das board wird mit dem neuen array aktuallisiert
			controller.updateBoard();
		}
	}

	public static void updateScoreScreen(config.Directions snakeMovingDirection) {
		controller.resetColors();
		snake.moveOnePixel(snakeMovingDirection);
		pencil.drawSnake(snake);
		pencil.drawSnakeScoreBox();
		pencil.drawScore(gameLogic.getScore());
		controller.updateBoard();
	}

	/**
	 * Draws the Score Screen with an animation at the top and a score at the bottom
	 */
	public static void scoreScreen() {
		controller.resetColors();
		snake = new Snake(10, 1);
		pencil.drawSnake(snake);
		System.out.println(gameLogic.getScore());
		// schlange auf dem bildschirm laufen lassen
		int x = 0;
		int y = 0;
		KeyEvent event = buffer.pop();
		// do while is important so the player has time to see his score
		do {
			for (; x < 9; x++) {
				updateScoreScreen(config.Directions.LEFT);
			}
			for (; y < 2; y++) {
				updateScoreScreen(config.Directions.DOWN);
			}
			for (; x > 0; x--) {
				updateScoreScreen(config.Directions.RIGHT);
			}
			for (; y > 0; y--) {
				updateScoreScreen(config.Directions.UP);
			}
			event = buffer.pop();

		} while (event == null);
		// to prevent the game to start right after the score screen
		buffer.clear();
	}

	public static void main(String[] args) {
		while (true) {
			///////// Initial State of the Game////////////
			init();
			///////// The Game/////////////////////////////
			Game();
			////////////// Snake Blinks////////////////////
			pencil.blinkSnake(snake, 5, 150, config.RED_COLOR_RGB(), config.WHITE_COLOR_RGB());
			buffer.clear();
			///////////// Go to Score Screen///////////////
			scoreScreen();
			///////////////////////////////////////////////
			controller.resetColors();
		}

	}
}
