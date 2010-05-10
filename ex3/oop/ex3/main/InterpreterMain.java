//###############  
// FILE : InterpreterMain.java  
// WRITER : Uri Greenberg, urig03, 021986039  
// WRITER : Yossi Mamo, ymamo29, 038073722
// EXERCISE : oop ex3 2010  
// DESCRIPTION: The Interpreter program's entry point, in charge of UI.
//###############

package oop.ex3.main;

import java.util.Scanner;

import oop.ex3.exceptions.IllegalArgumentException;

public class InterpreterMain {
	
	private static String EXIT_COMMAND = "exit";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter();
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while (!line.equals(EXIT_COMMAND)) {
			try {
				System.out.printf("%.4f\n", interpreter.interpret(line));
			} catch (IllegalArgumentException e) {
				System.err.println("error");
			}
			line = sc.nextLine();
		}
	}

}
