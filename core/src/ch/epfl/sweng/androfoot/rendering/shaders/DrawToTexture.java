package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface DrawToTexture {
	
	public void resetTexture();
	public void beginCapture();
	public void endCapture();
	public TextureRegion getOutput();
}
