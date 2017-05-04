import java.util.Random;

	//@author Anathi Swaphi

public abstract class Expression{

	public abstract double evaluate(double x, double y);

	public abstract Expression variation(int maxDepth, Random random);

	public static Expression random(int maxDepth, Random random) {

		/*
		 * If we have reached the maximum depth (maxDepth==0), then do the following:
		 * Throw a 5-sided die.
		 */

		if(maxDepth == 0){

			int side5 = random.nextInt(5)+1;

			if(side5 == 1 || side5 == 2){

				return new X();

			}

			else if(side5 == 3 || side5 == 4){

				return new Y();

			}

			else{

				return new K(random);

			}

		}
		if(maxDepth !=0){

			int side5 = random.nextInt(17)+1;

			if(side5 == 1 || side5 == 2 || side5 == 3){

				return new Sin(maxDepth-1,random);

			}

			else if(side5 == 4 || side5 == 5 || side5 == 6){

				return new Cos(maxDepth-1,random);

			}

			else if(side5 ==7 || side5 == 8 || side5 == 9){

				return new Average(maxDepth-1,random);

			}

			else if(side5 == 10 || side5 == 11 || side5 == 12){

				return new Product(maxDepth-1,random);

			}

			else if(side5 == 13 || side5 == 14){

				return new X();
			}

			else if(side5 == 15 || side5 == 16){

				return new Y();

			}

			else{

				return new K(random);
			}

		}

		return null;
	}

}



