package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents an paddle alone.
 * @see DefaultGroupPaddle for multiple paddles group
 * 
 * The position of a paddle is the bottom left vertex
 * 
 * UI gives it a velocity and the paddle doesn't go out of a certain area defined by a limit rectangle
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface DefaultPaddle {
    
    /**
     * Give a velocity to the paddle
     * @param x composant of the velocity
     * @param y composant of the velocity
     */
    void setPlayerVelocity(float x, float y);
    
    /**
     * Sets the player velocity along the x axis only.
     * @param x New velocity along the x axis.
     */
    void setPlayerXVelocity(float x);
    
    /**
     * Sets the player velocity along the y axis only.
     * @param y New velocity along the y axis.
     */
    void setPlayerYVelocity(float y);
    
    /**
     * Return the width of the paddle
     * @return
     */
    float getWidth();
    
    /**
     * Return the height of the paddle
     * @return
     */
    float getHeight();
    
	/**
	 * Say if this paddle is in range to control the paddle
	 * @return
	 */
	boolean isAbleToControlBall();

	void setPosition(Vector2 position);

	Vector2 getPosition();
}
