//###############  
// FILE : MyCrossword.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword.
//###############

package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


import oop.ex4.search.SearchBoard;

/**
 * A crossword.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class MyCrossword implements Crossword {
	
	// A CrosswordTerms Object for handling the terms in the dictionary.
	private MyCrosswordDictionary _dict;
	
	// A CrosswordVacantEntries object for handling the vacant entries.
	private MyCrosswordShape _shape;
	
	// The type of strategy we use.
	private CrosswordStrategy _strategy;
	
	// the crosswords quality.
	private int _quality = 0;
	
	// the entries that were entered into the crossword.
	private HashSet<CrosswordEntry> _entries = new HashSet<CrosswordEntry>();
	
	// A CrosswordOverlapManager object for making the overlapping.
	private MyCrosswordOverlapManager _overlapManager;
	
	/**
	 * an empty constructor.
	 */
	public MyCrossword() {
		
	}

	/**
	 * Initializes all structures associated with crossword dictionary 
	 * Assumes valid and non-NULL dictionary object
	 * 
	 * @param dictionary
	 *            - the (loaded from file) dictionary object
	 */
	public void attachDictionary(CrosswordDictionary dictionary) {
		_dict = (MyCrosswordDictionary)dictionary;
		if (null != _shape) {
			determineStrategy();
		}
	}

	/**
	 * Initializes all structures associated with crossword shape 
	 * Assumes valid and non-NULL shape object
	 * 
	 * @param shape
	 *            - the (loaded from file) shape object
	 */
	public void attachShape(CrosswordShape shape) {
		_shape = (MyCrosswordShape)shape;
		_overlapManager = new 
			MyCrosswordOverlapManager(_shape.getWidth(), _shape.getHeight());
		if (null != _dict) {
			determineStrategy();
		}
	}
	
	/**
	 * determines the strategy of the crossword (called only once throughout
	 * the program).
	 */
	private void determineStrategy() {
		if (_shape.getNumberOfEntries() <= _dict.getNumberOfTerms()) {
			_strategy = new SmallGridStrategy(_shape, _dict, _overlapManager);
		} else {
			_strategy = new 
					SmallDictionaryStrategy(_shape, _dict, _overlapManager);
		}
	}

	/**
	 * Retrieves list of crossword entries associated with this Crossword
	 * The set of filled entries should satisfy both of exercise requirements 
	 * 
	 * @return Collection of filled entries
	 */
	public Collection<CrosswordEntry> getCrosswordEntries() {
		return _entries;
	}

	/**
	 * adds a word to the crossword and updates the database accordingly.
	 * @param move the CrosswordEntry we wish to add to the crossword.
	 */
	public void doMove(CrosswordEntry move) {
		_overlapManager.addEntry(move);
		_dict.addEntry(move);
		_shape.addEntry(move);
		_entries.add(move);
		_quality+= move.getLength();
	}

	/**
	 * Returns a copy of the crossword.
	 * @return a copy of the crossword.
	 */
	public SearchBoard<CrosswordEntry> getCopy() {
		MyCrossword copiedCrossword = new MyCrossword();
		copiedCrossword._dict = new MyCrosswordDictionary(_dict);
		copiedCrossword._shape = new MyCrosswordShape(_shape);
		copiedCrossword._quality = _quality;
		copiedCrossword._strategy = _strategy;
		copiedCrossword._entries = (HashSet<CrosswordEntry>) _entries.clone();
		copiedCrossword._overlapManager = new 
						MyCrosswordOverlapManager(_overlapManager);
		return copiedCrossword;
	}

	/**
	 * Returns an iterator of the possible entries to add to the crossword.
	 * @return An iterator of the possible entries to add to the crossword.
	 */
	public Iterator<CrosswordEntry> getMoveIterator() {
		return _strategy.getIterator();
	}

	/**
	 * Returns the quality of the crossword.
	 * @return The quality of the crossword.
	 */
	public int getQuality() {
		return _quality;
	}

	/**
	 * Returns the quality bound for this crossword.
	 * @return The quality bound for this crossword.
	 */
	public int getQualityBound() {
		return _strategy.getUpperBoundQuality(_quality);
	}

	/**
	 * Returns true iff this is the best quality possible to get from these
	 * shape and dictionary.
	 * @return true iff this is the best quality possible to get from these
	 * shape and dictionary.
	 */
	public boolean isBestSolution() {
		if ((_dict.isFullyOccupied()) || 
				(_quality == _shape.getTotalEntryLengthsSum())) {
			return true;
		}
		return false;
	}

	/**
	 * removes a word from the crossword and updates the database accordingly.
	 * @param move the CrosswordEntry we wish to remove from the crossword.
	 */
	public void undoMove(CrosswordEntry move) {
		_overlapManager.removeEntry(move);
		_dict.removeEntry(move);
		_shape.removeEntry(move);
		_entries.remove(move);
		_quality -= move.getLength();
	}
	
	/**
	 * Returns a string representation of the crossword
	 * for debugging purposes.
	 * @return A string representation of the crossword
	 * for debugging purposes.
	 */
	public String toString() {
		return _overlapManager.toString();
	}

}
