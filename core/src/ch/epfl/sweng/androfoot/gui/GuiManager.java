/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.screens.GameScreen;

/**
 * @author Sidney Barthe This singleton class creates all the gui screens and
 *         handles interactions with the user using a GuiCommand enum type
 *         passed as argument of the executeCommand method.
 */
public class GuiManager {
	private static final GuiManager INSTANCE = new GuiManager();
	private static final int X_SIZE_PER_LETTER = 20;
	private static final int CHECKBOX_X_SIZE = 40;
	private static final int CHECKBOX_Y_SIZE = 40;
	private static final int BUTTON_Y_SIZE = 40;
	private final int[] TITLE_PADDING = {0, 0, 0, 40};
	private final int[] STD_PADDING = {0, 0, 0, 20};
	private final int[] COUNTER_PADDING = {20, 0, 20, 20};
	private TextureAtlas atlas;
	private Skin blueSkin;
	private GuiWidget[] mMainMenuWidgets = new GuiWidget[6];
	private GuiWidget[] mLocalPlayWidgets = new GuiWidget[8];
	private GuiWidget[] mNetworkPlayWidgets = new GuiWidget[3];
	private GuiWidget[] mSettingsWidgets = new GuiWidget[6];
	private GuiWidget[] mCreditsWidgets = new GuiWidget[3];
	private GuiCounter mScoreLimitCounter;

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		blueSkin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);

		// Main Menu
		mMainMenuWidgets[0] = new GuiLabel(blueSkin, true, TITLE_PADDING, 1,
				"Andro Foot");
		mMainMenuWidgets[1] = new GuiButton(blueSkin, true, STD_PADDING,
				"Local Play", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToLocalPlay);
		mMainMenuWidgets[2] = new GuiButton(blueSkin, true, STD_PADDING,
				"Network Play", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToNetworkPlay);
		mMainMenuWidgets[3] = new GuiButton(blueSkin, true, STD_PADDING,
				"Settings", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToSettings);
		mMainMenuWidgets[4] = new GuiButton(blueSkin, true, STD_PADDING,
				"Credits", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToCredits);
		mMainMenuWidgets[5] = new GuiButton(blueSkin, true, STD_PADDING,
				"Quit", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.exit);
		
		// Local Play
		mLocalPlayWidgets[0] = new GuiLabel(blueSkin, true, TITLE_PADDING, 4,
				"Local Play");
		mLocalPlayWidgets[1] = new GuiLabel(blueSkin, false, STD_PADDING, 1,
				"Score limit: ");
		mLocalPlayWidgets[2] = new GuiButton(blueSkin, false, STD_PADDING,
				" - ", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.subScoreLimit);
		mScoreLimitCounter = new GuiCounter(blueSkin, false, COUNTER_PADDING, 1,
				Configuration.getInstance().getScoreLimit());
		mLocalPlayWidgets[3] = mScoreLimitCounter;
		mLocalPlayWidgets[4] = new GuiButton(blueSkin, true, STD_PADDING,
				" + ", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.addScoreLimit);
		mLocalPlayWidgets[5] = new GuiButton(blueSkin, true, STD_PADDING,
				"Player vs Player", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.startHuman);
		mLocalPlayWidgets[6] = new GuiButton(blueSkin, true, STD_PADDING,
				"Player vs AI", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.startAI);
		mLocalPlayWidgets[7] = new GuiButton(blueSkin, true, STD_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.goToMainMenu);
		
		// Network Play
		mNetworkPlayWidgets[0] = new GuiLabel(blueSkin, true, TITLE_PADDING, 1,
				"Network Play");
		mNetworkPlayWidgets[1] = new GuiLabel(blueSkin, true, TITLE_PADDING, 1,
				"Not available for the moment");
		mNetworkPlayWidgets[2] = new GuiButton(blueSkin, true, STD_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToMainMenu);
		
		// Settings
		mSettingsWidgets[0] = new GuiLabel(blueSkin, true, TITLE_PADDING, 2,
				"Settings");
		mSettingsWidgets[1] = new GuiLabel(blueSkin, false, STD_PADDING, 1,
				"Feature 1: ");
		mSettingsWidgets[2] = new GuiCheckBox(blueSkin, true, STD_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSettingsWidgets[3] = new GuiLabel(blueSkin, false, STD_PADDING, 1,
				"Feature 2: ");
		mSettingsWidgets[4] = new GuiCheckBox(blueSkin, true, STD_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSettingsWidgets[5] = new GuiButton(blueSkin, true, STD_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 2,
				GuiCommand.goToMainMenu);
		
		// Credits
		mCreditsWidgets[0] = new GuiLabel(blueSkin, true, TITLE_PADDING, 1,
				"Credits");
		mCreditsWidgets[1] = new GuiLabel(blueSkin, true, TITLE_PADDING, 1,
				"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
				+ "Aurelien Farine\nGaylor Bosson\nAdam Haefliger");
		mCreditsWidgets[2] = new GuiButton(blueSkin, true, STD_PADDING,
				"Back", X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
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
				mScoreLimitCounter.add(1);
				Configuration.getInstance().setScoreLimit(mScoreLimitCounter.getValue());
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
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(PlayerType.LOCAL_PLAYER, PlayerType.LOCAL_PLAYER));
				break;
			case startAI:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(PlayerType.LOCAL_PLAYER, PlayerType.RANDOM_AI_PLAYER));
				break;
			case subScoreLimit:
				mScoreLimitCounter.sub(1);
				Configuration.getInstance().setScoreLimit(mScoreLimitCounter.getValue());
				break;
			case nothing:
				break;
			default:
				break;
		}
	}
}
