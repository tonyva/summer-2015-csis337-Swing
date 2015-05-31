package lab1C;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java2D_03_Panel
 *  
 * A example of a container that will be used 
 * inside a window. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_03_Panel extends JPanel implements ActionListener {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private final String[] buttonStrings = {"First", "Second", "Third", "Fourth" };
	
	private boolean[] status = new boolean[ buttonStrings.length ];

	public Java2D_03_Panel( boolean[] initial_status ) {
		/*
		 * Set the layout here
		 */
		LayoutManager layout = new GridLayout(3,2);
		setLayout( layout );

		/*
		 * Add the buttons to the window. You can change the number of 
		 * buttons to be added to the window by editing the buttonStrings
		 * array.
		 * 
		 */
		final int buttonListSize = buttonStrings.length;
		buttons = new JButton[buttonListSize];
		for (int i=0; i<buttonListSize; i++){
			buttons[i] = new JButton( buttonStrings[i] );
			buttons[i].addActionListener( this );
			add( buttons[i] );
		}
		
		for(int i=0; i<initial_status.length; i++)
			status[i] = initial_status[i];
		
		setBorder( new TitledBorder( new EtchedBorder(), "Control"));

	}

	public void paintComponent(Graphics g){
		super.paintComponent( g );
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		for (int i=0; i<buttons.length; i++ )
			if ( source.equals( buttons[i] ) )
				status[i] = !(status[i]);
		
		getParent().repaint();
	}
	
	public boolean getStatus(int i){
		return status[i];
	}
	
	public void setStatus(int i, boolean state){
		status[i] = state;
	}
}