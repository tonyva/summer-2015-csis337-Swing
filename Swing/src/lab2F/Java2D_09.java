package lab2F;

import javax.swing.JFrame;

import java.awt.GradientPaint;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Java2D_09
 * An example using GradientPaint objects. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_09 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Ninth Example: GradientPaint";
	private final int FRAME_WIDTH  = 600;
	private final int FRAME_HEIGHT = 300;

	

	/**
	 * class SliderPanel
	 * The Frame will contain sliders in a separate panel
	 */
	private class SliderPanel extends JPanel implements ChangeListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int COLORMIN = 0, COLORMAX = 255, COLORINTERVAL = 128; /*, COLOR_INITIAL = 0; */
		private JSlider redSlide, greenSlide, blueSlide;
		/*
		 * Color settings from the sliders
		 * As the user changes the sliders, the 3 values will be used to set the 
		 * three color components of the following Color object.
		 */
		private Color setting;
		
		
		public SliderPanel( Color start) {
			setting = new Color( start.getRed(), start.getGreen(), start.getBlue() );
			
			/*
			 * Set up 3 sliders, one each for red, green, and blue values.
			 */
			redSlide   = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, setting.getRed() );
			greenSlide = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, setting.getGreen() );
			blueSlide  = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, setting.getBlue() );
			blueSlide.setMajorTickSpacing(COLORINTERVAL);
			blueSlide.setPaintTicks(true);
			blueSlide.setPaintLabels(true);
			
			redSlide.addChangeListener( this );
			greenSlide.addChangeListener( this );
			blueSlide.addChangeListener( this );
			
			this.setLayout( new GridLayout(2,3));
			add( new JLabel("R") );
			add( new JLabel("G") );
			add( new JLabel("B") );
			add( redSlide );
			add( greenSlide );
			add( blueSlide );

		}
		
		
		/*
		 * When a slider is moved, this gets called.
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			
			int value = source.getValue();
			
			if (source == redSlide)
				setting = new Color( value, setting.getGreen(), setting.getBlue());				

			if (source == greenSlide)
				setting = new Color( setting.getRed(), value, setting.getBlue());				

			if (source == blueSlide)
				setting = new Color( setting.getRed(), setting.getGreen(), value);				

			getParent().repaint();			
		}
		public Color getSetting(){
			return new Color( setting.getRed(), setting.getGreen(), setting.getBlue() );
		}
	}
	
	
	
	private SliderPanel rightColors, leftColors;
	private JPanel draw_panel;

	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 4;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];

	
	public Java2D_09(String title) {
		super(title);

		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		

		/**
		 * Initialize the shapes
		 */
		myshapes[0] = new Ellipse2D.Double();
		shape_positions[0][0] =  25;
		shape_positions[0][1] = 125;	
		shape_sizes[0][0] = 80;
		shape_sizes[0][1] = 150;
		
		myshapes[1] = new Polygon();
		shape_positions[1][0] = 65;
		shape_positions[1][1] = 25;
		shape_sizes[1][0] = 80;
		shape_sizes[1][1] = 100;
		
		myshapes[2] = new Ellipse2D.Float();
		shape_positions[2][0] = 130;
		shape_positions[2][1] = 170;
		shape_sizes[2][0] = 70;
		shape_sizes[2][1] = 70;
		
		myshapes[3] = new Polygon();
		shape_positions[3][0] = 150;
		shape_positions[3][1] = 120;
		shape_sizes[3][0] = 80;
		shape_sizes[3][1] = 80;
		
		((Ellipse2D) myshapes[0]).setFrame( shape_positions[0][0], shape_positions[0][1],
											 shape_sizes    [0][0],     shape_sizes[0][1] );
		
		((Polygon) myshapes[1]).addPoint( (int) shape_positions[1][0], (int) shape_positions[1][1] );
		((Polygon) myshapes[1]).addPoint( (int) (shape_positions[1][0] + shape_sizes[1][0]),
										  (int) (shape_positions[1][1] + shape_sizes[1][1]) );
		((Polygon) myshapes[1]).addPoint( (int) (shape_positions[1][0] + shape_sizes[1][0]),
										  (int) shape_positions[1][1] );

		((Ellipse2D) myshapes[2]).setFrame( shape_positions[2][0], shape_positions[2][1],
											shape_sizes    [2][0], shape_sizes    [2][1] );

		((Polygon) myshapes[3]).addPoint( (int) shape_positions[3][0], (int) shape_positions[3][1] );
		((Polygon) myshapes[3]).addPoint( (int) (shape_positions[3][0] + shape_sizes[3][0]),
										  (int) (shape_positions[3][1] + shape_sizes[3][1]) );
		((Polygon) myshapes[3]).addPoint( (int) (shape_positions[3][0] + shape_sizes[3][0]),
										  (int) shape_positions[3][1] );

		
		/**
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );

		/*
		 * Sliders on the West side
		 */
		rightColors = new SliderPanel( new Color(0, 255, 0) );
		add( rightColors, BorderLayout.WEST );
		leftColors = new SliderPanel( new Color(255, 0, 0) );
		add( leftColors, BorderLayout.EAST );

		
		/*
		 *
		 * Although we could set up a new Panel class as we did with the Java2D_03_Panel
		 * class, here we set up what is called an "anonymous" class that inherits from 
		 * JPanel and extends it by redefining the paintComponent class. We do this when
		 * we only need one instance of a class or if we only need to redefine a small
		 * part of the inherited class.
		 * 
		 */
		draw_panel = new JPanel(){
			/**
			 * Data members
			 */
			private static final long serialVersionUID = 1L;

			/*
			 * In an AWT program we would redefine the paint() method. In Swing, what is
			 * preferred is that we redefine just the paintComponent() method if all we want
			 * to do is to draw our custom graphics.
			 * 
			 * In this case, we can see how the getStatus() method can be used to get
			 * information about which buttons in the panel component were clicked by
			 * the user.
			 * 
			 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
			 */
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
				Graphics2D g2d = (Graphics2D) g;
				/*
				 * Draw all the shapes first.
				 */
				Color c1 = leftColors.getSetting();
				Color c2 = rightColors.getSetting();

				Point2D start = new Point2D.Double( myshapes[0].getBounds().getMinX(), 
											 		myshapes[0].getBounds().getMinY() );
				Point2D end   = new Point2D.Double( myshapes[0].getBounds().getMaxX(),
											 		myshapes[0].getBounds().getMaxY() );
				GradientPaint grade0 = new GradientPaint( start, c1, end, c2 );
				g2d.setPaint( grade0 );
				g2d.fill( myshapes[0] );

				start = new Point2D.Double( myshapes[1].getBounds().getMinX(), 
									 		myshapes[1].getBounds().getMinY() );
				end   = new Point2D.Double( myshapes[1].getBounds().getCenterX(),
						 			 		myshapes[1].getBounds().getCenterY() );
				GradientPaint grade1 = new GradientPaint( start, c1, end, c2, true );
				g2d.setPaint( grade1 );
				g2d.fill( myshapes[1] );

				start = new Point2D.Double( myshapes[2].getBounds().getCenterX(), 
									 		myshapes[2].getBounds().getCenterY() );
				float radius = (float) myshapes[2].getBounds().getWidth() / 2;
				end   = new Point2D.Double( myshapes[2].getBounds().getCenterX(),
									 		myshapes[2].getBounds().getCenterY() );
				float[] fractions = {0.0f, 1.0f}; 
				Color[] colors = { c1, c2 };
				RadialGradientPaint grade2 = new RadialGradientPaint( start, radius, fractions, colors );
				g2d.setPaint( grade2 );
				g2d.fill( myshapes[2] );

				start = new Point2D.Double( myshapes[3].getBounds().getCenterX(), 
											myshapes[3].getBounds().getMinY() );
				end   = new Point2D.Double( myshapes[3].getBounds().getCenterX(),
											myshapes[3].getBounds().getMaxY() );
				GradientPaint grade3 = new GradientPaint( start, c1, end, c2 );
				g2d.setPaint( grade3 );
				g2d.fill( myshapes[3] );
			}
		};
		draw_panel.setBorder( new TitledBorder( new EtchedBorder(), "Draw Area"));
		add( draw_panel, BorderLayout.CENTER );

		
		/*
		 * Make the program quit when the window is closed
		 */
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent ev){
				System.exit( 0 );
			}
		});

		setVisible(true);
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	}

	/**
	 * Main method where the program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_09( TITLE );
	}
}
