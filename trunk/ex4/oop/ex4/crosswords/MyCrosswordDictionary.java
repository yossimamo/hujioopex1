package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MyCrosswordDictionary implements CrosswordDictionary, CrosswordTerms, PartitionedDataCollection<String> {
	
	private static final int MINIMUM_TERM_LENGTH = 2;
	
	private ArrayList<TreeMap<String, String>> _data;
	private int _dataLengths[];
	private HashSet<String> _usedEntries = new HashSet<String>();
	private int _maxAvailableTermLength = 0;
	private int _minAvailableTermLength = 0;
	private int _numOfTerms = 0;
	
	public MyCrosswordDictionary() {
		
	}
	
	public MyCrosswordDictionary(MyCrosswordDictionary other) {
		if (null != other._data) {
			_data = (ArrayList<TreeMap<String, String>>)other._data.clone();
		}
		if (null != other._usedEntries) {
			_usedEntries = (HashSet<String>)other._usedEntries.clone();
		}
		_dataLengths = new int[other._dataLengths.length];
		System.arraycopy(other._dataLengths, 0, _dataLengths, 0, other._dataLengths.length);
		_maxAvailableTermLength = other._maxAvailableTermLength;
		_minAvailableTermLength = other._minAvailableTermLength;
		_numOfTerms = other._numOfTerms;
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
		for (int i = 0 ; i < _data.size(); i++) {
			terms.addAll(_data.get(i).keySet());
		}
		return terms;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#load(java.lang.String)
	 */
	public void load(String dictionaryFileName) throws IOException {
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
					if (word.length() < MINIMUM_TERM_LENGTH) continue;
					glos = entryLine.substring(entryLine.indexOf(':') + 1);
					//Adding stars to repetitive glosses for convenience
					//if you implement your dictionary you don't have to do it
					while (glosCheck.contains(glos)) glos+="*";
					glosCheck.add(glos);
				} else {
					//Handling gloss-less files, you don't have to do it
					//If there is no ":" all glosses represented as numbers
					word = entryLine;
					if (word.length() < MINIMUM_TERM_LENGTH) continue;
					glos = "Dummy" + counter;
				}
				//Ignoring repetitive terms
				if (_data.size() <= word.length()) {
					for (int i = _data.size(); i <= word.length(); i++) {
						_data.add(new TreeMap<String, String>());
					}
				}
				_data.get(word.length()).put(word.toLowerCase(), glos);
				counter++;
			}
		} finally {
			if (sc != null)
				sc.close();
		}
		_numOfTerms = counter - 1;
		// Initialize terms lengths counter
		_dataLengths = new int[_data.size()];
		for (int i = 0; i < _data.size(); i++) {
			int size = _data.get(i).size();
			_dataLengths[i] = _data.get(i).size();
			if (size > 0) {
				_maxAvailableTermLength = i;
			}
		}
		_minAvailableTermLength = findMinAvailableTermLength(_minAvailableTermLength);
	}
	
	public int getNumberOfTerms() {
		return _numOfTerms;
	}
	
	public boolean isFullyOccupied() {
		return (_numOfTerms == _usedEntries.size());
	}
	
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getTerm());
		_dataLengths[entry.getLength()]++;
		_maxAvailableTermLength = Math.max(_maxAvailableTermLength, entry.getLength());
		_minAvailableTermLength = Math.min(_minAvailableTermLength, entry.getLength());
	}

	public void removeEntry(CrosswordEntry entry) {
		assert (_dataLengths[entry.getLength()] > 0);
		_usedEntries.remove(entry.getTerm());
		int newSize = --_dataLengths[entry.getLength()];
		if (0 == newSize) {
			_maxAvailableTermLength = findMaxAvailableTermLength(_maxAvailableTermLength);
			_minAvailableTermLength = findMinAvailableTermLength(_minAvailableTermLength);
		}
	}
	
	public int getMaxAvailableTermLength() {
		return _maxAvailableTermLength;
	}
	
	public int getMinAvailableTermLength() {
		return _minAvailableTermLength;
	}
	
	private int findMaxAvailableTermLength(int upperBound) {
		for (int i = upperBound; i >= 0; i--) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	private int findMinAvailableTermLength(int lowerBound) {
		for (int i = lowerBound; i < _data.size(); i++) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	public boolean isUsed(String term) {
		if (_usedEntries.contains(term)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getNumOfPartitions() {
		return _data.size();
	}
	
	public Iterator<String> getRawDataIterator(int partitionNumber) {
		return _data.get(partitionNumber).keySet().iterator();
	}
	
	public Iterator<String> getIterator(int startLength, int endLength) {
		return new MyContinuousIterator<MyCrosswordDictionary, String>(this, startLength, endLength);
	}
	
}
