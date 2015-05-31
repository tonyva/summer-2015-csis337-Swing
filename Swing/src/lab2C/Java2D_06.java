package lab2C;

import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * Java2D_06 
 * An example using the Area class to construct complex areas from simpler ones. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_06 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Sixth Example: Areas";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	/*
	 * Action
	 */
	private final String[] actions = {"Add", "Subtract", "Intersect", "Exclusive OR" };
	private int operation = -1;
	
	/*
	 * The Frame will "contain" an instance of the Panel class.
	 */
	private class RadioButtonPanel extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
		private JRadioButton add, sub, intr, xor;
		private ButtonGroup group = new ButtonGroup();
		
		public RadioButtonPanel() {
			add  = new JRadioButton( actions[0] , true );
			sub  = new JRadioButton( actions[1] , false );
			intr = new JRadioButton( actions[2] , false );
			xor  = new JRadioButton( actions[3] , false );

			add.setActionCommand(actions[0]);
			add.addActionListener( this );
			sub.setActionCommand(actions[1]);
			sub.addActionListener( this );
			intr.setActionCommand(actions[2]);
			intr.addActionListener( this );
			xor.setActionCommand(actions[3]);
			xor.addActionListener( this );

			group.add( add );
			group.add( sub );
			group.add( intr );
			group.add( xor );
			
			add( add );
			add( sub );
			add( intr );
			add( xor );
		}

		public void actionPerformed(ActionEvent e) {
			for (int i=0; i<actions.length; i++)
				if (e.getActionCommand().equals( actions[i] ))
					operation = i;
			getParent().repaint();
		}
		
	}
	private RadioButtonPanel button_panel;
	private JPanel draw_panel;

	/*
	 * Shapes to be drawn
	 */
	private final int number_shapes = 4;
	private Shape[]   myshapes = new Shape[ number_shapes ];
	private double[][] shape_positions = new double[ number_shapes ][2];
	private double[][] shape_sizes = new double[ number_shapes ][2];
	private Color[]    shape_colors = new Color[ number_shapes ];
	private Area a1, a2, a3, a4;
	
	
	
	public Java2D_06(String title) {
		super(title);

		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		

		/**
		 * Initialize the shapes
		 */
		myshapes[0] = new Ellipse2D.Double();
		shape_positions[0][0] = 50;
		shape_positions[0][1] = 50;	
		shape_sizes[0][0] = 100;
		shape_sizes[0][1] = 150;
		shape_colors[0] = Color.red;
		
		myshapes[1] = new Polygon();
		shape_positions[1][0] = 100;
		shape_positions[1][1] =  75;
		shape_sizes[1][0] = 80;
		shape_sizes[1][1] = 80;
		shape_colors[1] = Color.green;
		
		myshapes[2] = new Ellipse2D.Float();
		shape_positions[2][0] = 250;
		shape_positions[2][1] = 130;
		shape_sizes[2][0] = 70;
		shape_sizes[2][1] = 70;
		shape_colors[2] = Color.MAGENTA;
		
		myshapes[3] = new Polygon();
		shape_positions[3][0] = 250;
		shape_positions[3][1] = 120;
		shape_sizes[3][0] = 80;
		shape_sizes[3][1] = 80;
		shape_colors[3] = Color.ORANGE;
		
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
		 * Buttons on the West side
		 */
		button_panel = new RadioButtonPanel( );
		add( button_panel, BorderLayout.NORTH );
		
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
				for ( int i=0; i<myshapes.length; i++ ){
						g2d.setColor( shape_colors[i] );
						g2d.draw( myshapes[i] );
				}

				/*
				 * Based on what RadioButton the user has selected
				 * draw the appropriate combined area.
				 *  
				 */
				a1 = new Area( myshapes[0] );
				a2 = new Area( myshapes[1] );
				a3 = new Area( myshapes[2] );
				a4 = new Area( myshapes[3] );
				switch (operation){ // "operation" is set in the RadioButton Panel.
				case 0: // Add
					a1.add( a2 );
					a3.add( a4 );
					break;
				case 1: // Subtract
					a1.subtract( a2 );
					a3.subtract( a4 );
					break;
				case 2: // Intersection
					a1.intersect( a2 );
					a3.intersect( a4 );
					break;
				case 3: // Exclusive OR
					a1.exclusiveOr( a2 );
					a3.exclusiveOr( a4 );
					break;
				}
				g2d.setColor( Color.gray );
				g2d.fill( a1 );
				g2d.fill( a3 );
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
		new Java2D_06( TITLE );
	}	
}
