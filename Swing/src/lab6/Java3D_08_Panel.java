package lab6;

import java.awt.Panel;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java2D_08_Panel
 *  
 * A example of a container that will be used 
 * inside a window. 
 * 
 * 
 * @author Anthony Varghese
 *
 */
public class Java3D_08_Panel extends Panel implements ActionListener {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private final String[] buttonStrings = {"First", "Second", "Third", "Fourth" };
	
	private int button_pressed = -1;

	public Java3D_08_Panel( ) {
		/**
		 * Set the layout here
		 */
		LayoutManager layout = new GridLayout(4,1);
		setLayout( layout );

		/**
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
	}

	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		/*
		 * Set the data member, button_pressed, to indicate which button was pressed 
		 */
		for (int i=0; i<buttons.length; i++ ){
			if ( source.equals( buttons[i] ) )
				button_pressed = i;
		}
		getParent().repaint();
	}
	
	public int getStatus(){
		return button_pressed;
	}
	
	public void setStatus(int i){
		button_pressed = i;
	}

}