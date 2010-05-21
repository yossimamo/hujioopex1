package oop.ex4.search;

import java.util.Iterator;
import java.util.Stack;

public class MyDepthFirstSearch<B extends SearchBoard<M>, M extends SearchMove> implements DepthFirstSearch<B, M> {
	
	private long _startTime;
	private B _bestBoard;
	private Stack<M> _lastMoves;
	
	public MyDepthFirstSearch() {
		_lastMoves = new Stack<M>();
	}

	public B search(B board, int maxDepth, long timeOut) {
		_startTime =  System.currentTimeMillis();
		// TODO instead of casting, keep a reference to SearchBoard<M> and
		// cast only when necessary (in the end) - for performance
		_bestBoard = (B)board.getCopy();
		searchHelper(board, maxDepth, timeOut);
		// TODO check if this is ok
		return (B)_bestBoard.getCopy();
	}
	
	private void searchHelper(B board, int maxDepth, long timeOut) {
		if (timeOut < (System.currentTimeMillis() - _startTime)) {
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
			_bestBoard = (B)board.getCopy();
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
