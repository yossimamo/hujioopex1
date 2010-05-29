package oop.ex4.crosswords;

public class MyCrosswordPosition implements CrosswordPosition {
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_isVertical ? 1231 : 1237);
		result = prime * result + _x;
		result = prime * result + _y;
		return result;
	}

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

	private int _x, _y;
	
	private boolean _isVertical;
	
	public MyCrosswordPosition(int x, int y, boolean isVertical){
		_x = x;
		_y = y;
		_isVertical = isVertical;
	}
	
	public int getX() {
		return _x;
	}

	
	public int getY() {
		return _y;
	}

	public boolean isVertical() {
		return _isVertical;
	}
	
	// For debugging purposes
	public String toString() {
		return String.format("(%d,%d) %s", _x, _y, _isVertical ? "V" : "H");
	}

}
