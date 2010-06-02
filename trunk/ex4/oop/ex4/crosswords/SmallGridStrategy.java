//###############  
// FILE : SmallGridStrategy.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An implementation of small grid strategy type. 
//###############

package oop.ex4.crosswords;

import java.util.HashSet;
import java.util.Iterator;

/**
 * An implementation of small grid strategy type. 
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class SmallGridStrategy implements CrosswordStrategy {
	
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
	public SmallGridStrategy(CrosswordVacantEntries shape,
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
		HashSet<MyCrosswordPosition> positionHashset =
							new HashSet<MyCrosswordPosition>();
		int sum = 0;
		int minTermLength = _dict.getMinAvailableTermLength();
		// No point in iterating on vacant entries that are shorter than 
		// the shortest term, since no term can fit into them
		Iterator<CrosswordVacantEntry> vacantEntriesIterator =
								_shape.getIterator(minTermLength,
								_shape.getMaxVacantEntryLength());
		while (vacantEntriesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntriesIterator.next();
			// If we've already found a term which fits into the prefix
			// of this entry, we can sum this one up too.
			if (positionHashset.contains(nextEntry.getPosition())) {
				sum += nextEntry.getMaxCapacity();
				continue;
			}
			// Iterate only on terms that are exactly as long as the vacant 
			// entry, since shorter terms were already iterated on for the 
			// prefixes of the current entry
			Iterator<Term> termsIterator = 
				_dict.getIterator(nextEntry.getMaxCapacity(),
									nextEntry.getMaxCapacity());
			while (termsIterator.hasNext()) {
				if (_overlapManager.isMatch(termsIterator.next().getTerm(), nextEntry)) {
					positionHashset.add
						((MyCrosswordPosition)nextEntry.getPosition());
					sum += nextEntry.getMaxCapacity();
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
		return new SmallGridStrategyIterator(_shape, _dict, _overlapManager);
	}

}
