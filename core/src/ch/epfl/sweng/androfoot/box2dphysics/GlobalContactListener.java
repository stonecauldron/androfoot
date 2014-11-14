package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

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
public class GlobalContactListener implements ContactListener {
    
    private static Set<ContactListener> listeners;
    
    public GlobalContactListener() {
        listeners = new HashSet<ContactListener>();
    }
    
    public static void addListener(ContactListener listener) {
        listeners.add(listener);
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
