package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import oop.ex4.search.SearchBoard;

public class MyCrossword implements Crossword {
	
	private enum StrategyType {
		SMALL_GRID_STRATEGY,
		SMALL_DICTIONARY_STRATEGY
	}
	
	private MyCrosswordDictionary _dict;
	private MyCrosswordShape _shape;
	private CrosswordVacantEntries _vacantEntries;
	private int _quality = 0;
	private StrategyType _strategyType;
	private HashSet<CrosswordEntry> _entries;
	private HashSet<String> _usedTerms;
	private HashSet<CrosswordPosition> _usedEntries;
	private OverlapChecking _overlapChecking;
	// TODO add overlap checking object
	
	public MyCrossword() {
		
	}
	
	// TODO merge into getCopy()
	public MyCrossword(CrosswordDictionary dict, CrosswordShape shape,
			int quality, StrategyType strategyType,
			HashSet<CrosswordEntry> entries,
			OverlapChecking overlapChecking) {
		_dict = dict.getCopy();
		_shape = shape.getCopy();
		_quality = quality;
		_strategyType = strategyType;
		//TODO check if the shallow clone is sufficient. and casting
		_entries = (HashSet<CrosswordEntry>) entries.clone();
		_overlapChecking = overlapChecking.getCopy();
	}

	public void attachDictionary(CrosswordDictionary dictionary) {
		_dict = (MyCrosswordDictionary)dictionary;
		if (null != _shape) {
			determineStrategy();
		}
	}

	public void attachShape(CrosswordShape shape) {
		_shape = (MyCrosswordShape)shape;
		if (null != _dict) {
			determineStrategy();
		}
	}

	public Collection<CrosswordEntry> getCrosswordEntries() {
		return _entries;
	}

	public void doMove(CrosswordEntry move) {
		_overlapChecking.addEntry(move);
		_dict.removeTerm(move.getTerm());
		_shape.removeVacantEntry(move.getPosition());
		_entries.add(move);
		_quality+= move.getLength();
	}

	public SearchBoard<CrosswordEntry> getCopy() {
		return new MyCrossword(_dict, _shape, _quality, _strategyType,
				_entries, _overlapChecking);
	}

	public Iterator<CrosswordEntry> getMoveIterator() {
		switch(_strategyType) {
		case SMALL_GRID_STRATEGY:
			return new SmallGridStrategyIterator();
		case SMALL_DICTIONARY_STRATEGY:
			return new SmallDictionaryStrategyIterator();
		default:
			// TODO throw exception	
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
		}
		
	}

	private int getSumOfAvailableEntries() {
		//TODO very inefficient
		int sum = 0;
		Iterator<CrosswordVacantEntry> vacantEntiesIterator = _vacantEntries.getIterator();
		while (vacantEntiesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntiesIterator.next();
			Iterator<String> termsIterator = _dict.getIterator();
			while (termsIterator.hasNext()) {
				if (_overlapChecking.isOverlapping(nextEntry, termsIterator.next())) {
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
		Iterator<String> termsIterator = _dict.getIterator();
		while (termsIterator.hasNext()) {
			String nextTerm =  termsIterator.next();
			Iterator<CrosswordVacantEntry> vacantEntiesIterator = _shape.getIterator();
			while (vacantEntiesIterator.hasNext()) {
				if (_overlapChecking.isOverlapping(vacantEntiesIterator.next(), nextTerm)) {
					sum+= nextTerm.length();
					break;
				}
			}
		}
		return sum;
	}

	public boolean isBestSolution() {
		//best possible is one of:
		//-all words used
		//-all vacant entries filled to their maximal length (iterate on all
		// entries and compare lengths against vacant entries in the same positions
		
		if (_dict.isEmpty()) {
			return true;
		}
		if (_shape.isEmpty()) {
			Iterator<CrosswordEntry> iterator = _entries.iterator();
			while (iterator.hasNext()) {
				CrosswordEntry crosswordEntry = iterator.next();
				if (crosswordEntry.getLength() != _shape.getVacantEntryLength(crosswordEntry.getPosition())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void undoMove(CrosswordEntry move) {
		_overlapChecking.removeEntry(move);
		_dict.addTerm(move.getTerm());
		_shape.addVacantEntry(move.getPosition());
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
			_entryIt = _vacantEntries.getIterator();
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
				_dict.getIterator();
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
				// TODO check if word is already in crossword, if so, continue to next
				// TODO match word to entry
				String term = _termIt.next();
				if (isMatch(term, _currentVacantEntry)) {
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
			_termIt = _dict.getIterator();
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
				_vacantEntries.getIterator();
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
				// TODO match word to entry
				CrosswordVacantEntry vacantEntry = _entryIt.next();
				if (isMatch(_currentTerm, vacantEntry)) {
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
