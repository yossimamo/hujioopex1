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
	// TODO add overlap checking object
	
	public MyCrossword() {
		
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
		// TODO Auto-generated method stub
		
	}

	public SearchBoard<CrosswordEntry> getCopy() {
		// TODO Auto-generated method stub
		return null;
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

	public boolean isBestSolution() {
		//best possible is one of:
		//-all words used
		//-all vacant entries filled to their maximal length (iterate on all
		// entries and compare lengths against vacant entries in the same positions
		
		return false;
	}

	public void undoMove(CrosswordEntry move) {
		// TODO Auto-generated method stub
		
	}

}
