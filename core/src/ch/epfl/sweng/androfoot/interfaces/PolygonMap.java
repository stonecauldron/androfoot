package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a Map of polygons where the key is a string
 * @author Guillame Leclerc
 *
 */
public interface PolygonMap {
	/**
	 * Get the corresponding PolygonGenerator
	 * @param key the key of the generator
	 * @return the requested polygon
	 *  @throws RuntimeException if the key is not found
	 */
	public PolygonGenerator get(String key);
}
