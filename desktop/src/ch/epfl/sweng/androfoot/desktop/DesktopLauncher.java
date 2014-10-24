package ch.epfl.sweng.androfoot.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ch.epfl.sweng.androfoot.AndroGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.allowSoftwareMode = false;
		config.foregroundFPS = 0;
		config.vSyncEnabled = false;
		LwjglApplicationConfiguration.disableAudio = true;
		new LwjglApplication(new AndroGame(), config);
	}
}
