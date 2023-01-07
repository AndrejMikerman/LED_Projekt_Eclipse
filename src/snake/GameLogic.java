
package snake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This Class takes care of the calculation for the logic part of the game. It
 * knows only the BoardObjects in the Game.
 *
 */
public class GameLogic {

	private Snake snake;
	private Apple apple;
	private SpeedBoost speedBoost;
	private int frameLength;
	private boolean gameStarted;
	private ArrayList<Poop> poopQueue;
	private int score;

	public GameLogic(Apple apple, Snake snake, SpeedBoost speedBoost, ArrayList<Poop> poopQueue) {
		this.apple = apple;
		this.snake = snake;
		this.speedBoost = speedBoost;
		this.poopQueue = poopQueue;
		this.gameStarted = false;
		this.addScore(0);
		setFrameLength(config.DEFAULT_FRAME_LENGHT_MS);
	}

	/**
	 * Current frame Length
	 * 
	 * @return
	 */
	public int getFrameLength() {
		return frameLength;
	}

	/**
	 * Sets the time between each game frame
	 * 
	 * @param frameLength time in milliseconds
	 */
	public void setFrameLength(int frameLength) {
		this.frameLength = frameLength;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
	// The snakeIsHit Methods are no longer needed since the check is being made in
	// the canSnakeMoveToDirection Method before the snake moves

	/**
	 * Checks if snakes head collided with one of the body parts
	 * 
	 * @return true or false
	 */
	public boolean snakeIsHitSnake() {
		SnakeBody bodyPart = snake.getHead().getNextBodyPart();
		for (; bodyPart != null; bodyPart = bodyPart.getNextBodyPart()) {
			if (snake.getHead().getXPos() == bodyPart.getXPos() && snake.getHead().getYPos() == bodyPart.getYPos()) {
				// collision occured
				return true;
			}
		}
		// no collision
		return false;
	}

	/**
	 * Checks if snakes head collided with the Game Border
	 * 
	 * @return true or false
	 */
	public boolean snakeIsHitWall() {
		return snake.getHead().getXPos() > 11 || snake.getHead().getXPos() < 0 || snake.getHead().getYPos() > 11
				|| snake.getHead().getYPos() < 0;
	}

	public boolean snakeIsHitPoop() {
		for (Poop poop : poopQueue) {
			if (isOnCoords(poop, snake.getHead().getXPos(), snake.getHead().getYPos())) {
				return true;
			}
		}
		return false;
	}

	public boolean isSnakeHitSomething() {
		return snakeIsHitSnake() || snakeIsHitWall() || snakeIsHitPoop();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Checks if snakes head collided with the Apple.
	 * 
	 * @return true or false
	 */
	public boolean isSnakeEatingApple() {
		// schlange isst ein apfel?
		if (snake.getHead().getXPos() == apple.getXPos() && snake.getHead().getYPos() == apple.getYPos()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if snakes head collided with the SpeedBoost
	 * 
	 * @return true or false
	 */
	public boolean isSnakeEatingSpeedBoost() {
		if (snake.getHead().getXPos() == speedBoost.getXPos() && snake.getHead().getYPos() == speedBoost.getYPos()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the BoardObject has the given x and y position
	 * 
	 * @param object  GameObject
	 * @param xCoords x position
	 * @param yCoords y position
	 * @return true or false
	 */
	private boolean isOnCoords(BoardObject object, int xCoords, int yCoords) {
		return object.getXPos() == xCoords && object.getYPos() == yCoords;
	}

	/**
	 * Counts all Pixel Occupied by Board Objects
	 * 
	 * @return sum of occupied pixels
	 */
	private int getOccupiedPixelsCount() {
		int poopCount = 0;
		for (Poop poop : poopQueue) {
			// Poop is only visible after 1 decay
			if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME) {
				poopCount++;
			}
		}
		return snake.getLength() + (speedBoost.isVisible() ? 1 : 0) + (isSnakeEatingApple() ? 0 : 1) + poopCount;
	}

	/**
	 * Checks if the coordinate is Occupied by a BoardObject. edible Parameter
	 * describes if the checked BoardObject is edible. Edible are Apples and
	 * SpeedBoosts.
	 * 
	 * @param xCoord x coordinate
	 * @param yCoord y coordinate
	 * @param edible checks for Apples and SpeedBoosts if true
	 * @return true if coordinate is occupied. false if not occupied.
	 */
	public boolean isCoordinateOccupied(int xCoord, int yCoord, boolean edible) {
		// creates an array with snake coordinates
		int[][] snakeCoords = new int[snake.getLength()][2];
		int index = 0;
		for (SnakeBody currentBodyPart = snake.getHead(); currentBodyPart != null; currentBodyPart = currentBodyPart
				.getNextBodyPart()) {
			snakeCoords[index][0] = currentBodyPart.getXPos();
			snakeCoords[index][1] = currentBodyPart.getYPos();
			index++;
		}
		// check if the coords are not occupied
		if (edible) {
			// speedboost check
			if (speedBoost.isVisible() && isOnCoords(speedBoost, xCoord, yCoord)) {
				return true;
			}
			// apple check
			if (!isSnakeEatingApple() && isOnCoords(apple, xCoord, yCoord)) {
				return true;
			}
		}
		// poop Check
		for (int i = 0; i < poopQueue.size(); i++) {
			Poop poop = poopQueue.get(i);
			if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME && isOnCoords(poop, xCoord, yCoord)) {
				return true;
			}
		}
		// snake coords check
		for (int i = 0; i < snakeCoords.length; i++) {
			if (snakeCoords[i][0] == xCoord && snakeCoords[i][1] == yCoord) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Checks which Coordinates are not occupied by BoardObjects
	 * 
	 * @return Array with available coordinates
	 */
	public int[][] getAvailableCoords() {
		// Array with free Space
		int[][] availableCoords = new int[144 - getOccupiedPixelsCount()][2];
		// Free Coords
		int index = 0;
		for (int xCoord = 0; xCoord < 12; xCoord++) {
			for (int yCoord = 0; yCoord < 12; yCoord++) {
				// if not occupied add to the availableCoords array
				if (!isCoordinateOccupied(xCoord, yCoord, true)) {
					availableCoords[index][0] = xCoord;
					availableCoords[index][1] = yCoord;
					index++;
				}
			}
		}
		return availableCoords;
	}

	/**
	 * Returns the current game state value
	 * 
	 * @return current game state as true or false
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Sets the game start value to true
	 */
	public void startGame() {
		gameStarted = true;
	}

	/**
	 * Sets the game start value to false
	 */
	public void stopGame() {
		gameStarted = false;
	}

	/**
	 * Returns the Score
	 * 
	 * @return score as int
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Adds to the current score
	 * 
	 * @param score is added to the current score
	 */
	public void addScore(int score) {
		this.score += score;
	}

	/**
	 * Checks if the Snake can move in the given direction.
	 * 
	 * @param direction From the config class
	 * @return false if Snake runs into Poop or out of the boarder. true otherwise
	 */
	private boolean canSnakeMoveToDirection(config.Directions direction) {
		int xPos = snake.getHead().getXPos();
		int yPos = snake.getHead().getYPos();

		switch (direction) {
		case UP:
			return !isCoordinateOccupied(xPos, yPos - 1, false) && yPos - 1 >= 0;
		case DOWN:
			return !isCoordinateOccupied(xPos, yPos + 1, false) && yPos + 1 < 12;
		case RIGHT:
			return !isCoordinateOccupied(xPos + 1, yPos, false) && xPos + 1 < 12;
		case LEFT:
			return !isCoordinateOccupied(xPos - 1, yPos, false) && xPos - 1 >= 0;
		}
		return false;
	}

	/**
	 * Checks if snake can move in the given direction. When true the snake moves
	 * one pixel in that direction
	 * 
	 * @param direction moving direction of the snake(found in the class)
	 * @return true is the movement was successful. false otherwise
	 */
	private boolean performSnakeMovement(config.Directions direction) {
		if (!canSnakeMoveToDirection(direction)) {
			return false;
		} else {
			snake.moveOnePixel(direction);
			return true;
		}
	}

	/**
	 * Moves the Snake depending on the key input.
	 * 
	 * Also checks whether the movement is possible or not.
	 * 
	 * @param event Key Event
	 * @return whether the movement was successful as a boolean
	 */
	public boolean gameSnakeMovement(KeyEvent event) {
		// bewegt die schlange falls eine eingabe passiert ist
		if (event != null) {
			// die Schlange verandert ihre coordinaten in in Abhangigkeit von der Eingabe
			boolean doDefault = false;
			switch (event.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:
				if (snake.getFacingDirection() != config.Directions.DOWN) {
					return performSnakeMovement(config.Directions.UP);
				} else {
					doDefault = true;
					break;
				}
			case java.awt.event.KeyEvent.VK_DOWN:
				if (snake.getFacingDirection() != config.Directions.UP) {
					return performSnakeMovement(config.Directions.DOWN);
				} else {
					doDefault = true;
					break;
				}
			case java.awt.event.KeyEvent.VK_RIGHT:
				if (snake.getFacingDirection() != config.Directions.LEFT) {
					return performSnakeMovement(config.Directions.RIGHT);
				} else {
					doDefault = true;
					break;
				}
			case java.awt.event.KeyEvent.VK_LEFT:
				if (snake.getFacingDirection() != config.Directions.RIGHT) {
					return performSnakeMovement(config.Directions.LEFT);
				} else {
					doDefault = true;
					break;
				}
			}
			if (doDefault) {
				return performSnakeMovement(snake.getFacingDirection());
			}

		} else {
			// default verhalten der Schlange bei keiner eingabe
			return performSnakeMovement(snake.getFacingDirection());
		}
		return false;
	}

	/**
	 * Performs all the game logic operations for the all Poop Objects
	 */
	public void gameCheckupPoop() {
		// decays Poops
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
	}

	/**
	 * Performs all the game logic operations for the speedBoost Object
	 */
	public void gameCheckupSpeedBoost() {
		// 1 Fall Speedboost ist Inaktiv und Nicht Sichtbar und der Zuffal tritt ein
		if ((int) (Math.random() * config.BOOST_PROBABILITY) == 0 && !speedBoost.isVisible()
				&& !speedBoost.isActive()) {
			// SpeedBoost erhaelt eine der verfuergbaren koordinaten und wird sichtbar
			int[][] availableCoords = getAvailableCoords();
			speedBoost.show();
			speedBoost.relocate(availableCoords[(int) (Math.random() * availableCoords.length)]);
		}
		// 2 Fall SpeedBoost ist sichtbar auf dem Feld
		else if (speedBoost.isVisible()) {
			// wenn die schlage den Boost isst, wird dieser aktiviert,versteckt und die
			// Spielgeschwindlichkeit wird zufaallig schneller oder langsammer
			if (isSnakeEatingSpeedBoost()) {
				speedBoost.setActive(true);
				setFrameLength((int) (config.DEFAULT_FRAME_LENGHT_MS
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
				setFrameLength(config.DEFAULT_FRAME_LENGHT_MS);
			}

		}
	}

	/**
	 * Performs all the game logic operations for the Apple Object
	 */
	public void gameCheckupApple() {
		if (apple.getAge() > config.APPLE_MAX_AGE || isSnakeEatingApple()) {
			if (isSnakeEatingApple()) {
				snake.startDigesting();
				// 1 Apfel 1 punkt. wenn der SpeedBoost aktiv ist erhalt man 2 Punkte
				if (speedBoost.isActive()) {
					addScore(2);
				} else {
					addScore(1);
				}
			}
			int[][] availableCoords = getAvailableCoords();
			// choose randomly from one of the available coords
			apple.relocate(availableCoords[(int) (Math.random() * availableCoords.length)]);
		} else {
			apple.increaseAge();
		}
	}
}
