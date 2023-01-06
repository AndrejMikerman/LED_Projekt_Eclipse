package snake;

import java.awt.event.KeyEvent;
import ledControl.BoardController;
import ledControl.gui.KeyBuffer;
import java.util.ArrayList;

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
		apple = new Apple((int) (Math.random() * 12), (int) (Math.random() * 12));
		speedBoost = new SpeedBoost(0, 0);
		poopQueue = new ArrayList<Poop>();
		gameLogic = new GameLogic(apple, snake, speedBoost, poopQueue);

		pencil.drawSnake(snake);
		pencil.drawAnObject(apple);
		// Hintergrund um das Spielfeld besser zu sehen
		controller.setBackgroundColor(0, 0, 50);
		controller.updateBoard();
	}

	public static void main(String[] args) {
		init();

		while (true) {
			// Cooldown vor dem nachsten Frame
			controller.sleep(gameLogic.getFrameLength());

			KeyEvent event = buffer.pop();
			// Start Situation: Das spiel ist gefroren sonlange keine taste gedrueckt wurde
			if (!gameLogic.isGameStarted()) {
				if (event != null) {
					gameLogic.startGame();
				} else {
					continue;
				}
			}
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
			if (event != null) {
				// die Schlange verandert ihre coordinaten in in Abhangigkeit von der Eingabe

				boolean doDefault = false;
				switch (event.getKeyCode()) {
				case java.awt.event.KeyEvent.VK_UP:
					if (snake.getFacingDirection() != config.Directions.DOWN) {
						snake.moveOnePixel(config.Directions.UP);
						break;
					} else {
						doDefault = true;
						break;
					}
				case java.awt.event.KeyEvent.VK_DOWN:
					if (snake.getFacingDirection() != config.Directions.UP) {
						snake.moveOnePixel(config.Directions.DOWN);
						break;
					} else {
						doDefault = true;
						break;
					}
				case java.awt.event.KeyEvent.VK_RIGHT:
					if (snake.getFacingDirection() != config.Directions.LEFT) {
						snake.moveOnePixel(config.Directions.RIGHT);
						break;
					} else {
						doDefault = true;
						break;
					}
				case java.awt.event.KeyEvent.VK_LEFT:
					if (snake.getFacingDirection() != config.Directions.RIGHT) {
						snake.moveOnePixel(config.Directions.LEFT);
						break;
					} else {
						doDefault = true;
						break;
					}
				}
				if (doDefault) {
					snake.moveOnePixel(snake.getFacingDirection());
				}

			} else {
				// default verhalten der Schlange bei keiner eingabe
				snake.moveOnePixel(snake.getFacingDirection());
			}

			// GAME STATE CHECK
			if (gameLogic.snakeIsHitSnake() || gameLogic.snakeIsHitWall() || gameLogic.snakeIsHitPoop()) {
				// ends Game
				break;
			}
			// POOP

			// decays Poop
			for (Poop poop : poopQueue) {
				poop.decay();
			}
			// removes the dacayed Poop
			if (!poopQueue.isEmpty() && poopQueue.get(0).getCurrentDecayTime() <= 0) {
				poopQueue.remove(0);
			}

			// adds new Poop
			if (snake.hasPoop()) {
				poopQueue.add(snake.getPoop());
			}

			// SPEED BOOST

			// 1 Fall Speedboost ist Inaktiv und Nicht Sichtbar und der Zuffal tritt ein
			if ((int) (Math.random() * config.BOOST_PROBABILITY) == 0 && !speedBoost.isVisible()
					&& !speedBoost.isActive()) {
				// SpeedBoost erhaelt eine der verfuergbaren koordinaten und wird sichtbar
				int[][] availableCoords = gameLogic.getAvailableCoords();
				speedBoost.show();
				speedBoost.relocate(availableCoords[(int) (Math.random() * availableCoords.length)]);
			}
			// 2 Fall SpeedBoost ist sichtbar auf dem Feld
			else if (speedBoost.isVisible()) {
				// wenn die schlage den Boost isst, wird dieser aktiviert,versteckt und die
				// Spielgeschwindlichkeit wird zufaallig schneller oder langsammer
				if (gameLogic.isSnakeEatingSpeedBoost()) {
					speedBoost.setActive(true);
					gameLogic.setFrameLength((int) (config.FRAME_LENGHT_MS
							* ((int) (Math.random() * 2) == 0 ? config.BOOST_SPEED_DOWN_MULTIPLICATOR
									: config.BOOST_SPEED_UP_MULTIPLICATOR)));
					speedBoost.hide();
				}
				// der Boost wird versteckt wenn er zu lange auf dem feld ist
				else if (speedBoost.getAge() > config.BOOST_MAX_AGE) {
					speedBoost.hide();
				} else {
					speedBoost.increaseAge();
				}
			}
			// 3 Fall SpeedBoost ist aktiv
			else if (speedBoost.isActive()) {
				// Der SpeedBoost hat einen timer der hier runtergezahlt wird. Wenn der timer
				// abgelaufen ist wird der Boost deaktiviert und die ausgangs
				// Spielgeschwindlichkeit wird hergestellt
				if (speedBoost.getBoostTimer() > 0) {
					speedBoost.boostCountdown();
				} else {
					speedBoost.resetBoostTimer();
					speedBoost.setActive(false);
					gameLogic.setFrameLength(config.FRAME_LENGHT_MS);
				}

			}

			// APFEL COORDINATEN
			if (apple.getAge() > config.APPLE_MAX_AGE || gameLogic.isSnakeEatingApple()) {
				if (gameLogic.isSnakeEatingApple()) {
					snake.startDigesting();
				}
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
			if (speedBoost.isVisible()) {
				pencil.drawAnObject(speedBoost);
			}
			for (Poop poop : poopQueue) {
				if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME) {
					pencil.drawAnObject(poop);
				}
			}
			// das board wird mit dem neuen array aktuallisiert
			controller.updateBoard();
		}
	}
}