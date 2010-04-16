package oop.ex2.filters;

import oop.ex2.filescript.InvalidFilterParametersException;

public abstract class SizeFilter extends Filter {
	
	// the size given as the filter parameter.
	protected Long _size;
	
	/**
	 * a constructor that saves the size given as a filter
	 * parameter in a field.
	 * @param size the size given as a filter parameter.
	 * @throws InvalidFilterParametersException Upon illegal parameter (i.e.
	 * not a number, negative number etc.)
	 */
	public SizeFilter(String size) throws InvalidFilterParametersException {
		try{
		_size = new Long(size);
		} catch (NumberFormatException e) {
			throw new InvalidFilterParametersException();
		}
	}
}
