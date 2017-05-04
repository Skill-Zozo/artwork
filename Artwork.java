import java.util.Random;

//@author Anathi Swaphi

public class Artwork {

	private Expression red;
	private Expression green;
	private Expression blue;
	private Expression red2;
	private Expression green2;
	private Expression blue2;

	private int maxDepth;
	private int currentExpression;

	/*
	 * This is when original artwork is created.
	 */
	public Artwork(int maxDepth) {

		this.maxDepth = maxDepth;

	}

	public int getcurrentExpression() {

		return currentExpression;
	}

	public void maxDepth(int maxDepth) {

		this.maxDepth = maxDepth;

	}

	public void expressionGenerator(Random random) {

		currentExpression = 0;

		red = Expression.random(maxDepth, random);
		green = Expression.random(maxDepth, random);
		blue = Expression.random(maxDepth, random);
	}

	public void variationGenerator(Random random) {

		red2 = red.variation(maxDepth+1, random);
		green2 = green.variation(maxDepth+1, random);
		blue2 = blue.variation(maxDepth+1, random);

	}

	public Expression getRedVariation() {
		return red2;
	}

	public Expression getBlueVariation() {
		return blue2;
	}

	public Expression getGreenVariation() {
		return green2;
	}

	public Expression getRed() {
		return red;
	}

	public Expression getGreen() {
		return green;
	}

	public Expression getBlue() {
		return blue;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void newExpressions(Random random) {

		red = Expression.random(maxDepth, random);
		green = Expression.random(maxDepth, random);
		blue = Expression.random(maxDepth, random);

	}

	/*
	 * This is where variation artwork is created.
	 */

	public void setRed(Expression red) {

		this.red = red;
	}

	public void setGreen(Expression green) {

		this.green = green;
	}

	public void setBlue(Expression blue) {

		this.blue = blue;
	}

	public void SetExpression(Expression red0, Expression green0, Expression blue0) {

		red = red0;
		green = green0;
		blue = blue0;
	}

	public double red(double x, double y) {

		return red.evaluate(x, y);

	}

	public double green(double x, double y) {

		return green.evaluate(x, y);

	}

	public double blue(double x, double y) {

		return blue.evaluate(x, y);

	}

}
