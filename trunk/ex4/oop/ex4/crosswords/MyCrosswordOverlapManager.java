//###############  
// FILE : MyCrosswordOverlapManager.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: this class is in charge of checking the overlapping of words
// and entries in the crossword.
//###############

package oop.ex4.crosswords;

/**
 * 
 * this class is in charge of checking the overlapping of words
 * and entries in the crossword.
 * @author Uri Greenberg and Yossi Mamo
 *
 */
public class MyCrosswordOverlapManager implements CrosswordOverlapManager {
	
	// the value of a mismatch between an entry and a term.
	public static final int MISMATCH = -1;
	
	// represents an empty char.
	private static final char NO_CHAR = '\0';
	
	// a two dimensional array holding the information needed for the
	// comparison
	private OverlappedChar _overlapTable[][];	

	/**
	 * initializes the data base.
	 * @param width the maximal width of the crossword.
	 * @param height the maximal height of the crossword.
	 */
	public MyCrosswordOverlapManager(int width, int height) {
		_overlapTable = new OverlappedChar[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				_overlapTable[i][j] = new OverlappedChar();
			}
		}
	}
	
	/**
	 * a copy constructor
	 * @param other the MyCrosswordOverlapManager object to copy.
	 */
	public MyCrosswordOverlapManager(MyCrosswordOverlapManager other) {
		int width = other._overlapTable.length;
		int height = other._overlapTable[0].length;
		_overlapTable = new OverlappedChar[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				_overlapTable[i][j] = 
					new OverlappedChar(other._overlapTable[i][j]);
			}
		}
	}
	
	/**
	 * when a new entry is being entered into the crossword this method
	 * is called to update the database accordingly.(assuming the word fits).
	 * @param entry the entry that is being added to the crossword.
	 */
	public void addEntry (CrosswordEntry entry) {
		OverlappedChar[] chars = 
			getOverlappedChars(entry.getLength(), entry.getPosition());
		for (int i=0; i<chars.length; i++) {
			if (chars[i]._instances == 0) {
				chars[i]._char = entry.getTerm().charAt(i);
			}
			chars[i]._instances++;
		}
	}
	
	/**
	 * when an entry is being removed from the crossword this method
	 * is called to update the database accordingly.(assuming the word is in
	 * the database).
	 * @param entry the entry that is being removed from the crossword.
	 */
	public void removeEntry (CrosswordEntry entry) {
		OverlappedChar[] chars = 
			getOverlappedChars(entry.getLength() , entry.getPosition());
		for (int i=0; i<chars.length; i++) {
			if (chars[i]._instances == 1) {
				chars[i]._char = NO_CHAR;
			}
			chars[i]._instances--;
		}
	}

	/**
	 * receives a term and a vacantEntry and determines if it is possible 
	 * to insert the term into the entry.
	 * @param term a term
	 * @param vacantEntry a vacant entry
	 * @return true if it is possible to insert the term into the entry or 
	 * false otherwise.
	 */
	public boolean isMatch(String term, CrosswordVacantEntry vacantEntry) {
		if (getOverlapCount(term, vacantEntry) != MISMATCH) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * receives a term and a vacant entry and returns the number of 
	 * overlapping letters they have. or MISMATCH if they do not fit.
	 * @param term a term
	 * @param vacantEntry a vacant entry in the crossword
	 * @return the number of overlapping letters they have.
	 * or MISMATCH if they do not fit.
	 */
	public int getOverlapCount(String term, CrosswordVacantEntry vacantEntry) {
		int sum = 0;
		OverlappedChar[] chars =
			getOverlappedChars(term.length(), vacantEntry.getPosition());
		for (int i = 0 ; i < chars.length ; i++) {
			if ((term.charAt(i) != chars[i]._char) &&
								(chars[i]._char != NO_CHAR)) {
				sum = MISMATCH;
				break;
			}
			if (term.charAt(i) == chars[i]._char) {
				sum++;
			}
		}
		return sum;
	}
	
	/**
	 * returns a string representation of the array. for debugging purposes. 
	 * @return a string representation of the array.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < _overlapTable[0].length; j++) {
			for (int i = 0; i < _overlapTable.length; i++) {
				sb.append(' ');
				sb.append(_overlapTable[i][j]._char);
				sb.append(' ');
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * returns an array at the given length  holding all the OverlappedChar
	 * objects from the overlapTable from the position that was given.
	 * @param length the length of the array to be returned (meaning the 
	 * number of objects from the position to be returned).
	 * @param position a position in the crossword.
	 * @return an array at the given length  holding all the OverlappedChar
	 * objects from the overlapTable from the position that was given.
	 */
	private OverlappedChar[] getOverlappedChars(int length,
			CrosswordPosition position) {
		OverlappedChar[] overlappedChars = new OverlappedChar[length];
		int x = position.getX();
		int y = position.getY();
		if (position.isVertical()) {
			for (int i = y ; i < y + length ; i++) {
				overlappedChars[i - y] = _overlapTable[x][i];
			}
		}
		else {
			for (int i = x ; i < x + length ; i++) {
				overlappedChars[i - x] = _overlapTable[i][y];
			}
		}
		return overlappedChars;
	}
	
	/**
	 * A private class. it is the object which is being held in the 
	 * overlapTable. it holds the character that is in the bracket and the
	 * number of times its been added to the crossword.
	 * @author Uri Greenberg and Yossi mamo.
	 *
	 */
	private class OverlappedChar {
		
		// The character of this object
		private char _char;
		
		// The number of times its been added to the crossword and to the
		// table.
		private int _instances;
		
		/**
		 * Initializes the object so the char will hold NO_CHAR and the number
		 * of instances will be 0;
		 */
		public OverlappedChar() {
			_char = NO_CHAR;
			_instances = 0;
		}
		
		/**
		 * A copy constructor
		 * @param other another OverlappedChar object.
		 */
		public OverlappedChar(OverlappedChar other) {
			_char = other._char;
			_instances = other._instances;
		}
		
		
		
		/**
		 * For debugging purposes
		 */
		public String toString() {
			return String.format("%c(%d)", _char, _instances);
		}
	}
	
}
