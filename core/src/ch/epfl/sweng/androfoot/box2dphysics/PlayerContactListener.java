package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Defines the contact listener for the ball-player interactions.
 * @author Matvey
 *
 */
public class PlayerContactListener implements ContactListener {
	
	private Set<Player> players;
	private Set<PlayerObserver> observers;
	
	/**
	 * Constructor for the {@link PlayerContactListener} class.
	 */
	public PlayerContactListener() {
		players = new HashSet<Player>();
		observers = new HashSet<PlayerObserver>();
	}
	
	/**
	 * Adds the specified player to the list of the players for the contact listener.
	 * @param player Player to be added.
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	/**
	 * Adds the specified observer to the list of the observers for the contact listener.
	 * @param observer
	 */
	public void addObserver(PlayerObserver observer) {
		observers.add(observer);
	}

	@Override
	public void beginContact(Contact contact) {
		for (Player player : players) {
			if ((contact.getFixtureA() == player.getBody().getFixtureList().get(0)) 
					|| (contact.getFixtureB() == player.getBody().getFixtureList().get(0))) {
				
				for (PlayerObserver observer : observers) {
					observer.setBall(player, player.getTeam());
				}
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

}
