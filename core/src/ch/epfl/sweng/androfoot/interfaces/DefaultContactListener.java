package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactListener;

/**
 * A contact listener need to implement this interface in order to remove the body which it observes
 * @author Gaylor
 *
 */
public interface DefaultContactListener extends ContactListener {
    void removeBody(Body body);
}
