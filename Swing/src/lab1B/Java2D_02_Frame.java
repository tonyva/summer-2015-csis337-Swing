package lab1B;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * Java2D_02_Frame 
 * A Simple example using some of the features of Java 2D and the Swing toolkit. 
 * 
 * 
 * @author Anthony Varghese
 *
 */

public class Java2D_02_Frame extends JFrame implements ActionListener {

	/**
	 * Data members of the class
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Second Example: Layout in Swing";
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	private JMenuBar menubar;
	private JMenu fileMenu;
	private final String fileMenuString = "File";
	private JMenuItem[] fileMenuItems;
	private final String[] fileMenuItemStrings = {"Open" , "Exit" };
	
	private JButton[] buttons;
	private final String[] buttonStrings = {"First", "Second", "Third",
											"Fourth", "Fifth", "Sixth",
											"Seventh", "Eighth", "Ninth" };
	/*
	 * File Open dialog
	 */
	private final JFileChooser choose = new JFileChooser();
	
	public Java2D_02_Frame(String title) {
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
		 * Set the layout for arranging things inside the window here
		 */
//		LayoutManager layout = new BorderLayout();
//		LayoutManager layout = new FlowLayout();
//		LayoutManager layout = new GridLayout(5, 2);
//		LayoutManager layout = new BoxLayout( getContentPane(), BoxLayout.PAGE_AXIS);
//		LayoutManager layout = new GridBagLayout();		
//		setLayout( layout );

		/*
		 * If we use the GridBagLayout, we can contrain the components
		 * we are laying out with a GridBagConstratints object.
		 */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridheight = 1;
		gbc.gridwidth  = 1;

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

			gbc.gridx = i / 3; // These two lines are for the GridBagLayout constraints.
			gbc.gridy = i % 3;
			add( buttons[i] );
			/*
			 * For the GridBagLayout, use the following line instead.
			 */
			// add( buttons[i], gbc );
		}
		
		setVisible(true);
		/*
		 * Make the program quit when the window is closed
		 */
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	}

	/**
	 * actionPerformed - Respond to user actions here
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == fileMenuItems[0]){
			System.out.println("Open menu item chosen.");
			
			String cwd = System.getProperty("user.dir");
            System.out.println("Current working directory is " + cwd );
			File wd = new File( cwd );
            System.out.println("  Setting location of JFileChooser to " + wd.getName() );
			choose.setCurrentDirectory( wd );
			
			int choice = choose.showOpenDialog( this );
			if (choice == JFileChooser.APPROVE_OPTION) {
	            File file = choose.getSelectedFile();
	            System.out.println("Opening: " + file.getName() );
	            //Now open the file.
	            
	            // and read the contents

	        } else {
	            System.out.println("Open dialog canceled.");
	        }			
		}
	}

	/**
	 * Main method where the program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Java2D_02_Frame( TITLE );
	}
}
