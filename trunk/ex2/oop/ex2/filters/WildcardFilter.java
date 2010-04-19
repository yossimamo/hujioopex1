//###############  
// FILE : WildcardFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents the filters that uses a 
// wildcard expression.
//###############

package oop.ex2.filters;

/**
 * an abstract class which represents the filters that uses a 
 * wildcard expression.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class WildcardFilter extends Filter {

	// there are four types of wildcard expressions:
	// EQUALS - an expression with no '*' (example: "abc")
	// BEGINS_WITH - an expression that ends with '*' (example: "abc*")
	// ENDS_WITH - an expression that starts with '*' (example: "*abc")
	// CONTAINS - an expression surrounded by '*' (example: "*abc*")
	enum WildcardType {
		EQUALS,
		BEGINS_WITH,
		ENDS_WITH,
		CONTAINS
	};
	
	/// the string to be searched for in the file.
	private String _wildcardString;
	
	/// the type of wildcard expression the string is.
	private WildcardType _type;

	/**
	 * constructs a new filter from the wildcard expression. it determines
	 * its type and remove the "*" from the String.
	 * @param wildcardString the string to be searched for in the file.
	 */
	public WildcardFilter(String wildcardString) {
		_wildcardString = wildcardString;
		if (_wildcardString.startsWith("*")) {
			if (_wildcardString.length() == 1){
				_wildcardString = _wildcardString.substring(1);
				_type = WildcardType.CONTAINS;
			}
			else {
				if (_wildcardString.endsWith("*")) {
					_wildcardString = 
						_wildcardString.
						substring(1,_wildcardString.length() - 1);
					_type = WildcardType.CONTAINS;
				}
				else {
					_wildcardString = _wildcardString.substring(1);
					_type = WildcardType.ENDS_WITH;
				}
			}
		}
		else {
			if (_wildcardString.endsWith("*")) {
				_wildcardString = 
					_wildcardString.substring(0,_wildcardString.length() - 1);
				_type = WildcardType.BEGINS_WITH;
			}
			else{
				_type = WildcardType.EQUALS;
			}
		}
	}
	
	/**
	 * receives a string and returns true if the wild card string fits
	 * the string and false otherwise.
	 * @param str a string.
	 * @return true if the wild card string fits
	 * the string and false otherwise.
	 */
	protected boolean contains(String str) {
		switch (_type) {
		case EQUALS:
			if (str.equals(_wildcardString)) {
				return true;
			}
			break;
		case BEGINS_WITH:
			if (str.startsWith(_wildcardString)) {
				return true;
			}
			break;
		case ENDS_WITH:
			if (str.endsWith(_wildcardString)) {
				return true;
			}
			break;
		case CONTAINS:
			if (str.contains(_wildcardString)) {
				return true;
			}
			break;
		}
		return false;
	}
}