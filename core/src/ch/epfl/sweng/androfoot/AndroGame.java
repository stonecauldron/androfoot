package ch.epfl.sweng.androfoot;

import ch.epfl.sweng.androfoot.screens.ConfigScreen;
import ch.epfl.sweng.androfoot.screens.GameScreen;
import ch.epfl.sweng.androfoot.screens.MainMenuScreen;
import ch.epfl.sweng.androfoot.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroGame extends Game {
	SpriteBatch batch;
	
	MainMenuScreen mainMenuScreen;
	
	@Override
	public void create () {
		mainMenuScreen = new MainMenuScreen();
		setScreen(mainMenuScreen);
	}
}
