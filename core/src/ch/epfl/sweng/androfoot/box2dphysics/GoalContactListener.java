package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Listener to prevent a goal action
 * @author Gaylor
 *
 */
public class GoalContactListener implements ContactListener {
    
    private Set<Goal> goals;
    private Set<GoalObserver> observers;
    
    public GoalContactListener() {
        goals = new HashSet<Goal>();
        observers = new HashSet<GoalObserver>();
    }
    
    public void addGoal(Goal goal) {
        goals.add(goal);
    }
    
    public void addObserver(GoalObserver observer) {
        observers.add(observer);
    }

    @Override
    public void beginContact(Contact contact) {
        for (Goal goal : goals) {
            if (contact.getFixtureA().getBody() == goal.getBody()
                    || contact.getFixtureB().getBody() == goal.getBody()) {
                
                for (GoalObserver observer : observers) {
                    observer.goal(goal.isTeamOne());
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
