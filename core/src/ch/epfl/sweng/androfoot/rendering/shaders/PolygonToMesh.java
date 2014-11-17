package ch.epfl.sweng.androfoot.rendering.shaders;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.rendering.shaders.MeshBuilder;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class PolygonToMesh implements MeshBuilder {
	private final Mesh mesh;
	
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
