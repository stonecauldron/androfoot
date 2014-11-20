package ch.epfl.sweng.androfoot.utils;

import java.util.List;

import ch.epfl.sweng.androfoot.players.ai.AIState;

/**
 * An interface to represent the AI behavior coroutines.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public interface CoRoutine {
	public void execute();
	
	public List<AIState> getStatesWhereCoRoutineIsExecutable();
}
