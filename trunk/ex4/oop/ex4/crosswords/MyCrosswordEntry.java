//###############  
// FILE : MyCrosswordEntry.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: A crossword entry is a word at a given position in a crossword.
//###############

package oop.ex4.crosswords;

/**
 * A crossword entry is a word at a given position in a crossword.
 * 
 * @author Uri Greenberg and Yossi mamo.
 */
public class MyCrosswordEntry implements CrosswordEntry {
	
	// holds the term and the definition of the entry.
	private String _term, _definition;
	
	//holds the position of the entry.
	private CrosswordPosition _pos;

	/**
	 * Constructs a new MyCrosswordEntry object from the given parameters. 
	 * @param x The x coordinate of the position
	 * @param y The y coordinate of the position.
	 * @param term The term of the entry.
	 * @param definition The definition of the term.
	 * @param isVertical whether or not the position is vertical.
	 */
	public MyCrosswordEntry(int x, int y, String term, String definition, boolean isVertical) {
		_pos = new MyCrosswordPosition(x, y, isVertical);
		_term = term;
		_definition = definition;		
	}
	
	/**
	 * Returns the corresponding definition for the entry
	 * 
	 * @return the corresponding dictionary definition
	 */
	public String getDefinition() {
		return _definition;
	}

	/**
	 * Retrieves length of the entry (Redundant convenience, may be calculated
	 * through getTerm())
	 * 
	 * @return number of letters in term
	 */
	public int getLength() {
		return _term.length();
	}

	/**
	 * Returns the X/Y/Vertical position of an entry
	 * @return
	 */
	public CrosswordPosition getPosition() {
		return _pos;
	}

	/**
	 * Returns the corresponding word for the entry
	 * 
	 * @return the corresponding term
	 */
	public String getTerm() {
		return _term;
	}
	
	
	/**
	 * for debugging purposes.
	 */
	public String toString() {
		return String.format("%s (%d,%d) %s", _term, _pos.getX(), _pos.getY(), _pos.isVertical()?"V":"H");
	}

}
