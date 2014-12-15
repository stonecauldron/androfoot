package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector2;

/**
 * A class able to rendre a {@link Mesh}
 * 
 * @author Guillaume
 *
 */
public interface MeshRenderer {

	void setPosition(Vector2 pos);

	void setRotation(float angle);

	void setZIndex(int zIndex);

	void setMesh(Mesh mesh);

	void render();

}
