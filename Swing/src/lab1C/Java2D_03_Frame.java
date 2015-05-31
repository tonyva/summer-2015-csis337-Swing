package lab1C;

import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



/**
 * Java2D_03_Frame 
 * A Simple example using some of the features of Java 2D and the Swing toolkit. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_03_Frame extends JFrame implements ActionListener{

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Third Example: Swing containers";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	private JMenuBar menubar;
	private JMenu fileMenu;
	private final String fileMenuString = "File";
	private JMenuItem[] fileMenuItems;
	private final String[] fileMenuItemStrings = {"Open" , "Exit" };
	

	/*
	 * Shapes to be drawn
	 */
	private final int ARC_WIDTH = 10, ARC_HEIGHT = 20;
	private final int number_shapes = 4;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];
	private Color[]    shape_colors = new Color[ number_shapes ];

	
	/*
	 * The Frame will "contain" an instance of the Panel class.
	 */
	private boolean[] buttonstate = { true, false, false, false };
	private Java2D_03_Panel button_panel;
	private JPanel draw_panel;
	
	private final String[] shapeStrings = { "Rectangle", "Square", "Oval", "Circle" };
	private JComboBox<String> combo;
	
	private final String[] myColors = { "red", "green", "blue", "yellow", "black", "gold" };
	private JList<String> list;
	
	private JProgressBar progress;
	
	
	public Java2D_03_Frame(String title) {
		super(title);

		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		
		/*
		 * Add a menu bar to the window -- on a Mac OS X system, the
		 * menu appears at the top of the screen instead of the window.
		 */
		menubar = new JMenuBar();
		setJMenuBar( menubar );
		/*
		 * The menu bar will contain one menu -- the "File" menu
		 */
		fileMenu = new JMenu( fileMenuString );
		menubar.add( fileMenu );
		/*
		 * The File Menu will contain a number of menu items:
		 * at the moment, just 2 things -- Open and Exit -- but
		 * the code is written so that all one would have to do 
		 * would be to add strings to the fileMenuItemStrings
		 * array and everything gets done properly. It's magic!
		 */
		final int fileMenuItemSize = fileMenuItemStrings.length;
		fileMenuItems = new JMenuItem[fileMenuItemSize];
		for (int i=0; i<fileMenuItemSize; i++) {
			fileMenuItems[i] = new JMenuItem( fileMenuItemStrings[i] );
			fileMenuItems[i].addActionListener( this );
			fileMenu.add( fileMenuItems[i] );
		}

		/*
		 * Initialize the shapes
		 */
		myshapes[0] = new Rectangle2D.Double();
		shape_positions[0][0] = 50;
		shape_positions[0][1] = 50;	
		shape_sizes[0][0] = 40;
		shape_sizes[0][1] = 100;
		shape_colors[0] = Color.red;
		myshapes[1] = new RoundRectangle2D.Float();
		shape_positions[1][0] = 100;
		shape_positions[1][1] =  75;
		shape_sizes[1][0] = 80;
		shape_sizes[1][1] = 80;
		shape_colors[1] = Color.green;
		myshapes[2] = new Ellipse2D.Float();
		shape_positions[2][0] =  50;
		shape_positions[2][1] = 130;
		shape_sizes[2][0] = 70;
		shape_sizes[2][1] = 30;
		shape_colors[2] = Color.MAGENTA;
		myshapes[3] = new Ellipse2D.Double();
		shape_positions[3][0] = 100;
		shape_positions[3][1] = 140;
		shape_sizes[3][0] = 40;
		shape_sizes[3][1] = 40;
		shape_colors[3] = Color.ORANGE;
		((Rectangle2D) myshapes[0]).setRect( shape_positions[0][0], shape_positions[0][1],
											 shape_sizes    [0][0],     shape_sizes[0][1] );
		((RoundRectangle2D) myshapes[1]).setRoundRect(  shape_positions[1][0], shape_positions[1][1],
											 			shape_sizes    [1][0], shape_sizes    [1][1], ARC_WIDTH, ARC_HEIGHT );
		((Ellipse2D) myshapes[2]).setFrame( shape_positions[2][0], shape_positions[2][1],
											shape_sizes    [2][0], shape_sizes    [2][1] );
		((Ellipse2D) myshapes[3]).setFrame( shape_positions[3][0], shape_positions[3][1],
											shape_sizes    [3][0], shape_sizes    [3][1] );

		/*
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );

		/*
		 * Buttons on the West side
		 */
		button_panel = new Java2D_03_Panel( buttonstate );
		add( button_panel, BorderLayout.WEST );
		
		/*
		 * A ComboBox on the North side
		 */
		combo = new JComboBox<String>( shapeStrings );
		add( combo, BorderLayout.NORTH );
		
		/*
		 * A panel for drawing in the center
		 *
		 * Although we could set up a new Panel class as we did with the Java2D_03_Panel
		 * class, here we set up what is called an "anonymous" class that inherits from 
		 * JPanel and extends it by redefining the paintComponent class. We do this when
		 * we only need one instance of a class or if we only need to redefine a small
		 * part of the inherited class.
		 * 
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
				 * Cycle through the buttons to see which ones are "true"
				 *  and only draw those.
				 */
				for ( int i=0; i<buttonstate.length; i++ ) {
					buttonstate[ i ] = button_panel.getStatus( i );
					if ( buttonstate[ i ] )
						g2d.draw( myshapes[i] );
				}
			}
		};
		draw_panel.setBorder( new TitledBorder( new EtchedBorder(), "Draw Area"));
		add( draw_panel, BorderLayout.CENTER );

		/*
		 * A List in the East
		 */
		list = new JList<String>( myColors );
		list.setBorder( new TitledBorder( new EtchedBorder(), "Colors") );
		add( list, BorderLayout.EAST );
		
		/*
		 * A Progress Bar in the South
		 */
		progress = new JProgressBar(0,100);
		progress.setValue( 50 );
		add( progress, BorderLayout.SOUTH );
		
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


	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * Main method where the program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_03_Frame( TITLE );
	}
	
}
