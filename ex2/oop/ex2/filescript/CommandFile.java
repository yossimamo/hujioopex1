package oop.ex2.filescript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.*;

import oop.ex2.actions.*;
import oop.ex2.filters.*;

public class CommandFile {
	
	private static final String FILTERS = "FILTERS";
	private static final String ACTION = "ACTION";
	private static final char LEFT_PARENTHESIS = '(';
	private static final char BEGIN_COMMENT = '#';
	private static final int COMMENT_PREFIX_LENGTH = 1;
	private static final String BASIC_FILTER_REGEX = "^[^\\s():]+:[^\\s():]+$";
	private static final String BASIC_FILTER_DELIMITERS = ":";
	private static final String COMPOUND_FILTER_REGEX = "(NOT) (.+)$|(.+?) (AND|OR) (.+)$";
	private static final int NOT_OPERATOR_GROUP_INDEX = 1;
	private static final int NOT_OPERAND_GROUP_INDEX = 2;
	private static final int LEFT_OPERAND_GROUP_INDEX = 3;
	private static final int AND_OR_OPERATOR_GROUP_INDEX = 4;
	private static final int RIGHT_OPERAND_GROUP_INDEX = 5;
	private static final String AND_OPERATOR = "AND";
	private static final String OR_OPERATOR = "OR";
	private static final String ACTION_REGEX = "^[^\\s():]+:[^\\s():]+$";
	private static final String ACTION_DELIMITERS = ":";
	private static final int PARENTHESES_GROUP_INDEX = 1;
	private static final String PARENTHESES_REGEX = "^\\((.+)\\)$";
	
	private ArrayList<Section> _sections;
	
	private CommandFile() {
		_sections = new ArrayList<Section>();
	}
	
	public static CommandFile createFromFile(File file)
		throws InvalidActionException,
			   InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidSectionException, InvalidFilterParametersException,
			   FileNotFoundException, InvalidActionParametersException {
		Scanner sc = new Scanner(file);
		CommandFile cmdFile = new CommandFile();
		
		while (sc.hasNext()) {
			Section section = parseSection(sc);
			cmdFile._sections.add(section);
		}		
		return cmdFile;
	}
	
	private static Section parseSection(Scanner sc)
		throws InvalidActionException, InvalidFilterExpressionException,
			   UnsupportedFilterException, InvalidSectionException,
			   InvalidFilterParametersException, InvalidActionParametersException {
		Section section = new Section();
		String line = sc.nextLine();
		// Begin parsing FILTERS
		if (!line.equals(FILTERS)) {
			throw new InvalidSectionException();
		}
		line = sc.nextLine();
		while (!line.equals(ACTION)) {
			// Two possible types of lines:
			// 1. Comment
			// 2. Filter 
			switch (line.charAt(0)) {
				case BEGIN_COMMENT: {
					section.addComment(parseComment(line));
					break;			
				}
				default: {
					section.addFilter(parseFilter(line));
					break;
				}
			}
			line = sc.nextLine();
		}
		// Begin parsing ACTIONS
		line = sc.nextLine();
		// Comments are not allowed here
		if (BEGIN_COMMENT == line.charAt(0)) {
			throw new InvalidSectionException();
		}
		// Only one possible option - the action itself
		section.setAction(parseAction(line));		
			
		return section;
	}
	
	private static String parseComment(String line) {
		return line.substring(COMMENT_PREFIX_LENGTH);
	}
	
	private static Filter parseFilter(String line)
		throws InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidFilterParametersException {
		// Two possible types of filters:
		// 1. Compound filter
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
	
	private static Filter parseCompoundFilter(String line)
		throws InvalidFilterExpressionException, UnsupportedFilterException,
			   InvalidFilterParametersException {
		// Check for parentheses
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
	
	public void execute(File rootDirectory) throws IOException {
		for (int i = 0; i < _sections.size(); i++) {
			_sections.get(i).execute(rootDirectory);
		}
	}

}
