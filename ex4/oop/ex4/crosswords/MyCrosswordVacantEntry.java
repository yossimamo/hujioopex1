package oop.ex4.crosswords;

public class MyCrosswordVacantEntry extends CrosswordVacantEntry {
	
	private CrosswordPosition _position;
	private int _maxCapacity;
	
	public MyCrosswordVacantEntry(CrosswordPosition pos, int maxCapacity) {
		_position = pos;
		_maxCapacity = maxCapacity;
	}
	
	public CrosswordPosition getPosition() {
		return _position;
	}
	
	public int getMaxCapacity() {
		return _maxCapacity;
	}

	public int compareTo(CrosswordVacantEntry other) {
		if (this._maxCapacity == other.getMaxCapacity()) {
			if (this._position.getX() == other.getPosition().getX()) {
				if (this._position.getY() == other.getPosition().getY()) {
					if (this._position.isVertical() == other.getPosition().isVertical()) {
						return 0;
					} else {
						return (this._position.isVertical() ? -1 : 1);
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
	
	// For debugging purposes
	public String toString() {
		return String.format("<%d> (%d,%d) %s", _maxCapacity, _position.getX(), _position.getY(), _position.isVertical() ? "V" : "H");
	}

}
