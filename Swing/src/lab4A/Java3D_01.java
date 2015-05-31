package lab4A;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Java3D_01 
 * A basic example of Java3D - a rotating sphere with an image mapped to its surface. 
 * 
 * @author Anthony Varghese
 */

public class Java3D_01 extends Frame {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	/*	 */
	private final String TITLE = "Java 3D tutorial 1: Sphere";
	private final int FRAME_X = 100;
	private final int FRAME_Y = 100;
	private final int FRAME_WIDTH  = 400;
	private final int FRAME_HEIGHT = 300;

	/* Java3D objects:
 	 * SimpleUniverse is a class used to set up a scene in a Java3D program
	 */
	private SimpleUniverse univ;
	private GraphicsConfiguration gc;
	private Canvas3D canvas3D;
	private BranchGroup mainbranch;
	/*
	 * File for texture
	 */
	private final String directory = "src/lab4A/"; // where the image is
	private final String file      = "UWRFLogo.jpg"; // name of image file
	private final String filename  = directory + file;

	public Java3D_01(){
		setTitle( TITLE );
		setBounds( FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT );

		/* Set up a SimpleUniverse object */
		gc = SimpleUniverse.getPreferredConfiguration();
		canvas3D = new Canvas3D( gc );
		add( canvas3D );
		/*
		 * Set the scene
		 */
		mainbranch = createSceneGraph();
		mainbranch.compile();
		univ = new SimpleUniverse( canvas3D );
		univ.getViewingPlatform().setNominalViewingTransform();
		univ.addBranchGraph( mainbranch );

		/* Quit when window is closed
		 */
		addWindowListener( new WindowListener(){
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {System.exit(0);}
			public void windowClosing(WindowEvent e) { System.exit(0);}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
	}

	/**
	 * createSceneGraph -- creates a scene graph for Java3D
	 * 
	 * @return
	 */
	private BranchGroup createSceneGraph(){
		BranchGroup root = new BranchGroup();
		   /*
		    * Set up a spin transform node
		    */
		   TransformGroup spin = new TransformGroup();
		   		spin.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		   root.addChild( spin );
		   /*
		    * Add a sphere with texture
		    */
		   Appearance app = createAppearance();
		   Sphere sphere = new Sphere(0.5f, Primitive.GENERATE_TEXTURE_COORDS, 100, app );
		   spin.addChild( sphere );

		   BoundingSphere bounds = new BoundingSphere();

		   /*
		    * Specify parameters of the spin transformation
		    * 
		    * The spin causes the program to crash under Mac OS X running Java 1.6.0 build 88
		    * Comment out the next 3 lines if that happens.
		    */
		   RotationInterpolator rot = new RotationInterpolator( new Alpha(-1,6000), spin );
		   rot.setSchedulingBounds( bounds );
		   spin.addChild( rot );
		   
		   /*
		    * Lighting
		    */
		   Background bg = new Background( 1.0f, 0.1f, 0.1f ); // red
		   bg.setApplicationBounds(bounds);
		   root.addChild( bg );
		   AmbientLight light = new AmbientLight( true, new Color3f( Color.blue ));
		   root.addChild( light );
		return root;
	}
	

	/**
	 * createAppearance 
	 * 
	 * @return Appearance object with texture, etc.
	 */
	private Appearance createAppearance(){
		Appearance app = new Appearance();
		    System.out.print(" Opening file " + filename + " ... ");
	    	ImageComponent2D image = null;

			/*
			 * Texture image
			 */
			{
				File file = new File( filename );
				try {
					image = new ImageComponent2D( ImageComponent2D.FORMAT_RGB , ImageIO.read( file ) );
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Not able to read image file: " + filename +". Is it in the right place?");
					System.err.println("   It should be in the Java project directory. ");
					System.err.println("   If you have src and bin directories, it should be in the same directory that contains src and bin");
				}
			}
		    if (image != null){
		    	Texture2D texture = new Texture2D( Texture.BASE_LEVEL , Texture.RGB, image.getWidth(), image.getHeight() );
		    	texture.setImage(0, image);
		    	texture.setEnable( true );
		    	texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		    	texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
				app.setTexture(texture);
		    } else
		    	System.out.println("Not able to get image");
		    
		return app;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Java3D_01()).setVisible( true );
	}

}
