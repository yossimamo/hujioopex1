//###############  
// FILE : MyCrosswordShape.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION:  A class handling the shape of the crossword and the vacant 
// entries in it.
//###############

package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * A class handling the shape of the crossword and the vacant 
 * entries in it.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class MyCrosswordShape implements CrosswordShape,
	CrosswordVacantEntries, PartitionedDataCollection<CrosswordVacantEntry> {
	
	// The minimal length of entries allowed.
	private static final int MINIMUM_ENTRY_LENGTH = 2;
	
	// The boolean value of horizontal.
	private static final boolean HORIZONTAL = false;
	
	// The boolean value of vertical.
	private static final boolean VERTICAL = true;
	
	// An arrayList of strings, when each string is a line in the
	// crossword shape.txt file
	protected List<String> _originalData = new ArrayList<String>();
	
	// Holds in each cell of the array a treeSet of vacant entries with the 
	// same maximal capacity as the number of the cell in the array it is being
	//held at.
	private ArrayList<TreeSet<CrosswordVacantEntry>> _data;
	
	// An array holding in each cell the number of unused vacant entries
	// with the cells number max capacity left. 
	private int _dataLengths[];
	
	// A hashMap connecting positions to the corresponding vacant entry.
	private HashMap<CrosswordPosition,
	CrosswordVacantEntry>_initialVacantEntries;
	
	// The positions that terms were inserted into them.
	private HashSet<CrosswordPosition> _usedEntries =
		new HashSet<CrosswordPosition>();
	
	// The longest vacant entry which hasn't been used in the crossword.
	private int _maxVacantEntryLength = 0;
	
	// The shortest vacant entry which hasn't been used in the crossword.
	private int _minVacantEntryLength = 0;
	
	// The total sum of lengths of all vacant entries.
	private int _totalEntryLengthsSum = 0;
	
	// The total number of vacant entries existing.
	private int _numOfEntries = 0;
	
	/**
	 * An empty constructor.
	 */
	public MyCrosswordShape() {
		
	}
	
	/**
	 * A copy constructor.
	 * @param other another MyCrosswordShape object.
	 */
	public MyCrosswordShape(MyCrosswordShape other) {
		if (null != other._originalData) {
			_originalData = new ArrayList<String>(other._originalData);
		}
		if (null != other._data) {
			_data = 
				(ArrayList<TreeSet<CrosswordVacantEntry>>)other._data.clone();
		}
		if (null != other._initialVacantEntries) {
			_initialVacantEntries = 
				(HashMap<CrosswordPosition, CrosswordVacantEntry>)
				other._initialVacantEntries.clone();
		}
		if (null != _usedEntries) {
			_usedEntries =
				(HashSet<CrosswordPosition>)other._usedEntries.clone();
		}
		_dataLengths = new int[other._dataLengths.length];
		System.arraycopy(other._dataLengths, 0, _dataLengths, 0,
				other._dataLengths.length);
		_maxVacantEntryLength = other._maxVacantEntryLength;
		_minVacantEntryLength = other._minVacantEntryLength;
		_totalEntryLengthsSum = other._totalEntryLengthsSum;
		_numOfEntries = other._numOfEntries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#getHeight()
	 */
	public Integer getHeight() {
		return _originalData.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#getWidth()
	 */
	public Integer getWidth() {
		return _originalData.get(0).length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#isFilled(java.lang.Integer, java.lang.Integer)
	 */
	public SlotType getSlotType(CrosswordPosition pos)
			throws InvalidParameterException {
		if (pos.getX() >= getWidth() || pos.getX() < 0 || 
				pos.getY() >= getHeight() || pos.getY() < 0)
			return SlotType.FRAME_SLOT;
		switch (_originalData.get(pos.getY()).charAt(pos.getX())) {
		case '_':
			return SlotType.UNUSED_SLOT;
		default:
			return SlotType.FRAME_SLOT;
		}
	}

	/**
	 * loads the data related to the shape of the crossword and the vacant
	 *  entries from the file and initializes all database accordingly.
	 *  @param textFileName the path to the file of the shape.
	 */
	public void load(String textFileName) throws IOException {
		Scanner sc=null;
		try {
			_originalData = new ArrayList<String>();
			sc = new Scanner(new FileReader(textFileName));
			while (sc.hasNextLine()) {
				_originalData.add(sc.nextLine());
			}
		} finally {
			if (sc != null) sc.close();
		}
		int maxCapacity = Math.max(getHeight(), getWidth());
		assert(0 < maxCapacity);
		_data = new ArrayList<TreeSet<CrosswordVacantEntry>>(maxCapacity);
		// Makes the ArrayList as long as the longest vacant entry capacity
		// possible in this crossword.
		for (int i = 0; i <= maxCapacity; i++) {
			_data.add(new TreeSet<CrosswordVacantEntry>());
		}
		_initialVacantEntries =
			new HashMap<CrosswordPosition, CrosswordVacantEntry>();
		for (int y = 0; y < _originalData.size(); y++) {
			initLineInDatabase(_originalData.get(y), HORIZONTAL, y);
		}
		for (int x = 0; x < _originalData.get(0).length() ; x++) {
			StringBuilder currentColumn = new StringBuilder();
			// Builds a string from every column.
			for (int i=0; i < _originalData.size(); i++) {
				currentColumn.append(_originalData.get(i).charAt(x));
			}
			initLineInDatabase(currentColumn.toString(), VERTICAL, x);
		}
		// Initialize vacant entries lengths counter
		_dataLengths = new int[_data.size()];
		for (int i = 0; i < _data.size(); i++) {
			int size = _data.get(i).size();
			_dataLengths[i] = size;
			if (size > 0) {
				_maxVacantEntryLength = i;
			}
			_numOfEntries += size;
		}
		_minVacantEntryLength =
			findMinVacantEntryLength(_minVacantEntryLength);
		for (CrosswordPosition pos : _initialVacantEntries.keySet()) {
			_totalEntryLengthsSum += _initialVacantEntries.get(pos).getMaxCapacity();
		}
	}
	
	/**
	 * Receives a line or a column as a string and initializes it in the 
	 * database.
	 * @param currentLine the line or column as a string.
	 * @param isVertical true if its a column or false if it is a line.
	 * @param otherCoordinate the line or column number.
	 */
	private void initLineInDatabase(String currentLine, boolean isVertical,
														int otherCoordinate) {
		// runs on all chars in the string
		for (int i = 0; i < currentLine.length(); i++) {
			if (currentLine.charAt(i) == '_') {
				int vacantEntryMaxLength =
					getVacantEntryMaxLength(currentLine, i);
				// the next chars up until vacantEntryMaxLength - 1 are '_'
				//because we know the length of the vacant entry.
				for (int j = 0 ; j < vacantEntryMaxLength - 1; j++) {
					MyCrosswordPosition position =
						new MyCrosswordPosition(isVertical ? otherCoordinate:
						 i+j, isVertical ? i+j : otherCoordinate, isVertical);
					// For each char (position) there could be more than 
					// one vacant entry (different lengths)
					for (int k =1 ; k < vacantEntryMaxLength - j ; k++) {
						MyCrosswordVacantEntry entry =
							new MyCrosswordVacantEntry(position, k + 1);
						//overwritten for each k that is different, and the
						//last one is with the max capacity
						_initialVacantEntries.put(position,
								new MyCrosswordVacantEntry(position, k+1));
						_data.get(entry.getMaxCapacity()).add(entry);
					}
				}
				//all of these char have already been processed.
				i = i + vacantEntryMaxLength + 1;
			}
		}
	}

	/**
	 * Returns the maximal length of the vacant entry beginning at this point. 
	 * @param currentLine The line we are currently scanning.
	 * @param indexInLine The position in the line.
	 * @return The maximal length of the vacant entry beginning at this point.
	 */
	private int getVacantEntryMaxLength(String currentLine, int indexInLine) {
		int vacantEntryLength = 0;
		while ((indexInLine < currentLine.length()) &&
				(currentLine.charAt(indexInLine) == '_')) {
			indexInLine++;
			vacantEntryLength++;
		} 
		return vacantEntryLength;
	}
	
	/**
	 * Returns the sum of all lengths of all entries.
	 * @return The sum of all lengths of all entries.
	 */
	public int getTotalEntryLengthsSum() {
		return _totalEntryLengthsSum;
	}
	
	/**
	 * returns the max capacity of the vacant entry at the given position.
	 * @param pos A position in the crossword.
	 * @return The max capacity of the vacant entry at the given position.
	 */
	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
	}
	
	/**
	 * Returns true if there are no more vacant entries left or false
	 * otherwise.
	 * @return True if there are no more vacant entries left or false
	 * otherwise.
	 */
	public boolean isFullyOccupied() {
		return (_numOfEntries == _usedEntries.size());
	}
	
	/**
	 * Returns the total number of entries in the crossword. 
	 * @return The total number of entries in the crossword. 
	 */
	public int getNumberOfEntries() {
		return _numOfEntries;
	}
	
	/**
	 * Returns the length of the longest vacant entry in the crossword.
	 * @return The length of the longest vacant entry in the crossword.
	 */
	public int getMaxVacantEntryLength() {
		return _maxVacantEntryLength;
	}
	
	/**
	 * Returns the length of the shortest vacant entry in the crossword.
	 * @return The length of the shortest vacant entry in the crossword.
	 */
	public int getMinVacantEntryLength() {
		return _minVacantEntryLength;
	}
	
	/**
	 * This method is called when a new entry is added to the crossword.
	 * it updates the database accordingly.
	 * @param entry The entry that is being added to the crossword.
	 */
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getPosition());
		// As an entry is added we have one less vacant space of this size
		int newSize = --_dataLengths[entry.getLength()];
		if (0 == newSize) {
			_maxVacantEntryLength = findMaxVacantEntryLength(_maxVacantEntryLength);
			_minVacantEntryLength = findMinVacantEntryLength(_minVacantEntryLength);
		}
		
	}

	/**
	 * This method is called when a new entry is removed from the crossword.
	 * it updates the database accordingly.
	 * @param entry The entry that is being removed from the crossword.
	 */
	public void removeEntry(CrosswordEntry entry) {
		assert(_dataLengths[entry.getLength()] > 0);
		_usedEntries.remove(entry.getPosition());
		// As an entry is removed we have one more vacant space of this size
		_dataLengths[entry.getLength()]++;
		_maxVacantEntryLength = Math.max(_maxVacantEntryLength, entry.getLength());
		_minVacantEntryLength = Math.min(_minVacantEntryLength, entry.getLength());
	}

	/**
	 * Returns the largest length of available vacant entry 
	 * which is smaller than the upperBound given.
	 * @param upperBound the upper bound.
	 * @return The largest length of available vacant entry 
	 * which is smaller than the upperBound given.
	 */
	private int findMaxVacantEntryLength(int upperBound) {
		for (int i = upperBound; i >= 0; i--) {
			if (_dataLengths[i] > 0) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Returns the shortest length of available vacant entry 
	 * which is bigger than the lowerBound given.
	 * @param lowerBound the lower bound.
	 * @return The shortest length of available vacant entry 
	 * which is bigger than the lowerBound given.
	 */
	private int findMinVacantEntryLength(int lowerBound) {
		for (int i = lowerBound; i < _data.size(); i++) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Returns true if the element is used in the crossword or false
	 * otherwise.
	 * @param entry An element from the partitioned collection.
	 * @return  True if the element is used in the crossword or false
	 * otherwise.
	 */
	public boolean isUsed(CrosswordVacantEntry entry) {
		if (_usedEntries.contains(entry.getPosition())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns an iterator which iterates on vacant entries that their
	 * length is between startLength and endLength.
	 * @param startLength the length of vacant entries the iterator will
	 * start iterating from.
	 * @param endLength the length of vacant entries the iterator will
	 * iterate up until.
	 * @return An iterator which iterates on vacant entries that their
	 * length is between startLength and endLength.
	 */
	public Iterator<CrosswordVacantEntry> getIterator(int startLength,
															int endLength) {
		return new MyContinuousIterator<MyCrosswordShape, CrosswordVacantEntry>
											(this, startLength, endLength);	}

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
	public Iterator<CrosswordVacantEntry> getRawDataIterator(
			int partitionNumber) {
		return _data.get(partitionNumber).iterator();
	}

}
