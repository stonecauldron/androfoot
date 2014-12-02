package ch.epfl.sweng.androfoot.interfaces;

import java.util.SortedSet;

import com.badlogic.gdx.math.Rectangle;

/**
 * Represent a Set of object that can be represented by a World renderer 
 * @author Guillame Leclerc
 *
 */
public interface DrawableWorld {
	
	/**
	 * Return the set of all object that has to be drawn 
	 * @return a set of Drawable objects
	 */
	SortedSet<Drawable> toDraw();

	/**
	 * Specify the space occupied by the world (must not change during runtime)
	 * @return a rectangle representing this area 
	 */
	Rectangle regionToDraw();
	
	

}
