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

/**
 * A class to render polygons from {@link PolygonGenerator}
 * @author Guillame Leclerc
 *
 */
public class PolygonRenderer implements DrawableRenderer {
	private final static Color POLYGON_COLOR = new Color(0xFFF00FFF);
	
	private final UniformShaderBuilder shaderBuilder;
	private final Mesh mesh;
	private final Matrix4 transformationMatrix;
	private final Matrix4 rotationMatrix;
	private ImmutablePoint<Float> postition;
	private float zPos;
	private float scale;
	
	/**
	 * Create the class from a {@link PolygonGenerator}
	 * @param generator the generator
	 */
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
	
	/**
	 * Set the position of the polygon
	 * @param x the position on X axis
	 * @param y the position on Y axis
	 */
	public void setPosition(float x, float y) {
		postition = new ImmutablePoint<Float>(x, y);
	}
	
	/**
	 * Set the rotation angle of the polygon
	 * The rotation along its own origin
	 * rotations does not sum up over function calls
	 * @param angle the rotation angle in radials 
	 */
	public void setRotation(float angle) {
		rotationMatrix.idt().rotateRad(0f, 0f, 1f, angle);
	}
	
	/**
	 * Set the scale of the polygon
	 * Scale operations does not sum up over function calls
	 * @param s the scale
	 */
	public void setScale(float s) {
		scale = s;
	}
	
	/**
	 * Set the zPosition of the polygon
	 * @param z the position along the z axis
	 */
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
