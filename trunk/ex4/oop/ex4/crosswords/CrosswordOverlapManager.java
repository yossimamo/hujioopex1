package oop.ex4.crosswords;

public interface CrosswordOverlapManager {
	
	public void addEntry (CrosswordEntry entry);
	public void removeEntry (CrosswordEntry entry);
	public boolean isMatch(String term, CrosswordVacantEntry vacantEntry);
	public int getOverlapCount(String term, CrosswordVacantEntry vacantEntry);

}
