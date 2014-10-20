package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.SeparateThreadEngine;

public class PhysicsWorld implements DrawableWorld, SeparateThreadEngine {

	private static final int POSITION_ITERATIONS = 6;
	private static final int VELOCITY_ITERATIONS = 6;
	private static final float TIME_STEP = 1/30f;
	private static final float BALL_RESTITUTION = 0.8f;
	private static final float BALL_FRICTION = 0.4f;
	private static final float BALL_DENSITY = 0.5f;
	private static final float BALL_RADIUS = 50.0f;
	private static final int BALL_INIT_POS_Y = 450;
	private static final int BALL_INIT_POS_X = 800;
	private static final int WORLD_ORIGIN_Y = 0;
	private static final int WORLD_ORIGIN_X = 0;
	private static final int WORLD_SIZE_Y = 900;
	private static final int WORLD_SIZE_X = 1500;
	
	private static final PhysicsWorld PHYSICS_WORLD_INSTANCE = new PhysicsWorld();
	
	private World physicsWorld = new World(new Vector2(0, 0), false);
	private Set<Drawable> drawableObjectsSet = new HashSet<Drawable>();
	
	private PhysicsRunner runner;
	
	private PhysicsWorld() {
	}
	
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	@Override
	public Set<Drawable> toDraw() {
		return new HashSet<Drawable>(drawableObjectsSet);
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(WORLD_ORIGIN_X, WORLD_ORIGIN_Y, WORLD_SIZE_X, WORLD_SIZE_Y);
	}

	@Override
	public void init() {
		Ball ball = new Ball(physicsWorld, BALL_INIT_POS_X, BALL_INIT_POS_Y, BALL_RADIUS, 
				BALL_DENSITY, BALL_FRICTION, BALL_RESTITUTION);
		
		drawableObjectsSet.add(ball);
	}

	@Override
	public void reset() {
		init();
	}

	@Override
	public void pause() {
		assert(runner != null);
		runner.interrupt();
		runner = null;
	}

	@Override
	public void resume() {
		runner = new PhysicsRunner();
		runner.start();
	}

	@Override
	public void start() {
		resume();
	}

	private class PhysicsRunner extends Thread {
		@Override
		public void run() {
			this.setDaemon(true);
			super.run();
			while (!Thread.interrupted()) {
				long before = System.nanoTime();
				physicsWorld.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
				
				try {
					Thread.sleep((System.nanoTime() - before) / 1000);
				} catch (InterruptedException e) {
					break;
				} 
			}
		}
	}
}
