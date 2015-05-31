package lab5;

import javax.media.j3d.GeometryArray;
import javax.vecmath.Color3f;


public class ColorPyramid extends Pyramid {

	/*
	 * Set up colors -- a different color for each corner
	 */
	private static Color3f[] vertexColors = new Color3f[NUMBER_VERTICES];
	static {
		vertexColors[0] = new Color3f(  1.0f,  0.0f, 0.0f);
		vertexColors[1] = new Color3f(  0.0f,  1.0f, 0.0f);
		vertexColors[2] = new Color3f(  0.0f,  0.0f, 1.0f);
		vertexColors[3] = new Color3f(  0.0f,  1.0f, 1.0f);
		vertexColors[4] = new Color3f(  1.0f,  1.0f, 0.0f);
	}
	final static int[] colorIndices1= { 0, 0, 0, // Side 1
										1, 1, 1, // Side 2
										2, 2, 2, // Side 3
										3, 3, 3, // Side 4
										0, 0, 0, // Two triangles for the base
										1, 1, 1 
									  };
	final static int[] colorIndices2= { 4, 0, 1, // Side 1
										4, 1, 2, // Side 2
										4, 2, 3, // Side 3
										4, 3, 0, // Side 4
										0, 1, 2, // Two triangles for the base
										0, 2, 3 
									 };
	public ColorPyramid() {
		super(NUMBER_VERTICES, GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.COLOR_3,
				NUMBER_INDICES);

		setColors( 0, vertexColors );
		setColorIndices(0, colorIndices1);
	}

}
