package ch.epfl.sweng.androfoot.interfaces;

public interface PolygonMap {
	/**
	 * Get the corresponding PolygonGenerator
	 * @param key the key of the generator
	 * @return the requested polygon
	 *  @throws RuntimeException if the key is not found
	 */
	public PolygonGenerator get(String key);
}
