package lab6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Java3D_08
 *  
 * An example of 3D transformations  
 * 
 * @author Anthony Varghese
 */

public class Java3D_08 extends Frame {

	/**
	 * Data members
	 */
	private static final long serialVersionUID = 1L;
	/*  */
	private final String TITLE = "Java 3D tutorial 8: Transformations";
	private final int FRAME_X = 100;
	private final int FRAME_Y = 100;
	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 300;

	private Java3D_08_Panel button_panel;
	private TransformGroup basic_Xform;
	
	
	/*
	 * Java3D objects: SimpleUniverse is a class used to set up a scene in a
	 * Java3D program
	 */
	private SimpleUniverse univ;
	private GraphicsConfiguration gc;
	private Canvas3D canvas3D;
	private BranchGroup mainbranch;

	

	/**
	 * Default Constructor
	 * 
	 */
	public Java3D_08() {
		setTitle(TITLE);
		setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);

		/**
		 * Set the layout here
		 */
		LayoutManager layout = new BorderLayout();
		setLayout( layout );

		/*
		 * Buttons on the West side
		 */
		button_panel = new Java3D_08_Panel( );
		add( button_panel, BorderLayout.WEST );
		
		
		
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
		 * Start with a Transform3D object
		 */
		Transform3D finalXform = new Transform3D();
		
		/*
		 * Translate
		 */
		Transform3D translation = new Transform3D();
		Vector3f translate = new Vector3f( 0.3f, -0.1f, -0.4f);
		translation.set( translate );

		/*
		 * Scale
		 */
		Transform3D scale = new Transform3D();
		scale.setScale( new Vector3d( 1.3, 0.7, 0.8) );
		
		/*
		 * Rotate about x axis
		 */
		Transform3D rotateX = new Transform3D();
		rotateX.rotX( -Math.PI/10 );
		
		/*
		 * Rotate about Y axis 
		 */
		Transform3D rotateY = new Transform3D();
		rotateY.rotY( Math.PI/12 );
		
		/*
		 * Rotate about Z axis 
		 */
		Transform3D rotateZ = new Transform3D();
		rotateZ.rotY( Math.PI/12 );

		/*
		 * Rotate about a diagonal axis
		 */
		Transform3D rotateYZ = new Transform3D();
		rotateYZ.set(  new AxisAngle4d( 0, 1, 1,  Math.PI/6 ) );
		
		/*
		 * Shear in x-y
		 */
		Transform3D shearxy = new Transform3D();
		double[] sh_xy = {1,  1.05, 0,   0,
				          0,  1,    0,   0,
				          0,  0,    1,   0,
				          0,  0,    0,   1};
		shearxy.set( sh_xy );

		/*
		 * Shear in y-z
		 */
		Transform3D shearyz = new Transform3D();
		double[] sh_yz = {1,  0,    0,   0,
				          0,  1,  1.05,  0,
				          0,  0,    1,   0,
				          0,  0,    0,   1};
		shearyz.set( sh_yz );
		
		/*
		 * Shear in x-z
		 */
		Transform3D shearxz = new Transform3D();
		double[] sh_xz = {1,  0, 1.05,   0,
				          0,  1,    0,   0,
				          0,  0,    1,   0,
				          0,  0,    0,   1};
		shearxz.set( sh_xz );

		/*
		 * Shear in z-x
		 */
		Transform3D shearzx = new Transform3D();
		double[] sh_zx = {1,    0,    0,   0,
				          0,    1,    0,   0,
				          1.05, 0,    1,   0,
				          0,    0,    0,   1};
		shearzx.set( sh_zx );

		/*
		 * Shear in z-y
		 */
		Transform3D shearzy = new Transform3D();
		double[] sh_zy = {1,  0,    0,   0,
				          0,  1,    0,  0,
				          0,  1.05, 1,   0,
				          0,  0,    0,   1};
		shearzy.set( sh_zy );

		/*
		 * Shear in y-x
		 */
		Transform3D shearyx = new Transform3D();
		double[] sh_yx = {1,    0,    0,   0,
				          1.05, 1,    0,   0,
				          0,    0,    1,   0,
				          0,    0,    0,   1};
		shearyx.set( sh_yx );

		
		/*
		 * Make a composite transform from the ones we set up
		 */
		finalXform.mul( translation );
		finalXform.mul( scale );
		finalXform.mul( rotateX );
		finalXform.mul( rotateY );
		finalXform.mul( rotateZ );
		finalXform.mul( rotateYZ );
//		finalXform.mul( shearxy );
//		finalXform.mul( shearyz );
//		finalXform.mul( shearxz );
//		finalXform.mul( shearzx );
//		finalXform.mul( shearzy );
		finalXform.mul( shearyx );
		
		/*
		 * Set up a basic transform node to use the Tansform3D object we just set up.
		 */
		basic_Xform = new TransformGroup( finalXform );
		basic_Xform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		basic_Xform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild( basic_Xform );
		
		/*
		 * Add Shape3D objects
		 * 
		 * The appearance is part (along with the lighting) of what determines
		 * hoe the object will look
		 */
		Appearance app = new Appearance();
		PolygonAttributes polygonAttrib = new PolygonAttributes();
		polygonAttrib.setCullFace(PolygonAttributes.CULL_NONE);
		polygonAttrib.setBackFaceNormalFlip(true);
		app.setPolygonAttributes(polygonAttrib);
		
		final Color3f WHITE   = new Color3f( Color.WHITE   );
		final Color3f MAGENTA = new Color3f( Color.MAGENTA );
		final Color3f RED     = new Color3f( Color.RED     );
		final Color3f CYAN    = new Color3f( Color.CYAN    );
		final Color3f ORANGE  = new Color3f( Color.ORANGE  );
		final Color3f BLUE    = new Color3f( Color.BLUE    );
		final Color3f BLACK   = new Color3f( Color.BLACK   );
		final Color3f GREEN   = new Color3f( Color.GREEN   );

		
		Material material = new Material(
				WHITE,  /* Ambient Color */
				WHITE,  /* Emissive Color */
				WHITE,  /* Diffuse Color */
				WHITE,  /* Specular Color */
				50 /* Shininess: 1 == not shiny; 128 == all shine */
			);
		System.out.println("Lighting enable state for material is: " + material.getLightingEnable() );
		Material shinyRed = new Material(
				/* ambient   = */  WHITE,
				/* emissive  = */  BLACK, 
				/* diffuse   = */  RED,
				/* specular  = */  WHITE,
				/* shininess = */  50 );
		
		app.setMaterial(material);

		basic_Xform.addChild(new Shape3D(new Pyramid(), app));

		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(10.0);

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

	/*
	 * paint
	 */
	public void paint(Graphics g){
		super.paint( g );
		
		Transform3D xf = new Transform3D();
		basic_Xform.getTransform( xf );
		Transform3D change = new Transform3D();

		switch ( button_panel.getStatus() ){
			case 0:
				change.setRotation( new AxisAngle4d( 1, 0, 0, Math.PI/5 ) );
				break;
			case 1:
				change.setRotation( new AxisAngle4d( 0, 1, 0, Math.PI/5 ) );
				break;
			case 2:
				change.setRotation( new AxisAngle4d( 0, 0, 1, Math.PI/5 ) );
				break;
			case 3:
				change.setScale( new Vector3d( 1.1, 0.8, 0.9));
				break;
			default:
				change.setScale( new Vector3d( 1.1, 0.8, 0.9));
		}
		basic_Xform.setTransform( change );
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Java3D_08 window = new Java3D_08();
		window.setVisible(true);
	}
}