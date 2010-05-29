package oop.ex4.search;

import java.util.Iterator;
import java.util.Stack;

public class MyDepthFirstSearch<B extends SearchBoard<M>, M extends SearchMove> implements DepthFirstSearch<B, M> {
	
	private long _startTime;
	private int _bestQuality;
	private Stack<M> _lastMoves;
	private Stack<M> _bestMoves;
	private boolean _shouldStop;
	
	public MyDepthFirstSearch() {
		_lastMoves = new Stack<M>();
		_bestMoves = new Stack<M>();
		_bestQuality = 0;
		_startTime = 0;
		_shouldStop = false;
	}

	public B search(B board, int maxDepth, long timeOut) {
		_startTime =  System.currentTimeMillis();
		SearchBoard<M> initialBoard = board.getCopy();
		_shouldStop = false;
		searchHelper(board, maxDepth, timeOut);
		return (B)makeBestBoard(initialBoard);
	}
	
	private void searchHelper(B board, int maxDepth, long timeOut) {
		if (_shouldStop) {
			return;
		}
		if (timeOut < (System.currentTimeMillis() - _startTime)) {
			_shouldStop = true;
			return;
		}
		if ((maxDepth < _lastMoves.size()) ||
				(board.getQualityBound() <= _bestQuality)) {
			// This direction is either too deep, or of a lower quality
			if (!_lastMoves.empty()) {
				board.undoMove(_lastMoves.pop());
				return;
			} else {
				// We are at square one and there does not seem to be a better solution
				_shouldStop = true;
				return;
			}
		}
		int currentBoardQuality = board.getQuality();
		if (currentBoardQuality > _bestQuality) {
			// Found a better board
			_bestQuality = currentBoardQuality;
			_bestMoves = (Stack<M>)_lastMoves.clone();
			if (board.isBestSolution()) {
				// Found the best solution possible - no need to go on
				_shouldStop = true;
				return;
			}
		}
		
		Iterator<M> it = board.getMoveIterator();
		while (it.hasNext()) {
			M move = it.next();
			_lastMoves.push(move);
			board.doMove(move);
			searchHelper(board, maxDepth, timeOut);
			if (_shouldStop) {
				return;
			}
		}
		if (!_lastMoves.empty()) {
			board.undoMove(_lastMoves.pop());
			return;
		} else {
			_shouldStop = true;
			return;
		}
	}
	
	private SearchBoard<M> makeBestBoard(SearchBoard<M> initialBoard) {
		SearchBoard<M> bestBoard = initialBoard.getCopy();
		// Reverse stack
		Stack<M> reversedMoves = new Stack<M>();
		while (!_bestMoves.isEmpty()) {
			reversedMoves.push(_bestMoves.pop());
		}
		while (!reversedMoves.isEmpty()) {
			bestBoard.doMove(reversedMoves.pop());
		}
		return bestBoard;
	}

}
