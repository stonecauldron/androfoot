package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.BallInterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BallRenderer implements DrawableRenderer {
	
	private static final int NB_SEGMENTS = 100;
	private BallInterface ball;
	private ShaderProgram currentBasicShader = getSimpleShader();
	
	private static ShaderProgram basicShader = null;
	private static int basicShaderColorPosition = -1;
	private static int basicShaderProjMatrixPosition = -1;
	private static int basicShaderTransformationMatrixPosition = -1;
	private static ModelBuilder builder = new ModelBuilder();
	private static Model sphere = null;
	
	public BallRenderer(BallInterface ballToRender) {
		ball = ballToRender;
		sphere = builder.createSphere(1, 1, 1, 50, 50, new Material(), Usage.Position);	
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		
		Matrix4 transformationMat = new Matrix4().setTranslation(ball.getPositionX(), ball.getPositionY(), 0);
		Matrix4 scaleMatrix = new Matrix4().scl(ball.getRadius()*2);
		
		batch.end();
		Array<Mesh> meshes = sphere.meshes;
		currentBasicShader.begin();
		currentBasicShader.setUniformMatrix(basicShaderProjMatrixPosition, batch.getProjectionMatrix());
		currentBasicShader.setUniformMatrix(basicShaderTransformationMatrixPosition, transformationMat.mul(scaleMatrix));
		currentBasicShader.setUniformf(basicShaderColorPosition, Color.BLUE);
		for(Mesh m : meshes) {
			m.render(currentBasicShader, GL20.GL_TRIANGLES);
		}
		batch.begin();
	}
	
	private static void generateShaders() {
		String vertexShader = ""
				+ "uniform vec4 u_color;"
				+ "uniform mat4 u_projMatrix;"
				+ "uniform mat4 u_transMatrix;"
				+ "varying vec4 v_color;"
				+ "attribute vec4 a_position;"
				+ ""
				+ "void main () {"
				+ "gl_Position = u_projMatrix * u_transMatrix * a_position;"
				+ "v_color = u_color;"
				+ "}";
		String fragmentShader =
				"varying vec4 v_color;"
				+ "out vec4 final_color;"
				+ "void main() {"
				+ "final_color = v_color;"
				+ "}";
		
		basicShader = new ShaderProgram(vertexShader, fragmentShader);
		if (! basicShader.isCompiled()) {
			throw new GdxRuntimeException(basicShader.getLog());
		}
		basicShaderColorPosition = basicShader.getUniformLocation("u_color");
		basicShaderProjMatrixPosition = basicShader.getUniformLocation("u_projMatrix");
		basicShaderTransformationMatrixPosition = basicShader.getUniformLocation("u_transMatrix");
	}
	
	private static ShaderProgram getSimpleShader() {
		if(basicShader == null) {
			generateShaders();
		}
		return basicShader;
	}
	
	

}
