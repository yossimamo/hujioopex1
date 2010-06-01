//###############  
// FILE : MyCrosswordPosition.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: Specifies a basic position of an entry in a crossword.
//###############

package oop.ex4.crosswords;

/**
 * Specifies a basic position of an entry in a crossword.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class MyCrosswordPosition implements CrosswordPosition {
	
	// The X,Y coordinates
	private int _x, _y;
	
	// Is vertical or horizontal.
	private boolean _isVertical;
	
	/**
	 * constructs a new position.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param isVertical True if vertical or false otherwise.
	 */
	public MyCrosswordPosition(int x, int y, boolean isVertical){
		_x = x;
		_y = y;
		_isVertical = isVertical;
	}
	
	/**
	 * Returns a hashCode for the class.
	 * @return A hashCode for the class.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_isVertical ? 1231 : 1237);
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

	/**
	 * Returns true if the given object equals to this or false otherwise.
	 * @param obj another object.
	 * @return True if the given object equals to this or false otherwise.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyCrosswordPosition other = (MyCrosswordPosition) obj;
		if (_isVertical != other._isVertical)
			return false;
		if (_x != other._x)
			return false;
		if (_y != other._y)
			return false;
		return true;
	}
	
	/**
	 * @return The x coordinate
	 */
	public int getX() {
		return _x;
	}

	/**
	 * 
	 * @return The Y coordinate
	 */
	public int getY() {
		return _y;
	}

	/**
	 * 
	 * @return True iff position is a vertical position
	 */
	public boolean isVertical() {
		return _isVertical;
	}
	
	/**
	 *  For debugging purposes
	 *  @return A string representation of the position.
	 */
	public String toString() {
		return String.format("(%d,%d) %s", _x, _y, _isVertical ? "V" : "H");
	}

}
