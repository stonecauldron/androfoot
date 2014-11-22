package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Listener to prevent a goal action
 * @author Gaylor
 *
 */
public final class GoalContactListener implements ContactListener {
    
    private static GoalContactListener instance = new GoalContactListener();
    private static DefaultEventManager manager;
    private static Set<Goal> goals;
    private static Set<Ball> balls;
    
    private GoalContactListener() {
        goals = new HashSet<Goal>();
        balls = new HashSet<Ball>();
    }
    
    public static GoalContactListener getInstance() {
        return instance;
    }
    
    public static void addGoal(Goal goal) {
        goals.add(goal);
    }
    
    public static void addBall(Ball ball) {
        balls.add(ball);
    }
    
    public static void setEventManager(DefaultEventManager eventManager) {
        manager = eventManager;
    }

    @Override
    public void beginContact(Contact contact) {
        for (Ball ball : balls) {
            if (contact.getFixtureA().getBody() == ball.getBody()
                   || contact.getFixtureB().getBody() == ball.getBody()) {
        
                for (Goal goal : goals) {
                    if (contact.getFixtureA().getBody() == goal.getBody()
                            || contact.getFixtureB().getBody() == goal.getBody()) {
                        
                        if (manager != null) {
                            manager.addEventGoal(goal, ball);
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

}
