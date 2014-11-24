package ch.epfl.sweng.androfoot.interfaces;

/**
 * Occured when a ball enters in contact with a player
 * @author Gaylor
 *
 */
public interface PaddleContactObserver {

    void paddleContact(DefaultPlayer player, DefaultBall ball);
}
