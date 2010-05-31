//###############  
// FILE : CrosswordVacantEntry.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword entry is a word at a given position in a crossword.
//###############

package oop.ex4.crosswords;

public abstract class CrosswordVacantEntry implements Comparable<CrosswordVacantEntry> {
	
	
	public abstract CrosswordPosition getPosition();
	
	
	public abstract int getMaxCapacity();
	
	
	public abstract int compareTo(CrosswordVacantEntry other);
	
}
