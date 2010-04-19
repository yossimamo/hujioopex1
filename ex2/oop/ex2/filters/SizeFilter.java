//###############  
// FILE : SizeFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents all filters that check
// the size of the file.
//###############

package oop.ex2.filters;

import oop.ex2.filescript.InvalidFilterParametersException;

/**
 * an abstract class which represents all filters that check
 * the size of the file.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class SizeFilter extends Filter {
	
	/// the size given as the filter parameter
	protected long _size;
	
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
			if (_size < 0){
				throw new InvalidFilterParametersException();
			}
		} catch (NumberFormatException e) {
			throw new InvalidFilterParametersException();
		}
	}
}
