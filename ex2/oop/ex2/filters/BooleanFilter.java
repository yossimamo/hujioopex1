package oop.ex2.filters;

public abstract class BooleanFilter extends BasicFilter {
	
	private boolean _condition;
	
	public BooleanFilter(boolean condition) {
		_condition = condition;
	}
}
