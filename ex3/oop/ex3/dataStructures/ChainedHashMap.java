//###############  
// FILE : ChainedHashMap.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: this class is an implementation of a chained hashmap.
//###############

package oop.ex3.dataStructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * this class is an implementation of a chained hashmap.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class ChainedHashMap {
	
	// the hashMap itself. it is an array list which holds in each cell
	// a linked list of KeyValObjects.
	private ArrayList<LinkedList<KeyValObject>> _hashMap;
	
	//the size of the hashMap (how many keys and values have been inserted).
	private int _size;
	
	//the size of the array.
	private final static int ARRAY_SIZE = 100;
	
	//the code for index not found in a linked list.
	private final static int INDEX_NOT_FOUND = -1;
	
	
	
	/**
	 * constructs a new hashMap, initializing all linked lists in the array.
	 * and sets the size to 0.
	 */
	public ChainedHashMap() {
		_hashMap = new ArrayList<LinkedList<KeyValObject>>(ARRAY_SIZE);
		for (int i=0 ; i<ARRAY_SIZE ; i++){
			_hashMap.add(new LinkedList<KeyValObject>());
		}
		_size = 0;
	}
	
	/**
	 * returns the size of the hashMap (meaning the number of keys and values
	 * that has been inserted).
	 * @return the size of the hashMap.
	 */
	public int getSize() {
		return _size;
	}
	
	/**
	 * returns the value matching to the given key. or null if the key
	 * Isn't found.
	 * @param key a key.
	 * @return the value matching to the given key. or null if the key
	 * Isn't found.
	 * @throws NullPointerException in case the given key was null.
	 */
	public Object get(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (INDEX_NOT_FOUND == index) {
			return null;
		}
		else {
			return _hashMap.get(position).get(index).getValue();
		}
	}
	
	/**
	 * receives a key and a value and inserts them into the hashmap.
	 * in case the key is already in the hashmap, the value is simply being
	 * replaced.
	 * @param key the key
	 * @param value the value
	 * @throws NullPointerException in case the given key is null.
	 */
	public void put(HashObject key, Object value) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (INDEX_NOT_FOUND == index) {
			_hashMap.get(position).addLast(new KeyValObject(key , value));
			_size++;
		}
		else {
			_hashMap.get(position).get(index).setValue(value);
		}
	}
	
	/**
	 * removes the given key (and its value) from the hashmap.
	 * @param key the key.
	 * @return true if the removal was successful or false otherwise (meaning 
	 * the key wasn't found in the hashmap).
	 * @throws NullPointerException in case the given key was null.
	 */
	public boolean remove(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (INDEX_NOT_FOUND == index) {
			return false;
		}
		_hashMap.get(position).remove(index);
		_size--;
		return true;
	}
	
	/**
	 * returns true if the hashmap contains the given key and false otherwise.
	 * @param key the key.
	 * @return true if the hashmap contains the given key and false otherwise.
	 * @throws NullPointerException in case the given key is null.
	 */
	public boolean containsKey(HashObject key) throws NullPointerException {
		isNull(key);
		int position = key.hashCode(ARRAY_SIZE);
		int index = findObjectIndex(_hashMap.get(position) , key);
		if (INDEX_NOT_FOUND == index) {
			return false;
		}
		return true;
	}
	
	/**
	 * receives a linked list and a hashObject. and returns the index of the 
	 * HashObject in the list or -1 if the hashObject is not found in the
	 * list. 
	 * @param list the list to search the hashObject in.
	 * @param hashObject the hashObject to search for in the list.
	 * @return the index of the HashObject in the list or -1 if the hashObject
	 * is not found in the list. 
	 */
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
		return INDEX_NOT_FOUND;
	}
	
	/**
	 * throws a NullPointerException in case the given key is null.
	 * @param key a key.
	 * @throws NullPointerException in case the given key is null.
	 */
	private void isNull(HashObject key) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException();
		}
	}
}
