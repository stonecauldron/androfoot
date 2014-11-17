package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface TextureTransformer {
	
	public TextureRegion transform(TextureRegion input);

}
