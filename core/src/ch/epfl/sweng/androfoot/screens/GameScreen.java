package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

import com.badlogic.gdx.Screen;

/**
 * Display the game
 * @author Guillame Leclerc
 *
 */
public class GameScreen implements Screen {

	@Override
	public void render(float delta) {
		GraphicEngine.getEngine().render();
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
	}

	@Override
	public void show() {
		GraphicEngine.getEngine().bindToWorld(PhysicsWorld.getPhysicsWorld());
		PhysicsWorld.getPhysicsWorld().init();
		PhysicsWorld.getPhysicsWorld().start();
	}

	@Override
	public void hide() {
		PhysicsWorld.getPhysicsWorld().pause();

	}

	@Override
	public void pause() {
		PhysicsWorld.getPhysicsWorld().pause();

	}

	@Override
	public void resume() {
		PhysicsWorld.getPhysicsWorld().resume();
	}

	@Override
	public void dispose() {
		PhysicsWorld.getPhysicsWorld().pause();
	}

}
