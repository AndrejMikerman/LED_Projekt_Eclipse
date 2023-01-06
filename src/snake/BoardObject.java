package snake;

/**
 * This class represents a single LED pixel on the LED Board. Each BoardObject
 * has x and y coordinates and a color value;
 * 
 * 
 *
 */
public class BoardObject {
	protected int xPosition, yPosition;
	/**
	 * index: 0 = red, 1 = green, 2 = blue;
	 */
	protected int[] colours = new int[3];

	public BoardObject(int xPos, int yPos) {
		this.xPosition = xPos;
		this.yPosition = yPos;
	}

	public int getXPos() {
		return xPosition;
	}

	public int getYPos() {
		return yPosition;
	}

	public void setXPos(int x) {
		this.xPosition = x;
	}

	public void setYPos(int y) {
		this.yPosition = y;
	}

	/**
	 * Allows to set the coordinates with an array as parameter
	 * 
	 * @param coordinates [x position ,y position]
	 */
	public void setPosition(int[] coordinates) {
		this.xPosition = coordinates[0];
		this.yPosition = coordinates[1];
	}

	public int[] getColours() {
		return colours;
	}

	/**
	 * sets the color of the BoardObject as RGB colors. The value of each RGB value
	 * should be [0 <= value <= 127]
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setColor(int red, int green, int blue) {
		this.colours[0] = red;
		this.colours[1] = green;
		this.colours[2] = blue;
	}
}
