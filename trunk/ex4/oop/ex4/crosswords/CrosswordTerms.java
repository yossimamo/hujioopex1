package oop.ex4.crosswords;

import java.util.Iterator;


public interface CrosswordTerms {
	
	public Iterator<String> getIterator();
	public Iterator<String> getIterator(int maxLength);
	
}
