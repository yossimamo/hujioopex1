package oop.ex3.dataStructures;

public class HashString implements HashObject {

	private String _string;
	
	public HashString(String string) {
		_string = string;
	}

	public boolean equals(HashObject other) {
		if (other instanceof HashString) {
			HashString otherHashString = (HashString)other;
			if (otherHashString._string.equals(_string)) {
				return true;
			}
		}
		return false;
	}

	public int hashCode(int maxNum) {
		return _string.hashCode() % maxNum;
	}
}
