package oop.ex4.crosswords;

public class MyCrosswordEntry implements CrosswordEntry {
	
	private String _term, _definition;
	private CrosswordPosition _pos;

	public MyCrosswordEntry(int x, int y, String term, String definition, boolean isVertical) {
		_pos = new MyCrosswordPosition(x, y, isVertical);
		_term = term;
		_definition = definition;		
	}
	
	public String getDefinition() {
		return _definition;
	}

	public int getLength() {
		return _term.length();
	}

	public CrosswordPosition getPosition() {
		return _pos;
	}

	public String getTerm() {
		return _term;
	}

}
