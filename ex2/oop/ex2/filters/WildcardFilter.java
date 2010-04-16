package oop.ex2.filters;

public abstract class WildcardFilter extends Filter {

	//the string to be searched for in the file.
	protected String _wildcardString;
	
	/**
	 * saves the given string in a field.
	 * @param wildcardString the string to be searched for in the file.
	 */
	public WildcardFilter(String wildcardString) {
		_wildcardString = wildcardString;
	}
	
	/**
	 * receives a string and returns true if the wild card string fits
	 * the string and false otherwise.
	 * @param str a string.
	 * @return true if the wild card string fits
	 * the string and false otherwise.
	 */
	protected boolean contains(String str){
		if (str.equals(_wildcardString)){
			return true;
		}
		if (_wildcardString.startsWith("*")){
			_wildcardString = _wildcardString.substring(1);
			if (_wildcardString.endsWith("*")){
				_wildcardString = 
					_wildcardString.substring(0,_wildcardString.length() - 1);
				if (str.contains(_wildcardString)){
					return true;
				}
			}
			else {
				if (str.endsWith(_wildcardString)){
					return true;
				}
			}
		}
		else {
			if (_wildcardString.endsWith("*")){
				_wildcardString = 
					_wildcardString.substring(0,_wildcardString.length() - 1);
				if (str.startsWith(_wildcardString)){
					return true;
				}
			}
		}
		return false;
	}
}