package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


// TODO combine CrosswordTerms into this
public class MyCrosswordDictionary implements CrosswordDictionary, CrosswordTerms {
	
	private ArrayList<TreeMap<String, String>> _data;
	private int _maxLength;
	private int _maxLengthPos;

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
		Set<String> terms = _data.get(0).keySet();
		for (int i=1 ; i<=_maxLengthPos ; i++) {
			terms.addAll(_data.get(i).keySet());
		}
		return terms;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#load(java.lang.String)
	 */
	public void load(String dictionaryFileName) throws IOException {
		_maxLengthPos = 0;
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
				if (word.length() > 1) {
					_data.get(word.length() - 2).put(word.toLowerCase(), glos);
					_maxLengthPos = Math.max(_maxLengthPos, word.length() - 2);
				}
				counter++;
			}
		} finally {
			if (sc != null)
				sc.close();
		}
	}
	
	/*public Map.Entry<String, String> pollLongestTerm() {
		Map.Entry<String,String> entry =
					_data.get(_maxLengthPos).pollLastEntry();
		if (_data.get(_maxLengthPos).isEmpty()) {
			updateMaxLengthPos();
		}
		return entry;
	}
	
	public Map.Entry<String, String> pollTerm(int termLength) {
		while (termLength>=0) {
			if (!_data.get(termLength - 2).isEmpty()) {
				return _data.get(termLength).pollLastEntry();
			}
			termLength--;
		}
		//TODO change exception
		throw new NullPointerException();
	}
	
	public boolean isEmpty() {
		if (_maxLengthPos < 0) {
			return false;
		}
		return true;
	}
	
	private void updateMaxLengthPos() {
		while (_maxLengthPos >= 0 && _data.get(_maxLengthPos).isEmpty()) {
			_maxLengthPos--;
		}
	}*/
	
	public Iterator<String> getIterator() {
		return new TermIterator(_maxLength);
	}
	
	public Iterator<String> getIterator(int maxLength) {
		if (maxLength > _maxLength) {
			// TODO throw exception
		}
		return new TermIterator(maxLength);		
	}
	
	public class TermIterator implements Iterator<String> {
		
		private int _currentArrayPos;
		Iterator<String> _currentIterator;
		
		public TermIterator(int maxLength) {
			_currentArrayPos = maxLength;
		}

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public String next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
		
}
