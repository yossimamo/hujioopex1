urig03
ymamo29

Uri Greenberg 021986039
Yossi Mamo 038073722

-------------------------------
OOP Ex3 (Interpreter) README file
-------------------------------

-------------------------------
File Descriptions:
-------------------------------
ChainedHashMap.java - this class is an implementation of a chained hashmap.

HashObject.java - An abstract class representing object in the HashMap class.

HashString.java - this class implements HashObject. turning a string into
a key you can get a hash code of or compare to other keys from strings.

KeyValObject.java-  this class represents an object holding a key and a value.
the hashmap hold object from this type.

Interpreter.java - ****************

InterpreterMain.java - *************************

Functions.java - this class is the programs interface with the functions.
it looks them up if needed and saves them in a hashmap it holds.

Operators.java - this class is the programs interface with the operators.
it holds them in a hashmap.

Variables.java - this class is the programs interface with the variables.
it holds them in a hashmap.

Function.java -  A function abstract class. 
all functions and operators in the interpreter system implements it.

AbsFunction.java - this class is a function which returns the absolute
value of the input.

ExpFunction.java - this class is a function which returns the value of e
powered by the input.

LogFunction.java - this class is a function which returns the log
of the input.

MaxFunction.java - this class is a function which returns the maximum number
of all the inputs numbers.

MinFunction.java - this class is a function which returns the minimum number
of all the inputs numbers.

PowFunction.java - this class is a function which returns the power of two
numbers.

ModFunction.java - this class is a function which returns the modulo of two 
numbers.

Operator.java - an abstract class representing all operators.

MinusOperator.java - this class is an operator which subtract the second number
in the input from the first number in the input.

PlusOperator.java - this class is an operator which sums the first number
in the input with the second number in the input.

DivideOperator.java - this class is an operator which divides the first number
in the input by the second number in the input.

TimesOperator.java - this class is an operator which multiplies the first number
in the input by the second number in the input. 

IllegalArgumentException.java - An error thrown upon an illegal argument given
as input to the program.

IllegalExpressionException.java - an error thrown upon an illegal expression given 
as input.

IllegalFunctionException.java - An error thrown upon an illegal function given as
input to the program.

IllegalMathOperationException.java - an error thrown upon an illegal math operation 
given as input.

IllegalOperatorException.java - an error thrown upon an illegal operator given 
as input.

IllegalParametersException.java - an error thrown upon an illegal parameters (number
of parameters or if some of them are null) given as input.

UninitializedVariableException.java - an error thrown upon an uninitialized variable
given as input. 

README - This file.

Total: 30 files.

------------------------
Design:
------------------------
in our design we decided to devide the files into 4 packages.

the main package- holding the heart of the program. in this package we have
the  -*************interpreter and the interpreterMain*******************
we also have the Functions Operatores and Variables classes. these classes
are in fact the programs way of holding and handeling the functions the 
operatores and the variables (accordingly). each of these classes holds 
a hashmap with it the class can manage all of the above.

the DataStructures package - in this package we have the classes that 
implement a hashmap. first we have a HashObject interface. this object
represents a key. it must has the ability to be compared to another HashObject
and to create a hashcode.
the HashString class implements the HashObject. by giving its constructor a 
string, it turns into a key you can use in the hashmap.
the KeyValObject is the object the hashmap holds. it contains a key
(a HashObject) and a value (any object).
and the ChainedHashMap class is the implementation of a chained hash map.
it implement put, get, remove, getSize and containsKey.
the hashMap it self is an ArrayList holding in each cell a LinkedList of
KeyValObjects.

the Functions package - 


the Exceptions package - 





------------------------
Implementation issues:
------------------------

