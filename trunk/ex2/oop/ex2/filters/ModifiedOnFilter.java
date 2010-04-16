package oop.ex2.filters;

import java.io.File;
import java.util.Date;

public class ModifiedOnFilter extends ModificationDateFilter {

	/**
	 * Constructs a filter and put the filters value (a date) in a field.
	 * @param date the date given as the filters value.
	 */
	public ModifiedOnFilter(String date) {
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
