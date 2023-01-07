package snake;

public class SnakeBody extends BoardObject {
	private SnakeBody nextBody = null;
	private boolean isDigesting = false;

	public SnakeBody(int xPos, int yPos) {
		super(xPos, yPos);
		// green by default
		this.colors = config.SNAKE_BODY_COLOR_RGB();
	}

	public SnakeBody(int xPos, int yPos, int[] colors) {
		super(xPos, yPos);
		this.colors[0] = colors[0];
		this.colors[1] = colors[1];
		this.colors[2] = colors[2];
	}

	public SnakeBody getNextBodyPart() {
		return nextBody;
	}

	public void setNextBody(SnakeBody nextBody) {
		this.nextBody = nextBody;
	}

	/**
	 * BodyPart starts digesting and changes it's color to digesting color. <br>
	 * If isHead is true does not change it's color
	 * 
	 * @param isHead Set to true if BodyPart is Head. Otherwise leave it false.
	 */
	public void startDigesting(boolean isHead) {
		if (!isHead) {
			this.setColors(config.SNAKE_BODY_DIGESTING_COLOR_RGB());
		}
		this.isDigesting = true;
	}

	/**
	 * BodyPart stops digesting and changes it's color to default body part color.
	 * <br>
	 * If isHead is true does not change it's color
	 * 
	 * @param isHead Set to true if BodyPart is Head. Otherwise leave it false.
	 */
	public void stopDigesting(boolean isHead) {
		if (!isHead) {
			this.setColors(config.SNAKE_BODY_COLOR_RGB());
		}
		this.isDigesting = false;
	}

	/**
	 * Returns the digesting state of the Body Part
	 * 
	 * @return digesting state true/false
	 */
	public boolean isDigesting() {
		return this.isDigesting;
	}
}
