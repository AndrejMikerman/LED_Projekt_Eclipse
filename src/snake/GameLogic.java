
package snake;

import java.util.ArrayList;

/**
 * This Class takes care of the calculation for the logic part of the game It
 * knows all the objects in the Game
 *
 */
public class GameLogic {

	private Snake snake;
	private Apple apple;
	private SpeedBoost speedBoost;
	private int frameLength;
	private boolean gameStarted;
	private ArrayList<Poop> poopQueue;

	public GameLogic(Apple apple, Snake snake, SpeedBoost speedBoost, ArrayList<Poop> poopQueue) {
		this.apple = apple;
		this.snake = snake;
		this.speedBoost = speedBoost;
		this.poopQueue = poopQueue;
		setFrameLength(config.FRAME_LENGHT_MS);
	}

	public int getFrameLength() {
		return frameLength;
	}

	public void setFrameLength(int frameLength) {
		this.frameLength = frameLength;
	}

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

	private int getOccupiedPixelsCount() {
		int poopCount = 0;
		for (Poop poop : poopQueue) {
			// Poop is only visible after 1 decay
			if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME) {
				poopCount++;
			}
		}
//		if (!poopList.isEmpty()) {
//			if (poopList.get(0).getCurrentDecayTime() < config.POOP_DECAY_TIME) {
//				poopCount = poopList.size();
//			} else {
//				poopCount = poopList.size() - 1;
//			}
//		}
		return snake.getLength() + (speedBoost.isVisible() ? 1 : 0) + (isSnakeEatingApple() ? 0 : 1) + poopCount;
	}

	/**
	 * Checks which Coordinates are not occupied by BoardObjects
	 * 
	 * @return Array with available coordinates
	 */
	public int[][] getAvailableCoords() {
		// Array with free Space
		int[][] availableCoords = new int[144 - getOccupiedPixelsCount()][2];
		// Snake Coords
		int[][] snakeCoords = new int[snake.getLength()][2];
		int index = 0;
		for (SnakeBody currentBodyPart = snake.getHead(); currentBodyPart != null; currentBodyPart = currentBodyPart
				.getNextBodyPart()) {
			snakeCoords[index][0] = currentBodyPart.getXPos();
			snakeCoords[index][1] = currentBodyPart.getYPos();
			index++;
		}
		// Free Coords
		index = 0;
		for (int xCoord = 0; xCoord < 12; xCoord++) {
			for (int yCoord = 0; yCoord < 12; yCoord++) {
				// check if the coords are not occupied
				boolean occupied = false;
				// speedboost check
				if (speedBoost.isVisible() && isOnCoords(speedBoost, xCoord, yCoord)) {
					occupied = true;
				}
				// apple check
				else if (!isSnakeEatingApple() && isOnCoords(apple, xCoord, yCoord)) {
					occupied = true;
				}
				// poop Check
				for (int i = 0; i < poopQueue.size() && !occupied; i++) {
					Poop poop = poopQueue.get(i);
					if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME && isOnCoords(poop, xCoord, yCoord)) {
						occupied = true;
					}
				}
				// snake coords check
				for (int i = 0; i < snakeCoords.length && !occupied; i++) {
					if (snakeCoords[i][0] == xCoord && snakeCoords[i][1] == yCoord) {
						occupied = true;
					}
				}
				// if not occupied add to the availableCoords array
				if (!occupied) {
					availableCoords[index][0] = xCoord;
					availableCoords[index][1] = yCoord;
					index++;
				}
			}
		}
		return availableCoords;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void startGame() {
		gameStarted = true;
	}

	public void stopGame() {
		gameStarted = false;
	}

}