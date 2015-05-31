package lab2B;

import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.Polygon;
import java.awt.Color;



/**
 * Java2D_05 
 * A Simple example using some of the features of Java 2D. 
 * 
 * 
 * For more information, see the following articles:
 * http://java.sun.com/developer/technicalArticles/GUI/java2d/java2dpart1.html
 * 
 * The second part in the series deals with Images:
 * http://java.sun.com/developer/technicalArticles/GUI/java2d/java2dpart2.html
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_05 extends JFrame {
	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private final String TITLE = "Java 2D Tutorial 5: Graphics2D shapes";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	/*
	 * Shapes to be drawn
	 */
	private final int	n_shapes = 14;
	private boolean[]	draw_shapes = new boolean  [ n_shapes ];
	private Paint[]     paints		= new Paint    [ n_shapes ];
	private Rectangle2D.Double[] positions = new Rectangle2D.Double[ n_shapes ];
	private Line2D				line1,	line2;
	private Rectangle2D			box1,	box2;
	private RoundRectangle2D	box3,	box4;
	private Ellipse2D			ellipse, circle;
	private Arc2D				arc1,	arc2;
	private Polygon				polygon1,polygon2;
	private QuadCurve2D			qcurve1, qcurve2;

	
	/**
	 * Constructor for this class
	 */
	public Java2D_05() {
		setTitle( TITLE );
		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );

		/*
		 * Create all the shapes here and draw them in the paint method.
		 * 
		 */
		line1	= new Line2D.Double();
		line2	= new Line2D.Double();
		box1	= new Rectangle2D.Double();
		box2	= new Rectangle2D.Double();
		box3	= new RoundRectangle2D.Double();
		box4	= new RoundRectangle2D.Double();
		ellipse	= new Ellipse2D.Double();
		circle	= new Ellipse2D.Double();
		arc1	= new Arc2D.Double();
		arc2	= new Arc2D.Double();
		polygon1= new Polygon();
		polygon2= new Polygon();
		qcurve1 = new QuadCurve2D.Double();
		qcurve2 = new QuadCurve2D.Double();

		setVisible( true );
		setDefaultCloseOperation( EXIT_ON_CLOSE );		
	}

	public void reset_shapes(){
		/*
		 * Allow all the shapes to be drawn initially
		 */
		for (int i=0; i<draw_shapes.length; i++)
			draw_shapes[i] = true;

		/*
		 * Get the size of the window -- this will change if the window is
		 * resized by the user.
		 * 
		 */
		final int width  = getWidth();
		final int height = getHeight();
		
		/*
		 * Find the x and y coordinates of the center of the window.
		 * 
		 */
		final int centerx = width / 2;
		final int centery = height / 2;
		
		/*
		 * Set up an initial position for a shape such that the shape
		 * will appear in the center and the width and the height are
		 * 1/3rd of the window -- i.e. the same aspect ratio as the window.
		 */
		final double initial_width  = width / 3;
		final double initial_height = height / 3;
		final Rectangle2D.Double initial_position
				= new Rectangle2D.Double( centerx - initial_width/2,
										  centery - initial_height/2,
										  initial_width,
										  initial_height );
		
		/*
		 * Set up random positions
		 */
		for (int i=0; i<positions.length; i++){
			positions[i] = new Rectangle2D.Double(
							initial_position.getX() + (Math.random()-0.5) * initial_width,
							initial_position.getY() + (Math.random()-0.5) * initial_height,
							initial_position.width,
							initial_position.height
							);
		}
		/*
		 * Make position[7] have equal width and height. 
		 */
		final double initial_length = (initial_width > initial_height) ? initial_height : initial_width;
		positions[7] = new Rectangle2D.Double(	initial_position.getX(),
												initial_position.getY(),
												initial_length,
												initial_length );

		
		/*
		 * Set the positions of the various shapes
		 */
		line1.setLine(	positions[0].getX(),
						positions[0].getY(),
						positions[0].getX() + initial_position.getWidth(),
						positions[0].getY() + initial_position.getHeight());
		line2.setLine(	positions[1].getX() + initial_position.getWidth(),
						positions[1].getY(),
						positions[1].getX(),
						positions[1].getY() + initial_position.getHeight());
		box1.setRect(	positions[2] );
		box2.setRect(	positions[3] );
		box3.setFrame(	positions[4] );
		box4.setFrame(	positions[5] );
		ellipse.setFrame( positions[6] );
		circle.setFrame ( positions[7] );
		
		arc1.setFrame(	positions[8] );
		arc1.setAngleStart( 30 );
		arc1.setAngleExtent( 75 );
		arc1.setArcType( Arc2D.PIE );

		arc2.setFrame(	positions[9] );
		arc2.setAngleStart( -45 );
		arc2.setAngleExtent( -90 );
		arc2.setArcType( Arc2D.CHORD );

		/* Polygons
		 * The constant npoints1 sets the number of vertices in
		 * the polygon. 
		 *  3 ==> triangle
		 *  4 ==> quadrangle
		 *  etc.
		 * In the polygon below, the positions of the vertices are 
		 * selected at random.
		 * Try changing the value of npoints1 -- try values of 4, 5, 6, 7, 8 etc. 
		 */
		final int npoints1 = 3;
		final int x1 = (int) positions[10].x;
		final int y1 = (int) positions[10].y;
		final double w1 = positions[10].width;
		final double h1 = positions[10].height;
		/*
		 * 
		 * We need to initialize the polygon object each time or else we will 
		 * end up adding points each time the window is resized/redrawn. 
		 * To see what happens if you keep adding points,
		 *   comment out the next line
		 *   run the program
		 *   and resize the window.
		 */ 
		polygon1 = new Polygon();
		for (int i=0; i<npoints1; i++){
			polygon1.addPoint(	x1 + (int) ( (Math.random()-0.5) * w1 ),
								y1 + (int) ( (Math.random()-0.5) * h1 ) );
		}

		final int npoints2 = 4;
		final int x2 = (int) positions[11].x;
		final int y2 = (int) positions[11].y;
		final double w2 = positions[11].width;
		final double h2 = positions[11].height;
		polygon2 = new Polygon();
		for (int i=0; i<npoints2; i++){
			polygon2.addPoint(	x2 + (int) ( (Math.random()-0.5) * w2 ),
								y2 + (int) ( (Math.random()-0.5) * h2 ) );
		}

		
		/*
		 * The extents of the quad curves are stored in the positions array. 
		 * The control point is then set randomly
		 */
		final double q1x = positions[12].x;
		final double q1y = positions[12].y;
		final double q1w = positions[12].width;
		final double q1h = positions[12].height;
		Point2D p1 = new Point2D.Double( q1x, q1y );
		Point2D cp = new Point2D.Double( q1x + (Math.random()-0.5) * q1w, q1y + (Math.random()-0.5) * q1h );
		Point2D p2 = new Point2D.Double( q1x + q1w, q1y + q1h );
		qcurve1.setCurve(p1, cp, p2);
		
		final double q2x = positions[13].x;
		final double q2y = positions[13].y;
		final double q2w = positions[13].width;
		final double q2h = positions[13].height;
		p1 = new Point2D.Double( q2x, q2y );
		cp = new Point2D.Double( q2x + (Math.random()-0.5) * q2w, q2y + (Math.random()-0.5) * q2h );
		p2 = new Point2D.Double( q2x + q2w, q2y + q2h );
		qcurve2.setCurve(p1, cp, p2);
		
		
		

		
		/*
		 * Set up the colors for each of the objects
		 */
		paints[ 0] = Color.black;
		paints[ 1] = Color.magenta;
		paints[ 2] = Color.green;
		paints[ 3] = Color.orange;
		paints[ 4] = Color.gray;
		paints[ 5] = Color.cyan;
		paints[ 6] = Color.blue;
		paints[ 7] = Color.pink;
		paints[ 8] = Color.darkGray;
		paints[ 9] = Color.red;
		paints[10] = Color.yellow;
		paints[11] = Color.green;
		paints[12] = Color.black;
		paints[13] = Color.magenta;
		
	}
	
	
	
	
	/**
	 * Paint is a method that gets called by the Java run time system. It is
	 * called when the window is first displayed or when it is popped up to
	 * the foreground (for example, if it was behind another window which was
	 * then closed).
	 * 
	 * When we want to draw graphical objects in AWT, this is where we put the
	 * custom graphics code.
	 * 
	 * In general, we never actually call the paint method ourselves. If we
	 * want to force a redraw of the window, we can call the repaint() mehtod.
	 *  
	 */
	public void paint( Graphics g ){
		super.paint(g);
		
		/*
		 * To perform real 2D operations, we have to use the 
		 * actual Graphics2D object that is passed in
		 */
		Graphics2D g2d = (Graphics2D) g;

		reset_shapes();
		
		/*
		 * Render -- draw the objects
		 */
		for (int i=0; i<draw_shapes.length; i++){
			switch (i){
			case 0: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( line1 );
					}
					break;
			case 1: if (draw_shapes[i]){
						g2d.setPaint( (Color) paints[i] );
						g2d.draw( line2 );
					}
					break;
			case 2: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( box1 );
					}
					break;
			case 3: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( box2 );
					}
					break;
			case 4: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( box3 );
					}
					break;
			case 5: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( box4 );
					}
					break;
			case 6: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( ellipse );
					}
					break;
			case 7: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( circle );
					}
					break;
			case 8: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( arc1 );
					}
					break;
			case 9: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.draw( arc2 );
					}
					break;
			case 10: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.fill( polygon1 );
					}
					break;
			case 11: if (draw_shapes[i]){
						g2d.setPaint( paints[i] );
						g2d.fill( polygon2 );
					}
					break;
			case 12: if (draw_shapes[i]){
						/* Draw a circle at the control point */
						g2d.setPaint( Color.black );
						g2d.fillOval( (int) qcurve1.getCtrlX(), (int) qcurve1.getCtrlY(), 4, 4);
						/* Then draw the quad curve. */
						g2d.setPaint( paints[i] );
						g2d.draw( qcurve1 );
					}
					break;
			case 13: if (draw_shapes[i]){
						/* Draw a circle at the control point */
						g2d.setPaint( Color.black );
						g2d.fillOval( (int) qcurve2.getCtrlX(), (int) qcurve2.getCtrlY(), 4, 4);
						/* Then draw the quad curve. */
						g2d.setPaint( paints[i] );
						g2d.fill( qcurve2 );
					}
					break;
			default:
			} // end switch
		} // end for loop
	}
	
	
	/**
	 * main -- Program starts here.
	 *   All we do is create an instance of this class and 
	 *   the Java Abstract Windowing Toolkit takes over
	 *   from there.
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_05();
	}
}
