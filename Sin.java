import java.util.Random;

//@author Anathi Swaphi

public class Sin extends Expression {

	private Expression sinfunction;


	public Sin(int maxDepth, Random random){

		sinfunction= random(maxDepth, random);

	}

	public Sin(Expression h_prime){

		sinfunction = h_prime;
	}



	@Override

	public double evaluate(double x, double y) {

		return Math.sin(Math.PI * sinfunction.evaluate(x,y));

	}

	@Override

	public Expression variation(int maxDepth, Random random) {

		/*
		 * If f(h) is a one-argument function such as sinfunction or cos, 
		 * then do the following to calculate a variation of depth maxDepth:
		 * Throw a 5-sided die.
		 */

		int side5 = random.nextInt(5)+1; 										

		if(side5 == 1){

			return Expression.random(maxDepth, random);					
		}

		if(side5 == 2 || side5 == 3){

			return sinfunction.variation(maxDepth-1, random);								
		}

		else if(side5 == 4){

			return new Cos(sinfunction);													
		}

		else {

			return this;													

		}

	}

}
