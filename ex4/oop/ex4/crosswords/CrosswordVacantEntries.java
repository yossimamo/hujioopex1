package oop.ex4.crosswords;

import oop.ex4.crosswords.MyCrosswordVacantEntries.VacantEntryIterator;

public interface CrosswordVacantEntries {
	
	public int getMaxCapacity(CrosswordPosition pos);
	public VacantEntryIterator getIterator();
	public VacantEntryIterator getIterator(int maxLength);

}
