package lab3K;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author tony
 * Background is from http://badonk.deviantart.com/
 * Sprites are from http://untamed.wild-refuge.net/
 */
public class Player{
	private int x, y;
	private int delta_x = 0;
	private int delta_y = 0;
	private BufferedImage allSprites;
	private Image leftSprite, rightSprite;
	
	public Player(){
		x = 0;
		y = 100;
		try {
			allSprites = ImageIO.read( new File( "src/lab3K/pirate_m1.png") );
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (allSprites == null)
			return;
		int totalWidth = allSprites.getWidth(null);
		int totalHeight = allSprites.getHeight(null);
		rightSprite = allSprites.getSubimage(0, 0, totalWidth/4, totalHeight/4);
		leftSprite  = allSprites.getSubimage(0, totalHeight/4, totalWidth/4, totalHeight/4);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getDelta_x() {
		return delta_x;
	}
	public int getDelta_y() {
		return delta_y;
	}

	public void setDelta_x(int delta_x) {
		this.delta_x = delta_x;
	}
	public void setDelta_y(int delta_y) {
		this.delta_y = delta_y;
	}
	
	public void move(){
		x += delta_x;
		y += delta_y;
	}

	public Image getImage() {
		if (delta_x < 0)
			return leftSprite;
		if (delta_x > 0)
			return rightSprite;
		return allSprites;
	}
}