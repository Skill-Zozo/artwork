import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//@author Anathi Swaphi

/*
 * An extension of the Java Swing class Canvas. We extend it because we want
 * it to behave in a very special way: it should (1) draw our art and (2)
 * tell the application when the user clicks on it.
 */

public class MyCanvas extends Canvas {

	/*
	 * We have to add this here to make Java happy.
	 */

	private static final long serialVersionUID = 7570507763686587998L;

	/*
	 * The size of our canvas.
	 */

	private static final int SIZE = 200;
	
	/*
	 * indicator boolean, is it HSB or RGB
	 */
	private boolean HSB = false;
	/*
	 * Each canvas needs to know its number, so that when it is clicked, it can
	 * tell our application who it is.
	 */

	protected final int id;

	/*
	 * The framework (in other words, our application) to which this canvas
	 * belongs. We use this to inform the application that the user has clicked
	 * this canvas.
	 */

	protected final Framework framework;

	/*
	 * The color of the larger, outer Albers square.
	 */

	protected Color outerColor = Color.BLUE;
	/*
	 * previous artwork
	 */
	private Artwork prev;
	/*
	 * The color of the smaller, inner Albers square.
	 */

	protected Color innerColor = Color.GREEN;
	private Artwork artwork;

	/*
	 * To be able to react to user clicks, this canvas needs a "listener". We
	 * define it here, once.
	 */

	protected MouseListener clickListener = new MouseAdapter() {

		@Override

		public void mouseClicked(MouseEvent e) {

			framework.clicked(id);
		}
	};

	/*
	 * Construct an instance of MyCanvas. Basically, it starts from scratch by
	 * setting the id and framework, picking an outer and inner colour, and then
	 * doing some Swing stuff.
	 */
	
	public synchronized void setHSB(boolean b) {
		HSB = b;
	}

	public MyCanvas(int id, Framework framework, Random random) {

		this.id = id;
		this.framework = framework;
		System.out.println("Enter maxDepth :");
		artwork = new Artwork(StdIn.readInt());
		reset(random);
		Dimension size = new Dimension(SIZE, SIZE);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setSize(size);
		addMouseListener(clickListener);
	}

	/*
	 * Construct an instance of MyCanvas. It is almost the same as the previous
	 * constructor, except that it sets its color by making a slight variation
	 * of the color of the "original" parameter.
	 */

	public MyCanvas(int id, Framework framework, MyCanvas original, Random random) {

		artwork = new Artwork(original.artwork.getMaxDepth());
		this.prev = new Artwork(original.artwork.getMaxDepth());
		reset(random);
		this.id = id;
		this.framework = framework;
		reset(original, random);
		Dimension size = new Dimension(SIZE, SIZE);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		setSize(size);
		addMouseListener(clickListener);
	}

	/*
	 * Pick random colours.
	 */

	public void setToPrev() {
		artwork.setBlue(prev.getBlue());
		artwork.setGreen(prev.getGreen());
		artwork.setRed(prev.getRed());
	}
	public void reset(Random random){
		artwork.newExpressions(random);
		if(prev == null) {
			prev = new Artwork(this.artwork.getMaxDepth());
		}
		prev.newExpressions(random);
	}

	/*
	 * Pick colours by making small random changes to the colours of the
	 * "original" parameter.
	 */

	public void reset(MyCanvas original, Random random) {
		
		this.prev.setBlue(artwork.getBlue());
		this.prev.setGreen(artwork.getGreen());
		this.prev.setRed(artwork.getRed());
		original.artwork.variationGenerator(random);
		this.artwork.setRed(original.artwork.getRedVariation());
		this.artwork.setGreen(original.artwork.getGreenVariation());
		this.artwork.setBlue(original.artwork.getBlueVariation());
	}

	/*
	 * Make a copy of the colours of another artwork.
	 */
	public void copy(MyCanvas artworkclicked) {//gets the canvas that was clicked on

		artwork.setRed(artworkclicked.artwork.getRed());
		artwork.setGreen(artworkclicked.artwork.getGreen());
		artwork.setBlue(artworkclicked.artwork.getBlue());

	}

	/*
	 * Paint the artwork on the canvas. This is a very important routine that
	 * you will have to modify extensively.
	 */

	@Override

	public void paint(Graphics g) {

		for (int height = 0; height < SIZE; height++) {
			for (int width = 0; width < SIZE; width++) {

				float red = (float)Math.abs(artwork.red(height*0.008-0.75, width*0.008-0.85));
				float green = (float)Math.abs((artwork.green(height*0.008-0.79,width*0.008-0.88)));
				float blue = (float)Math.abs(artwork.blue(height*0.0080-1, width*0.008-1));
				Color colour;
				if(HSB) colour = Color.getHSBColor(red, green, blue);
				else colour = new Color(red, green, blue);
				g.setColor(colour);

				g.drawLine(height, width, height, width);

			}
		}

	}

	/*
	 * Save this artwork to a JPEG file.
	 */

	public void saveTo(String filename) {
		BufferedImage bi = new BufferedImage(SIZE, SIZE,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		paint(g2);
		g2.dispose();
		try {
			ImageIO.write(bi, "jpg", new File(filename));
		} catch (IOException x) {
			JOptionPane.showMessageDialog(framework, "Problem saving file: "
					+ x.getMessage(), "Save error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
