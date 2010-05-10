package oop.ex3.main;

import oop.ex3.dataStructures.ChainedHashMap;
import oop.ex3.dataStructures.HashString;
import oop.ex3.exceptions.IllegalOperatorException;
import oop.ex3.exceptions.NullPointerException;
import oop.ex3.functions.DivideOperator;
import oop.ex3.functions.MinusOperator;
import oop.ex3.functions.Operator;
import oop.ex3.functions.PlusOperator;
import oop.ex3.functions.TimesOperator;

public class Operators {
	
	private ChainedHashMap _hashMap;
	
	public Operators() throws NullPointerException {
		_hashMap = new ChainedHashMap();
		Operator op = new PlusOperator();
		HashString key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new MinusOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new TimesOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		op = new DivideOperator();
		key = new HashString(op.getSign());
		_hashMap.put(key, op);
		// TODO add overloaded operators
	}
	
	public Operator getOperator(String operatorSign)
					throws IllegalOperatorException, NullPointerException {
		HashString key = new HashString(operatorSign);
		if (!_hashMap.containsKey(key)) {
			throw new IllegalOperatorException();
		}
		return (Operator) _hashMap.get(key);
	}
}
