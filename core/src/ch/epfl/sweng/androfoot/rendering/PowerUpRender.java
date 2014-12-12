/**
 * 
 */
package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ch.epfl.sweng.androfoot.polygongenerator.PowerUpPolygonGenerator;

/**
 * Render a PowerUp
 * @author Guillaume
 *
 */
public class PowerUpRender extends PolygonRenderer implements DrawableRenderer {

	public PowerUpRender(PowerUpPolygonGenerator generator) {
		super(generator);
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		GraphicEngine.getEngine().enableBlending();
		super.render(batch, shapes);
	}
}
