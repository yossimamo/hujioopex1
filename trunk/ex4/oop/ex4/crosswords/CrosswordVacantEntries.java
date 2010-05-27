package oop.ex4.crosswords;

import java.util.Iterator;

public interface CrosswordVacantEntries {
	
	public int getMaxCapacity(CrosswordPosition pos);
	public Iterator<CrosswordVacantEntry> getIterator();
	public Iterator<CrosswordVacantEntry> getIterator(int maxLength);

}
