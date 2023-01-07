package snake;

import java.util.ArrayList;

import ledControl.BoardController;

/**
 * The Pencil Class is responsible for Drawing BoardObjects on the controller
 * board
 * 
 * 
 *
 */
public class Pencil {
	BoardController controller;

	public Pencil(BoardController controller) {
		this.controller = controller;
	}

	/**
	 * Draws the given Snake on the LED Board.
	 * 
	 * @param snake      The Snake
	 * @param controller The LED Board
	 */
	public void drawSnake(Snake snake) {
		for (SnakeBody currentBodyPart = snake.getHead(); currentBodyPart != null; currentBodyPart = currentBodyPart
				.getNextBodyPart()) {
			this.drawAnObject(currentBodyPart);
		}
	}

	/**
	 * Draws the given BoardObject on the LED Board.
	 * 
	 * @param boardObject The Apple
	 * @param controller  The LED Board
	 */
	public void drawAnObject(BoardObject boardObject) {
		controller.setColor(boardObject.getXPos(), boardObject.getYPos(), boardObject.getColors()[0],
				boardObject.getColors()[1], boardObject.getColors()[2]);
	}

	/**
	 * Draws the given Poop on the LED Board.
	 * 
	 * @param poopQueue is a Queue with all the Poop Objects
	 */
	public void drawPoop(ArrayList<Poop> poopQueue) {
		for (Poop poop : poopQueue) {
			if (poop.getCurrentDecayTime() < config.POOP_DECAY_TIME) {
				drawAnObject(poop);
			}
		}
	}

	/**
	 * Paints the whole Snake in one Color and draws in on the board
	 * 
	 * @param snake  The Snake
	 * @param colors RGB max value 127
	 */
	private void paintNdrawSnake(Snake snake, int[] colors) {
		for (SnakeBody snakeBody = snake.getHead(); snakeBody != null; snakeBody = snakeBody.getNextBodyPart()) {
			snakeBody.setColors(colors);
		}
		drawSnake(snake);
	}

	/**
	 * The Snake alternates between 2 colors
	 * 
	 * @param snake         The Snake Object
	 * @param count         How many Blinks should happen
	 * @param blinkDuration Cooldown time between each color change
	 * @param firstColor    first blink color
	 * @param secondColor   second blink color
	 */
	public void blinkSnake(Snake snake, int count, int blinkDuration, int[] firstColor, int[] secondColor) {
		for (int i = 0; i < count; i++) {
			paintNdrawSnake(snake, i % 2 == 0 ? firstColor : secondColor);
			controller.updateBoard();
			controller.sleep(blinkDuration);
		}

	}

	/////////////////////////////// NUMBERS METHODS/////////////////////////////////
	// The following methods draw a Number on the LED Board
	// The number always occupies a 3x5 field
	// The starting position is at upper left corner of the field

	/**
	 * This is a Helper Method. Numbers 2,3,5,6,8,9 have 3 horizontal lines. This
	 * Method Reduces redundant code
	 */
	private void drawHorizontalLines(int startingPositionX, int startingPositionY) {
		for (int i = 0; i < 3; i++) {
			controller.setColor(startingPositionX + i, startingPositionY, config.SCORE_COLOR_RGB());
			controller.setColor(startingPositionX + i, startingPositionY + 2, config.SCORE_COLOR_RGB());
			controller.setColor(startingPositionX + i, startingPositionY + 4, config.SCORE_COLOR_RGB());
		}
	}

	private void drawOne(int startingPositionX, int startingPositionY) {
		for (int i = 0; i < 5; i++) {
			controller.setColor(startingPositionX + 2, startingPositionY + i, config.SCORE_COLOR_RGB());
		}
	}

	private void drawTwo(int startingPositionX, int startingPositionY) {
		drawHorizontalLines(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX + 2, startingPositionY + 1, config.SCORE_COLOR_RGB());
		controller.setColor(startingPositionX, startingPositionY + 3, config.SCORE_COLOR_RGB());

	}

	private void drawThree(int startingPositionX, int startingPositionY) {
		drawHorizontalLines(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX + 2, startingPositionY + 1, config.SCORE_COLOR_RGB());
		controller.setColor(startingPositionX + 2, startingPositionY + 3, config.SCORE_COLOR_RGB());
	}

	private void drawFour(int startingPositionX, int startingPositionY) {
		drawOne(startingPositionX, startingPositionY);
		for (int i = 0; i < 3; i++) {
			controller.setColor(startingPositionX, startingPositionY + i, config.SCORE_COLOR_RGB());
		}
		controller.setColor(startingPositionX + 1, startingPositionY + 2, config.SCORE_COLOR_RGB());
	}

	private void drawFive(int startingPositionX, int startingPositionY) {
		drawHorizontalLines(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX + 2, startingPositionY + 3, config.SCORE_COLOR_RGB());
		controller.setColor(startingPositionX, startingPositionY + 1, config.SCORE_COLOR_RGB());
	}

	private void drawSix(int startingPositionX, int startingPositionY) {
		drawFive(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX, startingPositionY + 3, config.SCORE_COLOR_RGB());
	}

	private void drawSeven(int startingPositionX, int startingPositionY) {
		drawOne(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX, startingPositionY, config.SCORE_COLOR_RGB());
		controller.setColor(startingPositionX + 1, startingPositionY, config.SCORE_COLOR_RGB());
	}

	private void drawEight(int startingPositionX, int startingPositionY) {
		drawFive(startingPositionX, startingPositionY);
		drawTwo(startingPositionX, startingPositionY);
	}

	private void drawNine(int startingPositionX, int startingPositionY) {
		drawFour(startingPositionX, startingPositionY);
		drawFive(startingPositionX, startingPositionY);
	}

	private void drawZero(int startingPositionX, int startingPositionY) {
		drawEight(startingPositionX, startingPositionY);
		controller.setColor(startingPositionX + 1, startingPositionY + 2, 0, 0, 0);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Draws a number between 0 and 9 on the board
	 * 
	 * @param num integer 0 <= num <= 9
	 */
	private void drawNummber(int num, int xPos, int yPos) {
		switch (num) {
		case 1:
			drawOne(xPos, yPos);
			break;
		case 2:
			drawTwo(xPos, yPos);
			break;
		case 3:
			drawThree(xPos, yPos);
			break;
		case 4:
			drawFour(xPos, yPos);
			break;
		case 5:
			drawFive(xPos, yPos);
			break;
		case 6:
			drawSix(xPos, yPos);
			break;
		case 7:
			drawSeven(xPos, yPos);
			break;
		case 8:
			drawEight(xPos, yPos);
			break;
		case 9:
			drawNine(xPos, yPos);
			break;
		case 0:
			drawZero(xPos, yPos);
			break;
		}
	}

	/**
	 * Draws a number on the LED Board
	 * 
	 * @param score a number between 0 and 999
	 */
	public void drawScore(int score) {
		if (score < 10) {
			drawNummber(score, 8, 6);
		}
		if (score >= 10 && score < 100) {
			// last digit
			drawNummber(score % 10, 8, 6);
			// first digit
			drawNummber(score / 10, 4, 6);
		}
		if (score > 99) {
			// last digit
			drawNummber(score % 10, 8, 6);
			// middle
			drawNummber((score / 10) % 10, 4, 6);
			// first digit
			drawNummber((score / 100) % 10, 0, 6);
		}
	}

	/**
	 * Draws a Box at the Top of the Screen, where the snake circles
	 */
	public void drawSnakeScoreBox() {
		for (int i = 0; i < 12; i++) {
			controller.setColor(i, 0, config.SNAKE_SCORE_BOX_RGB());
			controller.setColor(i, 4, config.SNAKE_SCORE_BOX_RGB());
		}
		for (int i = 1; i < 4; i++) {
			controller.setColor(0, i, config.SNAKE_SCORE_BOX_RGB());
			controller.setColor(11, i, config.SNAKE_SCORE_BOX_RGB());
		}
	}

}
