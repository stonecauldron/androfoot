package ch.epfl.sweng.androfoot.players.ai;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.sun.medialib.mlib.mediaLibException;

public class ActRandomlyWhenDeadLocked extends ActRandomlyCoRoutine {

	ActRandomlyWhenDeadLocked(AbstractAIPlayer paddles) {
		super(paddles);
	}

	public void execute() {
		super.execute();
		if (!getPaddles().isDeadLocked()) {
			getPaddles().setState(AIState.DEFENSE);
		}
	}
}
