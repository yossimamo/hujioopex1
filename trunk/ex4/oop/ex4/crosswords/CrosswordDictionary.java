package oop.ex4.crosswords;

import java.io.IOException;
import java.util.Set;

/**
 * Represents dictionary which keeps all words together with their definitions
 * 
 * @author Dima
 * 
 */
public interface CrosswordDictionary {
	/**
	 * Retrieves term definition
	 * 
	 * @param term
	 *            The given term
	 * @return The definition string
	 */
	String getTermDefinition(String term);

	/**
	 * Retrieves set of terms in dictionary
	 * 
	 * @return
	 */
	Set<String> getTerms();

	/**
	 * Load dictionary from disk file. The dictionary format defined in Ex4 pdf.
	 * 
	 * @param dictFileName
	 *            The name of dictionary file
	 * @throws IOException
	 *             On any I/O error
	 */
	void load(String dictFileName) throws IOException;
}
