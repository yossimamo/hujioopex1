//###############  
// FILE : CommandFile.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex2 2010  
// DESCRIPTION: Represents a command file which may contain multiple sections
// of filters and actions.
//###############

package oop.ex2.filescript;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

import oop.ex2.actions.*;
import oop.ex2.filters.*;

/**
 * Represents a command file which may contain multiple sections of
 * filters and actions. This object is responsible for parsing commands from
 * a given file and executing them.
 * @author Uri Greenberg and Yossi Mamo
 */
public class CommandFile {
	
	/// Header of the filters section
	private static final String FILTERS = "FILTERS";
	/// Header of the action section
	private static final String ACTION = "ACTION";
	private static final char LEFT_PARENTHESIS = '(';
	private static final char BEGIN_COMMENT = '#';
	/// Regex which validates a basic filter string: FILTER_NAME:FILTER VALUE	
	private static final String BASIC_FILTER_REGEX = "^[A-Za-z0-9\\\\/\\*\\.\\-_]+:[A-Za-z0-9\\\\/\\*\\.\\-_]+$";
	private static final String BASIC_FILTER_DELIMITERS = ":";
	/// Regex which validates a compound filter string
	private static final String COMPOUND_FILTER_REGEX = "(NOT) (.+)$|(.+?) (AND|OR) (.+)$";
	/// Indices of groups in the compound filter regex
	private static final int NOT_OPERATOR_GROUP_INDEX = 1;
	private static final int NOT_OPERAND_GROUP_INDEX = 2;
	private static final int LEFT_OPERAND_GROUP_INDEX = 3;
	private static final int AND_OR_OPERATOR_GROUP_INDEX = 4;
	private static final int RIGHT_OPERAND_GROUP_INDEX = 5;
	private static final String AND_OPERATOR = "AND";
	private static final String OR_OPERATOR = "OR";
	/// Regex which validates an action string: ACTION_NAME:ACTION_PARAM
	private static final String ACTION_REGEX = "^[^\\s():]+:(.+)$";
	private static final String ACTION_DELIMITERS = ":";
	/// Regex which validates an expression surrounded by parentheses
	private static final String PARENTHESES_REGEX = "^\\((.+)\\)$";
	private static final int PARENTHESES_GROUP_INDEX = 1;
	
	/// Sections of the command file
	private ArrayList<Section> _sections;
	
	/**
	 * Initializes a new, empty CommandFile object. This method cannot be
	 * invoked from the outside. CommandFile objects should be properly
	 * constructed using the createFromFile static method.
	 */
	private CommandFile() {
		_sections = new ArrayList<Section>();
	}
	
	/**
	 * Static method which creates and returns a fully functional CommandFile
	 * object based upon the commands in the given command file. This method
	 * will only succeed if the given input file is properly formatted,
	 * otherwise, an exception will be thrown.
	 * This method is the only way to construct a proper CommandFile object.
	 * @param file The command file from which the new object should be built
	 * @return A new CommandFile object which represents the given file.
	 * @throws InvalidActionException If one of the actions in the given file
	 * is unknown.
	 * @throws InvalidFilterExpressionException If one of the filter expressions
	 * in the given file is not legally formatted
	 * @throws UnsupportedFilterException If one of the given filters is unknown
	 * @throws InvalidSectionException If one of the sections is not properly
	 * ordered
	 * @throws InvalidFilterParametersException If a given filter parameter is
	 * unknown or unsupported
	 * @throws FileNotFoundException If the given file does not exist
	 * @throws InvalidActionParametersException If a given action parameter is
	 * unknown or unsupported
	 */
	public static CommandFile createFromFile(File file)
		throws InvalidActionException,
			   InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidSectionException, InvalidFilterParametersException,
			   FileNotFoundException, InvalidActionParametersException {
		Scanner sc = new Scanner(file);
		CommandFile cmdFile = new CommandFile();
		
		// Parse each section individually, repeat until end of file (or
		// encountering an error of some sort
		while (sc.hasNext()) {
			Section section = parseSection(sc);
			cmdFile._sections.add(section);
		}		
		return cmdFile;
	}
	
	/**
	 * Parses a single section in a given input file.
	 * @param sc A scanner positioned at the beginning of the input file, or
	 * right after a previous section. This scanner is changed during the
	 * execution of this method.
	 * @return A Section object which represents the parsed section.
	 * @throws InvalidActionException If one of the actions in the section
	 * is unknown.
	 * @throws InvalidFilterExpressionException If one of the filter expressions
	 * in the section is not legally formatted
	 * @throws UnsupportedFilterException If one of the given filters is unknown
	 * @throws InvalidSectionException If the section is not properly ordered
	 * @throws InvalidFilterParametersException If a given filter parameter is
	 * unknown or unsupported
	 * @throws InvalidActionParametersException If a given action parameter is
	 * unknown or unsupported
	 */
	private static Section parseSection(Scanner sc)
		throws InvalidActionException, InvalidFilterExpressionException,
			   UnsupportedFilterException, InvalidSectionException,
			   InvalidFilterParametersException, InvalidActionParametersException {
		Section section = new Section();
		String line = getNextLine(sc);
		// Begin parsing FILTERS
		if (!line.equals(FILTERS)) {
			throw new InvalidSectionException();
		}
		line = getNextLine(sc);
		while (!line.equals(ACTION)) {
			// FILTERS at this point violate the section structure
			if (line.equals(FILTERS)) {
				throw new InvalidSectionException();
			}
			// Two possible types of lines:
			// 1. Comment
			// 2. Filter 
			switch (line.charAt(0)) {
				case BEGIN_COMMENT: {
					section.addComment(line);
					break;			
				}
				default: {
					section.addFilter(parseFilter(line));
					break;
				}
			}
			line = getNextLine(sc);
		}
		// Begin parsing ACTION
		line = getNextLine(sc);
		// FILTERS at this point violate the section structure
		if (line.equals(FILTERS)) {
			throw new InvalidSectionException();
		}
		// Comments are not allowed here
		if (BEGIN_COMMENT == line.charAt(0)) {
			throw new InvalidSectionException();
		}
		// Only one possible option - the action itself
		section.setAction(parseAction(line));		
			
		return section;
	}
	
	/**
	 * Parses a string which represents a filter and returns a Filter object
	 * which represents it.
	 * @param line A string (single-lined) which represents a filter. This
	 * string is assumed not to be empty (this assumption must be validated
	 * by the caller before invoking this method)
	 * @return A Filter object which represents the given line
	 * @throws InvalidFilterExpressionException If the given expression is
	 * improperly formatted
	 * @throws UnsupportedFilterException If the given expression contains an
	 * unsupported filter
	 * @throws InvalidFilterParametersException If the given expression contains
	 * an unsupported filter parameter
	 */
	private static Filter parseFilter(String line)
		throws InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidFilterParametersException {
		// Two possible types of filters:
		// 1. Compound filter (must begin with a left parenthesis)
		// 2. Basic filter
		switch (line.charAt(0)) {
			case LEFT_PARENTHESIS: {
				return parseCompoundFilter(line);
			}
			default: {
				return parseBasicFilter(line);
			}
		}
	}
	
	/**
	 * Parses a string (single-lined) of a basic filter and returns a Filter
	 * object which represents it.
	 * @param line A string which represents a basic filter
	 * @return A Filter object
	 * @throws UnsupportedFilterException If the filter is unsupported
	 * @throws InvalidFilterExpressionException If the expression is
	 * improperly formatted
	 * @throws InvalidFilterParametersException If the given parameters are
	 * invalid
	 */
	private static Filter parseBasicFilter(String line)
		throws UnsupportedFilterException, InvalidFilterExpressionException,
			   InvalidFilterParametersException {
		Pattern patt = Pattern.compile(BASIC_FILTER_REGEX);
		Matcher matcher = patt.matcher(line);
		if (!matcher.matches()) {
			throw new InvalidFilterExpressionException();
		}
		StringTokenizer st = new StringTokenizer(line, BASIC_FILTER_DELIMITERS);
		String filterName = st.nextToken();
		String filterValue = st.nextToken();
		
		return makeBasicFilter(filterName, filterValue);
	}
	
	/**
	 * Parses a string (single-lined) of an action and returns an Action
	 * object which represents it.
	 * @param line A string which represents an action
	 * @return An Action object
	 * @throws InvalidActionException If the given action is invalid
	 * @throws InvalidActionParametersException If the given action parameters
	 * are invalid
	 */
	private static Action parseAction(String line)
		throws InvalidActionException, InvalidActionParametersException {
		Pattern patt = Pattern.compile(ACTION_REGEX);
		Matcher matcher = patt.matcher(line);
		if (!matcher.matches()) {
			throw new InvalidActionException();
		}
		StringTokenizer st = new StringTokenizer(line, ACTION_DELIMITERS);
		String actionName = st.nextToken();
		String actionParam = st.nextToken();
		
		return makeAction(actionName, actionParam);
	}
	
	/**
	 * Recursively parses a compound filter expression and returns a Filter
	 * object which represents it.
	 * @param line A single line which represents the compound filter
	 * @return A Filter object
	 * @throws InvalidFilterExpressionException If the given expression is invalid
	 * @throws UnsupportedFilterException If one of the filters in the expression
	 * is unsupported
	 * @throws InvalidFilterParametersException If one of the filter parameters
	 * is invalid
	 */
	private static Filter parseCompoundFilter(String line)
		throws InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidFilterParametersException {
		// First we check for parentheses
		Pattern patt = Pattern.compile(PARENTHESES_REGEX);
		Matcher matcher = patt.matcher(line);
		if (!matcher.matches()) {
			// Expression has no legal parentheses
			throw new InvalidFilterExpressionException();
		}
		String trimmedLine = matcher.group(PARENTHESES_GROUP_INDEX);
		patt = Pattern.compile(COMPOUND_FILTER_REGEX);
		matcher = patt.matcher(trimmedLine);
		int i = 0;
		// Try to match the given expression with the regex iteratively,
		// that is, try all the possible ways to split the expression into
		// two operands and an AND/OR operator between them and parse the operands
		// recursively. If the given expression is indeed legal, only one of
		// the possible splits will prove to be a legal filter expressions, and
		// the others will fail somewhere down the recursion line.
		while (matcher.find(i)) {
			try {
				if (null != matcher.group(NOT_OPERATOR_GROUP_INDEX)) {
					Filter notFilter = parseFilter(matcher.group(NOT_OPERAND_GROUP_INDEX));
					return new NotFilter(notFilter);
				} else if (null != matcher.group(AND_OR_OPERATOR_GROUP_INDEX)) {
					String leftOperand;
					if (0 == i) {
						leftOperand = matcher.group(LEFT_OPERAND_GROUP_INDEX);
						
					} else {
						leftOperand = trimmedLine.substring(0, i) + matcher.group(LEFT_OPERAND_GROUP_INDEX);
					}
					String rightOperand = matcher.group(RIGHT_OPERAND_GROUP_INDEX);
					String operator = matcher.group(AND_OR_OPERATOR_GROUP_INDEX);
					Filter leftFilter = parseFilter(leftOperand);
					Filter rightFilter = parseFilter(rightOperand);
					if (operator.equals(AND_OPERATOR)) {
						return new AndFilter(leftFilter, rightFilter);
					} else if (operator.equals(OR_OPERATOR)) {
						return new OrFilter(leftFilter, rightFilter);
					} else {
						// This should never happen due to the regex, but just to
						// be on the safe side
						throw new InvalidFilterExpressionException();
					}
				} else {
					// This should never happen due to the regex, but just to
					// be on the safe side
					throw new InvalidFilterExpressionException();
				}
			} catch (InvalidFilterExpressionException e) {
				// Fail silently, to allow more attempts at matching the
				// filter string correctly
				i = matcher.start(RIGHT_OPERAND_GROUP_INDEX);
			}
		}
		// If we got here it means we tried all possible matches and none of
		// them were legal filter expressions
		throw new InvalidFilterExpressionException();
	}
	
	/**
	 * Builds a filter object, given a filter name and value
	 * @param filterName The filter's name
	 * @param filterValue The filter's value (parameters)
	 * @return A Filter object
	 * @throws UnsupportedFilterException If the filter name is unsupported
	 * @throws InvalidFilterParametersException If the given parameters are
	 * unsupported by this filter
	 */
	private static Filter makeBasicFilter(String filterName, String filterValue)
		throws UnsupportedFilterException, InvalidFilterParametersException {
		if (filterName.equals(FileWildcardFilter._name)) {
			return new FileWildcardFilter(filterValue);
		} else if (filterName.equals(IsReadableFilter._name)) {
			return new IsReadableFilter(filterValue);
		} else if (filterName.equals(IsWritableFilter._name)) {
			return new IsWritableFilter(filterValue);
		} else if (filterName.equals(ModifiedAfterFilter._name)) {
			return new ModifiedAfterFilter(filterValue);
		} else if (filterName.equals(ModifiedBeforeFilter._name)) {
			return new ModifiedBeforeFilter(filterValue);
		} else if (filterName.equals(ModifiedOnFilter._name)) {
			return new ModifiedOnFilter(filterValue);
		} else if (filterName.equals(SizeBiggerFilter._name)) {
			return new SizeBiggerFilter(filterValue);
		} else if (filterName.equals(SizeEqualFilter._name)) {
			return new SizeEqualFilter(filterValue);
		} else if (filterName.equals(SizeSmallerFilter._name)) {
			return new SizeSmallerFilter(filterValue);
		} else if (filterName.equals(SubdirWildcardFilter._name)) {
			return new SubdirWildcardFilter(filterValue);
		} else {
			throw new UnsupportedFilterException();
		}
	}
	
	/**
	 * Builds an Action object, given an action name and parameters
	 * @param actionName The action's name
	 * @param actionParam The action's parameters
	 * @return An Action object
	 * @throws InvalidActionException If the given action name is invalid
	 * @throws InvalidActionParametersException If the given action parameters
	 * are unsupported by this action
	 */
	private static Action makeAction(String actionName, String actionParam)
		throws InvalidActionException, InvalidActionParametersException {
		if (actionName.equals(AddPrefixAction._name)) {
			return new AddPrefixAction(actionParam);
		} else if (actionName.equals(AddSuffixAction._name)) {
			return new AddSuffixAction(actionParam);
		} else if (actionName.equals(CopyAction._name)) {
			return new CopyAction(actionParam);
		} else if (actionName.equals(PrintAction._name)) {
			return new PrintAction(actionParam);
		} else {
			throw new InvalidActionException();
		}
	}
	
	/**
	 * Obtains the next line from the file scanner and makes sure it is not empty
	 * @param sc Scanner to the input file
	 * @return The next line
	 * @throws InvalidSectionException If the next line was empty, which means
	 * the section is surely invalid
	 */
	private static String getNextLine(Scanner sc) throws InvalidSectionException {
		String nextLine = sc.nextLine();
		if (0 == nextLine.length()) {
			throw new InvalidSectionException();
		}
		return nextLine;
	}
	
	/**
	 * Executes the command file, section by section, on the given source
	 * directory
	 * @param rootDirectory The source directory to operate on
	 * @throws IOFailureException If an I/O error has occurred during the
	 * execution of the commands
	 */
	public void execute(File rootDirectory) throws IOFailureException {
		for (int i = 0; i < _sections.size(); i++) {
			_sections.get(i).execute(rootDirectory);
		}
	}

}
