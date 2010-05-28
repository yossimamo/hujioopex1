package oop.ex4.crosswords;

public class OverlapManager {
	
	private OverlappedChar _overlapTable[][];	

	public OverlapManager(int width, int height) {
		_overlapTable = new OverlappedChar[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				_overlapTable[i][j] = new OverlappedChar();
			}
		}
	}
	
	public OverlapManager(OverlapManager other) {
		int width = other._overlapTable.length;
		int height = other._overlapTable[0].length;
		_overlapTable = new OverlappedChar[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				_overlapTable[i][j] = new OverlappedChar(other._overlapTable[i][j]);
			}
		}
	}
	
	public void addEntry (CrosswordEntry entry) {
		OverlappedChar[] chars = getOverlappedChars(entry.getLength(), entry.getPosition());
		for (int i=0; i<chars.length; i++) {
			if (chars[i]._instances == 0) {
				chars[i]._char = entry.getTerm().charAt(i);
			}
			chars[i]._instances++;
		}
	}
	
	// assuming this word really exists
	public void removeEntry (CrosswordEntry entry) {
		OverlappedChar[] chars = getOverlappedChars(entry.getLength() , entry.getPosition());
		for (int i=0; i<chars.length; i++) {
			if (chars[i]._instances == 1) {
				chars[i]._char = '\0';
			}
			chars[i]._instances--;
		}
	}

	public boolean isOverlapping(String term, CrosswordVacantEntry vacantEntry) {
		OverlappedChar[] chars = getOverlappedChars(term.length(), vacantEntry.getPosition());
		for (int i=0 ; i < chars.length ; i++) {
			if (term.charAt(i) != chars[i]._char && chars[i]._char != '\0') {
				return false;
			}
		}
		return true;
	}

	public int getOverlapCount(String term, CrosswordVacantEntry vacantEntry) {
		int sum = 0;
		OverlappedChar[] chars = getOverlappedChars(term.length(), vacantEntry.getPosition());
		for (int i=0 ; i < chars.length ; i++) {
			if (term.charAt(i) == chars[i]._char) {
				sum++;
			}
		}
		return sum;
	}
	//TODO delete
	public void print() {
		for (int x=0 ; x<_overlapTable.length ; x++) {
			for (int y = 0 ; y<_overlapTable[0].length ; y++) {
				System.out.print(_overlapTable[x][y]._char);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		for (int x=0 ; x<_overlapTable.length ; x++) {
			for (int y = 0 ; y<_overlapTable[0].length ; y++) {
				System.out.print(_overlapTable[x][y]._instances);
			}
			System.out.println();
		}
	}
	
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
	
	private class OverlappedChar {
		private char _char;
		private int _instances;
		
		public OverlappedChar() {
			_char = '\0';
			_instances = 0;
		}
		
		public OverlappedChar(OverlappedChar other) {
			_char = other._char;
			_instances = other._instances;
		}
		
		// For debugging purposes
		public String toString() {
			return String.format("%c(%d)", _char, _instances);
		}
	}
	
}
