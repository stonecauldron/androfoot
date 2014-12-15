package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represent a frame buffer
 * 
 * @author Guillaume
 *
 */
public interface DrawToTexture {

	void resetTexture();

	void beginCapture();

	void endCapture();

	TextureRegion getOutput();
}
