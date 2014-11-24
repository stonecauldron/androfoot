package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.math.Vector2;

import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;

/**
 * Interface for the goal of the board
 * @author Gaylor
 *
 */
public interface DefaultGoal extends DefaultWorldObject {
    /**
     * Get the team of the goal
     * @return
     */
    GoalTeam getTeam();
    
    /**
     * Get the position of the middle of the goal
     * @return
     */
    Vector2 getPosition();
    
    /**
     * Clone the actual state of the goal
     * @return
     */
    DefaultGoal clone();
}
