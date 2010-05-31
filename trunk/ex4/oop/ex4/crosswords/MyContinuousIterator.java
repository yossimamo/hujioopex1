package oop.ex4.crosswords;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyContinuousIterator<D extends PartitionedDataCollection<E>, E> extends ContinuousIterator<D, E> {
	
	private D _data;
	private int _currentArrayPos;
	private int _lastArrayPos;
	private Iterator<E> _currentIterator;
	private E _next;
	private int _increment;
	private boolean _isContinuous;
	
	public MyContinuousIterator(D data, int startLength, int endLength) {
		_data = data;
		if ((startLength < 0) || (endLength >= _data.getNumOfPartitions())) {
			throw new IndexOutOfBoundsException();
		}
		if (startLength != endLength) {
			_isContinuous = true;
			_increment = startLength < endLength ? 1 : -1;
		} else {
			_isContinuous = false;
		}
		_currentArrayPos = startLength;
		_lastArrayPos = endLength;
		_currentIterator = _data.getRawDataIterator(_currentArrayPos);
	}

	public boolean hasNext() {
		if (null != _next) {
			return true;
		}
		while (_currentIterator.hasNext()) {
			_next = _currentIterator.next();
			if (!_data.isUsed(_next)) {
				return true;
			}
		}
		if (_isContinuous) {
			// Modify array position length and continue
			while (arrayPosInBounds()) {
				_currentArrayPos += _increment;
				_currentIterator = _data.getRawDataIterator(_currentArrayPos);
				while (_currentIterator.hasNext()) {
					_next = _currentIterator.next();
					if (!_data.isUsed(_next)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public E next() {
		if (this.hasNext()) {
			E ret = _next;
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
		if ((_increment > 0) && (_currentArrayPos + _increment >= _lastArrayPos)) {
			return false;
		}
		if ((_increment < 0) && (_currentArrayPos + _increment < _lastArrayPos)) {
			return false;
		}
		return true;
		
	}

}
