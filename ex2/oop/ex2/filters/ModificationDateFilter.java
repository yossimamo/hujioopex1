//###############  
// FILE : ModificationDateFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: an abstract class which represents all filters that check
// the date of the file.
//###############

package oop.ex2.filters;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oop.ex2.filescript.InvalidFilterParametersException;

/**
 * an abstract class which represents all filters that check
 * the date of the file.
 * @author Uri Greenberg and Yossi Mamo.
 */
public abstract class ModificationDateFilter extends Filter {
	
	//the date given as the filter value. 
	protected Date _date;
	
	// the format in which the date is received.
	private static final String DATE_FORMAT = "yyyy/dd/MM";
	
	/**
	 * constructs a filter and put the filters value (a date) in a field.
	 * @param date the date given as the filter value.
	 * @throws InvalidFilterParametersException upon illegal parameter (not a
	 * legal date format)
	 */
	public ModificationDateFilter(String date) 
					throws InvalidFilterParametersException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			_date = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new InvalidFilterParametersException();
		}
	}
	
	/**
	 * receives a file and returns the date in which it was last modified.
	 * @param file a file.
	 * @return the date in which the file was last modified.
	 */
	protected Date getLastModifiedDate(File file) {
		Date lastModifiedDate= new Date(file.lastModified());
		return lastModifiedDate;
	}
}
