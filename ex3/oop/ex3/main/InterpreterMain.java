package oop.ex3.main;

import java.util.Scanner;

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
			} catch (Exception e) {
				// TODO change to System.err
				System.out.println("error");
			}
			line = sc.nextLine();
		}
	}

}
