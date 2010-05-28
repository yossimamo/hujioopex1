package oop.ex4.crosswords;

public class MyCrosswordVacantEntry implements CrosswordVacantEntry, Comparable<CrosswordVacantEntry> {
	
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
					return (this._position.getY() - other.getPosition().getY());
				}
			} else {
				return (this._position.getX() - other.getPosition().getX());
			}
		} else {
			return (this._maxCapacity - other.getMaxCapacity());
		}
	}

}