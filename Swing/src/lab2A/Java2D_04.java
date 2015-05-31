package lab2A;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;


/**
 * Java2D_04 
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
public class Java2D_04 extends JFrame {
	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private final String TITLE = "Java 2D Example with Graphics2D";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;


	
	/**
	 * Constructor for this class
	 */
	public Java2D_04() {
		setTitle( TITLE );
		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		

		setVisible( true );
		setDefaultCloseOperation( EXIT_ON_CLOSE );		
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
		
		/*
		 * Construct a 2-d object
		 */
		Rectangle2D box = new Rectangle2D.Double();
		box.setRect(100, 30, 60, 40);
		
		/*
		 * Transform the object
		 */
		AffineTransform xformer = new AffineTransform();
		double theta = Math.PI / 4; // A 45 degree angle
		xformer.rotate( theta );
		Shape transformedShape1 = xformer.createTransformedShape( box );

		xformer.scale(1.5, 0.7);   // stretch in the x-direction and compress in the y-direction
		Shape transformedShape2 = xformer.createTransformedShape( box );

		xformer.translate(30, 40); // move to the point (50,70) in the x-y plane.
		Shape transformedShape3 = xformer.createTransformedShape( box );
		
		xformer.shear(0.1, 0.15);
		Shape transformedShape4 = xformer.createTransformedShape( box );
		
		/*
		 * Render -- draw the transformed object
		 */
		g2d.draw( box );
		
		g2d.setColor( Color.gray );
		g2d.draw( transformedShape1 );
		
		g2d.setColor( Color.green );
		g2d.draw( transformedShape2 );
		
		g2d.setColor( Color.magenta );
		g2d.draw( transformedShape3 );
		
		g2d.setColor( Color.magenta );
		g2d.fill( transformedShape4 );
		
	}
	
	
	/**
	 * main -- Program starts here.
	 *   All we do is create an instance of this class and 
	 *   the Java Abstract Windowing Toolkit takes over
	 *   from there.
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_04();
	}
}
