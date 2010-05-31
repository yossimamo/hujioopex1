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
 * This is a basic implementation of CrosswordShape stored as list of strings
 * 
 * @author Dmitry
 */
public class MyCrosswordShape implements CrosswordShape, CrosswordVacantEntries, PartitionedDataCollection<CrosswordVacantEntry> {
	private static final int MINIMUM_ENTRY_LENGTH = 2;
	
	protected List<String> _oldData = new ArrayList<String>();
	private ArrayList<TreeSet<CrosswordVacantEntry>> _data;
	private int _dataLengths[];
	private HashMap<CrosswordPosition, CrosswordVacantEntry> _initialVacantEntries;
	private HashSet<CrosswordPosition> _usedEntries = new HashSet<CrosswordPosition>();
	private int _maxVacantEntryLength = 0;
	private int _minVacantEntryLength = 0;
	private int _totalEntryLengthsSum = 0;
	private int _numOfEntries = 0;
	
	public MyCrosswordShape() {
		
	}
	
	public MyCrosswordShape(MyCrosswordShape other) {
		if (null != other._data) {
			_data = (ArrayList<TreeSet<CrosswordVacantEntry>>)other._data.clone();
		}
		if (null != other._initialVacantEntries) {
			_initialVacantEntries = (HashMap<CrosswordPosition, CrosswordVacantEntry>)other._initialVacantEntries.clone();
		}
		if (null != _usedEntries) {
			_usedEntries = (HashSet<CrosswordPosition>)other._usedEntries.clone();
		}
		_dataLengths = new int[other._dataLengths.length];
		System.arraycopy(other._dataLengths, 0, _dataLengths, 0, other._dataLengths.length);
		_maxVacantEntryLength = other._maxVacantEntryLength;
		_minVacantEntryLength = other._minVacantEntryLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#getHeight()
	 */
	public Integer getHeight() {
		return _oldData.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#getWidth()
	 */
	public Integer getWidth() {
		return _oldData.get(0).length();
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
		switch (_oldData.get(pos.getY()).charAt(pos.getX())) {
		case '_':
			return SlotType.UNUSED_SLOT;
		default:
			return SlotType.FRAME_SLOT;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#Load
	 */
	public void load(String textFileName) throws IOException {
		Scanner sc=null;
		try {
			_oldData = new ArrayList<String>();
			sc = new Scanner(new FileReader(textFileName));
			while (sc.hasNextLine()) {
				_oldData.add(sc.nextLine());
			}
		} finally {
			if (sc!=null) sc.close();
		}
		int maxCapacity = Math.max(getHeight(), getWidth());
		assert(0 < maxCapacity);
		_data = new ArrayList<TreeSet<CrosswordVacantEntry>>(maxCapacity);
		for (int i = 0; i <= maxCapacity; i++) {
			_data.add(new TreeSet<CrosswordVacantEntry>());
		}
		_initialVacantEntries = new HashMap<CrosswordPosition, CrosswordVacantEntry>();
		
		// TODO inefficient
		// Vertical spaces
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				int entryLength = 0;
				CrosswordPosition pos = new MyCrosswordPosition(i, j+entryLength, true);
				while (SlotType.UNUSED_SLOT == getSlotType(pos)) {
					entryLength++;
					if (entryLength >= MINIMUM_ENTRY_LENGTH) {
						CrosswordPosition entryPos = new MyCrosswordPosition(i, j, true); 
						MyCrosswordVacantEntry vacantEntry = new MyCrosswordVacantEntry(entryPos, entryLength);
						_data.get(entryLength).add(vacantEntry);
						// Let the put operation overwrite the previous vacant entry associated with
						// this position, since the current one is longer
						_initialVacantEntries.put(entryPos, vacantEntry);
					}
					pos = new MyCrosswordPosition(i, j+entryLength, true);
				}
			}
		}

		// TODO inefficient
		// Horizontal spaces
		for (int j = 0; j < getHeight(); j++) {
			for (int i = 0; i < getWidth(); i++) {
				int entryLength = 0;
				CrosswordPosition pos = new MyCrosswordPosition(i+entryLength, j, false);
				while (SlotType.UNUSED_SLOT == getSlotType(pos)) {
					entryLength++;
					if (entryLength >= MINIMUM_ENTRY_LENGTH) {
						CrosswordPosition entryPos = new MyCrosswordPosition(i, j, false); 
						MyCrosswordVacantEntry vacantEntry = new MyCrosswordVacantEntry(entryPos, entryLength);
						_data.get(entryLength).add(vacantEntry);
						// Let the put operation overwrite the previous vacant entry associated with
						// this position, since the current one is longer
						_initialVacantEntries.put(entryPos, vacantEntry);					}
					pos = new MyCrosswordPosition(i+entryLength, j, false);
				}
			}
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
			_totalEntryLengthsSum += i*size;
		}
		_minVacantEntryLength = findMinVacantEntryLength(_minVacantEntryLength);
	}
	
	public int getTotalEntryLengthsSum() {
		return _totalEntryLengthsSum;
	}
	
	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
	}
	
	public boolean isFullyOccupied() {
		return (_numOfEntries == _usedEntries.size());
	}
	
	public int getNumberOfEntries() {
		return _numOfEntries;
	}
	
	public int getMaxVacantEntryLength() {
		return _maxVacantEntryLength;
	}
	
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getPosition());
		_dataLengths[entry.getLength()]++;
		_maxVacantEntryLength = Math.max(_maxVacantEntryLength, entry.getLength());
	}

	public void removeEntry(CrosswordEntry entry) {
		assert(_dataLengths[entry.getLength()] > 0);
		_usedEntries.remove(entry.getPosition());
		int newSize = --_dataLengths[entry.getLength()];
		if (0 == newSize) {
			_maxVacantEntryLength = findMaxVacantEntryLength(_maxVacantEntryLength);
		}
	}

	private int findMaxVacantEntryLength(int upperBound) {
		for (int i = upperBound; i >= 0; i--) {
			if (_dataLengths[i] > 0) {
				return i;
			}
		}
		return 0;
	}
	
	private int findMinVacantEntryLength(int lowerBound) {
		for (int i = lowerBound; i < _data.size(); i++) {
			if (_data.get(i).size() > 0) {
				return i;
			}
		}
		return 0;
	}
	
	public boolean isUsed(CrosswordVacantEntry entry) {
		if (_usedEntries.contains(entry.getPosition())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Iterator<CrosswordVacantEntry> getIterator(int startLength, int endLength) {
		return new MyContinuousIterator<MyCrosswordShape, CrosswordVacantEntry>(this, startLength, endLength);	}

	public int getNumOfPartitions() {
		return _data.size();
	}

	public Iterator<CrosswordVacantEntry> getRawDataIterator(int partitionNumber) {
		return _data.get(partitionNumber).iterator();
	}

	@Override
	public int getMinVacantEntryLength() {
		return _minVacantEntryLength;
	}
}
