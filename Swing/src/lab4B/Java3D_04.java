package lab4B;

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
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Java3D_04
 * A basic example of Java3D - a rotating pyramid. 
 * 
 * @author Anthony Varghese
 */

public class Java3D_04 extends Frame {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	/*  */
	private final String TITLE = "Java 3D tutorial 4: Custom subclass of IndexedTriangleArray";
	private final int FRAME_X = 100;
	private final int FRAME_Y = 100;
	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 300;

	/*
	 * Java3D objects: SimpleUniverse is a class used to set up a scene in a
	 * Java3D program
	 */
	private SimpleUniverse univ;
	private GraphicsConfiguration gc;
	private Canvas3D canvas3D;
	private BranchGroup mainbranch;

	class Pyramid extends IndexedTriangleArray {
		public static final int NUMBER_VERTICES = 5;
		public static final double START_WIDTH = 1.0;
		public static final double START_HEIGHT = 1.0;
		private Point3d[] coordinates = new Point3d[NUMBER_VERTICES];
		/*
		 * Define the 6 triangles for the pyramid: 
		 *   The four sides and the base which will be made
		 *   up of two triangles.
		 */
		final int[] vertexIndices = { 4, 0, 1, // Side 1
									  4, 1, 2, // Side 2
									  4, 2, 3, // Side 3
									  4, 3, 0, // Side 4
									  0, 1, 2, // Two triangles for the base
									  0, 2, 3
									};
		final static int NUMBER_INDICES = 18; // vertexIndices.length;
		
		/**
		 * Constructor
		 * 
		 */
		Pyramid() {
			super(NUMBER_VERTICES, GeometryArray.COORDINATES, NUMBER_INDICES );
			/*
			 * The four corners of the base
			 */
			coordinates[0] = new Point3d(  START_WIDTH/2, -START_HEIGHT/2,  START_WIDTH/2 ); 
			coordinates[1] = new Point3d( -START_WIDTH/2, -START_HEIGHT/2,  START_WIDTH/2 ); 
			coordinates[2] = new Point3d( -START_WIDTH/2, -START_HEIGHT/2, -START_WIDTH/2 ); 
			coordinates[3] = new Point3d(  START_WIDTH/2, -START_HEIGHT/2, -START_WIDTH/2 ); 
			// The top
			coordinates[4] = new Point3d(       0,         START_HEIGHT/2,       0        );

			setCoordinates(0, coordinates);
			setCoordinateIndices(0, vertexIndices);
			
		}
	}
		
	
	public Java3D_04() {
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
			public void windowClosed(WindowEvent e) { System.exit(0); }
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
		 */
		Appearance app = new Appearance();
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace( PolygonAttributes.CULL_NONE );
		app.setPolygonAttributes( polyAttrib );

		spin.addChild( new Shape3D( new Pyramid(), app) );
		
		BoundingSphere bounds = new BoundingSphere();
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
		Background bg = new Background(1.0f, 0.1f, 0.1f); // red
		bg.setApplicationBounds(bounds);
		root.addChild(bg);
		AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
		root.addChild(light);
		return root;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new Java3D_04()).setVisible(true);
	}

}
