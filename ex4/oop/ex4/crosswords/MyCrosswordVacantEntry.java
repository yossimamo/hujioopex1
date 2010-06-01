//###############  
// FILE : MyCrosswordVacantEntry.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword vacant entry is an empty entry (an entry you can 
// put word in).
//###############

package oop.ex4.crosswords;

/**
 * A crossword vacant entry is an empty entry (an entry you can 
 * put word in).
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class MyCrosswordVacantEntry extends CrosswordVacantEntry {
	
	// The position of the vacant entry.
	private CrosswordPosition _position;
	
	// The max capacity (length) of the vacant entry.
	private int _maxCapacity;
	
	/**
	 * Constructs a new vacant entry from a position and a max capacity. 
	 * @param pos The position of the vacant entry.
	 * @param maxCapacity The max capacity of the vacant entry.
	 */
	public MyCrosswordVacantEntry(CrosswordPosition pos, int maxCapacity) {
		_position = pos;
		_maxCapacity = maxCapacity;
	}
	
	/**
	 * Returns the position of the vacant entry.
	 * @return The position of the vacant entry.
	 */
	public CrosswordPosition getPosition() {
		return _position;
	}
	
	/**
	 * Returns the max capacity of the vacant entry.
	 * @return The max capacity of the vacant entry.
	 */
	public int getMaxCapacity() {
		return _maxCapacity;
	}

	/**
	 * Compares this vacant entry to another. first by max capacity, then
	 * by the position (x,y,is vertical). returns a positive int if this 
	 * vacant entry is "bigger" and a negative int if the other is "bigger".
	 * @param other another CrosswordVacantEntry. 
	 */
	public int compareTo(CrosswordVacantEntry other) {
		if (this._maxCapacity == other.getMaxCapacity()) {
			if (this._position.getX() == other.getPosition().getX()) {
				if (this._position.getY() == other.getPosition().getY()) {
					if (this._position.isVertical() == other.getPosition().isVertical()) {
						return 0;
					} else {
						return (this._position.isVertical() ? 1 : -1);
					}
				} else {
					return (other.getPosition().getY() - this._position.getY());
				}
			} else {
				return (other.getPosition().getX() - this._position.getX());
			}
		} else {
			return (this._maxCapacity - other.getMaxCapacity());
		}
	}
	
	/**
	 *  For debugging purposes.
	 */
	public String toString() {
		return String.format("<%d> (%d,%d) %s", _maxCapacity, _position.getX(), _position.getY(), _position.isVertical() ? "V" : "H");
	}

}
