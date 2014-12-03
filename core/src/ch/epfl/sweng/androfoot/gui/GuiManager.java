/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.ArrayList;

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
	private static final float TITLE_X_SIZE = 0.7f;
	private static final float TITLE_Y_SIZE = 0.3f;
	private static final float TEAM_LOGO_X_SIZE = 0.2f;
	private static final float TEAM_LOGO_Y_SIZE = 0.25f;
	private static final float SLIDER_Y_SIZE = 0.1f;
	private static final float SLIDER_X_SIZE = 0.3f;
	private static final float LARGE_BUTTON_X_SIZE = 0.6f;
	private static final float MEDIUM_BUTTON_X_SIZE = 0.15f;
	private static final float TINY_BUTTON_X_SIZE = 0.1f;
	private static final float CHECKBOX_X_SIZE = 0.1f;
	private static final float CHECKBOX_Y_SIZE = 0.1f;
	private static final float BUTTON_Y_SIZE = 0.1f;
	private static final float TOUCHPAD_MIN_SENSITIVITY = 30;
	private static final float TOUCHPAD_MAX_SENSITIVITY = 120;
	private final float[] mTitlePadding = new float[] {0f, 0f, 0f, 0.05f };
	private final float[] mDefaultPadding = new float[] {0.01f, 0.01f, 0.01f, 0.01f };
	private TextureAtlas mAtlas;
	private Skin mDefaultSkin;
	private ArrayList<GuiWidget> mMainMenuWidgets = new ArrayList<GuiWidget>();
	private ArrayList<GuiWidget> mLocalPlayWidgets = new ArrayList<GuiWidget>();
	private ArrayList<GuiWidget> mSettingsWidgets = new ArrayList<GuiWidget>();
	private ArrayList<GuiWidget> mNetworkPlayWidgets = new ArrayList<GuiWidget>();
	private ArrayList<GuiWidget> mCreditsWidgets = new ArrayList<GuiWidget>();
	private ArrayList<GuiWidget> mGameOverWidgets = new ArrayList<GuiWidget>();
	private GuiLabel mScoreLimitCounter;
	private GuiLabel mFinalScore;
	private GuiLabel mPlayerOneFormationLabel;
	private GuiLabel mPlayerTwoFormationLabel;
	private GuiLabel mPlayerOneTypeLabel;
	private GuiLabel mPlayerTwoTypeLabel;
	private GuiSlider mSensitivityCounter;

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		mAtlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		mDefaultSkin = new Skin(Gdx.files.internal("gui/gui.json"), mAtlas);
		
		// Main Menu
		mMainMenuWidgets.add(new GuiImage(mDefaultSkin, "title", TITLE_X_SIZE,
						TITLE_Y_SIZE, true, mDefaultPadding, 1));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Local Play", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToLocalPlay));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Network Play", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToNetworkPlay));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Settings", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToSettings));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Credits", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToCredits));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Quit", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.exit));
		
		// Local Play
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", true, mDefaultPadding, 7,
						"Score limit: "));
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				""));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
						Align.right, " - ", MEDIUM_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.subScoreLimit));
		mScoreLimitCounter = new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				Integer.toString(Configuration.getInstance().getScoreLimit()));
		mLocalPlayWidgets.add(mScoreLimitCounter);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true, mDefaultPadding,
						Align.left," + ", MEDIUM_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.addScoreLimit));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.togglePlayerOneType));
		mPlayerOneTypeLabel = new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				"Human");
		mLocalPlayWidgets.add(mPlayerOneTypeLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false, mDefaultPadding,
				Align.left,"", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.togglePlayerOneType));
		
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				""));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.togglePlayerTwoType));
		mPlayerTwoTypeLabel = new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				"Human");
		mLocalPlayWidgets.add(mPlayerTwoTypeLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true, mDefaultPadding,
				Align.left,"", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.togglePlayerTwoType));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, TEAM_LOGO_Y_SIZE, 1,
				GuiCommand.nothing));
		mLocalPlayWidgets.add(new GuiImage(mDefaultSkin, "teamGreen", TEAM_LOGO_X_SIZE,
				TEAM_LOGO_Y_SIZE, false, mDefaultPadding, 1));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false, mDefaultPadding,
				Align.left, "", TINY_BUTTON_X_SIZE, TEAM_LOGO_Y_SIZE, 1,
				GuiCommand.nothing));
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				"VS"));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, TEAM_LOGO_Y_SIZE, 1,
				GuiCommand.nothing));
		mLocalPlayWidgets.add(new GuiImage(mDefaultSkin, "teamPurple", TEAM_LOGO_X_SIZE,
				TEAM_LOGO_Y_SIZE, false, mDefaultPadding, 1));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true, mDefaultPadding,
				Align.left, "", TINY_BUTTON_X_SIZE, TEAM_LOGO_Y_SIZE, 1,
				GuiCommand.nothing));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.subPlayerOneFormation));
		mPlayerOneFormationLabel = new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				"2-3");
		mLocalPlayWidgets.add(mPlayerOneFormationLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false, mDefaultPadding,
				Align.left,"", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.addPlayerOneFormation));
		
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				""));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false, mDefaultPadding,
				Align.right, "", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.subPlayerTwoFormation));
		mPlayerTwoFormationLabel = new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
				"2-3");
		mLocalPlayWidgets.add(mPlayerTwoFormationLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true, mDefaultPadding,
				Align.left,"", TINY_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
				GuiCommand.addPlayerTwoFormation));
		
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Start", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 7,
						GuiCommand.goToGame));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Back", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 7,
						GuiCommand.goToMainMenu));
		
		// Network Play
		mNetworkPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", true, mTitlePadding, 1,
						"Network Play"));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Host Game", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.goToHostNetwork));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Join Game", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.goToClientNetwork));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Back", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToMainMenu));
		
		// Settings
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", true, mTitlePadding, 2,
						"Settings"));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
						"Sound: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true, mDefaultPadding,
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
						GuiCommand.nothing));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
						"Allow tilting: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true, mDefaultPadding,
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
						GuiCommand.nothing));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
						"Powerups: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true, mDefaultPadding,
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
						GuiCommand.nothing));
		mSensitivityCounter	= new GuiSlider(mDefaultSkin, true, mDefaultPadding,
				SLIDER_X_SIZE, SLIDER_Y_SIZE, TOUCHPAD_MIN_SENSITIVITY, TOUCHPAD_MAX_SENSITIVITY,
				1, GuiCommand.updateSensivity);
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", false, mDefaultPadding, 1,
						"Touchpad sensitivity: "));
		mSettingsWidgets.add(mSensitivityCounter);
		mSettingsWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Back", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.goToMainMenu));
		
		// Credits
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "default", true, mTitlePadding, 1,
						"Credits"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "aux", true, mTitlePadding, 1,
						"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
						+ "Aurelien Farine\nGaylor Bosson\nAdam Haefliger"));
		mCreditsWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Back", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToMainMenu));
		
		// Game Over
		mGameOverWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mTitlePadding, 1, "Game Over"));
		mFinalScore = new GuiLabel(mDefaultSkin, "default", true, mDefaultPadding,
				1, "0 - 0");
		mGameOverWidgets.add(mFinalScore);
		mGameOverWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Play again", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToGame));
		mGameOverWidgets.add(new GuiButton(mDefaultSkin, "default", true, mDefaultPadding,
						Align.center, "Back to main menu", LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE,
						1, GuiCommand.goToMainMenu));
	}

	public static GuiManager getInstance() {
		return INSTANCE;
	}
	
	public void setFinalScore(int scorePlayerOne, int scorePlayerTwo) {
		mFinalScore.setText(Integer.toString(scorePlayerOne)+" - "+Integer.toString(scorePlayerTwo));
	}

	public void executeCommand(GuiCommand command) {
		switch (command) {
			case addPlayerOneFormation:
				Configuration.getInstance().addPlayerOneFormation();
				mPlayerOneFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerOneFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerOneFormation()[1]);
				break;
			case addPlayerTwoFormation:
				Configuration.getInstance().addPlayerTwoFormation();
				mPlayerTwoFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerTwoFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerTwoFormation()[1]);
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
			case goToMainMenu:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mMainMenuWidgets));
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
			case togglePlayerOneType:
				if (Configuration.getInstance().getPlayerOneType() == PlayerType.LOCAL_PLAYER) {
					Configuration.getInstance().setPlayerOneType(
							PlayerType.RANDOM_AI_PLAYER);
							mPlayerOneTypeLabel.setText("CPU");
				} else {
					Configuration.getInstance().setPlayerOneType(
							PlayerType.LOCAL_PLAYER);
							mPlayerOneTypeLabel.setText("Human");
				}
				break;
			case togglePlayerTwoType:
				if (Configuration.getInstance().getPlayerTwoType() == PlayerType.LOCAL_PLAYER) {
					Configuration.getInstance().setPlayerTwoType(
							PlayerType.RANDOM_AI_PLAYER);
							mPlayerTwoTypeLabel.setText("CPU");
				} else {
					Configuration.getInstance().setPlayerTwoType(
							PlayerType.LOCAL_PLAYER);
							mPlayerTwoTypeLabel.setText("Human");
				}
				break;
			case subPlayerOneFormation:
				Configuration.getInstance().subPlayerOneFormation();
				mPlayerOneFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerOneFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerOneFormation()[1]);
				break;
			case subPlayerTwoFormation:
				Configuration.getInstance().subPlayerTwoFormation();
				mPlayerTwoFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerTwoFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerTwoFormation()[1]);
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