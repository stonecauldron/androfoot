package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonTranslater;
import ch.epfl.sweng.androfoot.rendering.shaders.UniformShaderBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class PolygonRenderer implements DrawableRenderer {
	private final static Color POLYGON_COLOR = new Color(0xFFF00FFF);
	
	private final UniformShaderBuilder shaderBuilder;
	private final Mesh mesh;
	private final Matrix4 transformationMatrix = new Matrix4().idt();
	
	public PolygonRenderer(PolygonGenerator generator) {
		shaderBuilder = new UniformShaderBuilder();
		mesh = new Mesh(false, generator.generatePointsList().size(), 0, 
				new VertexAttribute(Usage.Position,3, "a_position"));
		mesh.setVertices(generator.generateVertexesFloatInZPlane(0f));
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		batch.end();
		shaderBuilder.build().begin();
		shaderBuilder.setColor(POLYGON_COLOR);
		shaderBuilder.setProjMatrix(batch.getProjectionMatrix());
		shaderBuilder.setTransfoMatrix(transformationMatrix);
		mesh.render(shaderBuilder.build(), GL20.GL_TRIANGLE_FAN);
		shaderBuilder.build().end();
		batch.begin();
	}

}
