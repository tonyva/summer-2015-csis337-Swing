package lab3B;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
 * Java2D_13
 * An example showing order of AffineTransforms. 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_13 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Thirteenth Example: Affine Transform order";
	private final int FRAME_WIDTH  = 500;
	private final int FRAME_HEIGHT = 300;

	
	private JPanel draw_panel;
	
	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 1;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];

	/*
	 * Image for texture
	 */
	private static final String DIR = "src/lab3A/";
	private BufferedImage image;

	
	public Java2D_13(String title) {
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
		
		
		((Ellipse2D) myshapes[0]).setFrame( shape_positions[0][0], shape_positions[0][1],
											 shape_sizes    [0][0],     shape_sizes[0][1] );
		
		/**
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );


		/*
		 * Texture image
		 */
		{
			final String filename = DIR + "image10.jpg";

			File file = new File( filename );
			try {
				image = ImageIO.read( file );
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

				Point2D start = new Point2D.Double( myshapes[0].getBounds().getMinX(), 
				 		myshapes[0].getBounds().getMinY() );

				TexturePaint grade0 = new TexturePaint( image, 
							new Rectangle2D.Double( start.getX(), start.getY(), 
											image.getWidth(), image.getHeight() ) );
				g2d.setPaint( grade0 );
				g2d.fill( myshapes[0] );

				/*
				 * The chosen transformation will be applied and the
				 * rest of the operations in this method will be affected by the transform.
				 */
				final int TRANSLATE_X = 60;
				final int TRANSLATE_Y = 50;
				final double ROTATE_ANGLE = -Math.PI/6;
//				final double SCALE_X =  1.2;
//				final double SCALE_Y =  0.75;
//				final double SHEAR_X =  0.3;
//				final double SHEAR_Y = -0.1;

				
				// 1. Translate and then rotate
				System.out.println("First");
				g2d.translate(TRANSLATE_X, TRANSLATE_Y);
				System.out.println("   Translate");
				g2d.rotate( ROTATE_ANGLE );
				System.out.println("   Rotate");

				// Draw ellipse
				g2d.fill( myshapes[0] );

				/*
				 * Perform the revese transforms
				 */
				g2d.rotate( -ROTATE_ANGLE );
				g2d.translate(-TRANSLATE_X, -TRANSLATE_Y);

				
				
				
				// 2. Rotate and then translate
				System.out.println("Second");
				g2d.rotate( ROTATE_ANGLE );
				System.out.println("   Rotate");
				g2d.translate(TRANSLATE_X, TRANSLATE_Y);
				System.out.println("   Translate");


				g2d.fill( myshapes[0] );

				/*
				 * Perform the revese transforms
				 */
				g2d.translate(-TRANSLATE_X, -TRANSLATE_Y);
				g2d.rotate( -ROTATE_ANGLE );

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
	 * paint 
	 */
	public void paint(Graphics g){
		super.paint(g);
	}



	/**
	 * Main method where the program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_13( TITLE );
	}
}
