package oop.ex2.filters;

import java.io.File;
import java.util.Date;

public class ModifiedOnFilter extends ModificationDateFilter {

	public ModifiedOnFilter(Date date) {
		super(date);
		// TODO Auto-generated constructor stub
	}

	public boolean isMatch(File file) {
		// TODO Auto-generated method stub
		return false;
	}

}
