//###############  
// FILE : MyCrosswordDictionary.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION: Represents a dictionary which keeps and handles all words and
// their definitions.
//###############

package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MyCrosswordDictionary implements CrosswordDictionary, CrosswordTerms, PartitionedDataCollection<Term> {

	// The minimal length allowed for a term.
	private static final int MINIMUM_TERM_LENGTH = 2;
	
	// Holds all the terms in an orderly fashion, partitioned by their lengths
	private ArrayList<TreeSet<Term>> _data;
	// Allows direct access to a certain Term object by its term string
	private HashMap<String, Term> _dictionary;
	private HashSet<String> _usedEntries = new HashSet<String>();
	
	// An array holding in each cell the number of unused terms left in the
	// dictionary in the current cells number. 
	private int _dataLengths[];
	
	// The longest term which hasn't been inserted into the crossword.
	private int _maxAvailableTermLength = 0;
	
	// The shortest term which hasn't been inserted into the crossword.
	private int _minAvailableTermLength = 0;
	
	// The total number of terms existing. 
	private int _numOfTerms = 0;
	
	/**
	 * An empty constructor.
	 */
	public MyCrosswordDictionary() {
		
	}
	
	/**
	 * A copy constructor.
	 * @param other another MyCrosswordDictionary object.
	 */
	public MyCrosswordDictionary(MyCrosswordDictionary other) {
		if (null != other._data) {
			_data = (ArrayList<TreeSet<Term>>)other._data.clone();
		}
		if (null != other._dictionary){
			_dictionary = (HashMap<String, Term>)other._dictionary.clone();
		}
		if (null != other._usedEntries) {
			_usedEntries = (HashSet<String>)other._usedEntries.clone();
		}
		_dataLengths = new int[other._dataLengths.length];
		System.arraycopy(other._dataLengths, 0, _dataLengths, 0,
											other._dataLengths.length);
		_maxAvailableTermLength = other._maxAvailableTermLength;
		_minAvailableTermLength = other._minAvailableTermLength;
		_numOfTerms = other._numOfTerms;
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#getTermDefinition
	 * (java.lang.String)
	 */
	public String getTermDefinition(String term) {
		return _dictionary.get(term).getDefinition();
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#getTerms()
	 */
	public Set<String> getTerms() {
		return _dictionary.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see oop.ex4.crosswords.CrosswordDictionary#load(java.lang.String)
	 */
	public void load(String dictionaryFileName) throws IOException {
		_data = new ArrayList<TreeSet<Term>>();
		_dictionary = new HashMap<String, Term>();
		HashSet<String> glosCheck = new HashSet<String>();
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
						_data.add(new TreeSet<Term>());
					}
				}
				Term term = new Term(word, glos);
				_dictionary.put(word, term);
				_data.get(word.length()).add(term);
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
		_minAvailableTermLength = 
				findMinAvailableTermLength(_minAvailableTermLength);
		
		buildSubTermsCache();
	}
	
	/**
	 * Returns the number of terms in the dictionary.
	 * @return The number of terms in the dictionary.
	 */
	public int getNumberOfTerms() {
		return _numOfTerms;
	}
	
	/**
	 * Returns true iff all terms have been inserted into the crossword.
	 * @return True iff all terms have been inserted into the crossword.
	 */
	public boolean isFullyOccupied() {
		return (_numOfTerms == _usedEntries.size());
	}
	
	/**
	 * This method is called when an entry is being added to the crossword.
	 * the method updates the dictionary databases accordingly.
	 * @param entry the entry which is being added to the crossword.
	 */
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getTerm());
		// As an entry is added we have one less term of this size
		int newSize = --_dataLengths[entry.getLength()];
		if (0 == newSize) {
			_maxAvailableTermLength = findMaxAvailableTermLength(_maxAvailableTermLength);
			_minAvailableTermLength = findMinAvailableTermLength(_minAvailableTermLength);
		}		
	}

	/**
	 * This method is called when an entry is being removed from the crossword.
	 * the method updates the dictionary databases accordingly.
	 * @param entry the entry which is being removed from the crossword.
	 */
	public void removeEntry(CrosswordEntry entry) {
		assert (_dataLengths[entry.getLength()] > 0);
		_usedEntries.remove(entry.getTerm());
		// As an entry is removed we have one more term of this size
		_dataLengths[entry.getLength()]++;
		_maxAvailableTermLength = Math.max(_maxAvailableTermLength, entry.getLength());
		_minAvailableTermLength = Math.min(_minAvailableTermLength, entry.getLength());
		
	}
	
	/**
	 * Returns the length of the longest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return The length of the longest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 */
	public int getMaxAvailableTermLength() {
		return _maxAvailableTermLength;
	}
	
	/**
	 * Returns the length of the shortest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return The length of the shortest word in the dictionary that hadn't 
	 * been inserted into the crossword.
	 * @return
	 */
	public int getMinAvailableTermLength() {
		return _minAvailableTermLength;
	}
	
	/**
	 * Returns the largest length of available term that hasn't been inserted
	 * into the crossword which is smaller than the upperBound given.
	 * @param upperBound the upper bound.
	 * @return The largest length of available term that hasn't been inserted
	 * into the crossword which is smaller than the upperBound given.
	 */
	private int findMaxAvailableTermLength(int upperBound) {
		for (int i = upperBound; i >= 0; i--) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Returns the shortest length of available term that hasn't been inserted
	 * into the crossword which is bigger than the lowerBound given.
	 * @param lowerBound the lower bound.
	 * @return The shortest length of available term that hasn't been inserted
	 * into the crossword which is bigger than the lowerBound given.
	 */
	private int findMinAvailableTermLength(int lowerBound) {
		for (int i = lowerBound; i < _data.size(); i++) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	private void buildSubTermsCache() {
		ArrayList<TreeSet<Term>> newData = new ArrayList<TreeSet<Term>>(_data.size());
		for (int i = 0; i < _data.size(); i++) {
			newData.add(new TreeSet<Term>());
		}
		// For every term in the dictionary (ascending order)
		for (int i = MINIMUM_TERM_LENGTH; i < _data.size(); i++) {
			for (Term term : _data.get(i)) {
				for (int j = 1; j < term.length(); j++) {
					for (int k = term.length(); k >= j + MINIMUM_TERM_LENGTH; k--) {
						String subTerm = term.getTerm().substring(j, k);
						Term subTermObj = _dictionary.get(subTerm);
						if (null != subTermObj) {
							term.addSubTerm(subTermObj, j);
							break; 
						} 
					}
				}
				// Finally, add the term into the new data structure which will
				// now order the terms also by their amount of subterms
				newData.get(i).add(term);
			}
		}
		_data = newData;
	}
	
	public boolean isUsed(Term term) {
		if (_usedEntries.contains(term.getTerm())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the number of partitions in this collection.
	 * @return The number of partitions in this collection.
	 */
	public int getNumOfPartitions() {
		return _data.size();
	}
	
	/**
	 * Returns an iterator of a single segment.
	 * @param partitionNumber The number of the partition we wish to iterate
	 * on.
	 * @return An iterator of a single segment.
	 */
	public Iterator<Term> getRawDataIterator(int partitionNumber) {
		return _data.get(partitionNumber).iterator();
	}
	
	/**
	 * Returns an iterator which iterates on term that haven't been
	 * inserted into the crossword and that their
	 * length is between startLength and endLength.
	 * @param startLength the length of terms the iterator will
	 * start iterating from.
	 * @param endLength the length of terms the iterator will
	 * iterate up until.
	 * @return An iterator which iterates on term that haven't been
	 * inserted into the crossword and that their
	 * length is between startLength and endLength.
	 */
	public Iterator<Term> getIterator(int startLength, int endLength) {
		return new MyContinuousIterator<MyCrosswordDictionary, Term>
											(this, startLength, endLength);
	}
	
}
