package oop.ex2.filters;

import java.io.File;
import java.util.Date;

import oop.ex2.filescript.InvalidFilterParametersException;

public class ModifiedOnFilter extends ModificationDateFilter {
	
	private static final String _name = "MODIFIED_ON";

	/**
	 * Constructs a filter and put the filters value (a date) in a field.
	 * @param date the date given as the filters value.
	 * @throws InvalidFilterParametersException Upon invalid parameter such as
	 * bad date format
	 */
	public ModifiedOnFilter(String date)
		throws InvalidFilterParametersException {
		super(date);
	}

	/**
	 * returns true if the files' last modification date is equal to the
	 * date given as the filters value or false otherwise.
	 * @param file a file.
	 * @return true if the files' last modification date is equal to the
	 * date given as the filters value or false otherwise.
	 */
	public boolean isMatch(File file) {
		Date lastModified = getLastModifiedDate(file);
		int compare = lastModified.compareTo(_date);
		if (0 == compare){
			return true;
		}
		return false;
	}
}
