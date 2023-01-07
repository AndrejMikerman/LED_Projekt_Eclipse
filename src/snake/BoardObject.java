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
	protected int[] colors = new int[3];

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

	public int[] getColors() {
		return colors;
	}

	/**
	 * sets the color of the BoardObject as RGB colors. The value of each RGB value
	 * should be [0 <= value <= 127]
	 * 
	 * @param colours is an int array of length 3 with [red,green,blue]
	 */
	public void setColors(int[] colours) {
		if (colours.length != 3) {
			return;
		} else {
			this.colors = colours;
		}
	}

	/**
	 * sets the color of the BoardObject as RGB colors. The value of each RGB value
	 * should be [0 <= value <= 127]
	 * 
	 * @param red   red value
	 * @param green green value
	 * @param blue  blue value
	 */
	public void setColor(int red, int green, int blue) {
		this.colors[0] = red;
		this.colors[1] = green;
		this.colors[2] = blue;
	}
}
