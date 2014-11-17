package ch.epfl.sweng.androfoot;

import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.soundeffect.SoundEffectManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroGame extends Game {
	SpriteBatch batch;
	
	@Override
	public void create() {
		SoundEffectManager.getInstance().loadSoundEffect();
		SoundEffectManager.getInstance().observe();
		GuiManager.getInstance().executeCommand(GuiCommand.goToMainMenu);
	}
	
	public void render() {
        super.render();
    }
}
