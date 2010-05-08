package oop.ex3.dataStructures;

import java.util.Iterator;
import java.util.LinkedList;

public class ChainedHashMap {
	
	private LinkedList<KeyValObject>[] _hashMap;
	
	private int _size;
	
	private final static int BUCKET_ARRAY_SIZE = 100;
	
	@SuppressWarnings("unchecked")
	public ChainedHashMap() {
		_hashMap = new LinkedList[BUCKET_ARRAY_SIZE];
		_size = 0;
	}
	
	public int size() {
		return _size;
	}
	
	public Object get(HashObject key) {
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap[position] , key);
		if (-1 == index) {
			return null;
		}
		else {
			return _hashMap[position].get(index).getValue();
		}
	}
	
	public void put(HashObject key, Object value) {
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap[position] , key);
		if (-1 == index) {
			_hashMap[position].addLast(new KeyValObject(key , value));
			_size++;
		}
		else {
			_hashMap[position].get(index).setValue(value);
		}
	}
	
	public boolean remove(HashObject key) {
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap[position] , key);
		if (-1 == index) {
			return false;
		}
		_hashMap[position].remove(index);
		_size--;
		return true;
	}
	
	public boolean containsKey(HashObject key) {
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap[position] , key);
		if (-1 == index) {
			return false;
		}
		return true;
	}
	
	private int findObjectIndex
				(LinkedList<KeyValObject> list, HashObject hashObject) {
		Iterator<KeyValObject> iterator = list.iterator();
		for (int i=0 ; iterator.hasNext() ; i++) {
			if (hashObject.equals(((KeyValObject) iterator.next()).getKey())) {
				return i;
			}
		}
		return -1;
	}
}
