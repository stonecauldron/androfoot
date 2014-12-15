package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Partial implementation of a {@link TextureTransformer}
 * 
 * @author Guillame Leclerc
 *
 */
public abstract class AbstractTextureTransformer implements TextureTransformer {

	private final ShaderProgram shader;
	private final SpriteBatch innerBatch;
	private DrawToTexture texturer;

	/**
	 * Initiate the transformer with the size of the texture
	 * 
	 * @param widthArg
	 *            the width of the texture
	 * @param heightArg
	 *            the height of the texture
	 */
	public AbstractTextureTransformer(int widthArg, int heightArg) {
		shader = new ShaderProgram(getVertexShader(), getFragmentShader());
		texturer = getTexturer(widthArg, heightArg);

		if (!shader.isCompiled()) {
			throw new GdxRuntimeException(shader.getLog());
		}

		innerBatch = new SpriteBatch();
		innerBatch.setShader(shader);
	}

	/**
	 * Allow the subclasses to choose their implementation of
	 * {@link DrawToTexture} (GOF factory)
	 * 
	 * @param width
	 *            the width of the texture
	 * @param height
	 *            the height of the texture
	 * @return
	 */
	protected DrawToTexture getTexturer(int width, int height) {
		return new ConcreteDrawToTexture(width, height);
	}

	/**
	 * Give the vertexShader use to transform the texture
	 * 
	 * @return the vertex shader
	 */
	protected abstract String getVertexShader();

	/**
	 * Give the fragment shader use to transform the texture
	 * 
	 * @return the fragment shader
	 */
	protected abstract String getFragmentShader();

	/**
	 * Callback to allow the subclasses to set the arguments on the shader
	 * (after shader.begin())
	 * 
	 * @param shader
	 *            the current shader
	 */
	protected abstract void setShaderArguments(ShaderProgram shaderArg);

	/**
	 * Callback to allow the subclasses to configure the spriteBatch before it
	 * is used to render the texture
	 * 
	 * @param batch
	 *            the sprite batch the class has to configure
	 */
	protected abstract void configureSpriteBatch(SpriteBatch batch);

	/**
	 * Callback to allow the subclasses to do a setup on their shader (after
	 * compilation)
	 * 
	 * @param shader
	 *            the shader to configure
	 */
	protected abstract void setupShader(ShaderProgram shaderArg);

	@Override
	public TextureRegion transform(TextureRegion input) {
		setShaderArguments(shader);
		texturer.resetTexture();
		texturer.beginCapture();
		innerBatch.begin();
		innerBatch.draw(input, 0, 0);
		innerBatch.end();
		texturer.endCapture();
		return texturer.getOutput();
	}

}
