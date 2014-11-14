package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

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
    
    private static final GoalContactListener instance = new GoalContactListener();
    private static Set<Goal> goals;
    
    private GoalContactListener() {
        goals = new HashSet<Goal>();
    }
    
    public static GoalContactListener getInstance() {
        return instance;
    }
    
    public static void addGoal(Goal goal) {
        goals.add(goal);
    }

    @Override
    public void beginContact(Contact contact) {
        for (Goal goal : goals) {
            if (contact.getFixtureA().getBody() == goal.getBody()
                    || contact.getFixtureB().getBody() == goal.getBody()) {
                
                EventManager.getEventManager().addEventGoal(goal.isTeamOne());
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
