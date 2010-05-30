package oop.ex4.crosswords;

import java.util.Iterator;


public interface CrosswordTerms {
	
	public int getNumberOfTerms();
	public void addEntry(CrosswordEntry entry);
	public void removeEntry(CrosswordEntry entry);
	public Iterator<String> getIterator(int startLength, int endLength);
	
}
