import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//@author Anathi Swaphi

public class Framework extends JFrame implements ActionListener {

	/*
	 * We have to add this here to make Java happy.
	 */

	private static final long serialVersionUID = 2180515451827694740L;

	/*
	 * The main panel that holds the entire application.
	 */

	private JPanel mainPanel;

	/*
	 * The subpanel of the main panel for holding the 2x2 canvases.
	 */
	private JPanel gridPanel;

	/*
	 * The subpanel of the main panel (at the bottom of the main panel) for
	 * holding my buttons.
	 */

	private JPanel buttonPanel;

	/*
	 * The button to press to save a canvas.
	 */

	private JButton saveButton;

	/*
	 * The button to press to quit the application.
	 */

	private JButton quitButton;

/* 
 * The button to press when switching between RGB and HSV.
 */
	private JButton switchToHSBButton;
	private JButton switchToRGBButton;

	/*
	 * The button for viewing the previous artwork.
	 */
	
	private JButton prevArtworkButton;
	
	/*
	 * A file chooser dialog. We create it here -- once -- and reuse it every
	 * time we save the file. The main reason is that file choosers are quite
	 * slow to create.
	 */

	private final JFileChooser fileChooser = new JFileChooser();

	/*
	 * An array that hold the actual canvases. In this case, the top left
	 * (canvas[0]) is the "original".
	 */

	private MyCanvas[] canvas = new MyCanvas[9];

	/*
	 * The one, single random number generator used in the program.
	 */

	private Random random = new Random(123);

	/*
	 * Construct a Framework instance. This mainly calls the "initializeApp"
	 * method to set up the GUI.
	 */

	public Framework() {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initializeApp();
	}

	/*
	 * Initialize the application by creating the panels, buttons, and -- most
	 * importantly -- the canvases. At the end we invoke some Swing routines to
	 * make everything "active".
	 */

	private void initializeApp() {

		buttonPanel = new JPanel();

		prevArtworkButton = new JButton("Previous");
		prevArtworkButton.addActionListener(this);
		prevArtworkButton.setActionCommand("Previous");
		buttonPanel.add(prevArtworkButton);
		
		
		saveButton = new JButton("Save image");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");
		buttonPanel.add(saveButton);
		
		switchToHSBButton = new JButton("switchToHSB");
		switchToHSBButton.addActionListener(this);
		switchToHSBButton.setActionCommand("switchToHSB");
		buttonPanel.add(switchToHSBButton);
		
		switchToRGBButton = new JButton("switchToRGB");
		switchToRGBButton.addActionListener(this);
		switchToRGBButton.setActionCommand("switchToRGB");
		buttonPanel.add(switchToRGBButton);
		

		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		quitButton.setActionCommand("quit");
		buttonPanel.add(quitButton);

		canvas[4] = new MyCanvas(4, this, random);

		for (int i = 0; i < 9; i++) {

			if(i !=  4){

				canvas[i] = new MyCanvas(i, this, canvas[4], random);	

			}


		}

		gridPanel = new JPanel(new GridLayout(3, 3, 7, 7));

		for (int i = 0; i < 9; i++) {

			gridPanel.add(canvas[i]);
		}

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(gridPanel, BorderLayout.CENTER);
		setContentPane(mainPanel);

		pack();
		setLocationRelativeTo(null);
	}

	/*
	 * The actual main routine that Java executes when we run this class.
	 * Essentially, it creates an instance of this class and then asks Swing to
	 * "invoke it later".
	 */

	public static void main(final String args[]) {

		final Framework fart = new Framework();
		SwingUtilities.invokeLater(new Runnable() {

			@Override

			public void run() {
				fart.setVisible(true);
			}
		});
	}

	/*
	 * React to an "action" (for example, when the user presses a button).
	 */

	@Override

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("save")) {
			saveImage();
		} else if (e.getActionCommand().equals("quit")) {
			quit();
		} else if (e.getActionCommand().equals("switchToRGB")) {
			switchToRGB();
		} else if (e.getActionCommand().equals("switchToHSB")) {
			switchToHSBButton();
		} else if (e.getActionCommand().equals("Previous")) {
			prevArtWorkButton();
		}
		
	}

	/*
	 * Save the current "original" canvas (which is canvas[0]).
	 */

	private void prevArtWorkButton() {
		
		for(int i = 0; i < 9; i++) {
			canvas[i].setToPrev();
		}
		update();
	}

	private void switchToHSBButton() {
		
		for(int i = 0; i < 9; i++) {
			canvas[i].setHSB(true);
		}
		update();
	}

	private void switchToRGB() {
	
		for(int i = 0; i < 9; i++) {
			canvas[i].setHSB(false);
		}
		update();
	}

	private void saveImage() {

		int r = fileChooser.showSaveDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			canvas[4].saveTo(file.getAbsolutePath());
		}
	}

	/*
	 * Quit the application.
	 */

	private void quit() {

		dispose();
	}

	/*
	 * React to the user clicking on some canvas.
	 */

	public void clicked(int id) {

		canvas[4].copy(canvas[id]);

		for (int i = 0; i < 9; i++) {

			if(i != 4){
				canvas[i].reset(canvas[4], random);	
			}

		}
		update();
	}

	/*
	 * One or more of the canvases has changed: ask Swing to repaint all of the
	 * canvases.
	 */

	public void update() {

		for (int i = 0; i < 9; i++) {

			canvas[i].repaint();
		}
	}

}
