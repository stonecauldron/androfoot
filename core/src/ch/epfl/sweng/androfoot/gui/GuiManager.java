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
	private static final float SLIDER_Y_SIZE = 0.1f;
	private static final float SLIDER_X_SIZE = 0.3f;
	private static final float BUTTON_X_SIZE_PER_LETTER = 0.05f;
	private static final float CHECKBOX_X_SIZE = 0.1f;
	private static final float CHECKBOX_Y_SIZE = 0.1f;
	private static final float BUTTON_Y_SIZE = 0.1f;
	private final float[] TITLE_PADDING = {0, 0, 0, 0.05f};
	private final float[] DEFAULT_PADDING = {0.01f, 0.01f, 0.01f, 0.01f};
	private TextureAtlas atlas;
	private Skin blueSkin;
	private GuiWidget[] mMainMenuWidgets = new GuiWidget[6];
	private GuiWidget[] mLocalPlayWidgets = new GuiWidget[8];
	private GuiWidget[] mNetworkPlayWidgets = new GuiWidget[3];
	private GuiWidget[] mSettingsWidgets = new GuiWidget[8];
	private GuiWidget[] mCreditsWidgets = new GuiWidget[3];
	private GuiLabel mScoreLimitCounter;
	private GuiSlider mSensibilityCounter;

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		blueSkin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);
		
		// Main Menu
		mMainMenuWidgets[0] = new GuiImage(blueSkin, "title", 0.8f, 0.3f, true, DEFAULT_PADDING, 1);
		mMainMenuWidgets[1] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Local Play", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToLocalPlay);
		mMainMenuWidgets[2] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Network Play", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToNetworkPlay);
		mMainMenuWidgets[3] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Settings", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToSettings);
		mMainMenuWidgets[4] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Credits", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToCredits);
		mMainMenuWidgets[5] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Quit", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.exit);
		
		// Local Play
		mLocalPlayWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 4,
				"Local Play");
		mLocalPlayWidgets[1] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Score limit: ");
		mLocalPlayWidgets[2] = new GuiButton(blueSkin, false, DEFAULT_PADDING,
				" - ", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.subScoreLimit);
		mScoreLimitCounter = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				Integer.toString(Configuration.getInstance().getScoreLimit()));
		mLocalPlayWidgets[3] = mScoreLimitCounter;
		mLocalPlayWidgets[4] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				" + ", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.addScoreLimit);
		mLocalPlayWidgets[5] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Player vs Player", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.startHuman);
		mLocalPlayWidgets[6] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Player vs AI", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.startAI);
		mLocalPlayWidgets[7] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Back", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 4,
				GuiCommand.goToMainMenu);
		
		// Network Play
		mNetworkPlayWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 1,
				"Network Play");
		mNetworkPlayWidgets[1] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 1,
				"Not available for the moment");
		mNetworkPlayWidgets[2] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Back", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
				GuiCommand.goToMainMenu);
		
		// Settings
		mSettingsWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 2,
				"Settings");
		mSettingsWidgets[1] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Feature 1: ");
		mSettingsWidgets[2] = new GuiCheckBox(blueSkin, true, DEFAULT_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSettingsWidgets[3] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Feature 2: ");
		mSettingsWidgets[4] = new GuiCheckBox(blueSkin, true, DEFAULT_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSensibilityCounter	= new GuiSlider(blueSkin, true, DEFAULT_PADDING,
				SLIDER_X_SIZE, SLIDER_Y_SIZE, 1,
				GuiCommand.updateSensibility);
		mSettingsWidgets[5] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Sensibility: ");
		mSettingsWidgets[6] = mSensibilityCounter;
		mSettingsWidgets[7] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Back", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 2,
				GuiCommand.goToMainMenu);
		
		// Credits
		mCreditsWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 1,
				"Credits");
		mCreditsWidgets[1] = new GuiLabel(blueSkin, "aux", true, TITLE_PADDING, 1,
				"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
				+ "Aurelien Farine\nGaylor Bosson\nAdam Haefliger");
		mCreditsWidgets[2] = new GuiButton(blueSkin, true, DEFAULT_PADDING,
				"Back", BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE, 1,
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
				Configuration.getInstance().addScoreLimit(1);
				mScoreLimitCounter.setText(Integer.toString(Configuration.getInstance().getScoreLimit()));
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
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(
					PlayerType.LOCAL_PLAYER, PlayerType.LOCAL_PLAYER));
				break;
			case startAI:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(
					PlayerType.LOCAL_PLAYER, PlayerType.RANDOM_AI_PLAYER));
				break;
			case subScoreLimit:
				Configuration.getInstance().subScoreLimit(1);
				mScoreLimitCounter.setText(Integer.toString(Configuration.getInstance().getScoreLimit()));
				break;
			case updateSensibility:
				Configuration.getInstance().setSensibility(mSensibilityCounter.getValue());
				break;
			case nothing:
				break;
			default:
				break;
		}
	}
}
