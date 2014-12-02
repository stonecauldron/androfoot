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
import ch.epfl.sweng.androfoot.screens.NetworkClientScreen;
import ch.epfl.sweng.androfoot.screens.NetworkHostScreen;

/**
 * @author Sidney Barthe This singleton class creates all the gui screens and
 *         handles interactions with the user using a GuiCommand enum type
 *         passed as argument of the executeCommand method.
 */
public final class GuiManager {
	private static final GuiManager INSTANCE = new GuiManager();
	private static final float TITLE_Y_SIZE = 0.3f;
	private static final float TITLE_X_SIZE = 0.7f;
	private static final float SLIDER_Y_SIZE = 0.1f;
	private static final float SLIDER_X_SIZE = 0.3f;
	private static final float STD_BUTTON_X_SIZE = 0.6f;
	private static final float SMALL_BUTTON_X_SIZE = 0.15f;
	private static final float CHECKBOX_X_SIZE = 0.1f;
	private static final float CHECKBOX_Y_SIZE = 0.1f;
	private static final float BUTTON_Y_SIZE = 0.1f;
	private static final float TOUCHPAD_MIN_SENSIVITY = 30;
	private static final float TOUCHPAD_MAX_SENSIVITY = 120;
	private final float[] TITLE_PADDING = {0, 0, 0, 0.05f };
	private final float[] DEFAULT_PADDING = {0.01f, 0.01f, 0.01f, 0.01f };
	private TextureAtlas atlas;
	private Skin blueSkin;
	private GuiWidget[] mMainMenuWidgets = new GuiWidget[6];
	private GuiWidget[] mLocalPlayWidgets = new GuiWidget[8];
	private GuiWidget[] mSettingsWidgets = new GuiWidget[10];
	private GuiWidget[] mNetworkPlayWidgets = new GuiWidget[4];
	private GuiWidget[] mCreditsWidgets = new GuiWidget[3];
	private GuiWidget[] mGameOverWidgets = new GuiWidget[4];
	private GuiLabel mScoreLimitCounter;
	private GuiLabel mFinalScore;
	private GuiSlider mSensitivityCounter;

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		blueSkin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);
		
		// Main Menu
		mMainMenuWidgets[0] = new GuiImage(blueSkin, "title", TITLE_X_SIZE, TITLE_Y_SIZE, true,
				DEFAULT_PADDING, 1);
		mMainMenuWidgets[1] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Local Play", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToLocalPlay);
		mMainMenuWidgets[2] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Network Play", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToNetworkPlay);
		mMainMenuWidgets[3] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Settings", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToSettings);
		mMainMenuWidgets[4] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Credits", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToCredits);
		mMainMenuWidgets[5] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Quit", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.exit);
		
		// Local Play
		mLocalPlayWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 4,
				"Local Play");
		mLocalPlayWidgets[1] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Score limit: ");
		mLocalPlayWidgets[2] = new GuiButton(blueSkin, "leftarrow", false, DEFAULT_PADDING,
				" - ", SMALL_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.subScoreLimit);
		mScoreLimitCounter = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				Integer.toString(Configuration.getInstance().getScoreLimit()));
		mLocalPlayWidgets[3] = mScoreLimitCounter;
		mLocalPlayWidgets[4] = new GuiButton(blueSkin, "rightarrow", true, DEFAULT_PADDING,
				" + ", SMALL_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.addScoreLimit);
		mLocalPlayWidgets[5] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Player vs Player", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 4,
				GuiCommand.startHuman);
		mLocalPlayWidgets[6] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Player vs AI", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 4,
				GuiCommand.startAI);
		mLocalPlayWidgets[7] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Back", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 4,
				GuiCommand.goToMainMenu);
		
		// Network Play
		mNetworkPlayWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 1,
				"Network Play");
		mNetworkPlayWidgets[1] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Host Game", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
				GuiCommand.goToHostNetwork);
		mNetworkPlayWidgets[2] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Join Game", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
				GuiCommand.goToClientNetwork);
		mNetworkPlayWidgets[3] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Back", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToMainMenu);
		
		// Settings
		mSettingsWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 2,
				"Settings");
		mSettingsWidgets[1] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Sound: ");
		mSettingsWidgets[2] = new GuiCheckBox(blueSkin, true, DEFAULT_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSettingsWidgets[3] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Allow tilting: ");
		mSettingsWidgets[4] = new GuiCheckBox(blueSkin, true, DEFAULT_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSettingsWidgets[5] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Powerups: ");
		mSettingsWidgets[6] = new GuiCheckBox(blueSkin, true, DEFAULT_PADDING,
				CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
				GuiCommand.nothing);
		mSensitivityCounter	= new GuiSlider(blueSkin, true, DEFAULT_PADDING,
				SLIDER_X_SIZE, SLIDER_Y_SIZE, TOUCHPAD_MIN_SENSIVITY, TOUCHPAD_MAX_SENSIVITY,
				1, GuiCommand.updateSensivity);
		mSettingsWidgets[7] = new GuiLabel(blueSkin, "default", false, DEFAULT_PADDING, 1,
				"Touchpad sensitivity: ");
		mSettingsWidgets[8] = mSensitivityCounter;
		mSettingsWidgets[9] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Back", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
				GuiCommand.goToMainMenu);
		
		// Credits
		mCreditsWidgets[0] = new GuiLabel(blueSkin, "default", true, TITLE_PADDING, 1,
				"Credits");
		mCreditsWidgets[1] = new GuiLabel(blueSkin, "aux", true, TITLE_PADDING, 1,
				"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
				+ "Aurelien Farine\nGaylor Bosson\nAdam Haefliger");
		mCreditsWidgets[2] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Back", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToMainMenu);
		
		// Game Over
		mGameOverWidgets[0] = new GuiLabel(blueSkin, "default", true,
				TITLE_PADDING, 1, "Game Over");
		mFinalScore = new GuiLabel(blueSkin, "default", true, DEFAULT_PADDING,
				1, "0 - 0");
		mGameOverWidgets[1] = mFinalScore;
		mGameOverWidgets[2] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Play again", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.goToGame);
		mGameOverWidgets[3] = new GuiButton(blueSkin, "default", true, DEFAULT_PADDING,
				"Back to main menu", STD_BUTTON_X_SIZE, BUTTON_Y_SIZE,
				1, GuiCommand.goToMainMenu);
	}

	public static GuiManager getInstance() {
		return INSTANCE;
	}
	
	public void setFinalScore(int scorePlayerOne, int scorePlayerTwo) {
		mFinalScore.setText(Integer.toString(scorePlayerOne)+" - "+Integer.toString(scorePlayerTwo));
	}

	public void executeCommand(GuiCommand command) {
		switch (command) {
			case goToMainMenu:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mMainMenuWidgets));
				break;
			case addScoreLimit:
				Configuration.getInstance().addScoreLimit(1);
				mScoreLimitCounter.setText(Integer.toString(Configuration
									.getInstance().getScoreLimit()));
				break;
			case exit:
				Gdx.app.exit();
				break;
			case goToCredits:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mCreditsWidgets));
				break;
			case goToNetworkPlay:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mNetworkPlayWidgets));
				break;
			case goToGame:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(
									Configuration.getInstance().getPlayerOneType(),
									Configuration.getInstance().getPlayerTwoType()));
				break;
			case goToGameOver:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mGameOverWidgets));
				break;
			case goToLocalPlay:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mLocalPlayWidgets));
				break;
			case goToSettings:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mSettingsWidgets));
				break;
			case startHuman:
				Configuration.getInstance().setPlayerOneType(
									PlayerType.LOCAL_PLAYER);
				Configuration.getInstance().setPlayerTwoType(
									PlayerType.LOCAL_PLAYER);
				executeCommand(GuiCommand.goToGame);
				break;
			case startAI:
				Configuration.getInstance().setPlayerOneType(
									PlayerType.LOCAL_PLAYER);
				Configuration.getInstance().setPlayerTwoType(
									PlayerType.RANDOM_AI_PLAYER);
				executeCommand(GuiCommand.goToGame);
				break;
			case subScoreLimit:
				Configuration.getInstance().subScoreLimit(1);
				mScoreLimitCounter.setText(Integer.toString(Configuration
									.getInstance().getScoreLimit()));
				break;
			case updateSensivity:
				Configuration.getInstance().setSensivity(
									mSensitivityCounter.getValue());
				break;
			case goToClientNetwork:
				((Game) Gdx.app.getApplicationListener())
					.setScreen(new NetworkClientScreen());
				break;
			case goToHostNetwork:
				((Game) Gdx.app.getApplicationListener())
					.setScreen(new NetworkHostScreen());
				break;
			case nothing:
				break;
			default:
				break;
		}
	}
}