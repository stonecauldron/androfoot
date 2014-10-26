package ch.epfl.sweng.androfoot.rendering.shaders;

import ch.epfl.sweng.androfoot.rendering.ShaderBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class UniformShaderBuilder implements ShaderBuilder{
	
	private final ShaderProgram shader;
	private final int projMatrixPosition;
	private final int colorPosition;
	private final int transfoMatrixPosition;
	
	public UniformShaderBuilder() {
		String vertexShader = ""
				+ "attribute vec4 a_position;"
				+ "uniform vec4 u_color;"
				+ "uniform mat4 u_projMatrix;"
				+ "uniform mat4 u_transMatrix;"
				+ "varying vec4 v_color;"
				+ "void main() {"
				+ "v_color = u_color;"
				+ "gl_Position = u_projMatrix * u_transMatrix * a_position;"
				+ "}";
		
		String fragmentShader = ""
				+ "varying vec4 v_color;"
				+ "void main() {"
				+ "gl_FragColor = v_color;"
				+ "}";
		
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if( ! shader.isCompiled()) {
			throw new GdxRuntimeException(shader.getLog());
		}
		projMatrixPosition = shader.getUniformLocation("u_projMatrix");
		colorPosition = shader.getUniformLocation("u_color");
		transfoMatrixPosition = shader.getUniformLocation("u_transMatrix");
	}
	
	public void setColor(Color c) {
		shader.setUniformf(colorPosition, c);
	}
	
	public void setProjMatrix(Matrix4 m) {
		shader.setUniformMatrix(projMatrixPosition, m);
	}
	
	public void setTransfoMatrix(Matrix4 m) {
		shader.setUniformMatrix(transfoMatrixPosition, m);
	}

	@Override
	public ShaderProgram build() {
		return shader;
	}

}
