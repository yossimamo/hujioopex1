package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MyCrosswordDictionary implements CrosswordDictionary, CrosswordTerms {
	
	private ArrayList<TreeMap<String, String>> _data;
	private HashSet<CrosswordPosition> _usedEntries = new HashSet<CrosswordPosition>();
	private int _maxLength;
	
	public MyCrosswordDictionary() {
		
	}
	
	public MyCrosswordDictionary(MyCrosswordDictionary other) {
		if (null != other._data) {
			_data = (ArrayList<TreeMap<String, String>>)other._data.clone();
		}
		if (null != other._usedEntries) {
			_usedEntries = (HashSet<CrosswordPosition>)other._usedEntries.clone();
		}
		
		_maxLength = other._maxLength;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#getTermDefinition(java.lang.String)
	 */
	public String getTermDefinition(String term) {
		return _data.get(term.length()).get(term);
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#getTerms()
	 */
	public Set<String> getTerms() {
		Set<String> terms = new TreeSet<String>();
		for (int i = 0 ; i <= _maxLength ; i++) {
			terms.addAll(_data.get(i).keySet());
		}
		return terms;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#load(java.lang.String)
	 */
	public void load(String dictionaryFileName) throws IOException {
		_maxLength = 0;
		_data = new ArrayList<TreeMap<String, String>>();
		HashSet<String> glosCheck=new HashSet<String>();
		int counter = 1;
		String word, glos;
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader(dictionaryFileName));
			while (sc.hasNextLine()) {
				String entryLine = sc.nextLine();
				if (entryLine.indexOf(':') != -1) {
					word = entryLine.substring(0, entryLine.indexOf(':'));
					if (word.length()<2) continue;
					glos = entryLine.substring(entryLine.indexOf(':') + 1);
					//Adding stars to repetitive glosses for convenience
					//if you implement your dictionary you don't have to do it
					while (glosCheck.contains(glos)) glos+="*";
					glosCheck.add(glos);
				} else {
					//Handling gloss-less files, you don't have to do it
					//If there is no ":" all glosses represented as numbers
					word = entryLine;
					glos = "Dummy" + counter;
				}
				//Ignoring repetitive terms
				if (_data.size() < word.length()) {
					for (int i = _data.size(); i <= word.length(); i++) {
						_data.add(new TreeMap<String, String>());
					}
				}
				_data.get(word.length()).put(word.toLowerCase(), glos);
				_maxLength = Math.max(_maxLength, word.length());
				counter++;
			}
		} finally {
			if (sc != null)
				sc.close();
		}
	}
	
	public int getNumberOfTerms() {
		return _data.size();
	}
	
	public boolean isFullyOccupied() {
		return (_data.size() == _usedEntries.size());
	}
	
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getPosition());
	}

	public void removeEntry(CrosswordEntry entry) {
		_usedEntries.remove(entry.getPosition());
	}
	
	private boolean isUsed(String term) {
		if (_usedEntries.contains(term)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Iterator<String> getIterator(boolean isAscending) {
		return new TermIterator(_maxLength, isAscending);
	}
	
	public Iterator<String> getIterator(int maxLength, boolean isAscending) {
		if (maxLength > _maxLength) {
			// TODO throw exception
		}
		return new TermIterator(maxLength, isAscending);		
	}
	
	// TODO this is almost the same as VacantEntryIterator!!!
	// consider doing something better
	public class TermIterator implements Iterator<String> {
		
		private int _currentArrayPos;
		Iterator<String> _currentIterator;
		String _next;
		int _increment;
		
		public TermIterator(int maxLength, boolean isAscending) {
			_currentArrayPos = maxLength;
			_currentIterator = _data.get(_currentArrayPos).keySet().iterator();
			_next = null;
			_increment = isAscending ? 1 : -1;
		}
		
		public boolean hasNext() {
			if (null != _next) {
				return true;
			}
			while (_currentIterator.hasNext()) {
				_next = _currentIterator.next();
				if (!isUsed(_next)) {
					return true;
				}
			} 
			while (arrayPosInBounds()) {
				_currentArrayPos += _increment;
				_currentIterator = _data.get(_currentArrayPos).keySet().iterator();
				while (_currentIterator.hasNext()) {
					_next = _currentIterator.next();
					if (!isUsed(_next)) {
						return true;
					}
				}
			}
			return false;
		}

		public String next() {
			if (this.hasNext()) {
				return _next;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();			
		}
		
		private boolean arrayPosInBounds() {
			if ((_increment > 0) && (_currentArrayPos + _increment >= _data.size())) {
				return false;
			}
			if ((_increment < 0) && (_currentArrayPos + _increment < 0)) {
				return false;
			}
			return true;
			
		}
	}
		
}
