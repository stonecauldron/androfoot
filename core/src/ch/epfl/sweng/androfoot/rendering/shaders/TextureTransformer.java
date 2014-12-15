package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class which transform a texture to another texture
 * 
 * @author Guillaume
 *
 */
public interface TextureTransformer {

	TextureRegion transform(TextureRegion input);

}
