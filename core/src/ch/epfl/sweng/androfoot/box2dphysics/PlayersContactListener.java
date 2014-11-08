package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PlayersContactListener implements ContactListener {
	
	private Set<Player> players;
	private Set<PlayerObserver> observers;
	
	public PlayersContactListener() {
		players = new HashSet<Player>();
		observers = new HashSet<PlayerObserver>();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void addObserver(PlayerObserver observer) {
		observers.add(observer);
	}

	@Override
	public void beginContact(Contact contact) {
		for (Player player : players) {
			if ((contact.getFixtureA().getBody() == player.getBody()) ||
					(contact.getFixtureB().getBody() == player.getBody())) {
				
				for (PlayerObserver observer : observers) {
					observer.setBall(player.getTeam());
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
