package oop.ex4.crosswords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class MyCrosswordVacantEntries implements CrosswordVacantEntries {
	
	private ArrayList<TreeSet<CrosswordVacantEntry>> _availableVacantEntries;
	private HashMap<CrosswordPosition, CrosswordVacantEntry> _initialVacantEntries;
	private int _maxCapacity;
	private CrosswordShape _shape;
	
	public MyCrosswordVacantEntries(CrosswordShape shape) {
		_shape = shape;
		_maxCapacity = Math.max(_shape.getHeight(), _shape.getWidth());
		assert(0 < _maxCapacity);
		_availableVacantEntries = new ArrayList<TreeSet<CrosswordVacantEntry>>(_maxCapacity);
		// TODO iterate on all positions and create vacant entries for each
		// insert into availableVacantEntries and (with position as a key) to
		// initialvacantentries
	}

	public int getMaxCapacity(CrosswordPosition pos) {
		return (_initialVacantEntries.get(pos)).getMaxCapacity();
	}
	
	public VacantEntryIterator getIterator() {
		return new VacantEntryIterator(_maxCapacity);
	}
	
	public VacantEntryIterator getIterator(int maxLength) {
		if (maxLength > _maxCapacity) {
			// TODO throw exception
		}
		return new VacantEntryIterator(maxLength);
	}
	
	public class VacantEntryIterator implements Iterator<CrosswordVacantEntry> {
		
		private int _currentArrayPos;
		Iterator<CrosswordVacantEntry> _currentIterator;
		
		public VacantEntryIterator(int maxLength) {
			_currentArrayPos = maxLength;
			_currentIterator = _availableVacantEntries.get(_currentArrayPos).iterator();
		}

		public boolean hasNext() {
			if (_currentIterator.hasNext()) {
				return true;
			} else {
				while (0 < _currentArrayPos) {
					_currentArrayPos--;
					_currentIterator = _availableVacantEntries.get(_currentArrayPos).iterator();
					if (_currentIterator.hasNext()) {
						return true;
					}
				}
			}
			return false;
		}

		public CrosswordVacantEntry next() {
			if (this.hasNext()) {
				return _currentIterator.next();
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
}
