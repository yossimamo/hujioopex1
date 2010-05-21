package oop.ex4.searchsamples;

import oop.ex4.search.SearchMove;



/**
 * 
 * @author Dima
 * A class which represents a single move for HorseBoard-based puzzle
 * It keeps 3 fields: x, y and move number
 */
class HorseMove implements SearchMove{
	HorseMove(int x, int y, int moveNumber) {
		_x = x;
		_y = y;
		_moveNumber = moveNumber;
	}

	public int getNumber() {
		return _moveNumber;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	protected int _x;
	protected int _y;
	protected int _moveNumber;
}
