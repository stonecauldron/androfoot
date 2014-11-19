package ch.epfl.sweng.androfoot.utils.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.utils.Timer;

public class TimerTest {
	
	private Timer timer;
	private static final float TICK_INTERVAL = 0.5f;
	
	@Before
	public void setUpTimer() {
		timer = new Timer(TICK_INTERVAL);
	}
	
	@Test
	public void testCheckTime() {
		timer.updateTimer(TICK_INTERVAL);
		assertTrue(timer.checkTimer());
	}
	
	@Test
	public void testReset() {
		timer.updateTimer(TICK_INTERVAL);
		timer.checkTimer();
		assertFalse(timer.checkTimer());
	}

}
