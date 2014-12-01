package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represent an object which can be drawn as a rectangle
 * @author Guillame Leclerc
 *
 */
public interface DrawableRectangle extends Drawable {
	/**
	 * get the color of the rectangle
	 * @return the color to fill the rectangle with
	 */
	Color getColor();

	/**
	 * Get the shape of the rectangle
	 * @return a {@link Rectangle} object that contain all the information about the shape
	 */
	Rectangle getShape();
}
