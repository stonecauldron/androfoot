package ch.epfl.sweng.androfoot;

import ch.epfl.sweng.androfoot.screens.ConfigScreen;
import ch.epfl.sweng.androfoot.screens.GameScreen;
import ch.epfl.sweng.androfoot.screens.MenuScreen;
import ch.epfl.sweng.androfoot.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroGame extends Game {
	SpriteBatch batch;
	
	SplashScreen splashScreen;
	GameScreen gameScreen;
	MenuScreen menuScreen;
	ConfigScreen configScreen;
	
	@Override
	public void create () {
		//splashScreen = new SplashScreen();
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
