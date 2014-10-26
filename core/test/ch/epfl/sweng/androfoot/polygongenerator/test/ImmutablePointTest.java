package ch.epfl.sweng.androfoot.polygongenerator.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;

public class ImmutablePointTest {
	
	private final static int NB_TESTS = 10;
	private final static double DELTA = 0.00001;
	
	
	@Test
	public void TestFloat() {
		Random randomizer = new Random();
		for(int i = 0 ; i < NB_TESTS ; i ++) {
			float vx = randomizer.nextFloat();
			float vy = randomizer.nextFloat();
			ImmutablePoint<Float> point = new ImmutablePoint<Float>(vx,vy);
			assertEquals("coords x not equals",(float)vx, (float)point.x, DELTA);
			assertEquals("coords y not equals",(float)vy, (float)point.y, DELTA);
		}
	}
		
	@Test
	public void TestDouble() {
		Random randomizer = new Random();
		for(int i = 0 ; i < NB_TESTS ; i ++) {
			double vx = randomizer.nextDouble();
			double vy = randomizer.nextDouble();
			ImmutablePoint<Double> point = new ImmutablePoint<Double>(vx,vy);
			assertEquals("coords x not equals",vx, point.x, DELTA);
			assertEquals("coords y not equals",vy, point.y, DELTA);
		}
	}

}
