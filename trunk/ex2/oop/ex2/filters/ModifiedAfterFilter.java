//###############  
// FILE : ModifiedAfterFilter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: a filter which checks if the file was last modified after 
// the given date.
//###############

package oop.ex2.filters;

import java.io.File;
import java.util.Date;
import oop.ex2.filescript.InvalidFilterParametersException;

/**
 * a filter which checks if the file was last modified after 
 * the given date.
 * @author Uri Greenberg and Yossi Mamo.
 */
public class ModifiedAfterFilter extends ModificationDateFilter {
	
	/// the name of the filter
	public static final String _name = "MODIFIED_AFTER";

	/**
	 * Constructs a filter and put the filters value (a date) in a field.
	 * @param date the date given as the filters value.
	 * @throws InvalidFilterParametersException Upon invalid parameter such as
	 * bad date format
	 */
	public ModifiedAfterFilter(String date)
		throws InvalidFilterParametersException {
		super(date);
	}

	/**
	 * returns true if the files' last modification date is after the
	 * date given as the filters value or false otherwise.
	 * @param file a file.
	 * @return true if the files' last modification date is after the
	 * date given as the filters value or false otherwise.
	 */
	public boolean isMatch(File file) {
		Date lastModified = getLastModifiedDate(file);
		int compare = lastModified.compareTo(_date);
		if (compare > 0){
			return true;
		}
		return false;
	}
}
