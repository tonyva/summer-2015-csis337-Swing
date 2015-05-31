package lab5;

import javax.media.j3d.GeometryArray;
import javax.vecmath.TexCoord2f;

/*
 * This needs a lot of work. The textures are not set up correctly.
 * 
 * Some strange behavior with Java3D: setting the NUM_TEXTURE_POINTS to 6 breaks this. Why?
 * 
 */
public class MultitexturePyramid extends Pyramid {

	/*
	 * Set up Texture coordinates
	 *  -- we will use various points in the combined texture images
	 */
	private static final int[] TEXTURE_COORDINATES_SET_MAP = {0, 1};
	private static final int TEXTURE_COORDINATES_SET_COUNT = 2;
	private static final int NUM_TEXTURE1_POINTS = 5;
	private static TexCoord2f[] textureCoordinates1 = new TexCoord2f[NUM_TEXTURE1_POINTS];
	static {
		textureCoordinates1[0] = new TexCoord2f(   0,    0 );
		textureCoordinates1[1] = new TexCoord2f(  0.5f,  0 );
		textureCoordinates1[2] = new TexCoord2f(  0.5f,  1 );
		textureCoordinates1[3] = new TexCoord2f(   0,    1 );
		textureCoordinates1[4] = new TexCoord2f(   1,    1 );
/*		textureCoordinates1[5] = new TexCoord2f(   0,    1 );
*/
/*
 * It should be possible to have there be 6 texture points but (on a Mac OS X 10.5) it results in:
 * 
 Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 50
	at javax.media.j3d.GeometryArrayRetained.setTextureCoordinates(GeometryArrayRetained.java:4798)
	at javax.media.j3d.GeometryArray.setTextureCoordinates(GeometryArray.java:2228)
	at MultitexturePyramid.<init>(MultitexturePyramid.java:98)
	at Java3D_07a.createSceneGraph(Java3D_07a.java:201)
	at Java3D_07a.<init>(Java3D_07a.java:73)
	at Java3D_07a.main(Java3D_07a.java:255) 
 *
 * Have to debug this before releasing.
 * 
 */
	}
	private static final int NUM_TEXTURE2_POINTS = 5;
	private static TexCoord2f[] textureCoordinates2 = new TexCoord2f[NUM_TEXTURE2_POINTS];
	static {
		textureCoordinates2[0] = new TexCoord2f(   0,    0 );
		textureCoordinates2[1] = new TexCoord2f(  0.5f,  0 );
		textureCoordinates2[2] = new TexCoord2f(  0.5f,  1 );
		textureCoordinates2[3] = new TexCoord2f(   0,    1 );
		textureCoordinates2[4] = new TexCoord2f(   1,    1 );
/*		textureCoordinates2[5] = new TexCoord2f(   0,    1 );
*/
	};

	/*
	 * The texture indices need more work to wrap the two textures around the 
	 * pyramid in an interesting way.
	 * 
	 */
	final static int[] textureIndices1= {
		2, // map textureCoordinates1[	2	] to vertexIndices[	0	]  Triangle 1
		1, //							1						1
		0, //							0						2
		2, // map textureCoordinates1[	2	] to vertexIndices[	3	]  Triangle 2
		0, //							0						4
		3, //							3						5
		3, // map textureCoordinates1[	3	] to vertexIndices[ 6	] Triangle 3
		2, //							2						7
		4, //							4						8
		3, // map textureCoordinates1[	3	] to vertexIndices[	9	] Triangle 4
		4, //							4						10
		2, //							2						11
		0, // map textureCoordinates1[	2	] to vertexIndices[	12	] Triangle 5
		0, //							0						13
		0, //							1						14
		0, // map textureCoordinates1[	2	] to vertexIndices[	15	] Triangle 6
		0, //							0						16
		0  //							1						17
	};
	final static int[] textureIndices2= {
		2, // map textureCoordinates1[	2	] to vertexIndices[	0	]  Triangle 1
		1, //							1						1
		0, //							0						2
		2, // map textureCoordinates1[	2	] to vertexIndices[	3	]  Triangle 2
		0, //							0						4
		3, //							3						5
		3, // map textureCoordinates1[	3	] to vertexIndices[ 6	] Triangle 3
		2, //							2						7
		4, //							4						8
		3, // map textureCoordinates1[	3	] to vertexIndices[	9	] Triangle 4
		4, //							4						10
		2, //							2						11
		0, // map textureCoordinates1[	2	] to vertexIndices[	12	] Triangle 5
		0, //							0						13
		0, //							1						14
		0, // map textureCoordinates1[	2	] to vertexIndices[	15	] Triangle 6
		0, //							0						16
		0  //							1						17
		};

	public MultitexturePyramid() {
		super(NUMBER_VERTICES, 
				GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2,
				TEXTURE_COORDINATES_SET_COUNT, 
				TEXTURE_COORDINATES_SET_MAP, 
				NUMBER_INDICES);
		setTextureCoordinates( 0, 0, textureCoordinates1 );
		setTextureCoordinates( 1, 0, textureCoordinates2 );
		setTextureCoordinateIndices( 0, 0, textureIndices1 );
		setTextureCoordinateIndices( 1, 0, textureIndices2 );
	}

}
