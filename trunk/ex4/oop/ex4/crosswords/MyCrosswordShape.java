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
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This is a basic implementation of CrosswordShape stored as list of strings
 * 
 * @author Dmitry
 */
public class MyCrosswordShape implements CrosswordShape, CrosswordVacantEntries {
	private static final int MINIMUM_ENTRY_LENGTH = 2;
	private static final boolean HORIZONTAL = false; 
	private static final boolean VERTICAL = true;
	private int _height;
	private int _width;
	private ArrayList<TreeSet<CrosswordVacantEntry>> _data;
	private HashMap<CrosswordPosition,PositionsInfo> _position;
	private HashSet<CrosswordPosition> _usedEntries = new HashSet<CrosswordPosition>();
	private int _maxCapacity;
	
	public MyCrosswordShape() {
		
	}
	
	public MyCrosswordShape(MyCrosswordShape other) {
		if (null != other._data) {
			_data = (ArrayList<TreeSet<CrosswordVacantEntry>>)other._data.clone();
		}
		if (null != other._position) {
			_position = (HashMap<CrosswordPosition,PositionsInfo>)other._position.clone();
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
		return _height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#getWidth()
	 */
	public Integer getWidth() {
		return _width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#isFilled(java.lang.Integer, java.lang.Integer)
	 */
	//TODO make hashcode for possition object or this function and maybe others may not work properly
	public SlotType getSlotType(CrosswordPosition pos)
			throws InvalidParameterException {
		if (pos == null) {
			throw new InvalidParameterException();
		}
		if (!_position.containsKey(pos)) {
			return SlotType.FRAME_SLOT;
		}
		else {
			return _position.get(pos)._slotType;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CrosswordGrid#Load
	 */
	public void load(String textFileName) throws IOException {
		Scanner sc=null;
		ArrayList<String> shape = new ArrayList<String>();
		try {
			sc = new Scanner(new FileReader(textFileName));
			while (sc.hasNextLine()) {
				shape.add(sc.nextLine());
			}
		} finally {
			if (sc!=null) sc.close();
		}
		_data = new ArrayList<TreeSet<CrosswordVacantEntry>>();
		_position = new HashMap<CrosswordPosition,PositionsInfo>();
		_maxCapacity = 0;
		_height = shape.size();
		_width = shape.get(0).length();
		_data.add(new TreeSet<CrosswordVacantEntry>());
		for (int y=0 ; y<shape.size() ; y++) {
			initLineInDatabase(shape.get(y), HORIZONTAL, y);
		}
		for (int x=0; x < shape.get(0).length() ; x++) {
			StringBuilder currentColumn = new StringBuilder();
			for (int i=0; i<shape.size() ; i++) {
				currentColumn.append(shape.get(i).charAt(x));
			}
			initLineInDatabase(currentColumn.toString(), VERTICAL, x);
		}
	}
	
	private void initLineInDatabase(String currentLine, boolean isVertical, int otherCoordinate) {
		for (int i=0 ; i < currentLine.length() ; i++) {
			if (currentLine.charAt(i) == '_') {
				int vacantEntryLength = getVacantEntryLength(currentLine, i);
				if (_maxCapacity < vacantEntryLength) {
					for (int j=_maxCapacity; j<vacantEntryLength ; j++ ) {
						_data.add(new TreeSet<CrosswordVacantEntry>());
					}
					_maxCapacity = vacantEntryLength;
				}
				if (isVertical) {
					for (int j=0 ; j < vacantEntryLength - 1 ; j++) {
						MyCrosswordPosition position = new MyCrosswordPosition(otherCoordinate, i+j, VERTICAL);
						MyCrosswordVacantEntry entry = new MyCrosswordVacantEntry(position, vacantEntryLength - j);
						_position.put(position, new PositionsInfo(entry, SlotType.UNUSED_SLOT));
						_data.get(entry.getMaxCapacity()).add(entry);
					}
					i = i + vacantEntryLength - 1;
					MyCrosswordPosition position = new MyCrosswordPosition(otherCoordinate, i, VERTICAL);
					_position.put(position, new PositionsInfo(null, SlotType.UNUSED_SLOT));
				}
				else {
					for (int j=0 ; j < vacantEntryLength - 1 ; j++) {
						MyCrosswordPosition position = new MyCrosswordPosition(i+j, otherCoordinate, HORIZONTAL);
						MyCrosswordVacantEntry entry = new MyCrosswordVacantEntry(position, vacantEntryLength - j);
						_position.put(position, new PositionsInfo(entry, SlotType.UNUSED_SLOT));
						_data.get(entry.getMaxCapacity()).add(entry);
					}
					i = i + vacantEntryLength - 1;
					MyCrosswordPosition position = new MyCrosswordPosition(i, otherCoordinate, HORIZONTAL);
					_position.put(position, new PositionsInfo(null, SlotType.UNUSED_SLOT));
				}
			}
			else {
				if (isVertical) {
					MyCrosswordPosition position = new MyCrosswordPosition(otherCoordinate, i, VERTICAL);
					_position.put(position, new PositionsInfo(null, SlotType.FRAME_SLOT));
				}
				else {
					MyCrosswordPosition position = new MyCrosswordPosition(i, otherCoordinate, HORIZONTAL);
					_position.put(position, new PositionsInfo(null, SlotType.FRAME_SLOT));
				}
			}
		}
	}

	private int getVacantEntryLength(String currentLine, int indexInLine) {
		int vacantEntryLength = 0;
		while ((indexInLine < currentLine.length()) && (currentLine.charAt(indexInLine) == '_')) {
			indexInLine++;
			vacantEntryLength++;
		} 
		return vacantEntryLength;
	}

	public int getMaxCapacity(CrosswordPosition pos) {
		return (_position.get(pos))._vacantEntry.getMaxCapacity();
	}
	
	public boolean isFullyOccupied() {
		return (_data.size() == _usedEntries.size());
	}
	
	public int getNumberOfEntries() {
		int numOfEntries = 0;
		for (int i=0 ; i<_data.size() ; i++) {
			numOfEntries += _data.get(i).size();
		}
		return numOfEntries;
	}
	//TODO check
	public void addEntry(CrosswordEntry entry) {
		_usedEntries.add(entry.getPosition());
	}
	
	//TODO check
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
	
	private class PositionsInfo {
		
		private CrosswordVacantEntry _vacantEntry;
		
		private SlotType _slotType;
		
		public PositionsInfo(CrosswordVacantEntry vacantEntry, SlotType slotType) {
			_vacantEntry = vacantEntry;
			_slotType = slotType;
		}
	}
	
}
