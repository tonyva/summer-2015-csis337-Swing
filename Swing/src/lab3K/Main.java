package lab3K;

import javax.swing.JFrame;

/**
 * A simple game setup with
 * - a background image that is bigger than the visible area
 * - a set of 16 images for a character - a pirate
 *      this will have to be "cut" up into 16 pieces
 *    
 * @author tony
 *
 */
public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private World game;
	
	public Main(){
		setBounds( 100, 100, 400, 300 );
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		game = new World();
		add( game );
		
		setVisible( true );
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Main();

	}

}
