package oop.ex4.crosswords;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import oop.ex4.search.SearchBoard;

public class MyCrossword implements Crossword {
	
	private enum StrategyType {
		SMALL_GRID_STRATEGY,
		SMALL_DICTIONARY_STRATEGY
	}
	
	private CrosswordDictionary _dict;
	private CrosswordShape _shape;
	private int _quality = 0;
	private StrategyType _strategyType;
	private MyCrosswordVacantEntries _vacantEntries;
	private HashSet<CrosswordEntry> _entries;
	private OverlapChecking _overlapChecking;
	// TODO add overlap checking object
	
	public MyCrossword() {
		
	}
	
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
		_dict = dictionary;
	}

	public void attachShape(CrosswordShape shape) {
		_shape = shape;		
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
		// TODO Auto-generated method stub
		return null;
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
		Iterator<MyCrosswordVacantEntry> vacantEntiesIterator = _shape.getIterator();
		while (vacantEntiesIterator.hasNext()) {
			MyCrosswordVacantEntry nextEntry =  vacantEntiesIterator.next();
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
			Iterator<MyCrosswordVacantEntry> vacantEntiesIterator = _shape.getIterator();
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

}
