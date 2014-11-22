package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Defines the contact listener for the ball-player interactions.
 * @author Matvey
 *
 */
public final class PlayerContactListener implements ContactListener {
	
    private static PlayerContactListener instance = new PlayerContactListener();
	private static Set<Player> players;
	
	/**
	 * Constructor for the {@link PlayerContactListener} class.
	 */
	private PlayerContactListener() {
		players = new HashSet<Player>();
	}
	
	public static PlayerContactListener getInstance() {
        return instance;
    }
	
	/**
	 * Adds the specified player to the list of the players for the contact listener.
	 * @param player Player to be added.
	 */
	public static void addPlayer(Player player) {
		players.add(player);
	}

	@Override
	public void beginContact(Contact contact) {
		for (Player player : players) {
			if (checkBallHitsPlayerBack(contact, player)) {
				
				EventManager.getEventManager().addEventPlayers(player, player.getTeam());
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// Does nothing.

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Does nothing

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Does nothing.

	}
	
	/**
	 * Auxiliary method that checks if it's the ball that hits the back of the player in
	 * the collision event.
	 * @param contact Contact that occurs.
	 * @param player Player in the collision.
	 * @return True if there's contact between ball and the back of the player.
	 */
	private boolean checkBallHitsPlayerBack(Contact contact, Player player) {
		
		return ((contact.getFixtureA() == player.getBody().getFixtureList().get(0)) 
					&& (contact.getFixtureB().getFilterData().categoryBits == Constants.CATEGORY_BALL))
					|| ((contact.getFixtureA().getFilterData().categoryBits == Constants.CATEGORY_BALL) 
					&& (contact.getFixtureB() == player.getBody().getFixtureList().get(0)));
	}

}
