package snake;

public class Snake {
	private SnakeBody snakeHead;
	private config.Directions facingDirection;
	private int length = 1;
	private Poop snakePoop;

	public Snake(int xPos, int yPos) {
		snakeHead = new SnakeBody(xPos, yPos, 127, 127, 127);
		// snake body
		addBodyPart();
		addBodyPart();

		// default direction
		this.facingDirection = config.Directions.LEFT;
	}

	private void addBodyPart() {
		SnakeBody pointer = this.snakeHead;
		while (pointer.getNextBodyPart() != null) {
			pointer = pointer.getNextBodyPart();
		}
		pointer.setNextBody(new SnakeBody(pointer.getXPos() + 1, pointer.getYPos()));
		this.length++;
	}

	public int getLength() {
		return length;
	}

	public SnakeBody getHead() {
		return snakeHead;
	}

	public void startDigesting() {
		this.snakeHead.startDigesting(true);
	}

	public void setFacingDirection(config.Directions direction) {
		this.facingDirection = direction;
	}

	public config.Directions getFacingDirection() {
		return facingDirection;
	}

	private void moveBodyParts(config.Directions direction) {
		int xFrontBodyOldPos = this.snakeHead.getXPos();
		int yFrontBodyOldPos = this.snakeHead.getYPos();
		boolean isFrontDigesting = this.snakeHead.isDigesting();
		boolean isNewDigesting;
		int xNew, yNew;
		// Die Koordinaten von dem Kopft werden veraendert
		if (direction == config.Directions.UP || direction == config.Directions.DOWN) {
			snakeHead.setYPos(this.snakeHead.getYPos() + (direction == config.Directions.UP ? -1 : 1));
		}
		if (direction == config.Directions.RIGHT || direction == config.Directions.LEFT) {
			snakeHead.setXPos(this.snakeHead.getXPos() + (direction == config.Directions.LEFT ? -1 : 1));
		}
		this.snakeHead.stopDigesting(true);
		// Andere Koerperteile nehmen den Koordinatenwert ihres Vorgaengers an.
		for (SnakeBody pointer = snakeHead.getNextBodyPart(); pointer != null; pointer = pointer.getNextBodyPart()) {
			// die Atribute des Forderen Koerperteils werden zum neuen
			xNew = xFrontBodyOldPos;
			yNew = yFrontBodyOldPos;
			isNewDigesting = isFrontDigesting;

			// die Atribute des jetzigen Koerperteils werden gespeichert fuer den nachsten
			xFrontBodyOldPos = pointer.getXPos();
			yFrontBodyOldPos = pointer.getYPos();
			isFrontDigesting = pointer.isDigesting();

			// die Atribute des jetzigen Koerperteils werden neu gesetzt
			pointer.setXPos(xNew);
			pointer.setYPos(yNew);
			if (isNewDigesting) {
				pointer.startDigesting(false);
			} else {
				pointer.stopDigesting(false);
			}

			// Wenn der Schwanz der Schlange verdaut dann soll die schlange sich verlangern.
			if (pointer.getNextBodyPart() == null && isFrontDigesting) {
				pointer.setNextBody(new SnakeBody(xFrontBodyOldPos, yFrontBodyOldPos));
				this.length++;
				this.snakePoop = new Poop(xFrontBodyOldPos, yFrontBodyOldPos);
				// wichtiger break sonst endlos schleife
				break;
			}

		}
	}

	public Poop getPoop() {
		Poop returnPoop = this.snakePoop;
		this.snakePoop = null;
		return returnPoop;
	}

	public boolean hasPoop() {
		return this.snakePoop != null;
	}

	public void moveOnePixel(config.Directions direction) {
		switch (direction) {
		case UP:
			this.moveBodyParts(direction);
			this.setFacingDirection(direction);
			break;
		case DOWN:
			this.moveBodyParts(direction);
			this.setFacingDirection(direction);
			break;
		case RIGHT:
			this.moveBodyParts(direction);
			this.setFacingDirection(direction);
			break;
		case LEFT:
			this.moveBodyParts(direction);
			this.setFacingDirection(direction);
			break;
		}
	}
}
