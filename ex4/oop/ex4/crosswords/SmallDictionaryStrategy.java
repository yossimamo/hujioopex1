//###############  
// FILE : SmallDictionaryStrategy.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An implementation of small dictionary strategy type. 
//###############

package oop.ex4.crosswords;

import java.util.Iterator;

/**
 * An implementation of small dictionary strategy type.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class SmallDictionaryStrategy implements CrosswordStrategy {
	
	// A CrosswordVacantEntries object for handling the vacant entries.
	private CrosswordVacantEntries _shape;
	
	// A CrosswordTerms Object for handling the terms in the dictionary.
	private CrosswordTerms _dict;
	
	// A CrosswordOverlapManager object for making the overlapping.
	private CrosswordOverlapManager _overlapManager;
	
	/**
	 * A constructor.
	 * @param shape the CrosswordVacantEntries object of the crossword.
	 * @param dict the CrosswordTerms object of the crossword.
	 * @param overlapManager the CrosswordOverlapManager object of the
	 *  crossword.
	 */
	public SmallDictionaryStrategy(CrosswordVacantEntries shape,
								   CrosswordTerms dict,
								   CrosswordOverlapManager overlapManager) {
		_shape = shape;
		_dict = dict;
		_overlapManager = overlapManager;
	}
	
	/**
	 * Returns an upper bound quality which is being calculated according 
	 * to the strategy.
	 * @param currentQuality The current quality of the crossword.
	 * @return An upper bound quality which is being calculated according 
	 * to the strategy.
	 */
	public int getUpperBoundQuality(int currentQuality) {
		int sum = 0;
		// No point in iterating on terms that are longer than the longest 
		// vacant entry, since they cannot fit into any vacant entry.
		Iterator<Term> termsIterator = _dict.getIterator(Math.min(
				_dict.getMaxAvailableTermLength(),
				_shape.getMaxVacantEntryLength()),
				_dict.getMinAvailableTermLength());
		while (termsIterator.hasNext()) {
			Term nextTerm =  termsIterator.next();
			// Iterate on vacant entries that are at least as long as the term
			Iterator<CrosswordVacantEntry> vacantEntriesIterator =
						_shape.getIterator(nextTerm.length(),
						_shape.getMaxVacantEntryLength());
			while (vacantEntriesIterator.hasNext()) {
				CrosswordVacantEntry vacantEntry =
								vacantEntriesIterator.next();
				if (_overlapManager.isMatch(nextTerm.getTerm(), vacantEntry)) {
					sum += nextTerm.length();
					break;
				}
			}
		}
		return currentQuality + sum;
	}

	/**
	 * Returns an iterator which iterates according to the strategy.
	 * @return An iterator which iterates according to the strategy.
	 */
	public Iterator<CrosswordEntry> getIterator() {
		return new SmallDictionaryStrategyIterator(_shape, _dict,
														_overlapManager);
	}

}
