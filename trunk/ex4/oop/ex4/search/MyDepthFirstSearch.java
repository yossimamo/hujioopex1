//###############  
// FILE : MyDepthFirstSearch.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A generic implementation of a DFS.
//###############

package oop.ex4.search;

import java.util.Iterator;
import java.util.Stack;

/**
 * 
 * @author Uri Greenberg and Yossi Mamo.
 * @param <B> The board we are searching with.
 * @param <M> A possible move in the board.
 */
public class MyDepthFirstSearch<B extends SearchBoard<M>, M extends SearchMove>
											implements DepthFirstSearch<B, M> {
	
	// The time (in milisec) we start the search at.
	private long _startTime;
	
	// The best quality so far.
	private int _bestQuality;
	
	// The moves we made to get to the current board.
	private Stack<M> _lastMoves;
	
	// The moves we made to get to the best board.
	private Stack<M> _bestMoves;
	
	// An indicator to when to stop searching.
	private boolean _shouldStop;
	
	/**
	 * a constructor.
	 */
	public MyDepthFirstSearch() {
		_lastMoves = new Stack<M>();
		_bestMoves = new Stack<M>();
		_bestQuality = 0;
		_startTime = 0;
		_shouldStop = false;
	}

	/**
	 * This method assumes that graph is a tree so it don't have to check
	 * for loops
	 * Performs DepthFirst search, up to depth maxDepth, and using at most
	 * timeOut ms, starting from the given SearchNode.
	 * 
	 * @param startNode
	 *            - The board where you start the search
	 * @param maxDepth
	 *            - Maximal depth of your search. Depth 0 means that you check
	 *            only the given startNode, Depth 1- only startNode and its
	 *            children...
	 * @param timeOut
	 *            - time in milliseconds as esti mated by java Date object
	 * 
	 * @return SearchNode for which isSolution() returns true; if no such node
	 *         found within given depth or time - returns SearchNode with
	 *         highest non-negative quality() value observed so far.
	 *         If all observed quality values are negative, returns null.
	 */
	public B search(B board, int maxDepth, long timeOut) {
		_startTime =  System.currentTimeMillis();
		SearchBoard<M> initialBoard = board.getCopy();
		_shouldStop = false;
		searchHelper(board, maxDepth, timeOut);
		return (B)makeBestBoard(initialBoard);
	}
	
	/**
	 * performing the DFS described above. (recursively)
	 * @param board A node in the search.
	 * @param maxDepth the maximal depth we are allowed to search.
	 * @param timeOut the time allowed for this search.
	 */
	private void searchHelper(B board, int maxDepth, long timeOut) {
		if (_shouldStop) {
			return;
		}
		if (timeOut < (System.currentTimeMillis() - _startTime)) {
			_shouldStop = true;
			return;
		}
		int upperBoundQuality = board.getQualityBound();
		if ((maxDepth < _lastMoves.size()) ||
				(upperBoundQuality <= _bestQuality)) {
			// TODO System.out.printf("This board's upper bound quality is: %d, less than the best: %d, going back\n" , upperBoundQuality, _bestQuality);
			// This direction is either too deep, or of a lower quality
			if (!_lastMoves.empty()) {
				board.undoMove(_lastMoves.pop());
				return;
			} else {
				// We are at square one and there does not seem to be a
				// better solution
				_shouldStop = true;
				return;
			}
		}
		int currentBoardQuality = board.getQuality();
		// TODO System.out.printf("Current board quality: %d\n", currentBoardQuality);
		if (currentBoardQuality > _bestQuality) {
			// TODO System.out.printf("This board is the best so far with %d\n", currentBoardQuality);
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
			// TODO System.out.printf("Depth: %d, Move: %s\n", _lastMoves.size(), move);
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
	
	/**
	 * Returns the best board found during the search.
	 * @param initialBoard An empty board.
	 * @return The best board found during the search.
	 */
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
