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
	protected List<String> _oldData = new ArrayList<String>();
	private ArrayList<TreeSet<CrosswordVacantEntry>> _data;
	private HashMap<CrosswordPosition, CrosswordVacantEntry> _initialVacantEntries;
	private HashSet<CrosswordPosition> _usedEntries;
	private int _maxCapacity;
	private CrosswordShape _shape;

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
		_maxCapacity = Math.max(_shape.getHeight(), _shape.getWidth());
		assert(0 < _maxCapacity);
		_data = new ArrayList<TreeSet<CrosswordVacantEntry>>(_maxCapacity);
		// TODO iterate on all positions and create vacant entries for each
		// insert into availableVacantEntries and (with position as a key) to
		// initialvacantentries
	}
	
	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
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
	
	public Iterator<CrosswordVacantEntry> getIterator() {
		return new VacantEntryIterator(_maxCapacity);
	}
	
	public Iterator<CrosswordVacantEntry> getIterator(int maxLength) {
		if (maxLength > _maxCapacity) {
			// TODO throw exception
		}
		return new VacantEntryIterator(maxLength);
	}
	
	public class VacantEntryIterator implements Iterator<CrosswordVacantEntry> {
		
		private int _currentArrayPos;
		Iterator<CrosswordVacantEntry> _currentIterator;
		CrosswordVacantEntry _next;
		
		public VacantEntryIterator(int maxLength) {
			_currentArrayPos = maxLength;
			_currentIterator = _data.get(_currentArrayPos).iterator();
			_next = null;
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
			while (0 < _currentArrayPos) {
				_currentArrayPos--;
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
				return _next;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}	
}
