package oop.ex4.crosswords;

public abstract class CrosswordVacantEntry implements Comparable<CrosswordVacantEntry> {
	
	public abstract CrosswordPosition getPosition();
	public abstract int getMaxCapacity();
	public abstract int compareTo(CrosswordVacantEntry other);
	
}
