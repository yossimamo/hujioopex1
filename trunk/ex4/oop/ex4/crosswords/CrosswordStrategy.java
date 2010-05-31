package oop.ex4.crosswords;

import java.util.Iterator;

public interface CrosswordStrategy {
	
	public Iterator<CrosswordEntry> getIterator();
	public int getUpperBoundQuality(int currentQuality);

}
