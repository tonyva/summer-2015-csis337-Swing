package lab3F;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Java2D_17
 * An example using animation 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_17 extends javax.swing.JFrame {
	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Seventeenth Example: Responsive animation";
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

	/**
	 * 
	 * The Java2D_17.DrawPanel class implements ActionListener so that 
	 * the panel is triggered by a Swing Timer
	 * 
	 */
	class DrawPanel extends JPanel implements ActionListener {
		/**
		 * Data members
		 */
		private static final long serialVersionUID = 1L;
		private AffineTransform move = AffineTransform.getTranslateInstance( 1 , 1 );
		private Shape moved = move.createTransformedShape( myshapes[0] );
		private final int SLEEP_INTERVAL = 100 /* milliseconds */;
		private Timer timer;
		
		public DrawPanel(){
			setBackground(Color.ORANGE);
			setBorder(new TitledBorder(new EtchedBorder(), "Draw Area"));
			timer = new Timer( SLEEP_INTERVAL, this);
			timer.start();
		}

		/**
		 * Rendering is performed here
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			/*
			 * Draw all the shapes first.
			 */

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

		public void actionPerformed(ActionEvent e) {
			/*
			 * Translate but first check if the ellipse has gone off the deep end.
			 */
			int bottom_of_ellipse = moved.getBounds().y + moved.getBounds().height;
			if (bottom_of_ellipse > FRAME_HEIGHT - 30)
				move.setToTranslation( 1, 0);
			moved = move.createTransformedShape( moved );
			repaint();
		} // end actionPerformed
	}// end DrawPanel class
	
	
	/**
	 * More data members
	 */
	private SliderPanel slider1;
	private SliderPanel slider2;
	private DrawPanel draw_panel;
//	private Thread thread;
	
	
	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 2;
	private Shape[] myshapes = new Shape[number_shapes];
	private double[][] shape_positions = new double[number_shapes][2];
	private double[][] shape_sizes = new double[number_shapes][2];
	private Paint[] shape_colors = new Color[number_shapes];

	public Java2D_17() {
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

		draw_panel = new DrawPanel();

		add(draw_panel, BorderLayout.CENTER);

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
		new Java2D_17();
	}
}
