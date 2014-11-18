package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * Represent a generic Point in two dimensions
 * 
 * @author Guillame Leclerc
 *
 * @param <T>
 *            the type of the point coordinates
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
	 * 
	 * @param xArg
	 *            the x coordinate
	 * @param yArg
	 *            the y coordinate
	 */
	public ImmutablePoint(T xArg, T yArg) {
		x = xArg;
		y = yArg;
	}
	
	@Override
	public String toString() {
		return "(" + x.toString() + "," + y.toString() + ")";
	}

	@Override
	public boolean equals(Object arg0) {
		System.out.println("test equality");
		if (arg0 == null) {
			return false;
		} else if (!(arg0 instanceof ImmutablePoint<?>)) {
			return false;
		} else {
			try {
				@SuppressWarnings("unchecked")
				ImmutablePoint<T> val = (ImmutablePoint<T>) arg0;
				return val.x.equals(x) && val.y.equals(y);
			} catch (ClassCastException e) {
				return false;
			}
		}
	}
}
