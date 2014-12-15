/**
 * 
 */
package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ch.epfl.sweng.androfoot.polygongenerator.PowerUpPolygonGenerator;

/**
 * Render a PowerUp
 * 
 * @author Guillaume
 *
 */
public class PowerUpRender extends PolygonRenderer implements DrawableRenderer {

	private final static float ROTATE_SPEED = 3f;

	private float angle = 0;

	public PowerUpRender(PowerUpPolygonGenerator generator) {
		super(generator);
	}

	public void rotate(float delta) {
		angle += ROTATE_SPEED * delta;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		GraphicEngine.getEngine().enableBlending();
		super.setRotation(angle);
		super.render(batch, shapes);
	}
}
