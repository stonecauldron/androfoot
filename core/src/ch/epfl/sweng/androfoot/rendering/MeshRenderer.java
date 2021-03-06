package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;

/**
 * Represent a class able to render a mesh
 * 
 * @author Guillame Leclerc
 *
 */
public interface MeshRenderer extends DrawableRenderer {

	/**
	 * Set the position of the polygon
	 * 
	 * @param x
	 *            the position on X axis
	 * @param y
	 *            the position on Y axis
	 */
	void setPosition(float x, float y);

	/**
	 * Set the color for the paddle
	 * 
	 * @param c
	 *            the color
	 */
	void setColor(Color c);

	/**
	 * Set the rotation angle of the polygon The rotation along its own origin
	 * rotations does not sum up over function calls
	 * 
	 * @param angle
	 *            the rotation angle in radials
	 */
	void setRotation(float angle);

	/**
	 * Set the scale of the polygon Scale operations does not sum up over
	 * function calls
	 * 
	 * @param s
	 *            the scale
	 */
	void setScale(float s);

	/**
	 * Set the zPosition of the polygon
	 * 
	 * @param z
	 *            the position along the z axis
	 */
	void setZindex(float z);

}