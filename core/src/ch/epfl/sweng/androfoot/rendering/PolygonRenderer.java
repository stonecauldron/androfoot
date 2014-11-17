package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * A class able to render a Polygon 
 * @author Guillame Leclerc
 *
 */
public class PolygonRenderer extends AbstractMeshRenderer {
	
	/**
	 * Build the renderer from a polygon generator
	 * @param generator
	 */
	public PolygonRenderer(PolygonGenerator generator) {
		super(new PolygonToMesh(generator).build());
	}
}
