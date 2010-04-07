package oop.ex2.filters;

public abstract class WildcardFilter extends BasicFilter {

	private String _wildcardString;
	
	public WildcardFilter(String wildcardString) {
		_wildcardString = wildcardString;
	}

}
