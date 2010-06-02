//###############  
// FILE : SmallDictionaryStrategyIterator.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: An iterator for small dictionary strategy.
//###############

package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * An iterator for small dictionary strategy.
 * @author Uri Greenberg and Yossi Mamo.
 *
 */
public class SmallDictionaryStrategyIterator extends CrosswordStrategyIterator {
		
	// A CrosswordVacantEntries object for handling the vacant entries.
	private CrosswordVacantEntries _shape;
	
	// A CrosswordTerms Object for handling the terms in the dictionary.
	private CrosswordTerms _dict;
	
	// A CrosswordOverlapManager object for making the overlapping.
	private CrosswordOverlapManager _overlapManager;
	
	// An Iterator on the vacant entries that haven't been used in the 
	// crossword
	private Iterator<CrosswordVacantEntry> _entryIt;
	
	// An Iterator on the terms that haven't been entered into the 
	// crossword
	private Iterator<Term> _termIt;
	
	// Holding the next crosswordEntry the iterator will return.
	private CrosswordEntry _next;
	
	//Holding the last iterated term from the terms iterator.
	private Term _currentTerm;
	
	// TODO
	private TreeSet<CrosswordMatchingVacantEntry> _currentMatchingEntries;
	
	// TODO
	private Iterator<CrosswordMatchingVacantEntry> _matchingEntriesIt;
	
	/**
	 * constructs a new SmallDictionaryStrategyIterator object
	 * @param shape the CrosswordVacantEntries object of the crossword.
	 * @param dict the CrosswordTerms object of the crossword.
	 * @param overlapManager the CrosswordOverlapManager object of the
	 *  crossword.
	 */
	public SmallDictionaryStrategyIterator(CrosswordVacantEntries shape,
			CrosswordTerms dict, CrosswordOverlapManager overlapManager) {
		_shape = shape;
		_dict = dict;
		_overlapManager = overlapManager;
		// No point in trying to match words that are longer than the longest
		// vacant entry
		int startingTermLength = Math.min(_dict.getMaxAvailableTermLength(),
						_shape.getMaxVacantEntryLength());
		_termIt = _dict.getIterator(startingTermLength,
						_dict.getMinAvailableTermLength());
		_next = null;
	}
	
	/**
	 * Returns true iff the iterator has another element.
	 * @return true iff the iterator has another element.
	 */
	public boolean hasNext() {
		if (null != _next) {
			return true;
		} else {
			try {
				_next = next();
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}
	}

	/**
	 * Returns the next element in the iteration.
	 * @return The next element in the iteration.
	 */
	public CrosswordEntry next() {
		if (null != _next) {
			CrosswordEntry ret = _next;
			_next = null;
			return ret;
		} else {
			return findNextMove();
		}
	}

	/**
	 * not implemented.
	 */
	public void remove() {
		throw new UnsupportedOperationException();			
	}
	
	/**
	 * Returns the next element the iterator needs to return.
	 * @return the next element the iterator needs to return.
	 */
	private CrosswordEntry findNextMove() {
		if (null == _currentTerm) {
			_currentTerm = _termIt.next();
		}
		if (null == _matchingEntriesIt) {
			_matchingEntriesIt = getMatchingEntriesIterator(_currentTerm);
		}
		
		while (!_matchingEntriesIt.hasNext()) {
			_currentTerm = _termIt.next();
			_matchingEntriesIt = getMatchingEntriesIterator(_currentTerm);
		}
		CrosswordMatchingVacantEntry entry = _matchingEntriesIt.next();
		CrosswordPosition pos = entry.getPosition();
		return new MyCrosswordEntry(pos.getX(),
									pos.getY(),
									_currentTerm.getTerm(),
									_currentTerm.getDefinition(),
									entry.getPosition().isVertical());
	}
	
	/**
	 * TODO
	 * @param term
	 * @return
	 */
	private Iterator<CrosswordMatchingVacantEntry>
							getMatchingEntriesIterator(Term term) {
		_currentMatchingEntries = new TreeSet<CrosswordMatchingVacantEntry>();
		_entryIt = _shape.getIterator(term.length(), term.length());
		while (_entryIt.hasNext()) {
			CrosswordVacantEntry entry = _entryIt.next();
			try {
				_currentMatchingEntries.add
							(new CrosswordMatchingVacantEntry(term, entry));
			} catch (TermMismatchException e) {
				// This entry mismatches the term, skip it
			}
		}
		return _currentMatchingEntries.iterator();
	}
	
	/**
	 * TODO whole class
	 * @author Uri Greenberg and Yossi Mamo.
	 *
	 */
	private class CrosswordMatchingVacantEntry implements
							Comparable<CrosswordMatchingVacantEntry> {
		//
		private int _overlapCount;
		private Term _term;
		
		//
		private CrosswordVacantEntry _entry;
		
		/**
		 * 
		 * @param term
		 * @param entry
		 * @throws TermMismatchException
		 */
		public CrosswordMatchingVacantEntry
						(Term term, CrosswordVacantEntry entry)
			throws TermMismatchException {
			_term = term;
			_entry = entry;
			_overlapCount = _overlapManager.getOverlapCount(_term.getTerm(), _entry);
			if (MyCrosswordOverlapManager.MISMATCH == _overlapCount) {
				throw new TermMismatchException();
			}
		}

		/**
		 * 
		 */
		public int compareTo(CrosswordMatchingVacantEntry other) {
			if (this._overlapCount == other._overlapCount) {
				return this._entry.compareTo(other._entry);
			} else {
				return this._overlapCount - other._overlapCount;
			}
		}

		/**
		 * 
		 * @return
		 */
		public CrosswordPosition getPosition() {
			return _entry.getPosition();
		}
	}

}
