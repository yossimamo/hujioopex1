package oop.ex4.search;

import java.util.Iterator;


/**
 * A search board is a possible solution of a problem, for example a partially
 * completed crossword. The type parameter M stands concrete type SearchMove.
 * You can assume that concrete SearchBoard/SearchMove implementations are
 * developed in pairs and always compatible
 * 
 * @author Dmitry
 * 
 */
public interface SearchBoard<M> {
	
	/**
	 * Returns true if a node object represents a one of best 
	 * possible solutions of the problem, false otherwise.
	 * 
	 * @return True/False
	 */
	boolean isBestSolution();

	/**
	 * Creates and returns an iterator on the list of nodes reachable by a
	 * single edge from the current node (in case of crossword - list of
	 * crosswords with one more word in the grid).
	 * 
	 * @return Iterator object
	 */
	Iterator<M> getMoveIterator();

	/**
	 * This function allows evaluation of quality of solutions
	 * Higher value means better solution 
	 * 
	 * @return The quality value of given node
	 */
	int getQuality();

	/**
	 * This function allows estimation of potential 
	 * upper bound on quality of solutions available through zero or 
	 * more doMove() operations from the current board.
	 * The returned value have to be always greater or equal to
	 * the best possible quality obtained through doMove operations
	 * Hence it is also assumed that doMove operation should never increase 
	 * the upper bound value.
	 * 
	 * @return The upper bound on quality of given node
	 */
	int getQualityBound();
	
	/**
	 * Performs a move on the board potentially (reversibly) changing 
	 * the board object
	 * @param move
	 */
	void doMove(M move);
	
	/**
	 * Restores the object to state before the last move
	 * It is assumed that sequence of undoMove operations always
	 * reflect in the correct order the sequence of doMove operations
	 * Hence undoMove always supplied with the most recent un-undoed move.
	 * You don't have to check this assumption
	 * @param move
	 */
	void undoMove(M move);

	/**
	 * Creates a stand-alone copy of the current board
	 * The returned copy should not be affected by subsequent doMove/undoMove
	 * operations on the current board 
	 * @return
	 */
	public SearchBoard<M> getCopy();
}
