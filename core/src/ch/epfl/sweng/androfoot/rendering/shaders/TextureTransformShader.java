package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A Shader that render a texture with an effect
 * @author Guillaume
 *
 */
public interface TextureTransformShader {

	void beginCapture();

	void endCapture();

	TextureRegion getOutput();
}
