package oop.ex2.filters;

import java.text.ParseException;


public abstract class SizeFilter extends BasicFilter {
	
	// the size given as the filter parameter.
	protected Long _size;
	
	/**
	 * a constructor that saves the size given as a filter
	 * parameter in a field.
	 * @param size the size given as a filter parameter.
	 */
	public SizeFilter(String size) {
		try{
		_size = new Long(size);
		} catch (NumberFormatException e) {
			throw InvalidFilterParameters;
		}
	}
}
