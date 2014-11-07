package ch.epfl.sweng.androfoot.interfaces;

/**
 * Event when a ball enter in a goal.
 * 
 * To prevent physic error, you need to add an observer in the physic engine not directly in the goal object if
 * you want to set a body
 * 
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface GoalObserver {

    /**
     * Triggered when the ball enter in one of the goal. The param is the goal where the ball is in. So
     * if isTeamOne is true, the team two won a point.
     * @param isTeamOne
     */
    public void goal(boolean isTeamOne);
}
