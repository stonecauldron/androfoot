package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

import ch.epfl.sweng.androfoot.interfaces.DefaultContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;

/**
 * Defines the contact listener for the ball-power up interaction.
 * @author Matvey
 *
 */
public final class PowerUpContactListener implements DefaultContactListener {

	private static final PowerUpContactListener INSTANCE = new PowerUpContactListener();
	private static DefaultEventManager manager;
	private static Set<PowerUpBody> powerUps;
	
	/**
	 * Constructor of the {@link PowerUpContactListener} class.
	 */
	private PowerUpContactListener() {
		powerUps = new HashSet<PowerUpBody>();
	}
	
	/**
	 * Returns the instance of the {@link PowerUpContactListener} class.
	 * @return
	 */
	public static PowerUpContactListener getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Adds the specified power up to the list of the power ups for the contact listener.
	 * @param powerUp Power Up to be added.
	 */
	public static void addPowerUp(PowerUpBody powerUp) {
		powerUps.add(powerUp);
	}
	
	/**
	 * Sets the Event Manager for the contact listener.
	 * @param eventManager
	 */
	public static void setEventManager(DefaultEventManager eventManager) {
		manager = eventManager;
	}
	
	@Override
	public void beginContact(Contact contact) {
		for (PowerUpBody powerUp : powerUps) {
			if (checkBallHitsPowerUp(contact, powerUp)) {
				
				if (manager != null) {
					manager.addEventPowerUp(powerUp);
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
		//Does nothing.
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Does nothing.
	}

	@Override
	public void removeBody(Body body) {
		Iterator<PowerUpBody> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUpBody powerUp = powerUpIterator.next();
            if (powerUp.getBody() == body) {
                powerUpIterator.remove();
            }
        }
	}
	
	/**
	 * Auxikiary method that checks if the ball hits the power up or not.
	 * @param contact
	 * @param powerUp
	 * @return
	 */
	private boolean checkBallHitsPowerUp(Contact contact, PowerUpBody powerUp) {
		
		return (contact.getFixtureA().getBody() == powerUp.getBody())
					&& (contact.getFixtureB().getFilterData().categoryBits == Constants.CATEGORY_BALL)
					|| (contact.getFixtureA().getFilterData().categoryBits == Constants.CATEGORY_BALL)
					&& (contact.getFixtureB().getBody() == powerUp.getBody());
	}
}
