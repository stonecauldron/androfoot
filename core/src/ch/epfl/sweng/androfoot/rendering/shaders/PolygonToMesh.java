package ch.epfl.sweng.androfoot.rendering.shaders;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * A class able to convert a {@link PolygonGenerator} to a {@link Mesh}
 * 
 * @author Guillaume
 *
 */
public class PolygonToMesh implements MeshBuilder {
	private static final int DIMENSION = 3;
	private final Mesh mesh;

	public PolygonToMesh(PolygonGenerator generator) {
		mesh = new Mesh(false, generator.generatePointsList().size(), 0,
						new VertexAttribute(Usage.Position, DIMENSION,
										ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(generator.generateVertexesFloatInZPlane(0f));
	}

	@Override
	public Mesh build() {
		return mesh;
	}

}
