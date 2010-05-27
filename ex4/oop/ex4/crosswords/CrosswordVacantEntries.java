package oop.ex4.crosswords;

import java.util.Iterator;

public interface CrosswordVacantEntries {
	
	public int getNumberOfEntries();
	public int getMaxCapacity(CrosswordPosition pos);
	public void addEntry(CrosswordEntry entry);
	public void removeEntry(CrosswordEntry entry);
	public Iterator<CrosswordVacantEntry> getIterator(boolean isAscending);
	public Iterator<CrosswordVacantEntry> getIterator(int maxLength, boolean isAscending);

}
