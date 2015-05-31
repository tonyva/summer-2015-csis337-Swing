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
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Java3D_03
 * A basic example of Java3D - various shapes revolving around a center. 
 * 
 * @author Anthony Varghese
 */

public class Java3D_03 extends Frame {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	/*  */
	private final String TITLE = "Java 3D tutorial 2: Basic Shapes";
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

	public Java3D_03() {
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
	 * createPointArray
	 *  -- sets up a PointArray object with 5 points 4 corners
	 *     of a rectangle and a point at the origin.
	 * 
	 * @return PointArray
	 */
	PointArray createPointArray() {
		final int NUM_POINTS = 5;
		PointArray points = new PointArray(NUM_POINTS, GeometryArray.COORDINATES);
		Point3f[] pointCoords = new Point3f[NUM_POINTS];
		pointCoords[0] = new Point3f(0, 0, 0);
		pointCoords[1] = new Point3f(0.5f, 0.5f, -0.5f);
		pointCoords[2] = new Point3f(-0.5f, -0.5f, -0.5f);
		pointCoords[3] = new Point3f(0.5f, 0.5f, -1.0f);
		pointCoords[4] = new Point3f(-0.5f, -0.5f, -1.0f);
		points.setCoordinates(0, pointCoords);
		return points;
	}

	/**
	 * createLineArray
	 *  -- sets up a LineStripArray object with 4 lines (6 points)
	 *     The first 3 joined end to end to make a triangle.
	 * 
	 * @return LineArray
	 */
	LineStripArray createLineArray() {
		final int[] vCounts = { 4, 2 };
		final int NUM_LINE_POINTS = vCounts[0] + vCounts[1];
		/**
		 * 
		 */
		LineStripArray lines = new LineStripArray(NUM_LINE_POINTS, GeometryArray.COORDINATES, vCounts);
		Point3d[] linePoints = new Point3d[NUM_LINE_POINTS];
		/*
		 * The triangle:
		 */
		linePoints[0] = new Point3d(-0.5, -0.5, 0.2);
		linePoints[1] = new Point3d(0.5, -0.5, -0.2);
		linePoints[2] = new Point3d(0.0, 0.7, 0.0);
		linePoints[3] = linePoints[0];
		/*
		 * Another line not connected to the other three
		 */
		linePoints[4] = new Point3d( 0.0, -0.1, 1.0 );
		linePoints[5] = new Point3d( 0.0, -0.2, -1.0 );
		
		lines.setCoordinates(0, linePoints);
		return lines;
	}

	/**
	 * createSimpleTriangleArray
	 *  -- sets up a TriangleArray with 2 triangles i.e. 6 points
	 * 
	 * @return TriangleArray
	 */
	TriangleArray createSimpleTriangleArray() {
		final int NUM_POINTS = 6;
		TriangleArray triangles = new TriangleArray( NUM_POINTS, GeometryArray.COORDINATES);
		Point3d[] triangleCoords = new Point3d[NUM_POINTS];
		/*
		 * The first triangle is constructed in the counter clockwise direction
		 */
		triangleCoords[0] = new Point3d( -0.5,  -0.5, 0.05);
		triangleCoords[1] = new Point3d( -0.0,  -0.5, 0.05);
		triangleCoords[2] = new Point3d( -0.25, -0.1,  0.05);

		/*
		 * The second triangle is made in the clockwise direction
		 */
		triangleCoords[3] = new Point3d(  0.5,   -0.5,  0.1);
		triangleCoords[4] = new Point3d(  0.05,  -0.5,  0.1);
		triangleCoords[5] = new Point3d(  0.225, -0.1,  0.1);

		triangles.setCoordinates( 0, triangleCoords );
		return triangles;
	}

	/**
	 * createColorTriangleArray
	 *  -- sets up a TriangleArray with 2 triangles i.e. 6 points + 6 Colors
	 * 
	 * @return TriangleArray
	 */
	TriangleArray createColorTriangleArray() {
		final int NUM_POINTS = 6;
		TriangleArray triangles = new TriangleArray( NUM_POINTS, 
				GeometryArray.COORDINATES | GeometryArray.COLOR_3 );
		Point3d[] triangleCoords = new Point3d[NUM_POINTS];
		/* The first triangle is constructed in the counter clockwise direction */
		triangleCoords[0] = new Point3d( -0.5,   0.0, 0.05);
		triangleCoords[1] = new Point3d( -0.0,   0.0, 0.05);
		triangleCoords[2] = new Point3d( -0.25,  0.4,  0.05);
		/* The second triangle is made in the clockwise direction */
		triangleCoords[3] = new Point3d(  0.5,   0.0,  0.1);
		triangleCoords[4] = new Point3d(  0.05,  0.0,  0.1);
		triangleCoords[5] = new Point3d(  0.225, 0.4,  0.1);
		triangles.setCoordinates( 0, triangleCoords );

		/*
		 * Set up colors -- a different color for each corner
		 */
		Color3f[] triangleColors = new Color3f[NUM_POINTS];
		triangleColors[0] = new Color3f(  1.0f,  0.0f, 0.0f);
		triangleColors[1] = new Color3f(  0.0f,  1.0f, 0.0f);
		triangleColors[2] = new Color3f(  0.0f,  0.0f, 1.0f);
		triangleColors[3] = triangleColors[0];
		triangleColors[4] = triangleColors[1];
		triangleColors[5] = triangleColors[2];
		triangles.setColors( 0, triangleColors );

		return triangles;
	}

	/**
	 * createCone_TriangleArray
	 *  -- sets up a TriangleFanArray to create a cone.
	 * 
	 * @return TriangleFanArray
	 */
	TriangleFanArray createCone_TriangleArray() {
		/*
		 * The base of the cone is a circle and is divided into a number of 
		 * lines and each of these line is joined with the "top" or the 
		 * point of the cone to form triangles.
		 * 
		 */
		final double Z_TOP  = -0.75;
		final double Z_BASE = -0.5;
		final double BASE_WIDTH = 0.2;
		final int NUMBER_OF_TRIANGULAR_PATCHES = 30;
		final int[] vCounts = {NUMBER_OF_TRIANGULAR_PATCHES+2};
		Point3d top = new Point3d( 0, 0, Z_TOP );
		
		final int NUMBER_OF_POINTS = 3 * NUMBER_OF_TRIANGULAR_PATCHES;
		final double Two_PI_Div_N = 2 * Math.PI / NUMBER_OF_TRIANGULAR_PATCHES;
		
		TriangleFanArray cone = new TriangleFanArray( NUMBER_OF_POINTS,
												GeometryArray.COORDINATES, vCounts );
		cone.setCoordinate(0, top );
		for (int i=0; i<=NUMBER_OF_TRIANGULAR_PATCHES; i++ ){
			double x = BASE_WIDTH * Math.cos( i * Two_PI_Div_N );
			double y = BASE_WIDTH * Math.sin( i * Two_PI_Div_N );
			Point3d end = new Point3d( x, y, Z_BASE );
			cone.setCoordinate(i+1, end );
		}
		/*
		 * To add colors
		 */
		// cone.setColors(index, colors);
		return cone;
	}

	/**
	 * createCube_IndexedQuadArray
	 *  -- sets up a IndexedQuadArray to create a cube.
	 * 
	 * @return IndexedQuadArray
	 */
	IndexedQuadArray createCube_IndexedQuadArray() {
		/*
		 * Six values are needed to specify the position of
		 * a box alingned with the 3 axes:
		 */
		final double MAX_X =  0.8;
		final double MAX_Y =  0.7;
		final double MAX_Z =  0.1;
		final double MIN_X =  0.6;
		final double MIN_Y =  0.5;
		final double MIN_Z = -0.1;
		
		final int NUMBER_VERTICES = 8;
		Point3d[] cubeCorners = new Point3d[ NUMBER_VERTICES ];
		cubeCorners[0] = new Point3d(MIN_X, MIN_Y, MIN_Z);
		cubeCorners[1] = new Point3d(MAX_X, MIN_Y, MIN_Z);
		cubeCorners[2] = new Point3d(MAX_X, MAX_Y, MIN_Z);
		cubeCorners[3] = new Point3d(MIN_X, MAX_Y, MIN_Z);
		cubeCorners[4] = new Point3d(MIN_X, MIN_Y, MAX_Z);
		cubeCorners[5] = new Point3d(MAX_X, MIN_Y, MAX_Z);
		cubeCorners[6] = new Point3d(MAX_X, MAX_Y, MAX_Z);
		cubeCorners[7] = new Point3d(MIN_X, MAX_Y, MAX_Z);

		
		final int[] vertexIndices = { 0, 1, 2, 3, // Wall 1
				4, 5, 6, 7, // Wall 2
				5, 1, 2, 6, // Wall 3
				4, 0, 3, 7, // Wall 4
				6, 2, 3, 7, // Ceiling
				5, 1, 0, 4  // Floor
			  };
		final int NUMBER_VERTEXINDICES = vertexIndices.length;

		IndexedQuadArray cube = new IndexedQuadArray( NUMBER_VERTICES,
								GeometryArray.COORDINATES, NUMBER_VERTEXINDICES );
		cube.setCoordinates(0, cubeCorners);
		cube.setCoordinateIndices( 0, vertexIndices );
		/*
		 * To add colors:
		 */
		// cube.setColorIndices( 0, colorIndices );

		return cube;
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
		Appearance basicapp = new Appearance();

		Shape3D pointShape = new Shape3D(createPointArray(), basicapp);
		spin.addChild(pointShape);

		Shape3D polygon = new Shape3D(createLineArray(), basicapp);
		spin.addChild(polygon);

		Shape3D tri = new Shape3D( createSimpleTriangleArray(), basicapp );
		spin.addChild( tri );

		Shape3D colortri = new Shape3D( createColorTriangleArray(), basicapp );
		spin.addChild( colortri );

		Appearance app = new Appearance();
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace( PolygonAttributes.CULL_NONE );
		app.setPolygonAttributes( polyAttrib );
		
		Shape3D cone = new Shape3D( createCone_TriangleArray(), basicapp );
		spin.addChild( cone );
		
		Shape3D cube = new Shape3D( createCube_IndexedQuadArray(), basicapp );
		spin.addChild( cube );
		
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
		(new Java3D_03()).setVisible(true);
	}

}
