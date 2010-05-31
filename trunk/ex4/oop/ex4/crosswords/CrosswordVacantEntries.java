package oop.ex4.crosswords;

import java.util.Iterator;

public interface CrosswordVacantEntries {
	
	public int getNumberOfEntries();
	public int getTotalEntryLengthsSum();
	public int getMaxCapacity(CrosswordPosition pos);
	public int getMaxVacantEntryLength();
	public int getMinVacantEntryLength();
	public boolean isFullyOccupied();
	public void addEntry(CrosswordEntry entry);
	public void removeEntry(CrosswordEntry entry);
	public Iterator<CrosswordVacantEntry> getIterator(int startLength, int endLength);
}
