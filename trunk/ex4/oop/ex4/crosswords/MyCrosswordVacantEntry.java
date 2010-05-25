package oop.ex4.crosswords;

// TODO make interface
public class MyCrosswordVacantEntry implements Comparable {
	
	private MyCrosswordPosition _position;
	private int _maxCapacity;
	
	public MyCrosswordVacantEntry(MyCrosswordPosition pos, int maxCapacity) {
		_position = pos;
		_maxCapacity = maxCapacity;
	}
	
	public MyCrosswordPosition getPosition() {
		return _position;
	}
	
	public int getMaxCapacity() {
		return _maxCapacity;
	}

	public int compareTo(Object obj) {
		if (obj instanceof MyCrosswordVacantEntry) {
			MyCrosswordVacantEntry other = (MyCrosswordVacantEntry)obj;
			if (this._maxCapacity == other._maxCapacity) {
				if (this._position.getX() == other._position.getX()) {
					if (this._position.getY() == other._position.getY()) {
						if (this._position.isVertical() == other._position.isVertical()) {
							return 0;
						} else {
							return (this._position.isVertical() ? -1 : 1);
						}
					} else {
						return (this._position.getY() - other._position.getY());
					}
				} else {
					return (this._position.getX() - other._position.getX());
				}
			} else {
				return (this._maxCapacity - other._maxCapacity);
			}
		} else {
			throw new ClassCastException();
		}
	}

}
