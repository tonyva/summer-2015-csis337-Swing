package lab5;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Java3D_07
 * A 3D pyramid with a mapped texture. 
 * 
 * @author Anthony Varghese
 */

public class Java3D_07 extends Frame {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	/*  */
	private final String TITLE = "Java 3D tutorial 7: Textures";
	private final int FRAME_X = 100;
	private final int FRAME_Y = 100;
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	private final String texture_directory = "src/lab5/";
	private final String texture_file1 = texture_directory + "image10.jpg";
	/*
	 * Java3D objects: SimpleUniverse is a class used to set up a scene in a
	 * Java3D program
	 */
	private SimpleUniverse univ;
	private GraphicsConfiguration gc;
	private Canvas3D canvas3D;
	private BranchGroup mainbranch;

	public Java3D_07() {
		setTitle(TITLE);
		setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);

		/* Set up a SimpleUniverse object */
		gc = SimpleUniverse.getPreferredConfiguration();
		canvas3D = new Canvas3D(gc);
		add(canvas3D);
		/*
		 * Set the scene
		 */
		mainbranch = createSceneGraph();
		mainbranch.compile();
		univ = new SimpleUniverse(canvas3D);
		univ.getViewingPlatform().setNominalViewingTransform();
		univ.addBranchGraph(mainbranch);

		/*
		 * Quit when window is closed
		 */
		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) { }
			public void windowClosed(WindowEvent e) {	System.exit(0); }
			public void windowClosing(WindowEvent e) { System.exit(0); }
			public void windowDeactivated(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowIconified(WindowEvent e) { }
			public void windowOpened(WindowEvent e) { }
		});
	}


	/**
	 * createSceneGraph -- creates a scene graph for Java3D
	 * 
	 * @return
	 */
	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		/*
		 * Set up a spin transform node
		 */
		TransformGroup spin = new TransformGroup();
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(spin);
		/*
		 * Add Shape3D objects
		 * 
		 * The appearance is part (along with the lighting) of what determines
		 * hoe the object will look. This appearance will also use a texture
		 */
		TextureLoader textureLoader = new TextureLoader(texture_file1, this);
		ImageComponent2D textureImage = textureLoader.getImage();
		Texture2D texture = new Texture2D( Texture2D.BASE_LEVEL,
										Texture.RGB,
										textureImage.getWidth(), textureImage.getHeight() );
		texture.setImage( 0, textureImage );
		
		Appearance app = new Appearance();
		TextureAttributes textureAttributes = new TextureAttributes();
		textureAttributes.setTextureMode( TextureAttributes.REPLACE );
		app.setTextureAttributes( textureAttributes );
		app.setTexture( texture );

		
		PolygonAttributes polygonAttrib = new PolygonAttributes();
		polygonAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		polygonAttrib.setBackFaceNormalFlip(true);
		app.setPolygonAttributes(polygonAttrib);
		
		
		final Color3f WHITE   = new Color3f( Color.WHITE   );
//		final Color3f MAGENTA = new Color3f( Color.MAGENTA );
		final Color3f RED     = new Color3f( Color.RED     );
//		final Color3f CYAN    = new Color3f( Color.CYAN    );
//		final Color3f ORANGE  = new Color3f( Color.ORANGE  );
		final Color3f BLUE    = new Color3f( Color.BLUE    );
//		final Color3f BLACK   = new Color3f( Color.BLACK   );
		final Color3f GREEN   = new Color3f( Color.GREEN   );

		
		Material material = new Material(
				WHITE,  /* Ambient Color */
				WHITE,  /* Emissive Color */
				WHITE,  /* Diffuse Color */
				WHITE,  /* Specular Color */
				50 /* Shininess: 1 == not shiny; 128 == all shine */
			);

		/* for shiny material 
		Material shinyRed = new Material(
				/* ambient   = *  WHITE,
				/* emissive  = *  BLACK, 
				/* diffuse   = *  RED,
				/* specular  = *  WHITE,
				/* shininess = *  50 );
		*/
		
		
		app.setMaterial(material);

		
		
		
		
		spin.addChild(new Shape3D(new TexturePyramid(), app));

		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(10.0);
		/*
		 * Specify parameters of the spin transformation
		 * 
		 * The spin causes the program to crash under Mac OS X running Java
		 * 1.6.0 build 88 Comment out the next 3 lines if that happens.
		 */
		RotationInterpolator rot = new RotationInterpolator(
				new Alpha(-1, 6000), spin);
		rot.setSchedulingBounds(bounds);
		spin.addChild(rot);

		/*
		 * Lighting
		 */

		// background
		Background bg = new Background(0.0f, 0.1f, 0.0f); // dark green
		bg.setApplicationBounds(bounds);
		root.addChild(bg);

		/* Ambient lighting */
		AmbientLight amb_light = new AmbientLight(true, BLUE );
		amb_light.setInfluencingBounds( bounds );
		root.addChild(amb_light);

		/* Directional lighting */
		DirectionalLight dir_light = new DirectionalLight(true, WHITE, 
												new Vector3f(0, -1, 0));
		dir_light.setInfluencingBounds( bounds );
		root.addChild(dir_light);

		/* Point Light source */
		PointLight point_light = new PointLight(true, RED,
												new Point3f(1, 1, 1), new Point3f(-1, -1, -1));
		point_light.setInfluencingBounds( bounds );
		root.addChild(point_light);

		SpotLight spot = new SpotLight(true, GREEN,
				new Point3f(-1, 0, 1), new Point3f(0.75f, 0, 0),
				new Vector3f(1, -1, -1), (float)(Math.PI/9),  100 );
		spot.setInfluencingBounds( bounds );
		root.addChild( spot );
		
		return root;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Java3D_07 window = new Java3D_07();
		window.setVisible(true);
	}
}
