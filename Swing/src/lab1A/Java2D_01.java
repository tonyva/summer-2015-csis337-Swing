package lab1A;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;

/**
 * Java2D_01 
 * A Simple example using some of the features of Java 2D. 
 * To keep things simple we use AWT (Abstract Windowing Toolkit) rather
 * than Swing,
 * 
 * For more information, see the following articles:
 * http://www.oracle.com/technetwork/articles/javase/java2dpart1-137217.html
 * 
 * The second part in the series deals with Images:
 * http://www.oracle.com/technetwork/articles/javase/java2dpart2-139741.html
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_01 extends Frame implements ActionListener, MouseListener {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private final String TITLE = "Simple Java 2D Example";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	private MenuBar menubar;
	private Menu fileMenu;
	private final String fileMenuString = "File";
	private MenuItem[] fileMenuItems;
	private final String[] fileMenuItemStrings = {"Open" , "Exit" };

	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 5;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];
	private Color[]    shape_colors = new Color[ number_shapes ];
	private GradientPaint paintjob;
	/*
	 * Use Alpha blending for transparency.
	 */
	private AlphaComposite alpha;
	private final float alpha_factor = 0.3f;
	/*
	 * Text font and glyph
	 */
	private final String text1 = "CSIS 337";
	private final String text2 = "Computer Graphics";
	private GlyphVector glyphv;
	
	private int font_size = 50;
	private final Font font = new Font( "Serif", Font.BOLD, font_size );
	
	/**
	 * Constructor for this class
	 */
	public Java2D_01() {
		setTitle( TITLE );
		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		
		/*
		 * Add a menu bar to the window -- on a Mac OS X system, the
		 * menu appears at the top of the screen instead of the window.
		 */
		menubar = new MenuBar();
		setMenuBar( menubar );
		/*
		 * The menu bar will contain one menu -- the "File" menu
		 */
		fileMenu = new Menu( fileMenuString );
		menubar.add( fileMenu );
		/*
		 * The File Menu will contain a number of menu items:
		 * at the moment, just 2 things -- Open and Exit -- but
		 * the code is written so that all one would have to do 
		 * would be to add strings to the fileMenuItemStrings
		 * array and everything gets done properly. It's magic!
		 */
		final int fileMenuItemSize = fileMenuItemStrings.length;
		fileMenuItems = new MenuItem[fileMenuItemSize];
		for (int i=0; i<fileMenuItemSize; i++) {
			fileMenuItems[i] = new MenuItem( fileMenuItemStrings[i] );
			fileMenuItems[i].addActionListener( this );
			fileMenu.add( fileMenuItems[i] );
		}
		
		addMouseListener( this );

		
		myshapes[0] = new Line2D.Float();
			shape_positions[0][0] = 20;
			shape_positions[0][1] = 20;
			shape_sizes[0][0] = 70;
			shape_sizes[0][1] = 160;
			shape_colors[0] = Color.blue;
		myshapes[1] = new Rectangle2D.Double();
			shape_positions[1][0] = 50;
			shape_positions[1][1] = 50;	
			shape_sizes[1][0] = 100;
			shape_sizes[1][1] = 100;
			shape_colors[1] = Color.red;
		myshapes[2] = new RoundRectangle2D.Float();
			shape_positions[2][0] = 200;
			shape_positions[2][1] =  50;
			shape_sizes[2][0] = 150;
			shape_sizes[2][1] = 100;
			shape_colors[2] = Color.green;
		myshapes[3] = new Ellipse2D.Float();
			shape_positions[3][0] = 150;
			shape_positions[3][1] = 250;
			shape_sizes[3][0] = 100;
			shape_sizes[3][1] = 200;
			shape_colors[3] = Color.MAGENTA;
		myshapes[4] = new Ellipse2D.Double();
			shape_positions[4][0] = 100;
			shape_positions[4][1] = 150;
			shape_sizes[4][0] = 200;
			shape_sizes[4][1] = 100;
			shape_colors[4] = Color.ORANGE;
		paintjob  = new GradientPaint((float) shape_positions[4][0], (float) shape_positions[4][1], shape_colors[3],
									  (float) shape_sizes[4][0],     (float) shape_sizes[4][1],     shape_colors[4]);
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha_factor );
		
		/*
		 * Make the program quit when the window is closed
		 */
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent ev){
				System.exit( 0 );
			}
		});
		setVisible( true );
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
		((Line2D) myshapes[0]).setLine( shape_positions[0][0], shape_positions[0][1],
							            shape_sizes[0][0],     shape_sizes[0][1] );
		((Rectangle2D) myshapes[1]).setRect( shape_positions[1][0], shape_positions[1][1],
										shape_sizes[1][0],     shape_sizes[1][1] );
		((RoundRectangle2D) myshapes[2]).setRoundRect( shape_positions[2][0], shape_positions[2][1],
										shape_sizes[2][0],     shape_sizes[2][1], FRAME_HEIGHT, FRAME_WIDTH );
		((Ellipse2D) myshapes[3]).setFrame( shape_positions[3][0], shape_positions[3][1],
										shape_sizes[3][0],     shape_sizes[3][1] );
		((Ellipse2D) myshapes[4]).setFrame( shape_positions[4][0], shape_positions[4][1],
										shape_sizes[4][0],     shape_sizes[4][1] );

		int i=0;
		for (Shape s: myshapes) {
			g2d.setColor( shape_colors[i++] );
			if (i==5){
				g2d.setPaint( paintjob );
				g2d.fill( s );
			} else 
				g2d.draw( s );
		}
		/*
		 * Add some text
		 */
		g2d.setComposite( alpha );
		g2d.setColor( Color.PINK );
		g2d.setFont( font );
		g2d.drawString( text1 , 100, 100);
		/*
		 * More text, at an angle
		 */
		FontRenderContext font_rc = g2d.getFontRenderContext();
		glyphv = font.createGlyphVector(font_rc, text2);
		g2d.rotate( Math.PI/4, 200, 200);
		g2d.fill( glyphv.getOutline( 120, 200 ));
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("Menu item chosen.");
		if (e.getActionCommand().equals(fileMenuItemStrings[1]))
			System.exit( 0 );
	}

	public void mouseClicked(MouseEvent e) {
		shape_positions[0][0] = e.getX();
		shape_positions[0][1] = e.getY();
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
		System.out.println(" x = " + e.getX() + "  y = " + e.getY() );
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}


	/**
	 * main -- Program starts here.
	 *   All we do is create an instance of this class and 
	 *   the Java Abstract Windowing Toolkit takes over
	 *   from there.
	 * @param args
	 */
	public static void main(String[] args) {
        System.getProperties().list(System.out);
        //For Mac OS X:
        //System.setProperty("com.apple.eawt.CocoaComponent.CompatibilityMode", "false"); 
		System.out.println("Starting a basic AWT window ... " + System.getProperty("com.apple.eawt.CocoaComponent.CompatibilityMode") );
		new Java2D_01();
	}
}
