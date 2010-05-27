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
			if (term.charAt(i) != chars[i]._char && term.charAt(i) != '\0') {
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
	
	private OverlappedChar[] getOverlappedChars(int length,
			CrosswordPosition position) {
		OverlappedChar[] overlappedChars = new OverlappedChar[length];
		int x = position.getX();
		int y = position.getY();
		if (position.isVertical()) {
			for (int i=y ; i< y+length ; i++) {
				overlappedChars[i] = _overlapTable[x][i];
			}
		}
		else {
			for (int i=x ; i< x+length ; i++) {
				overlappedChars[i] = _overlapTable[i][y];
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
	}
	
}
