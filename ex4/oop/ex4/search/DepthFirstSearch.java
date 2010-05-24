package oop.ex4.search;

/**
 * This interface contains the method for a search. The type parameter B stands
 * for concrete type of SearchNode An implementation must work with ANY class
 * that implements the SearchNode interface (including the supplied example),
 * and not only with implementations of Crossword interface.)
 * 
 * @author Dima
 */
public interface DepthFirstSearch<B extends SearchBoard<M>,M extends SearchMove> {
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
	public B search(B board, int maxDepth, long timeOut);
	
}
