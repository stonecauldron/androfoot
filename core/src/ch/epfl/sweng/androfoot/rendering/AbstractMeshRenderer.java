package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.rendering.shaders.BasicShaderBuilder;
import ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * A class to render polygons from {@link PolygonGenerator}
 * @author Guillame Leclerc
 *
 */
public class AbstractMeshRenderer implements DrawableRenderer, MeshRenderer {
	
	private static final Color DEFAULT_COLOR = Color.BLACK;
	
	private final SimpleShaderBuilder shaderBuilder;
	private final Mesh mesh;
	private final Matrix4 transformationMatrix;
	private final Matrix4 rotationMatrix;
	private ImmutablePoint<Float> postition;
	private float zPos;
	private float scale;
	private Color color;
	
	/**
	 * Create the class from a {@link PolygonGenerator}
	 * @param generator the generator
	 */
	protected AbstractMeshRenderer(Mesh meshArg) {
		shaderBuilder = getShader();
		mesh = meshArg;
		zPos = -1;
		scale = 0;
		postition = new ImmutablePoint<Float>(0f, 0f);
		transformationMatrix = new Matrix4().idt();
		rotationMatrix = new Matrix4().idt();
		color = DEFAULT_COLOR;	
	}
	protected SimpleShaderBuilder getShader() {
		return new BasicShaderBuilder();
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.MeshRenderer#setPosition(float, float)
	 */
	@Override
	public void setPosition(float x, float y) {
		postition = new ImmutablePoint<Float>(x, y);
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.MeshRenderer#setColor(com.badlogic.gdx.graphics.Color)
	 */
	@Override
	public void setColor(Color c) {
		color = c;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.MeshRenderer#setRotation(float)
	 */
	@Override
	public void setRotation(float angle) {
		rotationMatrix.idt().rotateRad(0f, 0f, 1f, angle);
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.MeshRenderer#setScale(float)
	 */
	@Override
	public void setScale(float s) {
		scale = s;
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.MeshRenderer#setZindex(float)
	 */
	@Override
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
		shaderBuilder.setColor(color);
		shaderBuilder.setProjMatrix(batch.getProjectionMatrix());
		shaderBuilder.setTransfoMatrix(transformationMatrix);
		mesh.render(shaderBuilder.build(), GL20.GL_TRIANGLE_FAN);
		shaderBuilder.build().end();
		batch.begin();
	}

}
