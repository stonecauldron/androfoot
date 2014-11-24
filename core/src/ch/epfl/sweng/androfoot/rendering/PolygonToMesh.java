package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

/**
 * Adapater class from a {@link PolygonGenerator} to {@link Mesh}
 * @author Guillame Leclerc
 *
 */
public class PolygonToMesh implements MeshBuilder {
	private final Mesh mesh;
	
	/**
	 * Build the adaptater from a {@link PolygonGenerator}
	 * @param generator the generator to use
	 */
	public PolygonToMesh(PolygonGenerator generator) {
		mesh = new Mesh(false, generator.generatePointsList().size(), 0, 
				new VertexAttribute(Usage.Position,3, "a_position"));
		mesh.setVertices(generator.generateVertexesFloatInZPlane(0f));
	}

	@Override
	public Mesh build() {
		return mesh;
	}

}
