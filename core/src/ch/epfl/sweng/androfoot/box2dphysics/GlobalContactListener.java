package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.DefaultContactListener;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * As Box2D has only one listener at the same time, I create this to encapsulate different implementation
 * of all the team
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public final class GlobalContactListener implements DefaultContactListener {
    
    private static GlobalContactListener instance = new GlobalContactListener();
    private static Set<DefaultContactListener> listeners;
    
    private GlobalContactListener() {
        listeners = new HashSet<DefaultContactListener>();
    }
    
    public static GlobalContactListener getInstance() {
        return instance;
    }
    
    public static void addListener(DefaultContactListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void removeBody(Body body) {
        for (DefaultContactListener listener : listeners) {
            listener.removeBody(body);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener listener : listeners) {
            listener.preSolve(contact, oldManifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener listener : listeners) {
            listener.postSolve(contact, impulse);
        }
    }

}
