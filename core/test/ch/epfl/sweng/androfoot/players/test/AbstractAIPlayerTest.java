package ch.epfl.sweng.androfoot.players.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.players.ai.AIState;
import ch.epfl.sweng.androfoot.players.ai.AbstractAIPlayer;
import ch.epfl.sweng.androfoot.utils.CoRoutine;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Class testing the functionality of CoRoutines.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AbstractAIPlayerTest {

	/**
	 * Mock class to test abstract class functionality.
	 * 
	 * @author Pedro Caldeira <pedrocaldeira>
	 *
	 */
	private class MockAIPlayer extends AbstractAIPlayer {

		MockAIPlayer(PlayerNumber number) {
			super(number);

			// fill the hashmap with coroutines
			addToCoRoutines(timer1, successCoRoutine);
			addToCoRoutines(timer2, successCoRoutine);
			addToCoRoutines(timer3, failCoRoutine);

			removeFromCoRoutines(timer3);
			
			// put mockAI in default static
			super.setState(AIState.RANDOM);
		}
		
		@Override
		public void update(float deltaTime) {
			updateTimers(deltaTime);
		}
	}

	private static final float TIMER_1_INTERVAL = 0.5f;

	private static final float TIMER_2_INTERVAL = 2.0f;

	private MockAIPlayer mockAI;
	private static Timer timer1 = new Timer(TIMER_1_INTERVAL);
	private static Timer timer2 = new Timer(TIMER_2_INTERVAL);
	private static Timer timer3 = new Timer(TIMER_1_INTERVAL);

	private static boolean wasCoRoutineCalled = false;

	private static CoRoutine successCoRoutine = new CoRoutine() {

		@Override
		public void execute() {
			wasCoRoutineCalled = true;
		}

		@Override
		public List<AIState> getStatesWhereCoRoutineIsExecutable() {
			List<AIState> list = new ArrayList<AIState>();
			list.add(AIState.RANDOM);
			return list;
		}
	};

	private static CoRoutine failCoRoutine = new CoRoutine() {

		@Override
		public void execute() {
			fail("This coroutine should have been removed");
		}

		@Override
		public List<AIState> getStatesWhereCoRoutineIsExecutable() {
			return null;
		}
	};

	@Before
	public void setUp() throws Exception {
		mockAI = new MockAIPlayer(PlayerNumber.MOCK);
	}

	@Test
	public void testUpdate() {
		mockAI.update(TIMER_1_INTERVAL);
		testThatCoRoutineWasCalled();
		mockAI.update(TIMER_1_INTERVAL / 2);
		testThatCoRoutineWasNotCalled();
		mockAI.update(TIMER_2_INTERVAL);
		testThatCoRoutineWasCalled();
	}
	
	private void testThatCoRoutineWasCalled() {
		assertTrue(wasCoRoutineCalled);
		wasCoRoutineCalled = false;
	}
	
	private void testThatCoRoutineWasNotCalled() {
		assertFalse(wasCoRoutineCalled);
	}
}
