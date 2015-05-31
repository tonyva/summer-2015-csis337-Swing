package lab6;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class SmallPyramid extends IndexedTriangleArray {
	public static final int NUMBER_VERTICES = 5;
	public static final double START_WIDTH = 0.1;
	public static final double START_HEIGHT = 0.2;
	private static Point3d[] coordinates = new Point3d[NUMBER_VERTICES];
	static {
		// The four corners of the base
		coordinates[0] = new Point3d( START_WIDTH / 2, -START_HEIGHT / 2,  START_WIDTH / 2);
		coordinates[1] = new Point3d(-START_WIDTH / 2, -START_HEIGHT / 2,  START_WIDTH / 2);
		coordinates[2] = new Point3d(-START_WIDTH / 2, -START_HEIGHT / 2, -START_WIDTH / 2);
		coordinates[3] = new Point3d( START_WIDTH / 2, -START_HEIGHT / 2, -START_WIDTH / 2);
		// The top
		coordinates[4] = new Point3d(0, START_HEIGHT / 2, 0);
	} // end static block
	private static Vector3f[] normals = new Vector3f[NUMBER_VERTICES];
	static {
		normals[0] = new Vector3f(1, -1, 1);
		normals[1] = new Vector3f(-1, -1, 1);
		normals[2] = new Vector3f(-1, -1, -1);
		normals[3] = new Vector3f(1, -1, -1);
		normals[4] = new Vector3f(0, 1, 0);

		for (Vector3f v : normals)
			v.normalize();
	} // end static block

	/*
	 * Define the 6 triangles for the pyramid: The four sides and the base which
	 * will be made up of two triangles.
	 */
	final static int[] vertexIndices = { 4, 0, 1, // Side 1
			4, 1, 2, // Side 2
			4, 2, 3, // Side 3
			4, 3, 0, // Side 4
			0, 1, 2, // Two triangles for the base
			0, 2, 3 };
	final static int[] normalIndices = { 0, 0, 0, // Side 1
			1, 1, 1, // Side 2
			2, 2, 2, // Side 3
			3, 3, 3, // Side 4
			4, 4, 4, // Two triangles for the base
			4, 4, 4 };
	final static int NUMBER_INDICES = 18; // vertexIndices.length;

	/**
	 * Constructor
	 * 
	 */
	SmallPyramid() {
		this(NUMBER_VERTICES, GeometryArray.COORDINATES | GeometryArray.NORMALS, NUMBER_INDICES);

	}

	public SmallPyramid(int vertexCount, int vertexFormat, int texCoordSetCount,
			int[] texCoordSetMap, int indexCount) {
		super(vertexCount, vertexFormat, texCoordSetCount, texCoordSetMap, indexCount);
		
	}

	public SmallPyramid(int vertexCount, int vertexFormat, int indexCount) {
		super(vertexCount, vertexFormat, indexCount);

		setCoordinates(0, coordinates);
		setCoordinateIndices(0, vertexIndices);

		setNormals(0, normals);
		setNormalIndices(0, normalIndices);
	}
} // end Pyramid class