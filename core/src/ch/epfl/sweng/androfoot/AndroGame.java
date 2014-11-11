package ch.epfl.sweng.androfoot;

import ch.epfl.sweng.androfoot.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroGame extends Game {
	SpriteBatch batch;
	
	private MainMenuScreen mainMenuScreen;
	
	@Override
	public void create() {
		mainMenuScreen = new MainMenuScreen();
		setScreen(mainMenuScreen);
	}
}
