package snake;

/**
 * The SpeedBoost is an BoardObject and represents the SpeedBoost in the Game
 * 
 *
 */
public class SpeedBoost extends BoardObject {

	private int age;
	private boolean visible;
	private boolean active;
	private int boostTimer;

	public SpeedBoost(int xPos, int yPos) {
		super(xPos, yPos);
		age = 0;
		visible = false;
		active = false;
		boostTimer = config.BOOST_TIMER;
		this.colors = config.SPEED_BOOST_COLOR_RGB();
	}

	/**
	 * Increases the age of the SpeedBoost by 1
	 */
	public void increaseAge() {
		this.age++;
	}

	public int getAge() {
		return age;
	}

	/**
	 * Sets the age to 0
	 */
	public void resetAge() {
		age = 0;
	}

	/**
	 * Gives the SpeedBoost new coordinates and sets its age to 0
	 * 
	 * @param coordinates array with coordinates [x,y]
	 */
	public void relocate(int[] coordinates) {
		this.age = 0;
		setPosition(coordinates);
	}

	/**
	 * Sets the visibility of the SpeedBoost to false and resets its age
	 * 
	 */
	public void hide() {
		this.resetAge();
		this.visible = false;
	}

	/**
	 * Sets the visibility of the SpeedBoost to true
	 */
	public void show() {
		this.visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getBoostTimer() {
		return boostTimer;
	}

	/**
	 * Resets the SpeedBoost timer to the default value in the config
	 */
	public void resetBoostTimer() {
		boostTimer = config.BOOST_TIMER;
	}

	/**
	 * Decreases the Timer by 1
	 */
	public void boostCountdown() {
		boostTimer--;
	}

}
