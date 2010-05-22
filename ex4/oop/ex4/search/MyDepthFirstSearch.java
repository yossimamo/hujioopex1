package oop.ex4.search;

import java.util.Iterator;
import java.util.Stack;

public class MyDepthFirstSearch<B extends SearchBoard<M>, M extends SearchMove> implements DepthFirstSearch<B, M> {
	
	private long _startTime;
	private SearchBoard<M> _bestBoard;
	private Stack<M> _lastMoves;
	private boolean _shouldStop;
	
	public MyDepthFirstSearch() {
		_lastMoves = new Stack<M>();
		_startTime = 0;
		_bestBoard = null;
		_shouldStop = false;
	}

	public B search(B board, int maxDepth, long timeOut) {
		_startTime =  System.currentTimeMillis();
		// TODO instead of casting, keep a reference to SearchBoard<M> and
		// cast only when necessary (in the end) - for performance
		_bestBoard = board.getCopy();
		_shouldStop = false;
		searchHelper(board, maxDepth, timeOut);
		// TODO check if this is ok
		System.out.println(System.currentTimeMillis() - _startTime);
		return (B)_bestBoard;
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
				(board.getQualityBound() <= _bestBoard.getQuality())) {
			// This direction is either too deep, or of a lower quality
			board.undoMove(_lastMoves.pop());
			return;
		}
		if (board.getQuality() > _bestBoard.getQuality()) {
			// TODO copy?
			_bestBoard = board.getCopy();
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
		}
		if (!_lastMoves.empty()) {
			board.undoMove(_lastMoves.pop());
		}
	}

}
