package evaluator;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 
 * A class to evaluate post-fix expressions that come from an input String.
 *
 * @author eckelsjd and koenigm
 *         Created Sep 18, 2019.
 */
public class PostfixEvaluator extends Evaluator {

	private Stack<Integer> operands;

	public PostfixEvaluator() {
		this.operands = new Stack<>();
	}

	/*
	 * Evaluates the provided String 'expression' as a post-fix equation
	 * and returns the answer.
	 * 
	 * Throws an ArithmeticException error if any invalid input is provided.
	 */
	@SuppressWarnings("boxing")
	@Override
	public int evaluate(String expression) throws ArithmeticException {
		String[] input = expression.split(" ");

		for (int i = 0; i < input.length; i++) {
			// Look for numbers first and put them on the stack
			try {
				int num = Integer.parseInt(input[i]);
				this.operands.push(num);
			}

			// We have caught something that is not a number
			catch (NumberFormatException | NullPointerException nfe) {

				// Check if the character is a valid operator
				if (Evaluator.isOperator(input[i])) {
					try {
						int num1 = this.operands.pop();
						int num2 = this.operands.pop();
						switch (input[i]) {
						case "+":
							this.operands.push(num2 + num1);
							break;
						case "-":
							this.operands.push(num2 - num1);
							break;
						case "*":
							this.operands.push(num2 * num1);
							break;
						case "/":
							this.operands.push(num2 / num1);
							break;
						case "^":
							this.operands.push((int) Math.pow(num2, num1));
							break;
						default:
							throw new ArithmeticException("Invalid postfix operand");
						}
					} catch (EmptyStackException e) {
						throw new ArithmeticException("Invalid format");
					}
				}

				// The character is not valid input
				else {
					throw new ArithmeticException("Invalid input");
				}
			}
		}
		
		// Catch if there are too many items left on the stack
		// (This means there were too many operators or too few operands)
		if (this.operands.size() > 1) {
			throw new ArithmeticException("Too many operands left");
		}
		
		return this.operands.pop();
	}

}
