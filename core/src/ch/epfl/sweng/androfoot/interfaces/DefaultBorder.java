package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.math.Vector2;

import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.ImmutableBorder;

/**
 * Represent a border in the world
 * @author Gaylor
 *
 */
public interface DefaultBorder extends DefaultWorldObject {
    BorderType getType();
    
    Vector2 getPosition();
    
    ImmutableBorder getStates();
}
