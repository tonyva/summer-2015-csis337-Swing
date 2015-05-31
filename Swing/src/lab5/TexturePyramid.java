package lab5;

import javax.media.j3d.GeometryArray;
import javax.vecmath.TexCoord2f;


public class TexturePyramid extends Pyramid {

	/*
	 * Set up Texture coordinates
	 *  -- we will use the four corners of the texture image
	 */
	private static final int NUM_TEXTURE_POINTS = 5;
	private static TexCoord2f[] textureCoordinates1 = new TexCoord2f[NUM_TEXTURE_POINTS];
	static {
		textureCoordinates1[0] = new TexCoord2f(   0,    0 );
		textureCoordinates1[1] = new TexCoord2f(   1,    0 );
		textureCoordinates1[2] = new TexCoord2f(   1,    1 );
		textureCoordinates1[3] = new TexCoord2f(   0,    1 );
		textureCoordinates1[4] = new TexCoord2f(  0.5f, 0.5f );		
	}
	/*
	 * The array of texture indices will map the texture coordinates
	 * to the vertices of the pyramid
	 * 
	 * The numbers in the textureIndices array correspond to the
	 *  vertices in the vertexIndices array in the Pyramid class.
	 *  
	 */
	final static int[] textureIndices1= { 2, // map textureCoordinates1[2] to vertexIndices[ 0]  Triangle 1
										    0, //                         0                    1
										    1, //						  1					   2
										    2, // map textureCoordinates1[2] to vertexIndices[ 3]  Triangle 2
										    0, //                         0                    4
										    1, //						  1					   5
										    2, // map textureCoordinates1[2] to vertexIndices[ 6]  Triangle 3
										    0, //                         0                    7
										    1, //						  1					   8
										    4, // map textureCoordinates1[4] to vertexIndices[ 9]  Triangle 4
										    1, //                         3                   10
										    0, //						  1				      11
										    2, // map textureCoordinates1[2] to vertexIndices[12]  Triangle 5
										    0, //                         0                   13
										    1, //						  1					  14
										    2, // map textureCoordinates1[2] to vertexIndices[15 ] Triangle 6
										    0, //                         0                   16
										    1  //						  1					  17
										};

	public TexturePyramid() {
		super(NUMBER_VERTICES, GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2,
				NUMBER_INDICES);
		this.setTextureCoordinates( 0, 0, textureCoordinates1 );
		this.setTextureCoordinateIndices( 0, 0, textureIndices1 );
	}
}
