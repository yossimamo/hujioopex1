package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


import oop.ex4.search.SearchBoard;

public class MyCrossword implements Crossword {
	
	private MyCrosswordDictionary _dict;
	private MyCrosswordShape _shape;
	private CrosswordStrategy _strategy;
	private int _quality = 0;
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

	public void attachShape(CrosswordShape shape) {
		_shape = (MyCrosswordShape)shape;
		_overlapManager = new MyCrosswordOverlapManager(_shape.getWidth(), _shape.getHeight());
		if (null != _dict) {
			determineStrategy();
		}
	}
	
	private void determineStrategy() {
		if (_shape.getNumberOfEntries() <= _dict.getNumberOfTerms()) {
			_strategy = new SmallGridStrategy(_shape, _dict, _overlapManager);
		} else {
			_strategy = new SmallDictionaryStrategy(_shape, _dict, _overlapManager);
		}
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
		copiedCrossword._strategy = _strategy;
		copiedCrossword._entries = (HashSet<CrosswordEntry>) _entries.clone();
		copiedCrossword._overlapManager = new MyCrosswordOverlapManager(_overlapManager);
		return copiedCrossword;
	}

	public Iterator<CrosswordEntry> getMoveIterator() {
		return _strategy.getIterator();
	}

	public int getQuality() {
		return _quality;
	}

	public int getQualityBound() {
		return _strategy.getUpperBoundQuality(_quality);
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
