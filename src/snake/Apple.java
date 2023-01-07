package snake;

/**
 * The Apple class is an BoardObject and represents the Apple in the Game. The
 * Apple has a cooldown that can be configured in the config class;
 * 
 * 
 *
 */
public class Apple extends BoardObject {

	private int age;

	public Apple(int xPos, int yPos) {
		super(xPos, yPos);
		age = 0;
		this.colors = config.APPLE_COLOR_RGB();
	}

	/**
	 * Return age of the apple
	 * 
	 * @return a positive int
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Increases the age of the apple by 1
	 */
	public void increaseAge() {
		this.age++;
	}

	/**
	 * Changes coordinates of the apple and resets its age to 0;
	 * 
	 * @param coordinates New Coordinates of the Apple
	 */
	public void relocate(int[] coordinates) {
		this.age = 0;
		setPosition(coordinates);
	}

}
