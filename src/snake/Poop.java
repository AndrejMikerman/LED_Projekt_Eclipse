package snake;

/**
 * 
 * The Poop class is an BoardObject and represents the Snake Poop in the Game
 * Snake collision with the Poop end the game
 *
 */
public class Poop extends BoardObject {
	private int decayTime;

	public Poop(int xPos, int yPos) {
		super(xPos, yPos);
		colours[0] = 127;
		colours[1] = 59;
		colours[2] = 0;
		this.decayTime = config.POOP_DECAY_TIME;
	}

	/**
	 * Decays the Poop by 1 and dimmers its colors
	 */
	public void decay() {
		colours[0] = (int) (127 * (double) decayTime / config.POOP_DECAY_TIME);
		colours[1] = (int) (59 * (double) decayTime / config.POOP_DECAY_TIME);
		decayTime--;
	}

	public int getCurrentDecayTime() {
		return this.decayTime;
	}
}
