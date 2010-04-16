package oop.ex2.filters;

import oop.ex2.filescript.InvalidFilterParametersException;

public abstract class BooleanFilter extends Filter {
	
	// the boolean condition which the file needs to fit to.
	protected boolean _condition;
	
	//the string which will mean true.
	private static final String YES = "Yes";
	
	//the string which will mean false.
	private static final String NO = "No";
	
	/**
	 * a constructor which saves the parameter received as a boolean field.
	 * @param condition the condition received.
	 * @throws InvalidFilterParametersException Upon illegal parameter (not
	 * "YES" or "NO")
	 */
	public BooleanFilter(String condition) throws InvalidFilterParametersException {
		if (condition.equals(YES)) {
			_condition = true;
		}
		else{
			if (condition.equals(NO)) {
				_condition = false;
			}
			else {
				throw new InvalidFilterParametersException();
			}
		}
	}
}
