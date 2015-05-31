package lab2D;

import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * Java2D_07
 * An example using the GeneralPath class to construct complex shapes. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_07 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Seventh Example: GeneralPath";
	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 400;

	private JPanel draw_panel;
	
	/*
	 * Path
	 */
	private GeneralPath path_a = new GeneralPath();
	private Vector<Point2D.Float> points_a = new Vector<Point2D.Float>();
	private GeneralPath path_b = new GeneralPath();
	private Vector<Point2D.Float> points_b = new Vector<Point2D.Float>();

	public Java2D_07(String title) {
		super(title);

		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		

		/*
		 * Initialize the points and set up the first path
		 *
		 * The first path consists of a line, 
		 */
		points_a.add( new Point2D.Float( 100, 100 ));
		points_a.add( new Point2D.Float(  50, 200 ));

		path_a.moveTo( points_a.elementAt(0).x, points_a.elementAt(0).y );
		path_a.lineTo( points_a.elementAt(1).x, points_a.elementAt(1).y );

		/* a quad */
		points_a.add( new Point2D.Float( 110, 110 ));
		points_a.add( new Point2D.Float(  75, 150 ));
		points_a.add( new Point2D.Float( 250, 200 ));

		path_a.moveTo( points_a.elementAt(2).x, points_a.elementAt(2).y );
		path_a.quadTo( points_a.elementAt(3).x, points_a.elementAt(3).y,
					 points_a.elementAt(4).x, points_a.elementAt(4).y );
		path_a.closePath();

		/* a cubic */
		points_a.add( new Point2D.Float( 120, 120 ));
		points_a.add( new Point2D.Float(  75, 150 ));
		points_a.add( new Point2D.Float( 275, 200 ));
		points_a.add( new Point2D.Float( 250, 250 ));

		path_a.moveTo( points_a.elementAt(5).x, points_a.elementAt(5).y );
		path_a.curveTo( points_a.elementAt(6).x, points_a.elementAt(6).y,
					  points_a.elementAt(7).x, points_a.elementAt(7).y,
					  points_a.elementAt(8).x, points_a.elementAt(8).y);
		path_a.closePath();
		
		/* and a complex shape */
		points_a.add( new Point2D.Float( 150,  80 ));
		points_a.add( new Point2D.Float( 175, 150 ));
		points_a.add( new Point2D.Float( 175,  50 ));
		points_a.add( new Point2D.Float( 250,  50 ));
		points_a.add( new Point2D.Float( 350, 200 ));
		points_a.add( new Point2D.Float(  75, 200 ));
		points_a.add( new Point2D.Float(  50,  50 ));
		
		path_a.moveTo( points_a.elementAt(9).x, points_a.elementAt(9).y );
		path_a.curveTo( points_a.elementAt(10).x, points_a.elementAt(10).y,
					  points_a.elementAt(11).x, points_a.elementAt(11).y,
					  points_a.elementAt(12).x, points_a.elementAt(12).y);
		path_a.lineTo( points_a.elementAt(13).x, points_a.elementAt(13).y );
		path_a.quadTo( points_a.elementAt(14).x, points_a.elementAt(14).y,
					 points_a.elementAt(15).x, points_a.elementAt(15).y );
		path_a.closePath();
		
		/*
		 * The second path, path_b consists of one object inside an another
		 * 
		 */
		points_b.add( new Point2D.Float( 350,  50 ));
		points_b.add( new Point2D.Float( 450,  50 ));
		points_b.add( new Point2D.Float( 450, 100 ));
		points_b.add( new Point2D.Float( 350, 100 ));
		
		path_b.moveTo( points_b.elementAt(0).x, points_b.elementAt(0).y );
		path_b.lineTo( points_b.elementAt(1).x, points_b.elementAt(1).y );
		path_b.lineTo( points_b.elementAt(2).x, points_b.elementAt(2).y );
		path_b.lineTo( points_b.elementAt(3).x, points_b.elementAt(3).y );
		path_b.closePath();
		
		points_b.add( new Point2D.Float( 375,  60 ));
		points_b.add( new Point2D.Float( 425,  60 ));
		points_b.add( new Point2D.Float( 425,  90 ));
		points_b.add( new Point2D.Float( 375,  90 ));
		
		path_b.moveTo( points_b.elementAt(4).x, points_b.elementAt(4).y );
		/* Switch the order of the next two lines and rerun the program. */
		path_b.lineTo( points_b.elementAt(5).x, points_b.elementAt(5).y );
		path_b.lineTo( points_b.elementAt(6).x, points_b.elementAt(6).y );
		path_b.lineTo( points_b.elementAt(7).x, points_b.elementAt(7).y );
		path_b.closePath();
		
		/*
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );

		/*
		 * draw_panel is an anonymous class that inherits from the JPanel class
		 */
		draw_panel = new JPanel(){
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
				 * Draw the first path.
				 */				
				g2d.draw( path_a );
				
				/*
				 * Draw the second path as lines
				 */
				g2d.draw( path_b );
				/* Then move over and draw it again with fill using the even-odd rule */
				path_b.setWindingRule( GeneralPath.WIND_EVEN_ODD );
				g2d.translate( 0, 100);
				g2d.fill( path_b );
				g2d.translate( -0, -100);
				
				/* Then move over and draw it again with fill using the non-zero rule*/
				path_b.setWindingRule( GeneralPath.WIND_NON_ZERO );
				g2d.translate( 0, 200);
				g2d.fill( path_b );
				g2d.translate( -0, -200);
				
				

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
		new Java2D_07( TITLE );
	}	
}
