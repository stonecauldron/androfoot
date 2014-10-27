package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonTranslater;
import ch.epfl.sweng.androfoot.rendering.shaders.UniformShaderBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
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
	private final Matrix4 transformationMatrix;
	private final Matrix4 rotationMatrix;
	private ImmutablePoint<Float> postition;
	private float zPos;
	private float scale;
	
	public PolygonRenderer(PolygonGenerator generator) {
		shaderBuilder = new UniformShaderBuilder();
		mesh = new Mesh(false, generator.generatePointsList().size(), 0, 
				new VertexAttribute(Usage.Position,3, "a_position"));
		mesh.setVertices(generator.generateVertexesFloatInZPlane(zPos));
		zPos = -1;
		scale = 0;
		postition = new ImmutablePoint<Float>(0f, 0f);
		transformationMatrix = new Matrix4().idt();
		rotationMatrix = new Matrix4().idt();
	}
	
	public void setPosition(float x, float y) {
		postition = new ImmutablePoint<Float>(x, y);
	}
	
	public void setRotation(float angle) {
		rotationMatrix.idt().rotateRad(0f, 0f, 1f, angle);
	}
	
	public void setScale(float s) {
		scale = s;
	}
	
	public void setZindex(float z) {
		zPos = z;
	}
	
	private void generateMatrix() {
		transformationMatrix.idt().scl(scale).translate(postition.x, postition.y, zPos).mul(rotationMatrix);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		generateMatrix();
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
