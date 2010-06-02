package oop.ex4.crosswords;

public class Term implements Comparable<Term> {
	
	private String _term;
	private String _definition;
	private Term _subTerms[];
	public int _subTermsLengthsSum; //TODO private
	
	public Term(String term, String definition) {
		_term = term;
		_definition = definition;
		_subTermsLengthsSum = 0;
	}
	
	public Term(Term other) {
		_term  = other._term;
		_definition = other._definition;
		_subTermsLengthsSum = other._subTermsLengthsSum;
		if (null != other._subTerms) {
			System.arraycopy(other._subTerms, 0, _subTerms, 0, other._subTerms.length);
		} else {
			_subTerms = null;
		}
	}
	
	public String getTerm() {
		return _term;
	}
	
	public String getDefinition() {
		return _definition;
	}
	
	public int length() {
		return _term.length();
	}
	
	public void addSubTerm(Term subTerm, int offset) {
		if (null == _subTerms) {
			_subTerms = new Term[_term.length()];
		}
		
		_subTerms[offset] = subTerm;
		_subTermsLengthsSum += subTerm.length();		
	}
	
	public Term[] getSubTerms() {
		return _subTerms;
	}

	public int compareTo(Term other) {
		if (this._subTermsLengthsSum != other._subTermsLengthsSum) {
			return other._subTermsLengthsSum - this._subTermsLengthsSum;
		} else {
			return _term.compareTo(other._term); 
		}
	}
	
	public boolean hasSubTerms() {
		return (null != _subTerms);
	}
	
	public String toString() {
		return _term;
	}

}
