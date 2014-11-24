package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Represent a basic object in the world
 * @author Gaylor
 *
 */
public interface DefaultWorldObject extends Drawable {
    Body getBody();
}
