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
 * Occured when a ball touch a player
 * @author Gaylor
 *
 */
public final class PaddleContactListener implements DefaultContactListener {

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
    public void removeBody(Body body) {
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();
            if (ball.getBody() == body) {
                ballIterator.remove();
            }
        }
        
        Iterator<Player> playerIterator = players.iterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            if (player.getBody() == body) {
                playerIterator.remove();
            }
        }
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
