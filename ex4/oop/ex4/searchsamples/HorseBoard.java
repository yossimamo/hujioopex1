package oop.ex4.searchsamples;

/**
 * This example implements board for well known "horse move" puzzle 
 * also known as "open knight tour"
 * http://en.wikipedia.org/wiki/Knight's_tour
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import oop.ex4.search.*;

/**
 * 
 * @author Dima
 * It keeps board status in array and creates iterator for new moves
 */
class HorseBoard implements SearchBoard<HorseMove> {
	/* properties */

	// List of all moves done so far 
	protected List<HorseMove> _movesRecord = new LinkedList<HorseMove>();
	// Grid size
	protected int _sizeX, _sizeY;
	// array to keep board status
	protected int _fillboard[][];
	protected int _quality=0;

	/* constructor  
	 * Creates board from dimensions
	 * */
	public HorseBoard(int sizeX, int sizeY) {
		_sizeX = sizeX;
		_sizeY = sizeY;
		_fillboard = new int[sizeX][sizeY];
	}

	// Returns true if done NxM moves
	@Override
	public boolean isBestSolution() {
		return (_quality == _sizeX * _sizeY);
	}

	// Applies move to board and stores it in record
	@Override
	public void doMove(HorseMove m) {
		_quality=m.getNumber();
		_fillboard[m.getX()][m.getY()] = _quality;
		_movesRecord.add(0, m);
	}

	// Undoes the most recent move and removes it from list
	@Override
	public void undoMove(HorseMove move) {
		// Get most recent move
		HorseMove lastMove = _movesRecord.get(0);
		assert(move==lastMove);
		// Check if move is valid
		// Restore board
		_fillboard[lastMove.getX()][lastMove.getY()] = 0;
		// update record
		_movesRecord.remove(0);
		_quality--;
	}

	// The iterator for current board's moves
	@Override
	public Iterator<HorseMove> getMoveIterator() {
		List<HorseMove> movesList;
		if (_movesRecord.isEmpty()) {
			movesList = createInitialMovesList();
		} else {
			movesList = createMovesList(_movesRecord.get(0));
		}
		return movesList.iterator();
	}

	// Overriding default method for printing
	// You don't have to do it for crosswords
	// but you may want to do it for debugging
	public String toString() {
		String s = "";
		for (int i = 0; i < _sizeX; i++) {
			for (int j = 0; j < _sizeY; j++) {
				s += String.format("%5d", _fillboard[i][j]);
				;
			}
			s += "\n";
		}
		return s;
 	}

	/* start internal (protected) methods) */
	protected List<HorseMove> createMovesList(HorseMove fromMove) {
		List<HorseMove> movesList = new LinkedList<HorseMove>();
		// List of all possible advancement pairs for chess knight
		int[][] movesDirections = new int[][] { { -2, -1 }, { -2, 1 },
				{ 2, -1 }, { 2, 1 }, { -1, 2 }, { 1, 2 }, { -1, -2 }, { 1, -2 } };

		for (int i = 0; i < movesDirections.length; i++) {
			int newDirX = movesDirections[i][0] + fromMove.getX();
			int newDirY = movesDirections[i][1] + fromMove.getY();
			int newNumber = fromMove.getNumber() + 1;
			// check if move is legal and add it to list
			if (newDirX >= 0 && newDirX < _sizeX && newDirY >= 0
					&& newDirY < _sizeY && _fillboard[newDirX][newDirY] == 0)
				movesList.add(new HorseMove(newDirX, newDirY, newNumber));
		}
		return movesList;
	}

	// Return iterator of created list
	// For crossword you can do it this way or design iterator
	// which creates moves "on the fly" when requested
	protected List<HorseMove> createInitialMovesList() {
		List<HorseMove> movesList = new LinkedList<HorseMove>();
		movesList.add(new HorseMove(0, 0, 1));
		return movesList;
	}


	
	@Override
	public int getQuality() {
		return _quality;
	}

	@Override
	public int getQualityBound() {
		return _sizeX*_sizeY;
	}

	@Override
	public SearchBoard<HorseMove> getCopy() {
		HorseBoard board=new HorseBoard(_sizeX,_sizeY);
		board._fillboard=Arrays.copyOf(_fillboard, _fillboard.length);
		for (int i=0;i<_fillboard.length;i++) {
			board._fillboard[i]=Arrays.copyOf(_fillboard[i], _fillboard[i].length);
			
		}
		board._quality=_quality;
		return board;
	}
}
