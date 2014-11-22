package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Occured when a ball touch a player
 * @author Gaylor
 *
 */
public final class PaddleContactListener implements ContactListener {

    private static PaddleContactListener instance = new PaddleContactListener();
    private static DefaultEventManager manager;
    private static Set<Player> players;
    private static Set<Ball> balls;
    
    private PaddleContactListener() {
        players = new HashSet<Player>();
        balls = new HashSet<Ball>();
    }
    
    public static PaddleContactListener getInstance() {
        return instance;
    }
    
    public static void addBall(Ball ball) {
        balls.add(ball);
    }
    
    public static void addPlayer(Player player) {
        players.add(player);
    }
    
    public static void setEventManager(DefaultEventManager eventManager) {
        manager = eventManager;
    }
    
    @Override
    public void beginContact(Contact contact) {
        for (Ball ball : balls) {
            if (contact.getFixtureA().getBody() == ball.getBody()
                    || contact.getFixtureB().getBody() == ball.getBody()) {
                
                for (Player player : players) {
                    if (contact.getFixtureA().getBody() == player.getBody()
                            || contact.getFixtureB().getBody() == player.getBody()) {
                        
                        if (manager != null) {
                            manager.addEventPaddle(player, ball);
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
