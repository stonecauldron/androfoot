package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * Represent a generic Point in two dimensions
 * @author Guillame Leclerc
 *
 * @param <T> the type of the point coordinates
 */
public class ImmutablePoint<T> {
	
	/**
	 * the x coord of the point
	 */
	public final T x;
	/**
	 * the y coord of the point
	 */
	public final T y;
	
	/**
	 * Create a new immutable point
	 * @param xArg the x coordinate
	 * @param yArg the y coordinate 
	 */
	public ImmutablePoint(T xArg, T yArg) {
		x = xArg;
		y = yArg;
	}
}
