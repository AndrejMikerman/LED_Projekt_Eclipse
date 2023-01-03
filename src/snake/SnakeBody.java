package snake;

public class SnakeBody extends BoardObject {
	private SnakeBody nextBody = null;
	private boolean isDigesting = false;

	public SnakeBody(int xPos, int yPos) {
		super(xPos, yPos);
		// green by default
		this.colours[0] = 0;
		this.colours[1] = 127;
		this.colours[2] = 0;
	}

	public SnakeBody(int xPos, int yPos, int red, int green, int blue) {
		super(xPos, yPos);
		this.colours[0] = red;
		this.colours[1] = green;
		this.colours[2] = blue;
	}

	public SnakeBody getNextBodyPart() {
		return nextBody;
	}

	public void setNextBody(SnakeBody nextBody) {
		this.nextBody = nextBody;
	}

	// Koerperteile die Verdauen sind Gelb, auser dem Kopf
	public void startDigesting(boolean isHead) {
		if (!isHead) {
			this.setColor(127, 127, 20);
		}
		this.isDigesting = true;
	}

	public void stopDigesting(boolean isHead) {
		if (!isHead) {
			this.setColor(0, 127, 0);
		}
		this.isDigesting = false;
	}

	public boolean isDigesting() {
		return this.isDigesting;
	}
}
