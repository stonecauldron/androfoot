package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

/**
 * A class that build a Ssimple shader
 * 
 * @author Guillaume
 *
 */
public interface SimpleShaderBuilder {

	/**
	 * Set the color of the mesh rendered
	 * 
	 * @param c
	 *            the color
	 */
	void setColor(Color c);

	/**
	 * Set the projection matrix
	 */
	void setProjMatrix(Matrix4 m);

	/**
	 * Set the transform matrix
	 * 
	 * @param m
	 *            the matrix
	 */
	void setTransfoMatrix(Matrix4 m);

	ShaderProgram build();

}