package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

import ch.epfl.sweng.androfoot.interfaces.DefaultContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;

public class PowerUpContactListener implements DefaultContactListener {

	private static PowerUpContactListener instance = new PowerUpContactListener();
	private static DefaultEventManager manager;
	private static Set<PowerUp> powerUps;
	
	private PowerUpContactListener() {
		powerUps = new HashSet<PowerUp>();
	}
	
	public static PowerUpContactListener getInastance() {
		return instance;
	}
	
	public static void addPowerUp(PowerUp powerUp) {
		powerUps.add(powerUp);
	}
	
	public static void setEventManager(DefaultEventManager eventManager) {
		manager = eventManager;
	}
	
	@Override
	public void beginContact(Contact contact) {
		for (PowerUp powerUp : powerUps) {
			if (checkBallHitsPowerUp(contact, powerUp)) {
				
				if (manager != null) {
					manager.addev
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

	@Override
	public void removeBody(Body body) {
		// TODO Auto-generated method stub

	}
	
	private boolean checkBallHitsPowerUp(Contact contact, PowerUp powerUp) {
		
		return ((contact.getFixtureA().getBody() == powerUp.getBody())
					&& (contact.getFixtureB().getFilterData().categoryBits == Constants.CATEGORY_BALL)
					|| (contact.getFixtureA().getFilterData().categoryBits == Constants.CATEGORY_BALL)
					&& (contact.getFixtureB().getBody() == powerUp.getBody()));
	}

}
