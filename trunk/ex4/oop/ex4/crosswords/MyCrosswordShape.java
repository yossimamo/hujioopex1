package oop.ex4.crosswords;

import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * This is a basic implementation of CrosswordShape stored as list of strings
 * 
 * @author Dmitry
 */
public class MyCrosswordShape implements CrosswordShape, CrosswordVacantEntries {
	private static final int MINIMUM_ENTRY_LENGTH = 2;
	
	protected List<String> _oldData = new ArrayList<String>();
	private ArrayList<TreeSet<CrosswordVacantEntry>> _data;
	private HashMap<CrosswordPosition, CrosswordVacantEntry> _initialVacantEntries;
	private HashSet<CrosswordPosition> _usedEntries = new HashSet<CrosswordPosition>();
	private int _maxCapacity;
	
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
		_maxCapacity = other._maxCapacity;
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
		_maxCapacity = Math.max(getHeight(), getWidth());
		assert(0 < _maxCapacity);
		_data = new ArrayList<TreeSet<CrosswordVacantEntry>>(_maxCapacity);
		for (int i = 0; i <= _maxCapacity; i++) {
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
						_initialVacantEntries.put(entryPos, vacantEntry);
					}
					pos = new MyCrosswordPosition(i+entryLength, j, false);
				}
			}
		}
	}
	
	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
	}
	
	public boolean isFullyOccupied() {
		return (_data.size() == _usedEntries.size());
	}
	
	public int getNumberOfEntries() {
		return _initialVacantEntries.size();
	}
	
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getPosition());
	}

	public void removeEntry(CrosswordEntry entry) {
		_usedEntries.remove(entry.getPosition());
	}
	
	private boolean isUsed(CrosswordVacantEntry entry) {
		if (_usedEntries.contains(entry.getPosition())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Iterator<CrosswordVacantEntry> getIterator(boolean isAscending) {
		return new VacantEntryIterator(_maxCapacity, isAscending);
	}
	
	public Iterator<CrosswordVacantEntry> getIterator(int maxLength, boolean isAscending) {
		if (maxLength > _maxCapacity) {
			// TODO throw exception
		}
		return new VacantEntryIterator(maxLength, isAscending);
	}
	
	public class VacantEntryIterator implements Iterator<CrosswordVacantEntry> {
		
		private int _currentArrayPos;
		Iterator<CrosswordVacantEntry> _currentIterator;
		CrosswordVacantEntry _next;
		int _increment;
		
		public VacantEntryIterator(int maxLength, boolean isAscending) {
			_currentArrayPos = maxLength;
			_currentIterator = _data.get(_currentArrayPos).iterator();
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
				_currentIterator = _data.get(_currentArrayPos).iterator();
				while (_currentIterator.hasNext()) {
					_next = _currentIterator.next();
					if (!isUsed(_next)) {
						return true;
					}
				}
			}
			return false;
		}

		public CrosswordVacantEntry next() {
			if (this.hasNext()) {
				CrosswordVacantEntry ret = _next;
				_next = null;
				return ret;
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
