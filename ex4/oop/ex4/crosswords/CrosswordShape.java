//###############  
// FILE : CrosswordShape.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex4 2010  
// DESCRIPTION:  A crossword shape represents rectangular form of crossword.
// Each cell in this
// from can be either FRAME_SLOT - the crossword background, 
// or UNUSED_SLOT - slot not belonging to
// background or any crossword entry 
//###############

package oop.ex4.crosswords;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * A crossword shape represents rectangular form of crossword. Each cell in this
 * from can be either FRAME_SLOT - the crossword background, 
 * or UNUSED_SLOT - slot not belonging to
 * background or any crossword entry 
 * 
 * @author Dima
 * 
 */
public interface CrosswordShape {

	/**
	 * Loads and initializes the shape from a text file as sequence lines, each
	 * of which is a sequence of #'s and _'s. The file format described in
	 * exercise PDF
	 * 
	 * @param textFileName
	 *            - the name of the text file
	 * @throws IOException
	 *             - if failed to read a file
	 */
	void load(String textFileName) throws IOException;


	/**
	 * Retrieves the slot type of specific entry
	 * 
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @return The cell type; FRAME_SLOT returned for any slot outside the bounds
	 * 
	 */
	SlotType getSlotType(CrosswordPosition pos) throws InvalidParameterException;

	enum SlotType {
		FRAME_SLOT, UNUSED_SLOT
	};

	/**
	 * Returns number of columns(x) in crossword
	 * 
	 * @return Number of columns
	 */
	Integer getWidth();

	/**
	 * Returns number of rows(y) in crossword
	 * 
	 * @return Number of rows
	 */
	Integer getHeight();
}
