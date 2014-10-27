package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * A class able to return a shader it has generated
 * @author Guillame Leclerc
 *
 */
public interface ShaderBuilder {
	/**
	 * Build and return a {@link ShaderProgram}
	 * @return the shader built
	 */
	public ShaderProgram build();
}
