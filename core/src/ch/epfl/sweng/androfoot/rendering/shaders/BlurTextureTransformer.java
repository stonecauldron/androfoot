package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

/**
 * A transformer to apply a blur on a texture
 * 
 * @author Guillame Leclerc
 *
 */
public class BlurTextureTransformer extends AbstractTextureTransformer {
	private final int blurX;
	private final int blurY;
	private final Matrix4 transformMatrix;
	private int shaderForceXPosition;
	private int shaderForceYPosition;

	/**
	 * Init the transformer
	 * 
	 * @param blurForceX
	 *            the force of the blux on the X axis
	 * @param blurForceY
	 *            the force of the blur on the Y axis
	 * @param widthArg
	 *            the width of the texture to transform
	 * @param heightArg
	 *            the height of the texture to transform
	 */
	public BlurTextureTransformer(int blurForceX, int blurForceY, int widthArg,
					int heightArg) {
		super(widthArg + 2 * blurForceX, heightArg + 2 * blurForceY);
		blurX = blurForceX;
		blurY = blurForceY;
		transformMatrix = new Matrix4().translate(blurX, blurY, 0f);
	}

	@Override
	protected String getVertexShader() {
		String textCoordName = ShaderProgram.TEXCOORD_ATTRIBUTE + "0";
		return "" + "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";"
						+ "attribute vec4 " + textCoordName + ";"
						+ "uniform mat4 u_projTrans;"
						+ "varying vec4 v_texCoord;" + "void main() {"
						+ "v_texCoord = " + textCoordName + ";"
						+ "gl_Position = " + ShaderProgram.POSITION_ATTRIBUTE
						+ ";" + "}";
	}

	@Override
	protected String getFragmentShader() {
		return "#ifdef GL_ES\r\n"
						+ "    precision mediump float;\r\n"
						+ "#endif"
						+ "uniform int u_blurX;"
						+ "uniform int u_blurY;"
						+ "uniform Sampler2D u_texture"
						+ "varying vec4 v_texCoord;"
						+ "void main() {"
						+ "vec4 accumulator = vec4(0.0);"
						+ "for(int i = -u_blurX ; i <= u_blurX ; i++) {"
						+ "for(int j = -u_blurY ; i <= u_blurY ; j++) {"
						+ "accumulator += texture2D(u_texture, v_texCoord + vec4(i,j,0.0,0.0));"
						+ "}"
						+ "gl_FragColor = accumulator/(u_blurY * u_blurX);"
						+ "}";
	}

	@Override
	protected void setShaderArguments(ShaderProgram shader) {
		shader.setUniformi(shaderForceXPosition, blurX);
		shader.setUniformi(shaderForceYPosition, blurY);
	}

	@Override
	protected void configureSpriteBatch(SpriteBatch batch) {
		batch.setTransformMatrix(transformMatrix);
	}

	@Override
	protected void setupShader(ShaderProgram shader) {
		shaderForceXPosition = shader.getUniformLocation("u_blurX");
		shaderForceYPosition = shader.getUniformLocation("u_blurY");
	}

}
