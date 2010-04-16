package oop.ex2.filters;

import java.io.File;

public class SizeBiggerFilter extends SizeFilter {

	/**
	 * a constructor that saves the size given as a filter
	 * parameter in a field.
	 * @param size the size given as a filter parameter.
	 */
	public SizeBiggerFilter(String size) {
		super(size);
	}

	/**
	 * returns true if the files size is bigger than the size given as the
	 * filters parameter or false otherwise.
	 * @param file the file.
	 * @return true if the files size is bigger than the size given as the
	 * filters parameter or false otherwise.
	 */
	public boolean isMatch(File file) {
		if (file.length() > _size){
			return true;
		}
		return false;
	}

}
