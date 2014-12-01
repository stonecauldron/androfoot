/**
 * 
 */
package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.PowerUpPolygonGenerator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Render a PowerUp
 * @author Guillaume
 *
 */
public class PowerUpRender extends PolygonRenderer implements DrawableRenderer {

	public PowerUpRender(PowerUpPolygonGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}
}
