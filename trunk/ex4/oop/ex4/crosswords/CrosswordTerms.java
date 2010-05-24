package oop.ex4.crosswords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class CrosswordTerms {
	
	private ArrayList<TreeSet<String>> _terms;
	
	private int _maxLengthPos;
	
	
	
	public CrosswordTerms(CrosswordDictionary dict , String dictFileName) 
					throws IOException {
		int maxLength = 0;
		dict.load(dictFileName);
		Set<String> allTerms = dict.getTerms();
		Iterator<String> iterator = allTerms.iterator();
		//TODO if there is a better way to find the maximum
		while (iterator.hasNext()) {
			maxLength = 
				Math.max(maxLength, ( iterator.next()).length());
		}
		_terms = new ArrayList<TreeSet<String>>(maxLength);
		for (int i=0; i<maxLength ; i++) {
			_terms.add(new TreeSet<String>());
		}
		iterator = allTerms.iterator();
		String str;
		while (iterator.hasNext()) {
			str = iterator.next();
			_terms.get(str.length() - 1).add(str);
		}
		_maxLengthPos = maxLength - 1;
	}
	
	public String pollLongestTerm() {
		if (!updateMaxLengthPos()) {
			throw new NullPointerException();
		}
		return _terms.get(_maxLengthPos).pollFirst();
	}
	
	public String pollTerm(int termLength) {
		while (termLength>=0) {
			if (!_terms.get(termLength).isEmpty()) {
				return _terms.get(termLength).pollFirst();
			}
			termLength--;
		}
		throw new NullPointerException();
	}

	public boolean isEmpty() {
		return updateMaxLengthPos();
	}
	
	private boolean updateMaxLengthPos() {
		while (_maxLengthPos >= 0) {
			if (!_terms.get(_maxLengthPos).isEmpty()) {
				return true;
			}
			_maxLengthPos--;
		}
		return false;
	}

}
