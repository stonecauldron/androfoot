package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.Player;

/**
 * Represent a manager of events to throw them in a secure way
 * @author Gaylor
 *
 */
public interface DefaultEventManager {
    /**
     * Throw the events store in the manager
     */
    void throwEvents();
    
    /**
     * Add an observer of goal event
     * @param obs
     */
    void addGoalObserver(GoalObserver obs);
    
    /**
     * Add an observer of paddle contact event
     * @param obs
     */
    void addPaddleContactObserver(PaddleContactObserver obs);
    
    /**
     * Add an observer of border contact event
     * @param observer
     */
    void addBorderContactObserver(BorderObserver observer);
    
    /**
     * Add an observer of player -> ball contact
     * @param observer
     */
    void addPlayerObserver(PlayerObserver observer);
    
    /**
     * Add an event which will be thrown
     * @param goal
     * @param ball
     */
    void addEventGoal(DefaultGoal goal, DefaultBall ball);

    /**
     * Add an event which will be thrown
     * @param player
     * @param team
     */
    void addEventPlayers(Player player, boolean team);

    /**
     * Add an event which will be thrown
     * @param player
     * @param ball
     */
    void addEventPaddle(DefaultPlayer player, DefaultBall ball);

    /**
     * Add an event which will be thrown
     * @param border
     * @param ball
     */
    void addEventBorder(BorderInterface border, DefaultBall ball);
}
