package lab2E;

import javax.swing.JFrame;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Java2D_08 
 * An example using Colors and Area objects. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_08 extends JFrame {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Eighth Example: Colors";
	private final int FRAME_WIDTH = 600;
	private final int FRAME_HEIGHT = 300;

	/*
	 * Action
	 */
	private final String[] actions = {"Add", "Subtract", "Intersect", "Exclusive OR" };
	private int operation = 0;
	
	/*
	 * Color settings from the sliders
	 * As the user changes the sliders, the 3 values will be used to set the 
	 * three color components of the following Color object.
	 */
	private Color setting = new Color( 0, 0, 0);

	/*
	 * The Frame will contain sliders in a separate panel
	 */
	private class SliderPanel extends JPanel implements ChangeListener{
		/**
		 * Slider Panel data members
		 */
		private static final long serialVersionUID = 1L;
		private final int COLORMIN = 0, COLORMAX = 255, COLORINTERVAL = 128, COLOR_INITIAL = 0;
		private JSlider redSlide, greenSlide, blueSlide;
		
		public SliderPanel() {
			/*
			 * Set up 3 sliders, one each for red, green, and blue values.
			 */
			redSlide   = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, COLOR_INITIAL );
			greenSlide = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, COLOR_INITIAL );
			blueSlide  = new JSlider( JSlider.VERTICAL, COLORMIN, COLORMAX, COLOR_INITIAL );
			blueSlide.setMajorTickSpacing(COLORINTERVAL);
			blueSlide.setPaintTicks(true);
			blueSlide.setPaintLabels(true);
			
			redSlide.addChangeListener( this );
			greenSlide.addChangeListener( this );
			blueSlide.addChangeListener( this );
			
			this.setLayout( new GridLayout(2,3));
			add( new JLabel("Red") );
			add( new JLabel("Green") );
			add( new JLabel("Blue") );
			add( redSlide );
			add( greenSlide );
			add( blueSlide );
		}
		/**
		 * stateChanged - 
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
	}
	
	/**
	 * RadioButtonPanel class
	 * The Frame will "contain" an instance of the RadioButtonPanel class.
	 */
	private class RadioButtonPanel extends JPanel implements ActionListener{
		/**
		 * 
		 */
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
	
	
	
	private SliderPanel sliders;
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
	private Area area0, area1, area2, area3;
	
	
	
	public Java2D_08(String title) {
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
		shape_colors[0] = new Color( setting.getRed(), 0, 0 );
		
		myshapes[1] = new Polygon();
		shape_positions[1][0] = 100;
		shape_positions[1][1] =  75;
		shape_sizes[1][0] = 80;
		shape_sizes[1][1] = 80;
		shape_colors[1] = new Color( 0, setting.getGreen(), 0);
		
		myshapes[2] = new Ellipse2D.Float();
		shape_positions[2][0] = 250;
		shape_positions[2][1] = 130;
		shape_sizes[2][0] = 70;
		shape_sizes[2][1] = 70;
		shape_colors[2] = new Color( 0, 0, setting.getBlue() );
		
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
		 * Sliders on the West side
		 */
		sliders = new SliderPanel( );
		add( sliders, BorderLayout.WEST );

		/*
		 * Radio buttons on the North side
		 */
		button_panel = new RadioButtonPanel( );
		add( button_panel, BorderLayout.NORTH );
		
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
			 * Data members
			 */
			private static final long serialVersionUID = 1L;

			/**
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
				setBackground( Color.YELLOW );

				Graphics2D g2d = (Graphics2D) g;
				/*
				 * Draw all the shapes first.
				 */
				shape_colors[0] = new Color( setting.getRed(), 0, 0 );
				shape_colors[1] = new Color( 0, setting.getGreen(), 0);
				shape_colors[2] = new Color( 0, 0, setting.getBlue() );

				for ( int i=0; i<myshapes.length; i++ ){
						g2d.setColor( shape_colors[i] );
						g2d.fill( myshapes[i] );
				}

				/*
				 * Based on what RadioButton the user has selected
				 * draw the appropriate combined area.
				 *  
				 */
				area0 = new Area( myshapes[0] );
				area1 = new Area( myshapes[1] );
				area2 = new Area( myshapes[2] );
				area3 = new Area( myshapes[3] );
				int red0   = shape_colors[0].getRed(),
				    red1   = shape_colors[1].getRed(),
				    red2   = shape_colors[2].getRed(),
				    red3   = shape_colors[3].getRed();
				int green0 = shape_colors[0].getGreen(),
					green1 = shape_colors[1].getGreen(),
					green2 = shape_colors[2].getGreen(),
					green3 = shape_colors[3].getGreen();
				int blue0  = shape_colors[0].getBlue(),
					blue1  = shape_colors[1].getBlue(),
					blue2  = shape_colors[2].getBlue(),
					blue3  = shape_colors[3].getBlue();

				switch (operation){ // "operation" is set in the RadioButton Panel.
				case 0: // Add
					area0.add( area1 );
					g2d.setColor( new Color( red0+red1, green0+green1, blue0+blue1) );
					g2d.fill( area0 );

					area2.add( area3 );
					g2d.setColor( new Color( red2+red3, green2+green3, blue2+blue3) );
					g2d.fill( area2 );
					break;
					
				case 1: // Subtract
					area0.subtract( area1 );
					g2d.setColor( new Color( red0, green1, blue0) );
					g2d.fill( area0 );

					area2.subtract( area3 );
					g2d.setColor( new Color( red2, green3, blue2) );
					g2d.fill( area2 );
					break;
					
				case 2: // Intersection
					area0.intersect( area1 );
					g2d.setColor( new Color( red0, green1, blue0) );
					g2d.fill( area0 );

					area2.intersect( area3 );
					g2d.setColor( new Color( red2, green3, blue2) );
					g2d.fill( area2 );
					break;
					
				case 3: // Exclusive OR
					area0.exclusiveOr( area1 );
					g2d.setColor( new Color( red0, green1, blue1) );
					g2d.fill( area0 );

					area2.exclusiveOr( area3 );
					g2d.setColor( new Color( red2, green2, blue2) );
					g2d.fill( area2 );
					break;
				}
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
		new Java2D_08( TITLE );
	}
}
