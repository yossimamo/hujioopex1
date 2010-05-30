package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
			// System.out.println("Small Grid");// TODO remove print
		}
		else {
			_strategyType = StrategyType.SMALL_DICTIONARY_STRATEGY;
			// System.out.println("Small Dictionary"); // TODO remove print
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
		copiedCrossword._entries = (HashSet<CrosswordEntry>) _entries.clone();
		copiedCrossword._overlapManager = new OverlapManager(_overlapManager);
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
		// long startTime = System.currentTimeMillis();
		HashSet<MyCrosswordPosition> positionHashset = new HashSet<MyCrosswordPosition>();
		int sum = 0;
		int minTermLength = _dict.getMinAvailableTermLength();
		Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(minTermLength, true);
		while (vacantEntriesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntriesIterator.next();
			if (positionHashset.contains(nextEntry.getPosition())) continue;
			// Iterate only on terms that are exactly as long as the vacant entry
			Iterator<String> termsIterator = _dict.getIterator(nextEntry.getMaxCapacity(), nextEntry.getMaxCapacity());
			while (termsIterator.hasNext()) {
				if (_overlapManager.isMatch(termsIterator.next(), nextEntry )) {
					positionHashset.add((MyCrosswordPosition)nextEntry.getPosition());
					sum+= nextEntry.getMaxCapacity();
					break;
				}
			}
		}
		// System.out.printf("getSumOfAvailableEntries took: %d ms\n", System.currentTimeMillis() - startTime);
		return sum;
	}
	
	private int getSumOfAvailableWords() {
		//TODO very inefficient. maybe can use in common with previous function
		long startTime = System.currentTimeMillis();
		int sum = 0;
		Iterator<String> termsIterator = _dict.getIterator(_dict.getMaxAvailableTermLength(), _dict.getMinAvailableTermLength());
		while (termsIterator.hasNext()) {
			String nextTerm =  termsIterator.next();
			// Iterate on vacant entries that are at least as long as the term
			Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(nextTerm.length(), true);
			while (vacantEntriesIterator.hasNext()) {
				CrosswordVacantEntry vacantEntry = vacantEntriesIterator.next();
				if (_overlapManager.isMatch(nextTerm, vacantEntry)) {
					sum += nextTerm.length();
					break;
				}
			}
		}
		//System.out.printf("getSumOfAvailableWords took: %d ms\n", System.currentTimeMillis() - startTime);
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
	
	public String toString() {
		return _overlapManager.toString();
	}
	
	public class SmallDictionaryStrategyIterator extends StrategyIterator {
		
		private Iterator<CrosswordVacantEntry> _entryIt;
		private Iterator<String> _termIt;
		private CrosswordEntry _next;
		private String _currentTerm;
		private TreeSet<CrosswordMatchingVacantEntry> _currentMatchingEntries;
		private Iterator<CrosswordMatchingVacantEntry> _matchingEntriesIt;
		
		public SmallDictionaryStrategyIterator() {
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
			_entryIt = _shape.getIterator(term.length());
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
				if (OverlapManager.MISMATCH == _overlapCount) {
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

}
