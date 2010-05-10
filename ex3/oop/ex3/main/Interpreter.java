//###############  
// FILE : Interpreter.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: The Interpreter is in charge of processing the given expressions
// and returning their results. It also holds a state of the currently available
// variables, functions and operators.
//###############

package oop.ex3.main;

import oop.ex3.exceptions.IllegalArgumentException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex3.exceptions.IllegalExpressionException;
import oop.ex3.exceptions.IllegalOperatorException;
import oop.ex3.exceptions.UninitializedVariableException;
import oop.ex3.functions.Function;
import oop.ex3.functions.Operator;

public class Interpreter {
	
	private Functions _functions;
	private Operators _operators;
	private Variables _variables;
	private Pattern _assignmentPatt;
	private Pattern _floatPatt;
	private Pattern _operatorPatt;
	private Pattern _variablePatt;
	private Pattern _functionPatt;
	private Pattern _parenthesisPatt;
	
	private static final String ASSIGNMENT_EXPR = "^\\s*\\$([a-z][a-z0-9]*)\\s+([^\\s=]*)=\\s+(.+)";
	private static final int ASSIGNMENT_LEFT_GROUP = 1;
	private static final int ASSIGNMENT_OPTIONAL_OPERATOR_GROUP = 2;
	private static final int ASSIGNMENT_RIGHT_GROUP = 3;
	private static final String FLOAT = "([-]?[0-9]+(?:[.]?[0-9]+)?)\\s*$";
	private static final int FLOAT_GROUP = 1;
	private static final String VARIABLE = "([-])?\\$([a-z][a-z0-9]*)\\s*$";
	private static final int VARIABLE_UNARY_MINUS_GROUP = 1;
	private static final int VARIABLE_NAME_GROUP = 2;
	private static final String FUNCTION = "([-])?([A-Z][a-z]+)\\[(.+)\\]\\s*$";
	private static final int FUNCTION_UNARY_MINUS_GROUP = 1;
	private static final int FUNCTION_NAME_GROUP = 2;
	private static final int FUNCTION_ARGS_GROUP = 3;
	private static final String OPERATOR = "\\s+(\\S+)\\s+$";
	private static final int OPERATOR_SIGN_GROUP = 1;
	private static final String PARENTHESIS_EXPR = "([-])?\\((.*)\\)$";
	private static final int PARENTHESIS_SIGN_GROUP = 1;
	private static final int PARENTHESIS_INSIDE_GROUP = 2;
	private static final int UNBALANCED = -1;
	
	public Interpreter() {
		_functions = new Functions();
		_operators = new Operators();
		_variables = new Variables();
		// Compile patterns
		_assignmentPatt = Pattern.compile(ASSIGNMENT_EXPR);
		_floatPatt = Pattern.compile(FLOAT);
		_operatorPatt = Pattern.compile(OPERATOR);
		_variablePatt = Pattern.compile(VARIABLE);
		_functionPatt = Pattern.compile(FUNCTION);
		_parenthesisPatt = Pattern.compile(PARENTHESIS_EXPR);
	}
	
	public double interpret(String line) throws IllegalArgumentException {
		StringBuilder expr = new StringBuilder(line.trim());
		// Check whether the given line is an assignment expression
		Matcher matcher = _assignmentPatt.matcher(expr);
		if (matcher.matches()) {
			// Treat this line as an assignment expression
			String varName = matcher.group(ASSIGNMENT_LEFT_GROUP);
			String assignedValueStr = matcher.group(ASSIGNMENT_RIGHT_GROUP);
			String optionalOperator = matcher.group(ASSIGNMENT_OPTIONAL_OPERATOR_GROUP);
			double newValue = interpretMathExpression(new StringBuilder(assignedValueStr.trim()));
			if (!optionalOperator.isEmpty()) {
				double varValue = _variables.getVariable(varName);
				Operator operator = _operators.getOperator(optionalOperator);
				if (!operator.isAssignmentAllowed()) {
					throw new IllegalExpressionException();
				}
				newValue = operator.calculate(makeArgumentList(varValue, newValue));
			}
			_variables.setVariable(varName, newValue);
			return newValue;
		} else {
			return interpretMathExpression(expr);
		}		
	}
	
	private double interpretMathExpression(StringBuilder expr) throws IllegalArgumentException {
		double result = interpretRightmostOperand(expr);
		while (0 != expr.length()) {
			String operatorSign = interpretRightmostOperator(expr);
			Operator operator = _operators.getOperator(operatorSign);
			double nextOperand = interpretRightmostOperand(expr);
			LinkedList<Double> opArguments = makeArgumentList(nextOperand, result);
			result = operator.calculate(opArguments);
		}
		return result;
	}
	
	private double interpretRightmostOperand(StringBuilder expr) throws IllegalArgumentException {
		// Try matching operand with a float
		Matcher matcher = _floatPatt.matcher(expr);
		if (matcher.find()) {
			return handleFloatOperand(expr, matcher);
		}
		// Try matching operand with a variable
		matcher = _variablePatt.matcher(expr);
		if (matcher.find()) {
			return handleVariableOperand(expr, matcher);						
		}
		if (expr.length() > 0) {
			// Try matching operand with a function, which must end with ']'
			if (']' == expr.charAt(expr.length()-1)) {
				return handleFunctionOperand(expr);
			}
			// Try matching operand with a parenthesis expression, which must end with ')'
			if (')' == expr.charAt(expr.length()-1)) {
				return handleParenthesisOperand(expr);
			}
		}
		// If no match was found, expression is surely invalid
		throw new IllegalExpressionException();
	}
	
	private double handleParenthesisOperand(StringBuilder expr) throws IllegalArgumentException {
		int res = checkBalance(expr, '(', ')');
		if (UNBALANCED != res) {
			Matcher matcher = _parenthesisPatt.matcher(expr.substring(Math.max(0, res-1)));
			if (matcher.find()) {
				double result = interpretMathExpression(new StringBuilder(matcher.group(PARENTHESIS_INSIDE_GROUP).trim()));
				String signGroup = matcher.group(PARENTHESIS_SIGN_GROUP);
				int signLength = 0;
				if (null != signGroup) {
					signLength = signGroup.length();
					result *= -1;
				}
				expr.delete(res-signLength, expr.length());
				return result;
			}
		}
		throw new IllegalExpressionException();
	}

	private double handleFunctionOperand(StringBuilder expr) throws IllegalArgumentException {
		int res = checkBalance(expr, '[', ']');
		if (UNBALANCED != res) {
			int start = Math.max(0, expr.toString().lastIndexOf(' ', res));
			Matcher matcher = _functionPatt.matcher(expr);
			if (matcher.find(start)) {
				double result = interpretFunction(matcher.group(FUNCTION_NAME_GROUP), matcher.group(FUNCTION_ARGS_GROUP));
				if (null != matcher.group(FUNCTION_UNARY_MINUS_GROUP)) {
					result *= -1;
				}
				expr.delete(matcher.start(), expr.length());
				return result; 
			}
		}
		throw new IllegalExpressionException();
	}

	private double handleVariableOperand(StringBuilder expr, Matcher matcher) throws UninitializedVariableException {
		double result = _variables.getVariable(matcher.group(VARIABLE_NAME_GROUP));
		if (null != matcher.group(VARIABLE_UNARY_MINUS_GROUP)) {
			result *= -1;
		}
		expr.delete(matcher.start(), expr.length());
		return result;
	}

	private double handleFloatOperand(StringBuilder expr, Matcher matcher) {
		double result = Double.parseDouble(matcher.group(FLOAT_GROUP));
		expr.delete(matcher.start(), expr.length());
		return result;
	}

	private String interpretRightmostOperator(StringBuilder expr) throws IllegalOperatorException {
		Matcher matcher = _operatorPatt.matcher(expr);
		if (matcher.find()) {
			String operatorSign = matcher.group(OPERATOR_SIGN_GROUP);
			expr.delete(matcher.start(), expr.length());
			return operatorSign;			
		} else {
			throw new IllegalOperatorException();
		}
	}
	
	private double interpretFunction(String name, String args) throws IllegalArgumentException {
		Function func = _functions.getFunction(name);
		LinkedList<Double> argsList = makeArgumentList(args);
		return func.calculate(argsList);
	}
	
	private LinkedList<Double> makeArgumentList(String args) throws IllegalArgumentException {
		LinkedList<Double> list = new LinkedList<Double>();
		int bracketBalance = 0;
		int beginIndex = 0;
		for (int i = 0; i < args.length(); i++) {
			switch (args.charAt(i)) {
			case ',':
				if (0 != bracketBalance) {
					continue;
				} else {
					list.add(interpretMathExpression(new StringBuilder(args.substring(beginIndex, i).trim())));
					beginIndex = i+1;
				}
				break;
			case '[':
				bracketBalance++;
				break;
			case ']':
				bracketBalance--;
				break;
			}
		}
		if (0 != bracketBalance) {
			throw new IllegalExpressionException();
		} else {
			list.add(interpretMathExpression(new StringBuilder(args.substring(beginIndex).trim())));
			return list;
		}
	}
	
	private LinkedList<Double> makeArgumentList(double firstArg, double secondArg) {
		LinkedList<Double> list = new LinkedList<Double>();
		list.add(firstArg);
		list.add(secondArg);
		return list;
	}
	
	private int checkBalance(StringBuilder expr, char open, char close) {
		int balance = 0;
		int i = expr.length();
		do {
			i--;
			if (close == expr.charAt(i)) {
				balance++;
				continue;
			}
			if (open == expr.charAt(i)) {
				balance--;
				continue;
			}
		} while ((balance != 0) && (i > 0));
		if (0 == balance) {
			return i;
		} else {
			return UNBALANCED;
		}
	}

}