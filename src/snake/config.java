package snake;

/**
 * This class has the purpose of storing constant values.<br>
 * Here you can change values and configuration in the game
 * 
 * 
 *
 */
public class config {
	public static enum Directions {
		RIGHT, LEFT, UP, DOWN
	}

	public static final int DEFAULT_FRAME_LENGHT_MS = 290;

	public static final int APPLE_MAX_AGE = 100;

	public static final int BOOST_TIMER = 25;
	// lower value = higher probability
	public static final int BOOST_PROBABILITY = 10;
	public static final int BOOST_MAX_AGE = 12;
	// FRAME_LENGHT * MULTIPLICATOR
	public static final double BOOST_SPEED_UP_MULTIPLICATOR = 0.5;
	public static final double BOOST_SPEED_DOWN_MULTIPLICATOR = 1.5;

	public static final int POOP_DECAY_TIME = 30;

	public static final int[] RED_COLOR_RGB() {
		int[] redColor = { 127, 0, 0 };
		return redColor;
	}

	public static final int[] WHITE_COLOR_RGB() {
		int[] whiteColor = { 127, 127, 127 };
		return whiteColor;
	}

	public static final int[] APPLE_COLOR_RGB() {
		int[] appleColor = { 127, 0, 0 };
		return appleColor;
	}

	public static final int[] SNAKE_HEAD_COLOR_RGB() {
		int[] snakeHeadColor = { 127, 127, 127 };
		return snakeHeadColor;
	}

	public static final int[] SNAKE_BODY_COLOR_RGB() {
		int[] snakeBodyColor = { 0, 127, 0 };
		return snakeBodyColor;
	}

	public static final int[] SNAKE_BODY_DIGESTING_COLOR_RGB() {
		int[] snakeBodyDigestingColor = { 127, 127, 20 };
		return snakeBodyDigestingColor;
	}

	public static final int[] SPEED_BOOST_COLOR_RGB() {
		int[] speedBostColor = { 0, 0, 127 };
		return speedBostColor;
	}

	public static final int[] POOP_COLOR_RGB() {
		int[] poopColor = { 127, 59, 0 };
		return poopColor;
	}

	public static final int[] SCORE_COLOR_RGB() {
		int[] scoreColor = { 127, 127, 127 };
		return scoreColor;
	}

	public static final int[] SNAKE_SCORE_BOX_RGB() {
		int[] snakeScoreBox = { 91, 64, 30 };
		return snakeScoreBox;
	}
}
