package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;


import oop.ex4.search.SearchBoard;

public class MyCrossword implements Crossword {
	
	private enum StrategyType {
		SMALL_GRID_STRATEGY,
		SMALL_DICTIONARY_STRATEGY
	}
	
	private MyCrosswordDictionary _dict;
	private MyCrosswordShape _shape;
	private int _quality = 0;
	private StrategyType _strategyType;
	private HashSet<CrosswordEntry> _entries = new HashSet<CrosswordEntry>();
	private OverlapManager _overlapManager;
	
	public MyCrossword() {
		
	}

	public void attachDictionary(CrosswordDictionary dictionary) {
		_dict = (MyCrosswordDictionary)dictionary;
		if (null != _shape) {
			determineStrategy();
		}
	}

	private void determineStrategy() {
		if (_shape.getNumberOfEntries() <= _dict.getNumberOfTerms()) {
			_strategyType = StrategyType.SMALL_GRID_STRATEGY;
		}
		else {
			_strategyType = StrategyType.SMALL_DICTIONARY_STRATEGY;
		}
	}

	public void attachShape(CrosswordShape shape) {
		_shape = (MyCrosswordShape)shape;
		if (null != _dict) {
			determineStrategy();
		}
		 _overlapManager = new OverlapManager(_shape.getWidth(), _shape.getHeight());
	}

	public Collection<CrosswordEntry> getCrosswordEntries() {
		return _entries;
	}

	public void doMove(CrosswordEntry move) {
		_overlapManager.addEntry(move);
		_dict.addEntry(move);
		_shape.addEntry(move);
		_entries.add(move);
		_quality+= move.getLength();
	}

	public SearchBoard<CrosswordEntry> getCopy() {
		MyCrossword copiedCrossword = new MyCrossword();
		copiedCrossword._dict = new MyCrosswordDictionary(_dict);
		copiedCrossword._shape = new MyCrosswordShape(_shape);
		copiedCrossword._quality = _quality;
		copiedCrossword._strategyType = _strategyType;
		//TODO check if the shallow clone is sufficient. and casting
		copiedCrossword._entries = (HashSet<CrosswordEntry>) _entries.clone();
		// TODO copiedCrossword._overlapManager = (OverlapManager)_overlapManager.clone();
		return copiedCrossword;
	}

	public Iterator<CrosswordEntry> getMoveIterator() {
		switch(_strategyType) {
		case SMALL_GRID_STRATEGY:
			return new SmallGridStrategyIterator();
		case SMALL_DICTIONARY_STRATEGY:
			return new SmallDictionaryStrategyIterator();
		default:
			//TODO throw exception
			return null;
		}
	}

	public int getQuality() {
		return _quality;
	}

	public int getQualityBound() {
		switch (_strategyType) {
		case SMALL_GRID_STRATEGY:
			return _quality + getSumOfAvailableEntries();
		case SMALL_DICTIONARY_STRATEGY:
			return _quality + getSumOfAvailableWords();
		default:
			//TODO throw exception
			return -1;
		}
		
	}

	private int getSumOfAvailableEntries() {
		//TODO very inefficient
		int sum = 0;
		Iterator<CrosswordVacantEntry> vacantEntiesIterator = _shape.getIterator(true);
		while (vacantEntiesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntiesIterator.next();
			// Iterate on terms that are at most as long as the vacant entry
			Iterator<String> termsIterator = _dict.getIterator(nextEntry.getMaxCapacity(), false);
			while (termsIterator.hasNext()) {
				if (_overlapManager.isOverlapping(termsIterator.next(), nextEntry )) {
					sum+= nextEntry.getMaxCapacity();
					break;
				}
			}
		}
		return sum;
	}
	
	private int getSumOfAvailableWords() {
		//TODO very inefficient. maybe can use in common with previous function
		int sum = 0;
		Iterator<String> termsIterator = _dict.getIterator(true);
		while (termsIterator.hasNext()) {
			String nextTerm =  termsIterator.next();
			// Iterate on vacant entries that are at least as long as the term
			Iterator<CrosswordVacantEntry> vacantEntiesIterator = _shape.getIterator(nextTerm.length(), true);
			while (vacantEntiesIterator.hasNext()) {
				if (_overlapManager.isOverlapping(nextTerm, vacantEntiesIterator.next())) {
					sum+= nextTerm.length();
					break;
				}
			}
		}
		return sum;
	}

	public boolean isBestSolution() {
		if (_dict.isFullyOccupied()) {
			return true;
		}
		if (_shape.isFullyOccupied()) {
			Iterator<CrosswordEntry> iterator = _entries.iterator();
			while (iterator.hasNext()) {
				CrosswordEntry crosswordEntry = iterator.next();
				if (crosswordEntry.getLength() != _shape.getMaxCapacity(crosswordEntry.getPosition())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void undoMove(CrosswordEntry move) {
		_overlapManager.removeEntry(move);
		_dict.removeEntry(move);
		_shape.removeEntry(move);
		_entries.remove(move);
		_quality-= move.getLength();
	}
	
	public abstract class StrategyIterator implements Iterator<CrosswordEntry> {
		
		
	}
	
	public class SmallGridStrategyIterator extends StrategyIterator {
		
		private Iterator<CrosswordVacantEntry> _entryIt;
		private Iterator<String> _termIt;
		private CrosswordEntry _next;
		private CrosswordVacantEntry _currentVacantEntry;
		
		public SmallGridStrategyIterator() {
			_entryIt = _shape.getIterator(false);
			_next = null;
		}

		public boolean hasNext() {
			if (null != _next) {
				return true;
			} else {
				try {
					_next = findNextMove();
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
			}
			if (null == _termIt) {
				_dict.getIterator(false);
			}
			CrosswordEntry ret = matchCurrentVacantEntry();
			if (null != ret) {
				return ret;
			}
			while (_entryIt.hasNext()) {
				_currentVacantEntry = _entryIt.next();
				ret = matchCurrentVacantEntry();
				if (null != ret) {
					return ret;
				}
			}
			throw new NoSuchElementException();							
		}
		
		private CrosswordEntry matchCurrentVacantEntry() {
			while (_termIt.hasNext()) {
				String term = _termIt.next();
				if (_overlapManager.isOverlapping(term, _currentVacantEntry)) {
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
	
	public class SmallDictionaryStrategyIterator extends StrategyIterator {
		
		private Iterator<CrosswordVacantEntry> _entryIt;
		private Iterator<String> _termIt;
		private CrosswordEntry _next;
		private String _currentTerm;
		
		
		public SmallDictionaryStrategyIterator() {
			_termIt = _dict.getIterator(false);
			_next = null;
		}
		
		public boolean hasNext() {
			if (null != _next) {
				return true;
			} else {
				try {
					_next = findNextMove();
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
			if (null == _entryIt) {
				_shape.getIterator(false);
			}
			CrosswordEntry ret = matchCurrentTerm();
			if (null != ret) {
				return ret;
			}
			while (_termIt.hasNext()) {
				_currentTerm = _termIt.next();
				ret = matchCurrentTerm();
				if (null != ret) {
					return ret;
				}
			}
			throw new NoSuchElementException();
		}
		
		private CrosswordEntry matchCurrentTerm() {
			while (_entryIt.hasNext()) {
				CrosswordVacantEntry vacantEntry = _entryIt.next();
				if (_overlapManager.isOverlapping(_currentTerm, vacantEntry)) {
					CrosswordPosition pos = vacantEntry.getPosition();
					return new MyCrosswordEntry(pos.getX(),
												pos.getY(),
												_currentTerm, _dict.getTermDefinition(_currentTerm),
												pos.isVertical());
				}
			}
			return null;
		}

	}

}
