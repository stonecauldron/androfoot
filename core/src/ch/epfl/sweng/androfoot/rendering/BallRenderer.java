package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.DefaultBall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Render a ball on the screen
 * 
 * @author Guillaume
 *
 */
public class BallRenderer implements DrawableRenderer {

	private static final int NB_SEGMENTS = 100;
	private static final int BALL_COLOR_HEXA = 0xFFFFFFFF;
	private static final int UNIT_VECTOR_VALUE = 1;
	public static final Color BALL_COLOR = new Color(BALL_COLOR_HEXA);
	private static final float SHADOW_SCALE = 1.5f;

	private static ShaderProgram basicShader = null;
	private static int basicShaderColorPosition = -1;
	private static int basicShaderProjMatrixPosition = -1;
	private static int basicShaderTransformationMatrixPosition = -1;
	private static int basicShaderScaleMatrix = -1;

	private final Matrix4 shadowScaleMatrix;
	private final Matrix4 transformationMatrix;
	private final ShaderProgram currentBasicShader = getSimpleShader();
	private final Model sphere;

	private DefaultBall ball = null;

	/**
	 * Init the {@link BallRenderer}
	 */
	public BallRenderer() {
		ModelBuilder builder = new ModelBuilder();
		sphere = builder.createSphere(UNIT_VECTOR_VALUE, UNIT_VECTOR_VALUE,
						UNIT_VECTOR_VALUE, NB_SEGMENTS, NB_SEGMENTS,
						new Material(), Usage.Position);
		shadowScaleMatrix = new Matrix4().scl(SHADOW_SCALE);
		transformationMatrix = new Matrix4();
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		assert ball != null;

		transformationMatrix
						.idt()
						.setTranslation(ball.getPositionX(),
										ball.getPositionY(), 0)
						.scl(ball.getRadius() + ball.getRadius());

		Array<Mesh> meshes = sphere.meshes;

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		currentBasicShader.begin();
		currentBasicShader.setUniformMatrix(basicShaderProjMatrixPosition,
						batch.getProjectionMatrix());
		currentBasicShader.setUniformMatrix(basicShaderScaleMatrix,
						shadowScaleMatrix);
		currentBasicShader.setUniformMatrix(
						basicShaderTransformationMatrixPosition,
						transformationMatrix);
		currentBasicShader.setUniformf(basicShaderColorPosition, BALL_COLOR);
		for (Mesh m : meshes) {
			m.render(currentBasicShader, GL20.GL_TRIANGLES);
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public BallRenderer setBall(DefaultBall ballArg) {
		ball = ballArg;
		return this;
	}

	/**
	 * Build and compile the {@link ShaderProgram}
	 */
	private static void generateShaders() {
		String vertexShader = ""
						+ "#ifdef GL_ES\n"
						+ "precision mediump float;\n"
						+ "#endif\n"
						+ "uniform vec4 u_color;"
						+ "uniform mat4 u_projMatrix;"
						+ "uniform mat4 u_transMatrix;"
						+ "uniform mat4 u_scaleMatrix;"
						+ "varying vec4 v_color;"
						+ "varying float v_distance;"
						+ "attribute vec4 a_position;"
						+ ""
						+ "void main () {"
						+ "vec4 afterScale = u_scaleMatrix * a_position;"
						+ "vec2 xyPoint = vec2(afterScale.x, afterScale.y);"
						+ "v_distance=clamp(((distance(xyPoint,vec2(0.0)))-0.5)/(u_scaleMatrix[0][0]-1.0)*2.0,0.0,1.0);"
						+ "gl_Position = u_projMatrix * u_transMatrix * afterScale;"
						+ "v_color = u_color;" + "}";
		String fragmentShader = "#ifdef GL_ES\n" + "precision mediump float;\n"
						+ "#endif\n" + "varying vec4 v_color;"
						+ "varying float v_distance;" + "void main() {"
						+ "float opacity;"
						+ "opacity = 1.0 - sqrt(sqrt(v_distance));"
						+ "gl_FragColor = v_color;"
						+ "gl_FragColor.a = opacity;" + "}";

		basicShader = new ShaderProgram(vertexShader, fragmentShader);
		if (!basicShader.isCompiled()) {
			throw new GdxRuntimeException(basicShader.getLog());
		}
		basicShaderColorPosition = basicShader.getUniformLocation("u_color");
		basicShaderProjMatrixPosition = basicShader
						.getUniformLocation("u_projMatrix");
		basicShaderTransformationMatrixPosition = basicShader
						.getUniformLocation("u_transMatrix");
		basicShaderScaleMatrix = basicShader
						.getUniformLocation("u_scaleMatrix");
	}

	/**
	 * Return (and build if necessary the shader for a ball)
	 * 
	 * @return the unique shader (Singleton design pattern)
	 */
	private static ShaderProgram getSimpleShader() {
		if (basicShader == null) {
			generateShaders();
		}
		return basicShader;
	}

}
