package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.Set;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class BorderContactListener implements ContactListener {
    
    private Set<Ball> balls;
    private Set<Border> borders;

    @Override
    public void beginContact(Contact contact) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
        
    }

}
