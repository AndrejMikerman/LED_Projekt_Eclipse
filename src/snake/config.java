package snake;

/**
 * This class has the purpose of storing values
 * 
 * @author Andrej
 *
 */
public class config {
	// man kann die direction auch als Zahl implementieren Z.B. -1 als direction
	// parameter uebergeben um sich nach oben zu bewegen
	public static enum Directions {
		RIGHT, LEFT, UP, DOWN
	}

	public static final int FRAME_LENGHT_MS = 250;
	public static final int APPLE_MAX_AGE = 100;
	public static final int BOOST_TIMER = 25;
	//lower value = higher probability
	public static final int BOOST_PROBABILITY = 20;
}
