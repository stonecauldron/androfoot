package ch.epfl.sweng.androfoot.players;

import java.util.HashMap;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.players.ai.AIObserver;
import ch.epfl.sweng.androfoot.utils.CoRoutine;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Class to represent a generic AI controlled player.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AbstractAIPlayer extends AbstractPlayer implements Controllable,
		AIObserver {

	// HashMap linking Timers with CoRoutines
	private HashMap<Timer, CoRoutine> coRoutinesMap;

	public AbstractAIPlayer(PlayerNumber number) {
		super(number);

		// start observing AIEngine
		AIEngine.getInstance().addAIObserver(this);
		
		// initialize HashMap
		coRoutinesMap = new HashMap<Timer, CoRoutine>();
	}

	@Override
	public void moveHorizontally(float deltaX) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveVertically(float deltaY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void move(float deltaX, float deltaY) {
		for (GroupPaddle paddles : getPaddles()) {
			paddles.setVelocity(deltaX, deltaY);
		}
	}

	@Override
	public void update(float deltaTime) {
		// iterate over the list of timers
		for (Timer timer : coRoutinesMap.keySet()) {
			timer.updateTimer(deltaTime);
			
			// if a timer ticks
			if (timer.checkTimer()) {
				// execute coRoutine associated with timer
				coRoutinesMap.get(timer).execute();
			}
		}
	}

	protected void addToCoRoutines(Timer timer, CoRoutine coRoutine) {
		coRoutinesMap.put(timer, coRoutine);
	}

	protected void removeFromCoRoutines(Timer timer) {
		coRoutinesMap.remove(timer);
	}

}
