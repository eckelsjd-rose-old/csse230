package evaluator;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * A class to evaluate in-fix expressions from an input string,
 * 
 * @author koenignm and eckelsjd date: 9/17/2019
 *
 */
public class InfixEvaluator extends Evaluator {

	private Stack<String> operators;
	private PostfixEvaluator postfix;

	public InfixEvaluator() {
		this.operators = new Stack<>();
		this.postfix = new PostfixEvaluator();
	}
	
	/*
	 * Converts an expression to postfix, then evaluates using a PostfixEvaluator object.
	 */
	@Override
	public int evaluate(String expression) throws ArithmeticException {
		String post = this.convertToPostfix(expression);
		return this.postfix.evaluate(post);
	}

	/*
	 * Converts an in-fix expression to post-fix
	 */
	public String convertToPostfix(String exp) {
		String[] input = exp.split(" ");
		StringBuilder output = new StringBuilder();
		
		// handle empty string case
		if (exp.length() == 0) { return ""; }
		
		for (int i = 0; i < input.length; i++) {
			// Look for numbers first and add them to string
			// Order of numbers doesn't change from infix->postfix
			try {
				Integer.parseInt(input[i]);
				output.append(input[i] + " ");
			}

			// We have caught something that is not a number
			catch (NumberFormatException | NullPointerException nfe) {

				// Check if the character is a valid operator
				if (Evaluator.isOperator(input[i])) {
					
					// check for "(" or empty stack
					if (this.operators.empty() || this.operators.peek().equals("(")) {
						this.operators.push(input[i]);
						continue;
					}
					
					try {
						switch (input[i]) {
						case "+":
							
						case "-":
						case "*":
						case "/":
						case "^":
							
							String cur = input[i];
							while (!this.operators.empty()) {
								String top = this.operators.peek();
								
								// higher precedence case
								if (getLevel(cur) > getLevel(top)) {
									this.operators.push(cur);
									break;
								} 
								
								// equal precedence case (assume left associativity)
								else if (getLevel(cur) == getLevel(top)) {
									output.append(this.operators.pop() + " ");
									this.operators.push(cur);
									break;
								}
								
								// lower precedence case
								else {
									output.append(this.operators.pop() + " ");
									if (this.operators.empty()) {
										this.operators.push(cur);
										break;
									}
								}
							}
							break;
						case "(":
							this.operators.push(input[i]);
							break;
						case ")":
							// print all operators until "(" is encountered
							while (true) {
								if (this.operators.peek().equals("(")) {
									this.operators.pop(); // discard "("
									break;
								}
								if (this.operators.empty()) {
									throw new ArithmeticException("Non-matching delimiters");
								}
								output.append(this.operators.pop() + " ");
							}
							break;
						default:
							throw new ArithmeticException("Invalid infix operator");
						}
						
					} 
					
					// catch invalid characters or formatting
					catch (EmptyStackException e) {
						throw new ArithmeticException("Invalid format");
					}
				}

				// The character is not valid input
				else {
					throw new ArithmeticException("Invalid input");
				}
			}
		}
		
		// append the rest of the remaining operators at the end
		while (!this.operators.empty()) {
			String next = this.operators.pop();
			
			// check for stray parentheses on the stack
			if (next.equals("(") || next.equals(")")) {
				throw new ArithmeticException("Too many parentheses leftover.");
			}
			
			if (this.operators.empty()) {
				output.append(next);
				break;
			}
			output.append(next + " ");
		}
		
		return output.toString().trim();
	}

	/**
	 * 
	 * Checks level of operators:
	 *  ^      = 3
	 *  * or / = 2
	 *  + or - = 1
	 *
	 * @param string
	 */
	private static int getLevel(String string) {
		switch (string) {
		case "^":
			return 3;
		case "*":
		case "/":
			return 2;
		case "+":
		case "-":
			return 1;
		default:
			return 0;
		}
	}

}
