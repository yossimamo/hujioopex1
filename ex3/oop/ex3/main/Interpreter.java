package oop.ex3.main;

import java.lang.reflect.InvocationTargetException;
import oop.ex3.exceptions.IllegalArgumentException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex3.exceptions.IllegalExpressionException;
import oop.ex3.exceptions.IllegalFunctionException;
import oop.ex3.exceptions.IllegalOperatorException;
import oop.ex3.exceptions.UninitializedVariableException;
import oop.ex3.functions.Function;
import oop.ex3.functions.Operator;

public class Interpreter {
	
	private Functions _functions;
	private Operators _operators;
	private Variables _variables;
	
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
	
	public Interpreter() throws NullPointerException {
		_functions = new Functions();
		_operators = new Operators();
		_variables = new Variables();
	}
	
	public double interpret(String line)
		throws IllegalArgumentException, UninitializedVariableException,
		IllegalExpressionException, IllegalFunctionException, InstantiationException,
		IllegalAccessException, InvocationTargetException, IllegalOperatorException, NullPointerException {
		StringBuilder expr = new StringBuilder(line.trim());
		// Check whether the given line is an assignment expression
		Pattern patt = Pattern.compile(ASSIGNMENT_EXPR);
		Matcher matcher = patt.matcher(expr);
		if (matcher.matches()) {
			// Treat this line as an assignment expression
			String varName = matcher.group(ASSIGNMENT_LEFT_GROUP);
			String assignedValueStr = matcher.group(ASSIGNMENT_RIGHT_GROUP);
			String optionalOperator = matcher.group(ASSIGNMENT_OPTIONAL_OPERATOR_GROUP);
			double newValue = interpretMathExpression(new StringBuilder(assignedValueStr.trim()));
			if (!optionalOperator.isEmpty()) {
				double varValue = _variables.getVariable(varName);
				Operator operator = _operators.getOperator(optionalOperator);
				newValue = operator.calculate(makeArgumentList(varValue, newValue));
			}
			_variables.setVariable(varName, newValue);
			return newValue;
		} else {
			return interpretMathExpression(expr);
		}		
	}
	
	private double interpretMathExpression(StringBuilder expr)
		throws UninitializedVariableException, IllegalExpressionException,
		IllegalArgumentException, IllegalFunctionException, InstantiationException,
		IllegalAccessException, InvocationTargetException, IllegalOperatorException, NullPointerException {
		double result = interpretRightmostOperand(expr);
		while (0 != expr.length()) {
			// get operator
			// get operand
			// perform operation
			// accumulate result
			// repeat with next...
			String operatorSign = interpretRightmostOperator(expr);
			Operator operator = _operators.getOperator(operatorSign);
			double nextOperand = interpretRightmostOperand(expr);
			LinkedList<Double> opArguments = makeArgumentList(nextOperand, result);
			result = operator.calculate(opArguments);
		}
		return result;
	}
	
	// TODO separate to functions?
	private double interpretRightmostOperand(StringBuilder expr)
		throws UninitializedVariableException, IllegalExpressionException,
		IllegalArgumentException, IllegalFunctionException, InstantiationException,
		IllegalAccessException, InvocationTargetException, IllegalOperatorException, NullPointerException {
		// Capture the rightmost operand, which can be one of four: float, variable,
		// function call of parenthesis expression
		// TODO make Pattern members
		Pattern patt = Pattern.compile(FLOAT);
		Matcher matcher = patt.matcher(expr);
		if (matcher.find()) {
			double result = Double.parseDouble(matcher.group(FLOAT_GROUP));
			expr.delete(matcher.start(), expr.length());
			return result;
		}
		patt = Pattern.compile(VARIABLE);
		matcher = patt.matcher(expr);
		if (matcher.find()) {
			double result = _variables.getVariable(matcher.group(VARIABLE_NAME_GROUP));
			if (null != matcher.group(VARIABLE_UNARY_MINUS_GROUP)) {
				result *= -1;
			}
			expr.delete(matcher.start(), expr.length());
			return result;			
		}
		patt = Pattern.compile(FUNCTION);
		matcher = patt.matcher(expr);
		if (matcher.find()) {
			double result = interpretFunction(matcher.group(FUNCTION_NAME_GROUP), matcher.group(FUNCTION_ARGS_GROUP));
			if (null != matcher.group(FUNCTION_UNARY_MINUS_GROUP)) {
				result *= -1;
			}
			expr.delete(matcher.start(), expr.length());
			return result; 
		}
		if (')' == expr.charAt(expr.length()-1)) {
			int parenthesisBalance = 0;
			int i = expr.length();
			do {
				i--;
				switch (expr.charAt(i)) {
				case ')':
					parenthesisBalance++;
					break;
				case '(':
					parenthesisBalance--;
					break;
				}
			} while ((parenthesisBalance != 0) && (i > 0));
			if (0 == parenthesisBalance) {
				patt = Pattern.compile(PARENTHESIS_EXPR);
				matcher = patt.matcher(expr.substring(Math.max(0, i-1)));
				if (matcher.find()) {
					double result = interpretMathExpression(new StringBuilder(matcher.group(PARENTHESIS_INSIDE_GROUP).trim()));
					String signGroup = matcher.group(PARENTHESIS_SIGN_GROUP);
					int signLength = 0;
					if (null != signGroup) {
						signLength = signGroup.length();
						result *= -1;
					}
					expr.delete(i-signLength, expr.length());
					return result;
				}
			}
		}
		// Found no match - expression is surely invalid
		throw new IllegalExpressionException();
	}
	
	private String interpretRightmostOperator(StringBuilder expr)
		throws IllegalOperatorException {
		Pattern patt = Pattern.compile(OPERATOR);
		Matcher matcher = patt.matcher(expr);
		if (matcher.find()) {
			String operatorSign = matcher.group(OPERATOR_SIGN_GROUP);
			expr.delete(matcher.start(), expr.length());
			return operatorSign;			
		} else {
			throw new IllegalOperatorException();
		}
	}
	
	private double interpretFunction(String name, String args)
		throws IllegalFunctionException, IllegalArgumentException, InstantiationException,
		IllegalAccessException, InvocationTargetException, UninitializedVariableException,
		IllegalExpressionException, IllegalOperatorException, NullPointerException {
		Function func = _functions.getFunction(name);
		LinkedList<Double> argsList = makeArgumentList(args);
		return func.calculate(argsList);
	}
	
	private LinkedList<Double> makeArgumentList(String args)
		throws IllegalArgumentException, UninitializedVariableException,
		IllegalExpressionException, IllegalFunctionException, InstantiationException,
		IllegalAccessException, InvocationTargetException, IllegalOperatorException, NullPointerException {
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

}
