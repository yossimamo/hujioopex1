package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


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
	private MyCrosswordOverlapManager _overlapManager;
	
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
		 _overlapManager = new MyCrosswordOverlapManager(_shape.getWidth(), _shape.getHeight());
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
		copiedCrossword._overlapManager = new MyCrosswordOverlapManager(_overlapManager);
		return copiedCrossword;
	}

	public Iterator<CrosswordEntry> getMoveIterator() {
		switch(_strategyType) {
		case SMALL_GRID_STRATEGY:
			return new SmallGridStrategyIterator(_shape, _dict, _overlapManager);
		case SMALL_DICTIONARY_STRATEGY:
			return new SmallDictionaryStrategyIterator(_shape, _dict, _overlapManager);
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
		Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(minTermLength, _shape.getMaxVacantEntryLength());
		while (vacantEntriesIterator.hasNext()) {
			CrosswordVacantEntry nextEntry =  vacantEntriesIterator.next();
			if (positionHashset.contains(nextEntry.getPosition())) continue;
			// Iterate only on terms that are exactly as long as the vacant entry
			Iterator<String> termsIterator = _dict.getIterator(nextEntry.getMaxCapacity(), nextEntry.getMaxCapacity());
			while (termsIterator.hasNext()) {
				if (_overlapManager.isMatch(termsIterator.next(), nextEntry)) {
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
			Iterator<CrosswordVacantEntry> vacantEntriesIterator = _shape.getIterator(nextTerm.length(), _shape.getMaxVacantEntryLength());
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
		if ((_dict.isFullyOccupied()) || (_quality == _shape.getTotalEntryLengthsSum())) {
			return true;
		}
		return false;
	}

	public void undoMove(CrosswordEntry move) {
		_overlapManager.removeEntry(move);
		_dict.removeEntry(move);
		_shape.removeEntry(move);
		_entries.remove(move);
		_quality -= move.getLength();
	}
	
	public String toString() {
		return _overlapManager.toString();
	}

}
