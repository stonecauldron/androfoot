package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Mesh;

/**
 * Object able to build a Mesh Builder pattern
 * 
 * @author Guillame Leclerc
 *
 */
public interface MeshBuilder {
	/**
	 * Build the {@link Mesh}
	 * 
	 * @return the mesh
	 */
	Mesh build();
}
