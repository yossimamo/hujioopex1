package oop.ex3.dataStructures;

import java.util.ArrayList;
import oop.ex3.exceptions.NullPointerException;
import java.util.Iterator;
import java.util.LinkedList;

public class ChainedHashMap {
	
	private ArrayList<LinkedList<KeyValObject>> _hashMap;
	
	private int _size;
	
	private final static int BUCKET_ARRAY_SIZE = 100;
	
	public ChainedHashMap() {
		_hashMap = new ArrayList<LinkedList<KeyValObject>>(BUCKET_ARRAY_SIZE);
		for (int i=0 ; i<BUCKET_ARRAY_SIZE ; i++){
			_hashMap.add(new LinkedList<KeyValObject>());
		}
		_size = 0;
	}
	
	public int size() {
		return _size;
	}
	
	public Object get(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (-1 == index) {
			return null;
		}
		else {
			return _hashMap.get(position).get(index).getValue();
		}
	}
	
	public void put(HashObject key, Object value) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (-1 == index) {
			_hashMap.get(position).addLast(new KeyValObject(key , value));
			_size++;
		}
		else {
			_hashMap.get(position).get(index).setValue(value);
		}
	}
	
	public boolean remove(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (-1 == index) {
			return false;
		}
		_hashMap.get(position).remove(index);
		_size--;
		return true;
	}
	
	public boolean containsKey(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(BUCKET_ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (-1 == index) {
			return false;
		}
		return true;
	}
	
	private int findObjectIndex
				(LinkedList<KeyValObject> list, HashObject hashObject) {
		if (_size != 0){
			Iterator<KeyValObject> iterator = list.iterator();
			for (int i=0 ; iterator.hasNext() ; i++) {
				if (hashObject.equals(((KeyValObject) iterator.next()).getKey())) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private void isNull(HashObject key) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException();
		}
	}
}
