package snake;

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
		this.colours[2] = 127;
	}
	
	public void increaseAge() {
		this.age++;
	}
	
	public int getAge() {
		return age;
	}
	
	public void resetAge() {
		age = 0;
	}
	
	public void relocate(int[] coordinates) {
		this.age = 0;
		setPosition(coordinates);
	}
	
	public void setVisibility(boolean visibility) {
		visible = visibility;
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

	public void resetBoostTimer() {
		boostTimer = config.BOOST_TIMER;
	}
	
	public void boostCountdown() {
		boostTimer--;
	}


}
