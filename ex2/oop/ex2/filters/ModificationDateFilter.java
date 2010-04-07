package oop.ex2.filters;

import java.util.Date;

public abstract class ModificationDateFilter extends BasicFilter {
	
	private Date _date;
	
	public ModificationDateFilter(Date date) {
		_date = date;
	}


}
