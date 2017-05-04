import java.util.Random;

//@author Anathi Swaphi

public class Product extends Expression {

	private Expression h;
	private Expression k;
	
	public Product(Expression h, Expression k){
		
		this.h = h;
		this.k = k;
	}

	public Product(int maxDepth, Random random){
	
		h = random(maxDepth, random);
		k = random(maxDepth, random);
	}
	
	@Override

	public double evaluate(double x, double y) {
		
		return h.evaluate(x,y)*k.evaluate(x,y);
	}

	public Expression variation(int maxDepth, Random random) {

		/*
		 * If f(h,k) is a two-argument function such as avg or prod, 
		 * then do the following to calculate a variation of depth maxDepth:
		 * Throw a six sided die
		 */

		int side6 = random.nextInt(6)+1;


		if(side6 == 1){

			return Expression.random(maxDepth, random); 			
		}
		else if(side6 == 2 || side6 == 3){

			return new Product(h.variation(maxDepth-1, random),k); 							
	
		}
		
		else if(side6 == 4 || side6 == 5){

			return new Product(h,h.variation(maxDepth-1, random)); 							
																	   
		}

		else{

			return this;
		}



	}

}
