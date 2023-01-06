package snake;

/**
 * This class has the purpose of storing constant values
 * 
 * 
 *
 */
public class config {
	public static enum Directions {
		RIGHT, LEFT, UP, DOWN
	}

	public static final int FRAME_LENGHT_MS = 250;

	public static final int APPLE_MAX_AGE = 100;

	public static final int BOOST_TIMER = 25;
	// lower value = higher probability
	public static final int BOOST_PROBABILITY = 10;
	public static final int BOOST_MAX_AGE = 12;
	// FRAME_LENGHT * MULTIPLICATOR
	public static final double BOOST_SPEED_UP_MULTIPLICATOR = 0.5;
	public static final double BOOST_SPEED_DOWN_MULTIPLICATOR = 1.5;

	public static final int POOP_DECAY_TIME = 30;
}
