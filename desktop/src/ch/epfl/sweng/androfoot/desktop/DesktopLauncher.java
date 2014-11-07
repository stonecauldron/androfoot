package ch.epfl.sweng.androfoot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ch.epfl.sweng.androfoot.AndroGame;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.allowSoftwareMode = false;
		config.foregroundFPS = (int) (1f/Constants.TIME_STEP);
		config.vSyncEnabled = false;
		new LwjglApplication(new AndroGame(), config);
	}
}
