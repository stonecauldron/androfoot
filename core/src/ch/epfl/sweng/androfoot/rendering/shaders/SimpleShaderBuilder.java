package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public interface SimpleShaderBuilder {

	/**
	 * Set the color of the mesh rendered
	 * @param c the color
	 */
	public abstract void setColor(Color c);

	/**
	 * Set the projection matrix
	 */
	public abstract void setProjMatrix(Matrix4 m);

	/**
	 * Set the transform matrix
	 * @param m the matrix
	 */
	public abstract void setTransfoMatrix(Matrix4 m);

	public abstract ShaderProgram build();

}