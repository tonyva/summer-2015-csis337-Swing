package lab3I;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Java2D_20
 * An example using animation 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_20 extends javax.swing.JFrame {
	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Twentieth Example: Responsive animation";
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
	 * DrawPanel implements Runnable so that the panel gets features of multithreading.
	 * 
	 */
	class DrawPanel extends JPanel implements Runnable {
		/**
		 * Data members
		 */
		private static final long serialVersionUID = 1L;
		private final Color BACKGROUND = Color.ORANGE;
		private AffineTransform move = AffineTransform.getTranslateInstance( 1 , 1 );
		private Shape moved = move.createTransformedShape( myshapes[0] );
		private final int SLEEP_INTERVAL = 100 /* milliseconds */;
		private boolean keep_going = true;
		private java.awt.Graphics2D graphics_context;
		private java.awt.Image    graphics_buffer;

		public DrawPanel(){
			setBackground( BACKGROUND );
			setBorder(new TitledBorder(new EtchedBorder(), "Draw Area"));
		}

		/**
		 * Displaying is performed here
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			/*
			 * graphics_buffer is created in the run method which
			 * may not have been called yet before the first time
			 * that paintComponent was called.
			 * 
			 */
			if ( graphics_buffer != null )
				g.drawImage(graphics_buffer, 0, 0, null);
		}

		/**
		 * Program-scheduled paint method
		 * 
		 * Instead of having Swing decide when to paint the panel, we will call
		 * panel directly. Since Swing does not call this method, we don't need
		 * to worry about the kinds of problems we would run into if were to 
		 * call the paint() method directly.
		 *
		 */
		private void paintPanel(){
			/*
			 * This should not happen but just in case ...
			 */
			if ( graphics_buffer == null )
				return;
			/*
			 * Draw to the window/screen
			 */
			java.awt.Graphics2D g = (java.awt.Graphics2D) getGraphics();
			if (g != null)
				g.drawImage(graphics_buffer, 0, 0, null);
				
			/*
			 * Sync display
			 */
			java.awt.Toolkit.getDefaultToolkit().sync();
			
		}
		/**
		 * run -- where all the work is done
		 * 
		 * Note: Rendering is performed here
		 */
		public void run() {
			AlphaComposite a0, a1;
			final AlphaComposite a_win = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
			GradientPaint grade;
			long start_time, end_time;
			int sleep_time;
			
			/*
			 * Set up the drawing area
			 */
			int w = getWidth();
			int h = getHeight();
			graphics_buffer = createImage( w, h );
			if (graphics_buffer != null)
				graphics_context = (java.awt.Graphics2D) graphics_buffer.getGraphics();

			/*
			 * The compute - render - display loop
			 */
			while (keep_going) {
				start_time = System.currentTimeMillis();

				/*
				 * Computations:
				 * 
				 * Translate but first check if the ellipse has gone off the
				 * deep end.
				 */
				int bottom_of_ellipse = moved.getBounds().y
						+ moved.getBounds().height;
				if (bottom_of_ellipse > FRAME_HEIGHT - 30)
					move.setToTranslation(1, 0);
				moved = move.createTransformedShape(moved);

				/*
				 * Render:
				 * 
				 * Set object color characteristics
				 */
				Point2D start = new Point2D.Double(myshapes[0].getBounds()
						.getCenterX(), myshapes[0].getBounds().getMinY());
				Point2D end = new Point2D.Double(myshapes[0].getBounds()
						.getCenterX(), myshapes[0].getBounds().getMaxY());
				grade = new GradientPaint(start, Color.RED,	end, Color.BLUE);

				float alpha0 = slider1.getAlphaValue();
				a0 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha0);

				float alpha1 = slider2.getAlphaValue();
				a1 = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha1);

				/*
				 * Render shapes.
				 */
				graphics_context.setColor( BACKGROUND );
				graphics_context.fillRect( 0, 0, w, h );
				graphics_context.setComposite( a0 );
				graphics_context.setPaint( grade );
				graphics_context.fill( moved );

				graphics_context.setComposite( a1 );
				graphics_context.setPaint( shape_colors[1] );
				graphics_context.fill( myshapes[1] );

				/*
				 * Reset alpha value for the drawing of the window.
				 */
				graphics_context.setComposite( a_win );
				
				
				/*
				 * Draw:
				 */
				paintPanel();

				end_time = System.currentTimeMillis();
				
				
				if (end_time - start_time > SLEEP_INTERVAL)
					sleep_time = 0;
				else {
					sleep_time = (int) (end_time - start_time);
					try {
						Thread.sleep( sleep_time );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// end catch
				} // end if
			}// end loop
		} // end run
		
		/**
		 * This method should be called to stop the animation.
		 *
		 */
		public void stopPanel() {
			keep_going = false;
		}
	} // end DrawPanel class
	
	
	/**
	 * More data members
	 */
	private SliderPanel slider1;
	private SliderPanel slider2;
	private DrawPanel draw_panel;
	private Thread thread;
	
	
	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 2;
	private Shape[] myshapes = new Shape[number_shapes];
	private double[][] shape_positions = new double[number_shapes][2];
	private double[][] shape_sizes = new double[number_shapes][2];
	private Paint[] shape_colors = new Color[number_shapes];

	public Java2D_20() {
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

		thread = new Thread( draw_panel );
		thread.start();

	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_20();
	}
}
