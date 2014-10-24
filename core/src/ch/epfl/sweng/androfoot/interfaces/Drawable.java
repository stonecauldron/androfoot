package ch.epfl.sweng.androfoot.interfaces;

import java.util.Comparator;

/**
 * Represents an object that can be drow by a worldRenderer
 * @author Guillame Leclerc
 *
 */
public interface Drawable extends Visitable{
	/**
	 * Specify the z-index of a Drawable object (Objects are drawn in ascending order)
	 * @return an integer that represent the the priority given to an object.
	 */
	public int getZIndex();
	
	public final static Comparator<Drawable> DRAWABLE_COMPARATOR = new Comparator<Drawable>() {
		
		@Override
		public int compare(Drawable o1, Drawable o2) {
			return o1.getZIndex() - o2.getZIndex();
		}
	};
}
