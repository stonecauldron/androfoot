package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Mesh;

/**
 * A class able to output a {@link Mesh}
 * @author Guillaume
 *
 */
public interface MeshBuilder {
	Mesh build();
}
