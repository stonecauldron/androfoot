package ch.epfl.sweng.androfoot.interfaces;

/**
 * Occured when the ball begins a contact with a border
 * @author Gaylor
 *
 */
public interface BorderObserver {
    void borderContact(DefaultBorder border, DefaultBall ball);
}
