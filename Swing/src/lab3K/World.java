package lab3K;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class World extends JPanel implements ActionListener {
	private Player p1;
	private Image  backgroundImage;
	private KeyAdapter keys;
	
	public World(){
		backgroundImage = (new ImageIcon("src/lab3K/image11.jpg")).getImage();

		keys = new KeyAdapter(){
			public void keyReleased(KeyEvent ke){
				if (p1 == null)
					return;
				switch (ke.getKeyCode()) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
					p1.setDelta_x( 0 );
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
					p1.setDelta_y( 0 );
					break;
				default:
				}
			}
			public void keyPressed(KeyEvent ke){
				if (p1 == null)
					return;
				// for acceleration
				int current_delta = p1.getDelta_x();

				switch (ke.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					p1.setDelta_x( -1 );
					break;
				case KeyEvent.VK_RIGHT:
					p1.setDelta_x( 1 );
					break;
				case KeyEvent.VK_UP:
					p1.setDelta_y( -1 );
					break;
				case KeyEvent.VK_DOWN:
					p1.setDelta_y( 1 );
					break;
				default:
					
				}
			}
		};
		this.addKeyListener( keys );
		setFocusable( true );
	
		p1 = new Player();
		
		(new javax.swing.Timer(20, this)).start(); // call actionPerformed every 20 milliseconds.
	}

	int y = 0;
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		if (y > -(backgroundImage.getHeight(null) - this.getHeight()))
			y--;
		g2d.drawImage(backgroundImage, 0, y, null);
		g2d.drawImage(p1.getImage(), p1.getX(), p1.getY(), null );
	}
	public void actionPerformed(ActionEvent e) {
		p1.move(); // Move the player
		repaint(); // refresh all of the drawing
	}
}
