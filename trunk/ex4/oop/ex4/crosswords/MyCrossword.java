package oop.ex4.crosswords;

import java.util.Collection;
import java.util.Iterator;

import oop.ex4.search.SearchBoard;

public class MyCrossword implements Crossword {
	
	private CrosswordDictionary _dict;
	private CrosswordShape _shape;
	
	public MyCrossword() {
		
	}

	public void attachDictionary(CrosswordDictionary dictionary) {
		_dict = dictionary;
	}

	public void attachShape(CrosswordShape shape) {
		_shape = shape;		
	}

	public Collection<CrosswordEntry> getCrosswordEntries() {
		
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	public int getQualityBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isBestSolution() {
		// TODO Auto-generated method stub
		return false;
	}

	public void undoMove(CrosswordEntry move) {
		// TODO Auto-generated method stub
		
	}

}
