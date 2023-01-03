package snake;

import java.awt.event.KeyEvent;

import ledControl.BoardController;
import ledControl.gui.KeyBuffer;

public class JavaGarbageCreators {
	public static BoardController controller;
	public static KeyBuffer buffer;
	public static Pencil pencil;
	public static Apple apple;
	public static Snake snake;
	public static GameLogic gameLogic;

	public static void init() {
		// Initialisierung der Objekte
		controller = BoardController.getBoardController();
		buffer = controller.getKeyBuffer();
		pencil = new Pencil(controller);
		snake = new Snake(5, 5);
		apple = new Apple((int) (Math.random() * 12), (int) (Math.random() * 12));
		gameLogic = new GameLogic(apple, snake);

		pencil.drawSnake(snake);
		pencil.drawAnObject(apple);
		// Hintergrund um das Spielfeld besser zu sehen
		controller.setBackgroundColor(0, 0, 50);
	}

	public static void main(String[] args) {
		init();

		while (true) {
			KeyEvent event = buffer.pop();
			boolean firstKeyPress = false;
			if (event.getID() != java.awt.event.KeyEvent.KEY_PRESSED || !firstKeyPress) {
				firstKeyPress = !firstKeyPress;
				continue;
			}
			// Cooldown vor dem nachsten Frame
			controller.sleep(config.FRAME_LENGHT_MS);
			// LED Board wird resetet
			controller.resetColors();

			// ich brauche nur KEY_PRESSED Events oder null events; Alles andere wird
			// ignoriert
			// TO-DO: Der KeyBUffer kann ueberladen werden, so dass viele eingaben in einer
			// warteschlagen stehen und man keine kontrolle ueber die Schlange hat
			// Vielleicht kann man regulieren wie viele eingaben in dem buffer gleichzeitig
			// drin sind?

			while (event != null && event.getID() != java.awt.event.KeyEvent.KEY_PRESSED) {
				event = buffer.pop();
			}

			// SCHLANGE COORDINATEN
			// bewegt die schlange falls eine eingabe passiert ist
			// BUG: Die gegensetzliche bewegungen sind noch nicht richting behandelt
			if (event != null) {
				// die Schlange verandert ihre coordinaten in in Abhangigkeit von der Eingabe
				switch (event.getKeyCode()) {
				case java.awt.event.KeyEvent.VK_UP:
					if (snake.getFacingDirection() != config.Directions.DOWN) {
						snake.moveOnePixel(config.Directions.UP);
						break;
					}
				case java.awt.event.KeyEvent.VK_DOWN:
					if (snake.getFacingDirection() != config.Directions.UP) {
						snake.moveOnePixel(config.Directions.DOWN);
						break;
					}
				case java.awt.event.KeyEvent.VK_RIGHT:
					if (snake.getFacingDirection() != config.Directions.LEFT) {
						snake.moveOnePixel(config.Directions.RIGHT);
						break;
					}
				case java.awt.event.KeyEvent.VK_LEFT:
					if (snake.getFacingDirection() != config.Directions.RIGHT) {
						snake.moveOnePixel(config.Directions.LEFT);
						break;
					}
				default:
					snake.moveOnePixel(snake.getFacingDirection());
					break;

				}

			} else {
				// default verhalten der Schlange bei keiner eingabe
				snake.moveOnePixel(snake.getFacingDirection());
			}
			// GAME STATE CHECK
			if (gameLogic.snakeIsHitSnake() || gameLogic.snakeIsHitWall()) {
				// ends Game
				break;
			}
			// APFEL COORDINATEN
			if (apple.getAge() > config.APPLE_MAX_AGE || gameLogic.checkSnakeEatingApple()) {
				int[][] availableCoords = gameLogic.getAvailableCoords();
				// choose randomly from one of the available coords
				apple.relocate(availableCoords[(int) (Math.random() * availableCoords.length)]);
			} else {
				apple.increaseAge();
			}

			// soll am ende des loops passieren
			// Alle Objekte werden auf dem board gemalt
			pencil.drawSnake(snake);
			pencil.drawAnObject(apple);
			// das board wird mit dem neuen array aktuallisiert
			controller.updateBoard();
		}
	}
}