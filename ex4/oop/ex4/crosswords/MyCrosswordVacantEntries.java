package oop.ex4.crosswords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

// TODO make generic and combine with entries?
public class MyCrosswordVacantEntries {
	
	private ArrayList<TreeSet<MyCrosswordVacantEntry>> _availableVacantEntries;
	private HashMap<CrosswordPosition, MyCrosswordVacantEntry> _initialVacantEntries;
	private int _maxCapacity;
	
	public MyCrosswordVacantEntries(CrosswordShape shape) {
		_maxCapacity = Math.max(shape.getHeight(), shape.getWidth());
		assert(0 < _maxCapacity);
		_availableVacantEntries = new ArrayList<TreeSet<MyCrosswordVacantEntry>>(_maxCapacity);
		// TODO iterate on all positions and create vacant entries for each
		// insert into availableVacantEntries and (with position as a key) to
		// initialvacantentries
	}
	
	public MyCrosswordVacantEntry getLongestVacantEntry() {
		// TODO
	}
	
	public MyCrosswordVacantEntry getVacantEntry(int length) {
		// TODO
	}
	
	// restores a vacant entry back into the available pool
	public void restoreVacantEntry(CrosswordPosition pos) {
		// TODO
	}
	
	public boolean isEmpty() {
		// TODO
		return false;
	}
	
	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
	}
}
