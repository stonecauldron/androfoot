package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**
 * Basic implementation of {@link DrawToTexture}
 * 
 * @author Guillame Leclerc
 *
 */
public class ConcreteDrawToTexture implements DrawToTexture {

	private static final Format FRAME_BUFFER_FORMAT = Format.RGBA8888;
	private final FrameBuffer frameBuffer;
	private final TextureRegion result;
	private final int width;
	private final int height;

	/**
	 * Init the capturer
	 * 
	 * @param widthArg
	 *            width to caputre
	 * @param heightArg
	 *            height to caputre
	 */
	public ConcreteDrawToTexture(int widthArg, int heightArg) {
		width = widthArg;
		height = heightArg;
		frameBuffer = new FrameBuffer(FRAME_BUFFER_FORMAT, width, height, true);
		result = new TextureRegion(frameBuffer.getColorBufferTexture());
		result.flip(false, true);
	}

	@Override
	public void beginCapture() {
		frameBuffer.begin();
	}

	@Override
	public void endCapture() {
		frameBuffer.end();
	}

	@Override
	public TextureRegion getOutput() {
		return result;
	}

	@Override
	public void resetTexture() {
		FrameBuffer.clearAllFrameBuffers(Gdx.app);
		frameBuffer.begin();
		Gdx.gl.glClearColor(0, 0, 0, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		frameBuffer.end();
	}

}
