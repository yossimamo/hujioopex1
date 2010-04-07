package oop.ex2.filters;

import java.io.File;

public class IsWritableFilter extends BooleanFilter {

	public IsWritableFilter(boolean condition) {
		super(condition);
		// TODO Auto-generated constructor stub
	}

	public boolean isMatch(File file) {
		// TODO Auto-generated method stub
		return false;
	}

}
