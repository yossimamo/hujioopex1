package oop.ex4.crosswords;

import java.util.Iterator;

public abstract class CrosswordStrategyIterator implements Iterator<CrosswordEntry> {

	abstract public boolean hasNext();
	abstract public CrosswordEntry next();
	abstract public void remove();

}
