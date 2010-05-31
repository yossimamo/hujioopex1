package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;

import oop.ex4.crosswords.StrategyIterator;

public class SmallGridStrategyIterator extends StrategyIterator {
	
	private CrosswordTerms _dict;
	private CrosswordOverlapManager _overlapManager;
	private Iterator<CrosswordVacantEntry> _entryIt;
	private Iterator<String> _termIt;
	private CrosswordEntry _next;
	private CrosswordVacantEntry _currentVacantEntry;
	
	
	public SmallGridStrategyIterator(CrosswordVacantEntries shape, CrosswordTerms dict, CrosswordOverlapManager overlapManager) {
		_dict = dict;
		_overlapManager = overlapManager;
		_entryIt = shape.getIterator(shape.getMaxVacantEntryLength(), shape.getMinVacantEntryLength());
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
			//System.out.printf("Current vacant entry: %s\n", _currentVacantEntry);
		}
		if (null == _termIt) {
			_termIt = _dict.getIterator(_currentVacantEntry.getMaxCapacity(), _dict.getMinAvailableTermLength());
		}
		CrosswordEntry ret = matchCurrentVacantEntry();
		if (null != ret) {
			//System.out.printf("Current match: %s\n", ret);
			return ret;
		}
		while (_entryIt.hasNext()) {
			_currentVacantEntry = _entryIt.next();
			_termIt = _dict.getIterator(_currentVacantEntry.getMaxCapacity(), _dict.getMinAvailableTermLength());
			//System.out.printf("Current vacant entry: %s\n", _currentVacantEntry);
			ret = matchCurrentVacantEntry();
			if (null != ret) {
				//System.out.printf("Current match: %s\n", ret);
				return ret;
			}
		}
		throw new NoSuchElementException();							
	}
	
	private CrosswordEntry matchCurrentVacantEntry() {
		while (_termIt.hasNext()) {
			String term = _termIt.next();
			// TODO System.out.printf("Current term: %s\n", term);
			if (_overlapManager.isMatch(term, _currentVacantEntry)) {
				CrosswordPosition pos = _currentVacantEntry.getPosition();
				return new MyCrosswordEntry(pos.getX(),
											pos.getY(),
											term, _dict.getTermDefinition(term),
											pos.isVertical());
			}
		}
		return null;
	}

}


