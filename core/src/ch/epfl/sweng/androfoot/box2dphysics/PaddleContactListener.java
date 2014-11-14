package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PaddleContactListener implements ContactListener {

    private static final PaddleContactListener instance = new PaddleContactListener();
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
    
    @Override
    public void beginContact(Contact contact) {
        for (Ball ball : balls) {
            if (contact.getFixtureA().getBody() == ball.getBody() || 
                    contact.getFixtureB().getBody() == ball.getBody()) {
                
                for (Player player : players) {
                    if (contact.getFixtureA().getBody() == player.getBody() ||
                            contact.getFixtureB().getBody() == player.getBody()) {
                        
                        EventManager.getEventManager().addEventPaddle();
                    }
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
        
    }

}
