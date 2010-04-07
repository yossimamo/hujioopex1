package oop.ex2.filters;

import java.io.File;

public class SubdirWildcardFilter extends WildcardFilter {

	public SubdirWildcardFilter(String wildcardString) {
		super(wildcardString);
		// TODO Auto-generated constructor stub
	}

	public boolean isMatch(File file) {
		// TODO Auto-generated method stub
		return false;
	}

}
