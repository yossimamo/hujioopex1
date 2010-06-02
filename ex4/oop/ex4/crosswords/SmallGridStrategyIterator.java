package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;

import oop.ex4.crosswords.CrosswordStrategyIterator;

public class SmallGridStrategyIterator extends CrosswordStrategyIterator {
	
	private CrosswordTerms _dict;
	private CrosswordOverlapManager _overlapManager;
	private Iterator<CrosswordVacantEntry> _entryIt;
	private Iterator<Term> _termIt;
	private CrosswordEntry _next;
	private CrosswordVacantEntry _currentVacantEntry;
	
	
	public SmallGridStrategyIterator(CrosswordVacantEntries shape, CrosswordTerms dict, CrosswordOverlapManager overlapManager) {
		_dict = dict;
		_overlapManager = overlapManager;
		// No point in iterating over vacant entries that are longer than the
		// longest available term or shorter than the shortest available term
		// TODO System.out.printf("Getting entry iterator from min(%d, %d) to %d\n", shape.getMaxVacantEntryLength(), dict.getMaxAvailableTermLength(), shape.getMinVacantEntryLength());
		_entryIt = shape.getIterator(Math.min(shape.getMaxVacantEntryLength(), dict.getMaxAvailableTermLength()), shape.getMinVacantEntryLength());
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
		if (null == _currentVacantEntry) {
			_currentVacantEntry = _entryIt.next();
			// TODO System.out.printf("Current vacant entry: %s\n", _currentVacantEntry);
		}
		if (null == _termIt) {
			// Iterate on terms that are at most at long as the entry, starting with the
			// longest ones first
			_termIt = _dict.getIterator(_currentVacantEntry.getMaxCapacity(), _dict.getMinAvailableTermLength());
		}
		CrosswordEntry ret = matchCurrentVacantEntry();
		if (null != ret) {
			// TODO System.out.printf("Current match: %s\n", ret);
			return ret;
		}
		while (_entryIt.hasNext()) {
			_currentVacantEntry = _entryIt.next();
			_termIt = _dict.getIterator(_currentVacantEntry.getMaxCapacity(), _dict.getMinAvailableTermLength());
			// TODO System.out.printf("Current vacant entry: %s\n", _currentVacantEntry);
			ret = matchCurrentVacantEntry();
			if (null != ret) {
				// TODO System.out.printf("Current match: %s\n", ret);
				return ret;
			}
		}
		throw new NoSuchElementException();							
	}
	
	private CrosswordEntry matchCurrentVacantEntry() {
		while (_termIt.hasNext()) {
			Term term = _termIt.next();
			if (_overlapManager.isMatch(term.getTerm(), _currentVacantEntry)) {
				CrosswordPosition pos = _currentVacantEntry.getPosition();
				return new MyCrosswordEntry(pos.getX(),
											pos.getY(),
											term.getTerm(),
											term.getDefinition(),
											pos.isVertical());
			}
		}
		return null;
	}

}


