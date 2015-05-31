package lab2H;

import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Java2D_11
 * An example using Stroke. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_11 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eleventh Example: Stroke";
	private final int FRAME_WIDTH  = 600;
	private final int FRAME_HEIGHT = 300;

	

	/*
	 * The Frame will contain sliders in a separate panel
	 */
	private class ControlPanel extends JPanel implements ActionListener, ChangeListener{
		/**
		 * Data members
		 */
		private static final long serialVersionUID = 1L;
		final String[] widths = { "1", "2", "4", "8", "16", "32" };
		private SpinnerListModel w = new SpinnerListModel( widths );
		private JSpinner spin = new JSpinner( w );
		private float widthChoice;

		final String[] caps = { "butt", "round", "square" };
		private JComboBox<String> capStyle  = new JComboBox<String>( caps );
		private int capChoice;

		final String[] joins = { "bevel", "miter", "round" };
		private JComboBox<String> joinStyle = new JComboBox<String>( joins );
		private int joinChoice;

		private JTextField dash_start = new JTextField();
		private float dash_start_value;
		
		
		
		public ControlPanel() {
			spin.addChangeListener( this );
			capStyle.addActionListener( this );
			joinStyle.addActionListener( this );
			
			dash_start.setSize( 50, 20 );
			dash_start.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try{
						dash_start_value = Float.parseFloat( dash_start.getText() );						
					} catch (NumberFormatException n){
						dash_start_value = 0;
						dash_start.setText( "0" );
						repaint();
					}
					getParent().repaint();
				}
			});
			
			setLayout( new GridLayout(4,2));
			
			add( new JLabel("width") );
			add( spin );
			add( new JLabel("Cap") );
			add( capStyle );
			add( new JLabel("Join") );
			add( joinStyle );
			add( new JLabel("Dash Start"));
			add( dash_start );
		}
		
		
		/*
		 * When a slider is moved, this gets called.
		 */
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unchecked")
			JComboBox<String> source = (JComboBox<String>) e.getSource();
			
			String choice = (String) source.getSelectedItem();
			
			if (source == capStyle){
				String capChoiceString = choice;				
				if (capChoiceString.equals( controls.caps[0] )){
					capChoice = BasicStroke.CAP_BUTT;
				} else if (capChoiceString.equals( controls.caps[1] )) {
					capChoice = BasicStroke.CAP_ROUND;
				} else if (capChoiceString.equals( controls.caps[2] )) {
					capChoice = BasicStroke.CAP_SQUARE;
				}
				
			}

			if (source == joinStyle){
				String joinChoiceString = choice;
				if (joinChoiceString.equals( controls.joins[0] )){
					joinChoice = BasicStroke.JOIN_BEVEL;
				} else if (joinChoiceString.equals( controls.joins[1] )) {
					joinChoice = BasicStroke.JOIN_MITER;
				} else if (joinChoiceString.equals( controls.joins[2] )) {
					joinChoice = BasicStroke.JOIN_ROUND;
				}
			}

			getParent().repaint();			
		}

		public void stateChanged(ChangeEvent e) {
			SpinnerListModel width = (SpinnerListModel) spin.getModel();
			widthChoice = Float.parseFloat( (String) width.getValue() );
			getParent().repaint();
		}

		public int getcapChoice(){
			return capChoice;
		}
		public int getjoinChoice(){
			return joinChoice;
		}
		public float getWidthChoice(){
			return widthChoice;
		}
		public float getDashStartChoice(){
			return dash_start_value;
		}

	}
	
	
	
	private ControlPanel controls;
	private JPanel draw_panel;

	/*
	 * Paths to be drawn
	 */
	private GeneralPath path_a = new GeneralPath();
	private Vector<Point2D.Double> points_a = new Vector<Point2D.Double>();
	private GeneralPath path_b = new GeneralPath();
	private Vector<Point2D.Double> points_b = new Vector<Point2D.Double>();

	
	public Java2D_11(String title) {
		super(title);

		setSize( FRAME_WIDTH, FRAME_HEIGHT );
		setBackground( Color.white );
		

		/*
		 * Initialize the points and set up the first path
		 *
		 * The first path consists of a line, 
		 */
		points_a.add( new Point2D.Double( 100,  50 )); // Line
		points_a.add( new Point2D.Double(  50, 100 ));
		points_a.add( new Point2D.Double( 100, 150 ));
		points_a.add( new Point2D.Double( 140,  30 )); // Quad
		points_a.add( new Point2D.Double(  75,  50 ));
		points_a.add( new Point2D.Double( 250, 150 ));
		points_a.add( new Point2D.Double(  75,  50 ));
		points_a.add( new Point2D.Double( 140, 175 ));
		points_a.add( new Point2D.Double(  20, 250 )); // Cubic
		points_a.add( new Point2D.Double(  15, 150 ));
		points_a.add( new Point2D.Double( 175, 240 ));
		points_a.add( new Point2D.Double( 150, 150 ));

		path_a.moveTo( points_a.elementAt(0).x, points_a.elementAt(0).y );
		path_a.lineTo( points_a.elementAt(1).x, points_a.elementAt(1).y );
		path_a.lineTo( points_a.elementAt(2).x, points_a.elementAt(2).y );

		/* quads */
		path_a.moveTo( points_a.elementAt(3).x, points_a.elementAt(3).y );
		path_a.quadTo( points_a.elementAt(4).x, points_a.elementAt(4).y,
					   points_a.elementAt(5).x, points_a.elementAt(5).y );
		path_a.quadTo( points_a.elementAt(6).x, points_a.elementAt(6).y,
					   points_a.elementAt(7).x, points_a.elementAt(7).y );

		/* a cubic */
		path_a.moveTo( points_a.elementAt(8).x, points_a.elementAt(8).y );
		path_a.curveTo( points_a.elementAt(9).x, points_a.elementAt(9).y,
					    points_a.elementAt(10).x, points_a.elementAt(10).y,
					    points_a.elementAt(11).x, points_a.elementAt(11).y);
		
		
		/*
		 * The second path, path_b consists of one object inside an another
		 * 
		 */
		points_b.add( new Point2D.Double( 300,  50 ));
		points_b.add( new Point2D.Double( 400,  50 ));
		points_b.add( new Point2D.Double( 400, 250 ));
		points_b.add( new Point2D.Double( 300, 250 ));
		points_b.add( new Point2D.Double( 325,  60 ));
		points_b.add( new Point2D.Double( 375,  60 ));
		points_b.add( new Point2D.Double( 375, 200 ));
		points_b.add( new Point2D.Double( 325, 200 ));
		
		path_b.moveTo( points_b.elementAt(0).x, points_b.elementAt(0).y );
		path_b.lineTo( points_b.elementAt(1).x, points_b.elementAt(1).y );
		path_b.lineTo( points_b.elementAt(2).x, points_b.elementAt(2).y );
		path_b.lineTo( points_b.elementAt(3).x, points_b.elementAt(3).y );
		path_b.closePath();
		
		path_b.moveTo( points_b.elementAt(4).x, points_b.elementAt(4).y );
		/* Switch the order of the next two lines and rerun the program. */
		path_b.lineTo( points_b.elementAt(5).x, points_b.elementAt(5).y );
		path_b.lineTo( points_b.elementAt(6).x, points_b.elementAt(6).y );
		path_b.lineTo( points_b.elementAt(7).x, points_b.elementAt(7).y );
		path_b.closePath();
		
		/**
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );

		/*
		 * Sliders on the West side
		 */
		controls = new ControlPanel( );
		add( controls, BorderLayout.WEST );

		
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
				float miterlimit = 5;
				float[] dashes = { 50, 10, 10, 30 };
				float start = 20;
				
				/* Get settings from the control panel */
				float  width = controls.getWidthChoice();
				int capstyle = controls.getcapChoice();
				int joinstyle = controls.getjoinChoice();
				start = controls.getDashStartChoice();
				if (start < 0)
					start = 0;
				if (start > 100)
					start = 100;
				
				/*
				 * Draw the paths.
				 */
				g2d.setStroke( new BasicStroke( width, capstyle, joinstyle, miterlimit ));
				g2d.draw( path_a );				
				g2d.setStroke( new BasicStroke( width, capstyle, joinstyle,
						miterlimit, dashes, start ));
				g2d.draw( path_b );
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
		new Java2D_11( TITLE );
	}
}
