import java.util.Random;

public class Cos extends Expression {

	private Expression cosfunction;
	
	public Cos(Expression h_prime){
		
		cosfunction = h_prime;
		
	}

public Cos(int maxDepth, Random random){

		cosfunction = random(maxDepth, random);
		
	}

	@Override

	public double evaluate(double x, double y) {

		return Math.cos(Math.PI * cosfunction.evaluate(x,y));
	}

	public Expression variation(int maxDepth, Random random) {

		/*
		 * If f(h) is a one-argument function such as sin or cos, 
		 * then do the following to calculate a variation of depth maxDepth:
		 * Throw a five sided die.
		 */

		int side5 = random.nextInt(5)+1; 

		if(side5 == 1){

			return Expression.random(maxDepth, random);

		}


		if(side5 == 2 || side5 == 3){

			return cosfunction.variation(maxDepth-1, random);

		}

		else if(side5 == 4){

			return new Sin(cosfunction);		

		}

		else {

			return this;								   

		}
		
	}
	
}
