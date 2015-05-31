package lab2G;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * Java2D_10
 * An example using TexturePaint objects. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_10 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Tenth Example: TexturePaint";
	private final int FRAME_WIDTH  = 500;
	private final int FRAME_HEIGHT = 300;

	
	private JPanel draw_panel;

	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 4;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];

	private Color rightColor, leftColor;
	
	/*
	 * Image for texture
	 */
	private static final String DIR = "src/lab2G/";
	private BufferedImage image1, image2, image3;

	
	public Java2D_10(String title) {
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
		shape_positions[1][0] = 25;
		shape_positions[1][1] = 25;
		shape_sizes[1][0] = 120;
		shape_sizes[1][1] = 100;
		
		myshapes[2] = new Ellipse2D.Float();
		shape_positions[2][0] = 130;
		shape_positions[2][1] = 170;
		shape_sizes[2][0] = 70;
		shape_sizes[2][1] = 70;
		
		myshapes[3] = new Polygon();
		shape_positions[3][0] = 145;
		shape_positions[3][1] =  25;
		shape_sizes[3][0] = 250;
		shape_sizes[3][1] = 250;
		
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
		((Polygon) myshapes[3]).addPoint( (int) (shape_positions[3][0] + shape_sizes[3][0]*0.5),
										  (int) (shape_positions[3][1] + shape_sizes[3][1]*0.8) );
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
		rightColor = new Color(0, 255, 0);
		leftColor  = new Color(255, 0, 0);

		/*
		 * Texture image
		 */
		{
			final String filename = DIR + "/image10.jpg";

			File file = new File( filename );
			try {
				image1 = ImageIO.read( file );
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Not able to read image file: " + filename +". Is it in the right place?");
				System.err.println("   It should be in the Java project directory. ");
				System.err.println("   If you have src and bin directories, it should be in the same directory that contains src and bin");
			}
		}
		{
			final String filename = DIR + "image11.jpg";

			File file = new File( filename );
			try {
				image2 = ImageIO.read( file );
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Not able to read image file: " + filename +". Is it in the right place?");
				System.err.println("   It should be in the Java project directory. ");
				System.err.println("   If you have src and bin directories, it should be in the same directory that contains src and bin");
			}
		}
		{
			final String filename = DIR + "greenery.jpg";

			File file = new File( filename );
			try {
				image3 = ImageIO.read( file );
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Not able to read image file: " + filename +". Is it in the right place?");
				System.err.println("   It should be in the Java project directory. ");
				System.err.println("   If you have src and bin directories, it should be in the same directory that contains src and bin");
			}
		}

		
		
		
		/*
		 * Although we could set up a new Panel class as we did with the Java2D_03_Panel
		 * class, here we set up what is called an "anonymous" class that inherits from 
		 * JPanel and extends it by redefining the paintComponent class. We do this when
		 * we only need one instance of a class or if we only need to redefine a small
		 * part of the inherited class.
		 * 
		 */
		draw_panel = new JPanel(){
			/**
			 * Data member
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
				Color c1 = leftColor;
				Color c2 = rightColor;

				Point2D start = new Point2D.Double( myshapes[0].getBounds().getMinX(), 
											 		myshapes[0].getBounds().getMinY() );
				/*
				 * For debugging:
				 * 	if (image != null)
				 *		g2d.drawImage(image, 20, 20, null );
				 */
				TexturePaint grade0 = new TexturePaint( image1, 
							new Rectangle2D.Double( start.getX(), start.getY(), 
									image1.getWidth(), image1.getHeight() ) );
				g2d.setPaint( grade0 );
				g2d.fill( myshapes[0] );

				start = new Point2D.Double( myshapes[1].getBounds().getMinX(), 
									 		myshapes[1].getBounds().getMinY() );
				TexturePaint grade1 = new TexturePaint( image2, 
						new Rectangle2D.Double( start.getX(), start.getY(), 
								image2.getWidth(), image2.getHeight() ) );
				g2d.setPaint( grade1 );
				g2d.fill( myshapes[1] );

				start = new Point2D.Double( myshapes[2].getBounds().getCenterX(), 
									 		myshapes[2].getBounds().getCenterY() );
				float radius = (float) myshapes[2].getBounds().getWidth() / 2;
//				Point2D end   = new Point2D.Double( myshapes[2].getBounds().getCenterX(),
//									 		myshapes[2].getBounds().getCenterY() );
				float[] fractions = {0.0f, 1.0f}; 
				Color[] colors = { c1, c2 };
				RadialGradientPaint grade2 = new RadialGradientPaint( start, radius, fractions, colors );
				g2d.setPaint( grade2 );
				g2d.fill( myshapes[2] );

				start = new Point2D.Double( myshapes[3].getBounds().getCenterX(), 
											myshapes[3].getBounds().getMinY() );
				TexturePaint grade3 = new TexturePaint( image3, 
						new Rectangle2D.Double( start.getX(), start.getY(), 
								image3.getWidth(), image3.getHeight() ) );
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
		new Java2D_10( TITLE );
	}
}
