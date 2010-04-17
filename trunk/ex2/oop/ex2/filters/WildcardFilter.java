package oop.ex2.filters;

public abstract class WildcardFilter extends Filter {

	enum WildcardType {
		EQUALS,
		BEGINS_WITH,
		ENDS_WITH,
		CONTAINS
	};
	
	//the string to be searched for in the file.
	private String _wildcardString;
	private WildcardType _type;

	
	
	
	/**
	 * saves the given string in a field.
	 * @param wildcardString the string to be searched for in the file.
	 */
	public WildcardFilter(String wildcardString) {
		_wildcardString = wildcardString;
		if (_wildcardString.startsWith("*")) {
			if (_wildcardString.endsWith("*")) {
				_wildcardString = 
					_wildcardString.substring(1,_wildcardString.length() - 1);
				_type = WildcardType.CONTAINS;
			}
			else {
				_wildcardString = _wildcardString.substring(1);
				_type = WildcardType.ENDS_WITH;
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