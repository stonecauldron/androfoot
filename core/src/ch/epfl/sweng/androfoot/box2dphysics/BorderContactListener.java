package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.DefaultContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Occured when a ball touch a border
 * @author Gaylor
 *
 */
public final class BorderContactListener implements DefaultContactListener {
    
    private static final BorderContactListener INSTANCE = new BorderContactListener();
    private static DefaultEventManager manager;
    private static Set<Ball> balls;
    private static Set<Border> borders;
    
    private BorderContactListener() {
        balls = new HashSet<Ball>();
        borders = new HashSet<Border>();
    }
    
    public static BorderContactListener getInstance() {
        return INSTANCE;
    }
    
    public static void addBall(Ball ball) {
        balls.add(ball);
    }
    
    public static void addBorder(Border border) {
        borders.add(border);
    }
    
    public static void setEventManager(DefaultEventManager eventManager) {
        manager = eventManager;
    }

    @Override
    public void beginContact(Contact contact) {
        for (Ball ball : balls) {
            if (contact.getFixtureA().getBody() == ball.getBody()
                   || contact.getFixtureB().getBody() == ball.getBody()) {
                
                for (Border border : borders) {
                    if (contact.getFixtureA().getBody() == border.getBody()
                           || contact.getFixtureB().getBody() == border.getBody()) {
                        
                        if (manager != null) {
                            manager.addEventBorder(border, ball);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    	// Do nothing
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    	// Do nothing
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    	// Do nothing
    }

    @Override
    public void removeBody(Body body) {
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();
            if (ball.getBody() == body) {
                ballIterator.remove();
            }
        }
        
        Iterator<Border> borderIterator = borders.iterator();
        while (borderIterator.hasNext()) {
            Border border = borderIterator.next();
            if (border.getBody() == body) {
                borderIterator.remove();
            }
        }
    }

}
