package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;


public class ActRandomlyWhenDeadLocked extends ActRandomlyCoRoutine {
	
	private List<AIState> authorizedStates = Arrays.asList(AIState.RANDOM);

	ActRandomlyWhenDeadLocked(AbstractAIPlayer paddles) {
		super(paddles);
	}
	
	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	public void execute() {
		super.execute();
		if (!getPaddles().isDeadLocked()) {
			getPaddles().setState(AIState.DEFENSE);
		}
	}
}
