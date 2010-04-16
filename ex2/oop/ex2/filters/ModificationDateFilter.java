package oop.ex2.filters;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ModificationDateFilter extends BasicFilter {
	
	//the date given as the filter value. 
	protected Date _date;
	
	// the format in which the date is received.
	private static final String DATE_FORMAT = "YYYY/DD/MM";
	
	/**
	 * constructs a filter and put the filters value (a date) in a field.
	 * @param date the date given as the filter value.
	 */
	public ModificationDateFilter(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			_date = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw InvalidFilterParameters;
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
