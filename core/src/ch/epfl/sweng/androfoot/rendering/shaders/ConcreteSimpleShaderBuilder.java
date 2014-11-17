package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A shader builder that fill the pixels with an uniform color
 * @author Guillame Leclerc
 *
 */
public class ConcreteSimpleShaderBuilder implements SimpleShaderBuilder{
	
	protected final ShaderProgram shader;
	private final int projMatrixPosition;
	private final int colorPosition;
	private final int transfoMatrixPosition;
	
	protected String getVertexShader() {
		return 
				"#ifdef GL_ES\n" 
                + "precision mediump float;\n"  
                + "#endif\n" 
				+ "attribute vec4 a_position;"
				+ "uniform vec4 u_color;"
				+ "uniform mat4 u_projMatrix;"
				+ "uniform mat4 u_transMatrix;"
				+ "varying vec4 v_color;"
				+ "void main() {"
				+ "v_color = u_color;"
				+ "gl_Position = u_projMatrix * u_transMatrix * a_position;"
				+ "}";
	}
	
	protected String getFragmentShader() {
		return 
				"#ifdef GL_ES\n" 
                + "precision mediump float;\n"  
                + "#endif\n" 
				+ "varying vec4 v_color;"
				+ "void main() {"
				+ "gl_FragColor = v_color;"
				+ "}";
	}
	
	/**
	 * Instanciate a new {@link ConcreteSimpleShaderBuilder}
	 */
	public ConcreteSimpleShaderBuilder() {
		String vertexShader = getVertexShader();
		
		String fragmentShader = getFragmentShader();
		
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if( ! shader.isCompiled()) {
			throw new GdxRuntimeException(shader.getLog());
		}
		projMatrixPosition = shader.getUniformLocation("u_projMatrix");
		colorPosition = shader.getUniformLocation("u_color");
		transfoMatrixPosition = shader.getUniformLocation("u_transMatrix");
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder#setColor(com.badlogic.gdx.graphics.Color)
	 */
	@Override
	public void setColor(Color c) {
		shader.setUniformf(colorPosition, c);
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder#setProjMatrix(com.badlogic.gdx.math.Matrix4)
	 */
	@Override
	public void setProjMatrix(Matrix4 m) {
		shader.setUniformMatrix(projMatrixPosition, m);
	}
	
	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder#setTransfoMatrix(com.badlogic.gdx.math.Matrix4)
	 */
	@Override
	public void setTransfoMatrix(Matrix4 m) {
		shader.setUniformMatrix(transfoMatrixPosition, m);
	}

	/* (non-Javadoc)
	 * @see ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder#render()
	 */
	@Override
	public ShaderProgram build() {
		return shader;
	}
}
