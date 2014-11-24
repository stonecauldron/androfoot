/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.screens.GameScreen;

/**
 * @author Sidney Barthe This singleton class creates all the gui screens and
 *         handles interactions with the user using a GuiCommand enum type
 *         passed as argument of the executeCommand method.
 */
public class GuiManager {
	private static final GuiManager INSTANCE = new GuiManager();
	private static final int X_PADDING = 20;
	private static final int Y_PADDING = 20;
	private static final int X_SIZE_PER_LETTER = 20;
	private static final int BUTTON_Y_SIZE = 40;
	private TextureAtlas atlas;
	private Skin blueSkin;
	private GuiWidget[] mMainMenuWidgets = new GuiWidget[6];
	private GuiWidget[] mLocalPlayWidgets = new GuiWidget[4];
	private GuiWidget[] mNetworkPlayWidgets = new GuiWidget[3];
	private GuiWidget[] mSettingsWidgets = new GuiWidget[3];
	private GuiWidget[] mCreditsWidgets = new GuiWidget[3];

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		blueSkin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);

		// Main Menu
		mMainMenuWidgets[0] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Andro Foot");
		mMainMenuWidgets[1] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Local Play", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToLocalPlay);
		mMainMenuWidgets[2] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Network Play", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToNetworkPlay);
		mMainMenuWidgets[3] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Settings", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToSettings);
		mMainMenuWidgets[4] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Credits", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToCredits);
		mMainMenuWidgets[5] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Quit", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, GuiCommand.exit);
		
		// Local Play
		mLocalPlayWidgets[0] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Local Play");
		mLocalPlayWidgets[1] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Player vs Player", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.startHuman);
		mLocalPlayWidgets[2] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Player vs AI", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.startAI);
		mLocalPlayWidgets[3] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToMainMenu);
		
		// Network Play
		mNetworkPlayWidgets[0] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Network Play");
		mNetworkPlayWidgets[1] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Not available for the moment");
		mNetworkPlayWidgets[2] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToMainMenu);
		
		// Settings
		mSettingsWidgets[0] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Settings");
		mSettingsWidgets[1] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"There are no settings for the moment");
		mSettingsWidgets[2] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToMainMenu);
		
		// Credits
		mCreditsWidgets[0] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Credits");
		mCreditsWidgets[1] = new GuiLabel(blueSkin, true, 0, Y_PADDING * 2,
				"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
				+ "Aurelien Farine\nGaylor Bosson\nAdam Haefliger");
		mCreditsWidgets[2] = new GuiButton(blueSkin, true, 0, Y_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE,
				GuiCommand.goToMainMenu);
	}

	public static GuiManager getInstance() {
		return INSTANCE;
	}

	public void executeCommand(GuiCommand command) {
		switch (command) {
			case goToMainMenu:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(mMainMenuWidgets));
				break;
			case addScoreLimit:
				break;
			case exit:
				Gdx.app.exit();
				break;
			case goToCredits:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(mCreditsWidgets));
				break;
			case goToNetworkPlay:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(mNetworkPlayWidgets));
			case goToGameOver:
				break;
			case goToLocalPlay:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(mLocalPlayWidgets));
				break;
			case goToSettings:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(mSettingsWidgets));
				break;
			case startHuman:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(PlayerType.LOCAL_PLAYER));
				break;
			case startAI:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(PlayerType.RANDOM_AI_PLAYER));
				break;
			case subScoreLimit:
				break;
			case nothing:
				break;
			default:
				break;
		}
	}
}
