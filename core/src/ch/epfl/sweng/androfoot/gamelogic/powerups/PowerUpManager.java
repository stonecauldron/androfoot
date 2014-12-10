/**
 * 
 */
package ch.epfl.sweng.androfoot.gamelogic.powerups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;
import ch.epfl.sweng.androfoot.interfaces.PowerUpEffect;
import ch.epfl.sweng.androfoot.interfaces.PowerUpEffectApplier;
import ch.epfl.sweng.androfoot.interfaces.PowerUpSpawner;
import ch.epfl.sweng.androfoot.interfaces.Resettable;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * @author Guillaume
 *
 */
public class PowerUpManager implements PowerUpEffectApplier, PowerUpSpawner,
		PaddleContactObserver, Resettable {

	private final static float POWERUP_SIZE = 0.30f;
	private final static int MAX_NB_POWERUP = 3;
	private final static PowerUpManager instance = new PowerUpManager();
	private static Random randomizer = new Random(7);
	private final Map<DefaultPowerUp, PowerUpEffect> bodyToEffectMap = new HashMap<DefaultPowerUp, PowerUpEffect>();
	private boolean playerOneTouched;
	private final Map<DefaultPowerUp, Timer> timers = new HashMap<DefaultPowerUp, Timer>();
	private final Set<PowerUpEffect> possibleEffects = new HashSet<PowerUpEffect>();
	private Timer timer = null;

	private PowerUpManager() {
		addPowerUpEffect(new BulletPowerUp());
		addPowerUpEffect(new SmallPowerUp());
		addPowerUpEffect(new BallSizePowerUp());
		if (Configuration.getInstance().getPowerups()) {
			setSpawnRate(5f);
		}
		EventManager.getEventManager().addPowerUpContactObserver(this);
		EventManager.getEventManager().addPaddleContactObserver(this);
	}

	public static PowerUpManager getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpObserver#applyPowerUp(ch.epfl
	 * .sweng.androfoot.interfaces.DefaultPowerUp)
	 */
	@Override
	public void applyPowerUp(DefaultPowerUp powerUp) {
		if (bodyToEffectMap.containsKey(powerUp)) {
			timers.put(powerUp, new Timer(bodyToEffectMap.get(powerUp)
					.getEffectDuration()));
			PowerUpEffect effect = bodyToEffectMap.get(powerUp);
			effect.begin(playerOneTouched);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpSpawner#addPowerUpEffect(ch
	 * .epfl.sweng.androfoot.interfaces.PowerUpEffect)
	 */
	@Override
	public void addPowerUpEffect(PowerUpEffect effect) {
		possibleEffects.add(effect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpSpawner#setSpawnRate(float)
	 */
	@Override
	public void setSpawnRate(float time) {
		timer = new Timer(time);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpEffectApplier#update(float)
	 */
	@Override
	public void update(float delta) {
		/*
		 * PowerUpSpawner
		 */
		if (timer != null) {
			timer.updateTimer(delta);
			int nbPowerUps = possibleEffects.size();
			if (bodyToEffectMap.size() < MAX_NB_POWERUP && timer.checkTimer()
					&& nbPowerUps > 0) {
				int effectIndex = randomizer.nextInt(nbPowerUps);
				PowerUpEffect effect = (PowerUpEffect) possibleEffects
						.toArray()[effectIndex];
				float Xpos = randomizer.nextFloat() * Constants.WORLD_SIZE_X
						+ Constants.WORLD_ORIGIN_X;
				float Ypos = randomizer.nextFloat() * Constants.WORLD_SIZE_Y
						+ Constants.WORLD_ORIGIN_Y;
				DefaultPowerUp powerUpBody = PhysicsWorld.getPhysicsWorld()
						.createPowerUp(Xpos, Ypos, POWERUP_SIZE);
				setEffectForBody(powerUpBody, effect.copy());
				timer.resetTimer();
			}
		}

		/*
		 * PowerupEffectApplier
		 */

		Iterator<DefaultPowerUp> iter = timers.keySet().iterator();
		while (iter.hasNext()) {
			DefaultPowerUp pw = iter.next();
			Timer pwTimer = timers.get(pw);
			pwTimer.updateTimer(delta);
			if (pwTimer.checkTimer()) {
				if (bodyToEffectMap.containsKey(pw)) {
					PowerUpEffect effect = bodyToEffectMap.get(pw);
					effect.end();
					bodyToEffectMap.remove(pw);
				}
				iter.remove();
			}
		}
	}

	@Override
	public void paddleContact(DefaultPlayer player, DefaultBall ball) {
		playerOneTouched = false;
		if (player.getTeam()) {
			playerOneTouched = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpEffectApplier#setEffectForBody
	 * (ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp,
	 * ch.epfl.sweng.androfoot.interfaces.PowerUpEffect)
	 */
	@Override
	public void setEffectForBody(DefaultPowerUp power, PowerUpEffect effect) {
		bodyToEffectMap.put(power, effect);
	}

	@Override
	public void setSeed(long l) {
		randomizer = new Random(l);
	}

	@Override
	public void reset() {
		for (PowerUpEffect effect : bodyToEffectMap.values()) {
			effect.end();
		}
		bodyToEffectMap.clear();
		timers.clear();
		if (Configuration.getInstance().getPowerups()) {
			setSpawnRate(5f);
		}
	}
}
