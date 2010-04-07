package oop.ex2.filters;

import java.io.File;

public class IsReadableFilter extends BooleanFilter {

	public IsReadableFilter(boolean condition) {
		super(condition);
		// TODO Auto-generated constructor stub
	}

	public boolean isMatch(File file) {
		// TODO Auto-generated method stub
		return false;
	}

}
