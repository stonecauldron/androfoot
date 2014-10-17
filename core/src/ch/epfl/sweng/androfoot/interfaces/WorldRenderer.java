package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.Screen;

/**
 * Represent a object able to represent a DrawableWorld on the screen
 * @author Guillame Leclerc
 *
 */
public interface WorldRenderer {
	
	/**
	 * specify the world to render
	 * @param world the world
	 */
	public void bindToWorld(DrawableWorld world);

}
