package ch.epfl.sweng.androfoot.screens;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;

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
		final DrawableWorld world = new DrawableWorld() {

			@Override
			public Set<Drawable> toDraw() {
				return new HashSet<Drawable>();
			}

			@Override
			public Rectangle regionToDraw() {
				return new Rectangle(0f, 0f, 1500, 900);
			}
			
		};
		GraphicEngine.getEngine().bindToWorld(world);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
