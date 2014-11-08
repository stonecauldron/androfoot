package ch.epfl.sweng.androfoot.rendering.test;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

/**
 * Can Take and handle screenshots
 * 
 * @author Guillame Leclerc
 *
 */
final class ScreenShotTaker {
	public static void saveScreenshot(FileHandle file) {
		Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), true);
		byte[] bytes;

		try {
			bytes = PNG.toPNG(pixmap);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		boolean append = false;
		file.writeBytes(bytes, append);
	}

	public static Pixmap getScreenshot(int x, int y, int w, int h, boolean flipY) {
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);

		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE,
				pixels);

		final int numBytes = w * h * 4;
		byte[] lines = new byte[numBytes];
		if (flipY) {
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
		} else {
			pixels.clear();
			pixels.get(lines);
		}

		return pixmap;
	}

}
