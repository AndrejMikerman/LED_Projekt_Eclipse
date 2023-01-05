
package snake;

public class GameLogic {

	private Snake snake;
	private Apple apple;
	private boolean gameStarted;

	public GameLogic(Apple apple, Snake snake) {
		this.apple = apple;
		this.snake = snake;
		this.gameStarted = false;
	}

	public boolean snakeIsHitSnake() {
		// schlange gegen schlange?
		// Checks if the head colided with one of the body parts
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

	public boolean snakeIsHitWall() {
		// schlange gegen die wand?
		return snake.getHead().getXPos() > 11 || snake.getHead().getXPos() < 0 || snake.getHead().getYPos() > 11
				|| snake.getHead().getYPos() < 0;
	}

	public boolean checkSnakeEatingApple() {
		// schlange isst ein apfel?
		if (snake.getHead().getXPos() == apple.getXPos() && snake.getHead().getYPos() == apple.getYPos()) {
			snake.startDigesting();
			return true;
		}
		return false;
	}

	public int[][] getAvailableCoords() {
		// Array with free Space
		int[][] availableCoords = new int[144 - snake.getLength()][2];
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
				for (int i = 0; i < snakeCoords.length; i++) {
					if (snakeCoords[i][0] == xCoord && snakeCoords[i][1] == yCoord) {
						occupied = true;
						break;
					}
				}
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
		this.gameStarted = true;
	}

	public void stopGame() {
		this.gameStarted = false;
	}

}