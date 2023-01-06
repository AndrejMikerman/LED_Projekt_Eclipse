package snake;

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
		controller.setColor(boardObject.getXPos(), boardObject.getYPos(), boardObject.getColours()[0],
				boardObject.getColours()[1], boardObject.getColours()[2]);
	}
}
