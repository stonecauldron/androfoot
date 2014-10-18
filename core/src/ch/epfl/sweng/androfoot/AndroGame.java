package ch.epfl.sweng.androfoot;

import ch.epfl.sweng.androfoot.screens.ConfigScreen;
import ch.epfl.sweng.androfoot.screens.GameScreen;
import ch.epfl.sweng.androfoot.screens.MenuScreen;
import ch.epfl.sweng.androfoot.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	SplashScreen splashScreen;
	GameScreen gameScreen;
	MenuScreen menuScreen;
	ConfigScreen configScreen;
	
	@Override
	public void create () {
		splashScreen = new SplashScreen();
		setScreen(splashScreen);
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
