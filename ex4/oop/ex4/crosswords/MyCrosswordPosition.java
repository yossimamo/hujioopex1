package oop.ex4.crosswords;

public class MyCrosswordPosition implements CrosswordPosition {
	
	private int _x, _y;
	
	private boolean _isVertical;
	
	public MyCrosswordPosition(int x, int y, boolean isVertical){
		_x = x;
		_y = y;
		_isVertical = isVertical;
	}
	
	public MyCrosswordPosition(CrosswordPosition other) {
		_x = other.getX();
		_y = other.getY();
		_isVertical = other.isVertical();
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

}
