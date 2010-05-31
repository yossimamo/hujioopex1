package oop.ex4.crosswords;

import java.util.Iterator;

public abstract class ContinuousIterator<D extends PartitionedDataCollection<E>, E> implements Iterator<E> {
	
	abstract public boolean hasNext();
	abstract public E next();
	abstract public void remove();

}
