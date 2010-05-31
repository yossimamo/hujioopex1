package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class SmallDictionaryStrategyIterator extends StrategyIterator {
		
	private CrosswordVacantEntries _shape;
	private CrosswordTerms _dict;
	private CrosswordOverlapManager _overlapManager;
	private Iterator<CrosswordVacantEntry> _entryIt;
	private Iterator<String> _termIt;
	private CrosswordEntry _next;
	private String _currentTerm;
	private TreeSet<CrosswordMatchingVacantEntry> _currentMatchingEntries;
	private Iterator<CrosswordMatchingVacantEntry> _matchingEntriesIt;
	
	public SmallDictionaryStrategyIterator(CrosswordVacantEntries shape, CrosswordTerms dict, CrosswordOverlapManager overlapManager) {
		_shape = shape;
		_dict = dict;
		_overlapManager = overlapManager;
		// No point in trying to match words that are longer than the longest vacant entry
		int startingTermLength = Math.min(_dict.getMaxAvailableTermLength(), _shape.getMaxVacantEntryLength());
		_termIt = _dict.getIterator(startingTermLength, _dict.getMinAvailableTermLength());
		_next = null;
	}
	
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

	public CrosswordEntry next() {
		if (null != _next) {
			CrosswordEntry ret = _next;
			_next = null;
			return ret;
		} else {
			return findNextMove();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();			
	}
	
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
		CrosswordEntry ret = new MyCrosswordEntry(entry.getPosition().getX(),
												  entry.getPosition().getY(),
												  _currentTerm,
												  _dict.getTermDefinition(_currentTerm),
												  entry.getPosition().isVertical());
		return ret;
	}
	
	private Iterator<CrosswordMatchingVacantEntry> getMatchingEntriesIterator(String term) {
		_currentMatchingEntries = new TreeSet<CrosswordMatchingVacantEntry>();
		_entryIt = _shape.getIterator(term.length(), term.length());
		while (_entryIt.hasNext()) {
			CrosswordVacantEntry entry = _entryIt.next();
			try {
				_currentMatchingEntries.add(new CrosswordMatchingVacantEntry(term, entry));
			} catch (TermMismatchException e) {
				// This entry mismatches the term, skip it
			}
		}
		return _currentMatchingEntries.iterator();
	}
	
	private class CrosswordMatchingVacantEntry implements Comparable<CrosswordMatchingVacantEntry> {
		private int _overlapCount;
		private String _term;
		private CrosswordVacantEntry _entry;
		
		public CrosswordMatchingVacantEntry(String term, CrosswordVacantEntry entry)
			throws TermMismatchException {
			_term = term;
			_entry = entry;
			_overlapCount = _overlapManager.getOverlapCount(_term, _entry);
			if (MyCrosswordOverlapManager.MISMATCH == _overlapCount) {
				throw new TermMismatchException();
			}
		}

		public int compareTo(CrosswordMatchingVacantEntry other) {
			if (this._overlapCount == other._overlapCount) {
				return this._entry.compareTo(other._entry);
			} else {
				return this._overlapCount - other._overlapCount;
			}
		}

		public CrosswordPosition getPosition() {
			return _entry.getPosition();
		}
	}

}
