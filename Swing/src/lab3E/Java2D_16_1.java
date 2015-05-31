package lab3E;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Java2D_16_1
 * Another example using animation 
 *   This version uses an anonymous class that only inherits JPanel
 * @author Anthony Varghese
 *
 */
public class Java2D_16_1 extends JFrame {
	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Inanimate animation";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	/*
	 * The Frame will contain sliders in a separate panel
	 */
	private class SliderPanel extends JPanel implements ChangeListener {
		/**
		 * Data members
		 */
		private static final long serialVersionUID = 1L;
		private final int ALPHAMIN = 0, ALPHAMAX = 255, ALPHAINTERVAL = 128, ALPHA_INITIAL = 100;
		private JSlider alphaSlide;
		private float alphavalue = 0.5f;

		public SliderPanel() {
			/*
			 * Set up 3 sliders, one each for red, green, and blue values.
			 */
			alphaSlide = new JSlider(JSlider.VERTICAL, ALPHAMIN, ALPHAMAX, ALPHA_INITIAL);
			alphaSlide.setMajorTickSpacing(ALPHAINTERVAL);
			alphaSlide.setPaintTicks(true);
			alphaSlide.setPaintLabels(true);

			alphaSlide.addChangeListener(this);

			this.setLayout(new GridLayout(2, 1));
			add(new JLabel("Alpha"));
			add(alphaSlide);
		}

		/*
		 * When a slider is moved, this gets called.
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();

			alphavalue = (float) source.getValue() / ALPHAMAX;

			getParent().repaint();
		}

		public float getAlphaValue() {
			return alphavalue;
		}
	}

	private SliderPanel slider1;
	private SliderPanel slider2;
	private JPanel draw_panel;

	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 2;
	private Shape[] myshapes = new Shape[number_shapes];
	private double[][] shape_positions = new double[number_shapes][2];
	private double[][] shape_sizes = new double[number_shapes][2];
	private Paint[] shape_colors = new Color[number_shapes];

	public Java2D_16_1() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(TITLE);
		setBackground(Color.white);

		/**
		 * Initialize the shapes
		 */
		myshapes[0] = new Ellipse2D.Double();
		shape_positions[0][0] = 50;
		shape_positions[0][1] = 50;
		shape_sizes[0][0] = 100;
		shape_sizes[0][1] = 150;
		shape_colors[0] = Color.RED;

		myshapes[1] = new Polygon();
		shape_positions[1][0] = 100;
		shape_positions[1][1] = 75;
		shape_sizes[1][0] = 80;
		shape_sizes[1][1] = 80;
		shape_colors[1] = Color.GREEN;

		((Ellipse2D) myshapes[0]).setFrame(shape_positions[0][0],
				shape_positions[0][1], shape_sizes[0][0], shape_sizes[0][1]);

		((Polygon) myshapes[1]).addPoint((int) shape_positions[1][0],
				(int) shape_positions[1][1]);
		((Polygon) myshapes[1]).addPoint(
				(int) (shape_positions[1][0] + shape_sizes[1][0]),
				(int) (shape_positions[1][1] + shape_sizes[1][1]));
		((Polygon) myshapes[1]).addPoint(
				(int) (shape_positions[1][0] + shape_sizes[1][0]),
				(int) shape_positions[1][1]);

		/**
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout(layout);

		/*
		 * Sliders on the West and east sides
		 */
		slider1 = new SliderPanel();
		add(slider1, BorderLayout.WEST);
		slider2 = new SliderPanel();
		add(slider2, BorderLayout.EAST);

		/*
		 * 
		 * Although we could set up a new Panel class as we did with the
		 * Java2D_03_Panel class, here we set up what is called an "anonymous"
		 * class that inherits from JPanel and extends it by redefining the
		 * paintComponent class. We do this when we only need one instance of a
		 * class or if we only need to redefine a small part of the inherited
		 * class.
		 * 
		 */
		draw_panel = new JPanel() {
			/**
			 * Data members
			 */
			private static final long serialVersionUID = 1L;

			/*
			 * In an AWT program we would redefine the paint() method. In Swing,
			 * what is preferred is that we redefine just the paintComponent()
			 * method if all we want to do is to draw our custom graphics.
			 * 
			 * In this case, we can see how the getStatus() method can be used
			 * to get information about which buttons in the panel component
			 * were clicked by the user.
			 * 
			 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
			 */
			public void paintComponent(Graphics g) {

				Graphics2D g2d = (Graphics2D) g;
				/*
				 * Draw all the shapes first.
				 */
				AffineTransform move = AffineTransform.getTranslateInstance( 1 , 1 );
				Shape moved = move.createTransformedShape( myshapes[0] );

				/*
				 * Increase th iterations to make this program less responsive
				 */
				final int ITERATIONS = 50;
				for (int i = 0; i < ITERATIONS; i++) {
					super.paintComponent(g);
					System.out.println(" paint iteration " + i);
					float alpha0 = slider1.getAlphaValue();
					Point2D start = new Point2D.Double(myshapes[0].getBounds()
							.getCenterX(), myshapes[0].getBounds().getMinY());
					Point2D end = new Point2D.Double(myshapes[0].getBounds()
							.getCenterX(), myshapes[0].getBounds().getMaxY());
					GradientPaint grade = new GradientPaint(start, Color.RED,
							end, Color.BLUE);
					AlphaComposite a0 = AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, alpha0);

					g2d.setComposite(a0);
					g2d.setPaint(grade);
					
					/*
					 * Translate the first shape
					 */
					moved = move.createTransformedShape( moved );
					g2d.fill( moved );

					float alpha1 = slider2.getAlphaValue();

					a0 = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
							alpha1);
					g2d.setComposite(a0);
					g2d.setPaint(shape_colors[1]);
					g2d.fill(myshapes[1]);

					/*
					 * Reset alpha value for the drawing of the window. Comment
					 * the next two lines and re-run the program to see what
					 * happens.
					 */
					a0 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							1.0f);
					g2d.setComposite(a0);
				}

			}
		};
		draw_panel.setBackground(Color.ORANGE);
		draw_panel.setBorder(new TitledBorder(new EtchedBorder(), "Draw Area"));
		add(draw_panel, BorderLayout.CENTER);
		draw_panel.repaint();
		
		/*
		 * Make the program quit when the window is closed
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_16_1();
	}
}
