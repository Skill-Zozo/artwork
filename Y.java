import java.util.Random;

//@author Anathi Swaphi

public class Y extends Expression {


	@Override

	public double evaluate(double x, double y) {

		return y;
	}

	public Expression variation(int maxDepth, Random random) {

		/*
		 * If f is a "leaf" function such as x or y or a constant,
		 * then do the following to calculate a variation of depth maxDepth:
		 * Throw a 2-sided die.
		 */

		int side2 = random.nextInt(2)+1; 										

		if(side2 == 1){

			return Expression.random(maxDepth, random); 						
		}

		else {

			return this; 													
		}
	}

}
