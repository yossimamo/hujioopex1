package oop.ex4.crosswords;

import java.util.Iterator;

public abstract class StrategyIterator implements Iterator<CrosswordEntry> {

	abstract public boolean hasNext();
	abstract public CrosswordEntry next();
	abstract public void remove();

}
