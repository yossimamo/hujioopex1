package oop.ex3.main;

import java.util.HashMap;

import oop.ex3.exceptions.IllegalOperatorException;
import oop.ex3.functions.DivideOperator;
import oop.ex3.functions.MinusOperator;
import oop.ex3.functions.Operator;
import oop.ex3.functions.PlusOperator;
import oop.ex3.functions.TimesOperator;

public class Operators {
	
	private HashMap<String, Operator> _hashMap;
	
	public Operators() {
		_hashMap = new HashMap<String, Operator>();
		Operator op = new PlusOperator();
		_hashMap.put(op.getSign(), op);
		op = new MinusOperator();
		_hashMap.put(op.getSign(), op);
		op = new TimesOperator();
		_hashMap.put(op.getSign(), op);
		op = new DivideOperator();
		_hashMap.put(op.getSign(), op);
		// TODO add overloaded operators
	}
	
	public Operator getOperator(String operatorSign) throws IllegalOperatorException {
		//HashString key = new HashString(operatorSign);
		if (!_hashMap.containsKey(operatorSign)) {
			throw new IllegalOperatorException();
		}
		return (Operator) _hashMap.get(operatorSign);
	}

}
