package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Can be rendered by the graphic engine
 * 
 * @author Guillame Leclerc
 *
 */
public interface DrawableRenderer {

	/**
	 * Render this object
	 * 
	 * @param batch
	 *            the sprite batch
	 * @param shapes
	 *            the shape renderer
	 */
	void render(SpriteBatch batch, ShapeRenderer shapes);
}
