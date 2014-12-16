package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.util.ArrayList;

import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.screens.GameScreen;
import ch.epfl.sweng.androfoot.screens.NetworkClientScreen;
import ch.epfl.sweng.androfoot.screens.NetworkHostScreen;

/**
 * This singleton class creates all the gui screens and
 * handles interactions with the user.
 * @author Sidney Barthe 
 * 
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
	private static final float TOUCHPAD_MIN_SENSITIVITY = 30f;
	private static final float TOUCHPAD_MAX_SENSITIVITY = 120f;
	private static final float TINY_FONT_SIZE = 0.4f;
	private static final float SMALL_FONT_SIZE = 0.55f;
	private static final float MEDIUM_FONT_SIZE = 0.8f;
	private final float[] mTitlePadding = new float[] {0f, 0f, 0f, 0.05f };
	private final float[] mDefaultPadding = new float[] {0.01f, 0.01f, 0.01f, 0.01f };
	private final String[] mTeamImage = new String[] {"teamRed", "teamBlue",
		"teamGreen", "teamPurple", "teamTeal", "teamOrange", "teamYellow",
		"teamWhite" };
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
	private GuiImage mPlayerOneTeam;
	private GuiImage mPlayerTwoTeam;
	private GuiImage mPlayerOneTeamFinal;
	private GuiImage mPlayerTwoTeamFinal;
	private GuiSlider mSensitivityCounter;

	private GuiManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		mAtlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
		mDefaultSkin = new Skin(Gdx.files.internal("gui/gui.json"), mAtlas);
		createMainMenu();
		createLocalPlay();
		createNetworkPlay();
		createSettings();
		createCredits();
		createGameOver();
		refreshDisplay();
	}

	public static GuiManager getInstance() {
		return INSTANCE;
	}
	
	public void setFinalScore(int scorePlayerOne, int scorePlayerTwo) {
		mFinalScore.setText(Integer.toString(scorePlayerOne)+" - "+Integer.toString(scorePlayerTwo));
	}

	/**
	 * Executes a GUI command.
	 * 
	 * @param command  the command to execute
	 */
	public void executeCommand(GuiCommand command) {
		switch (command) {
			case addPlayerOneTeam:
				Configuration.getInstance().addPlayerOneTeam();
				refreshDisplay();
				break;
			case subPlayerOneTeam:
				Configuration.getInstance().subPlayerOneTeam();
				refreshDisplay();
				break;
			case addPlayerTwoTeam:
				Configuration.getInstance().addPlayerTwoTeam();
				refreshDisplay();
				break;
			case subPlayerTwoTeam:
				Configuration.getInstance().subPlayerTwoTeam();
				refreshDisplay();
				break;
			case addPlayerOneFormation:
				Configuration.getInstance().addPlayerOneFormation();
				refreshDisplay();
				break;
			case addPlayerTwoFormation:
				Configuration.getInstance().addPlayerTwoFormation();
				refreshDisplay();
				break;
			case addScoreLimit:
				Configuration.getInstance().addScoreLimit(1);
				refreshDisplay();
				break;
			case exit:
				Gdx.app.exit();
				break;
			case goToCredits:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mCreditsWidgets));
				break;
			case goToMainMenu:
				Configuration.getInstance().savePreferences();
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mMainMenuWidgets));
				break;
			case goToNetworkPlay:
				((Game) Gdx.app.getApplicationListener()).setScreen(new GuiScreen(
									mNetworkPlayWidgets));
				break;
			case goToGame:
				Configuration.getInstance().savePreferences();
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(
									Configuration.getInstance().getPlayerOneType(),
									Configuration.getInstance().getPlayerTwoType()));
				break;
			case goToGameOver:
				refreshDisplay();
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
			case addPlayerOneType:
				Configuration.getInstance().addPlayerOneType();
				refreshDisplay();
				break;
			case subPlayerOneType:
				Configuration.getInstance().subPlayerOneType();
				refreshDisplay();
				break;
			case addPlayerTwoType:
				Configuration.getInstance().addPlayerTwoType();
				refreshDisplay();
				break;
			case subPlayerTwoType:
				Configuration.getInstance().subPlayerTwoType();
				refreshDisplay();
				break;
			case subPlayerOneFormation:
				Configuration.getInstance().subPlayerOneFormation();
				refreshDisplay();
				break;
			case subPlayerTwoFormation:
				Configuration.getInstance().subPlayerTwoFormation();
				refreshDisplay();
				break;
			case subScoreLimit:
				Configuration.getInstance().subScoreLimit(1);
				refreshDisplay();
				break;
			case toggleSound:
				Configuration.getInstance().toggleSound();
				break;
			case toggleTilting:
				Configuration.getInstance().toggleTilting();
				break;
			case togglePowerups:
				Configuration.getInstance().togglePowerups();
				break;
			case updateSensitivity:
				Configuration.getInstance().setSensivity(mSensitivityCounter.getValue());
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

	private void createMainMenu() {
		mMainMenuWidgets.add(new GuiImage(mDefaultSkin, "title", TITLE_X_SIZE,
						TITLE_Y_SIZE, true, mDefaultPadding, 1));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Local Play",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToLocalPlay));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Network Play",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 1,
						GuiCommand.goToNetworkPlay));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Settings", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.goToSettings));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Credits", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.goToCredits));
		mMainMenuWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Quit", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.exit));
		mMainMenuWidgets.add(new GuiLabel(mDefaultSkin, "aux", true,
						mTitlePadding, Align.bottomLeft, TINY_FONT_SIZE, 1,
						"Copyright (c)2014 AndroFoot team"));
	}
	
	/**
	 * Refreshes all the widgets to display the current values
	 * (score limit, teams, formations...).
	 * 
	 */
	private void refreshDisplay() {
		mPlayerOneFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerOneFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerOneFormation()[1]);
		mPlayerTwoFormationLabel.setText(Integer.toString(
						Configuration.getInstance().getPlayerTwoFormation()[0])
						+ "-"
						+ Configuration.getInstance().getPlayerTwoFormation()[1]);
		mScoreLimitCounter.setText(Integer.toString(Configuration
						.getInstance().getScoreLimit()));
		switch (Configuration.getInstance().getPlayerOneType()) {
			case LOCAL_PLAYER:
				mPlayerOneTypeLabel.setText("Human");
				break;
			case RANDOM_AI_PLAYER:
				mPlayerOneTypeLabel.setText("CPU rdm");
				break;
			case EASY_AI_PLAYER:
				mPlayerOneTypeLabel.setText("CPU lvl1");
				break;
			case MEDIUM_AI_PLAYER:
				mPlayerOneTypeLabel.setText("CPU lvl2");
				break;
			case HARD_AI_PLAYER:
				mPlayerOneTypeLabel.setText("CPU lvl3");
				break;
			case REMOTE_PLAYER:
				mPlayerOneTypeLabel.setText("Human");
				break;
			default:
				break;
		}
		switch (Configuration.getInstance().getPlayerTwoType()) {
			case LOCAL_PLAYER:
				mPlayerTwoTypeLabel.setText("Human");
				break;
			case RANDOM_AI_PLAYER:
				mPlayerTwoTypeLabel.setText("CPU rdm");
				break;
			case EASY_AI_PLAYER:
				mPlayerTwoTypeLabel.setText("CPU lvl1");
				break;
			case MEDIUM_AI_PLAYER:
				mPlayerTwoTypeLabel.setText("CPU lvl2");
				break;
			case HARD_AI_PLAYER:
				mPlayerTwoTypeLabel.setText("CPU lvl3");
				break;
			case REMOTE_PLAYER:
				mPlayerTwoTypeLabel.setText("Human");
				break;
			default:
				break;
		}
		mPlayerOneTeam.changeImage(mDefaultSkin,
						mTeamImage[Configuration.getInstance().getPlayerOneTeam()]);
		mPlayerTwoTeam.changeImage(mDefaultSkin,
						mTeamImage[Configuration.getInstance().getPlayerTwoTeam()]);
		mPlayerOneTeamFinal.changeImage(mDefaultSkin,
						mTeamImage[Configuration.getInstance().getPlayerOneTeam()]);
		mPlayerTwoTeamFinal.changeImage(mDefaultSkin,
						mTeamImage[Configuration.getInstance().getPlayerTwoTeam()]);
	}
	
	private void createLocalPlay() {
		final int nbColumns = 7;
		
		// Score limit
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "aux", true,
						mDefaultPadding, Align.center, 1f, nbColumns, "Score limit: "));
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false,
						mDefaultPadding, Align.center, 1f, 1, ""));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, " - ", MEDIUM_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 2, GuiCommand.subScoreLimit));
		mScoreLimitCounter = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, Integer.toString(Configuration
						.getInstance().getScoreLimit()));
		mLocalPlayWidgets.add(mScoreLimitCounter);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true,
						mDefaultPadding, Align.left, " + ", MEDIUM_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 2, GuiCommand.addScoreLimit));

		// Player one type
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.subPlayerOneType));
		mPlayerOneTypeLabel = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, "Human");
		mLocalPlayWidgets.add(mPlayerOneTypeLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.addPlayerOneType));
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false,
						mDefaultPadding, Align.center, 1f, 1, ""));

		// Player two type
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.subPlayerTwoType));
		mPlayerTwoTypeLabel = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, "Human");
		mLocalPlayWidgets.add(mPlayerTwoTypeLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.addPlayerTwoType));

		// Player one team
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						TEAM_LOGO_Y_SIZE, 1, GuiCommand.subPlayerOneTeam));
		mPlayerOneTeam = new GuiImage(mDefaultSkin, "teamRed",
				TEAM_LOGO_X_SIZE, TEAM_LOGO_Y_SIZE, false, mDefaultPadding, 1);
		mLocalPlayWidgets.add(mPlayerOneTeam);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						TEAM_LOGO_Y_SIZE, 1, GuiCommand.addPlayerOneTeam));
		
		// VS
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "aux", false,
						mDefaultPadding, Align.center, 1f, 1, "VS"));
		
		// Player two team
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						TEAM_LOGO_Y_SIZE, 1, GuiCommand.subPlayerTwoTeam));
		mPlayerTwoTeam = new GuiImage(mDefaultSkin, "teamBlue",
				TEAM_LOGO_X_SIZE, TEAM_LOGO_Y_SIZE, false, mDefaultPadding, 1);
		mLocalPlayWidgets.add(mPlayerTwoTeam);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						TEAM_LOGO_Y_SIZE, 1, GuiCommand.addPlayerTwoTeam));

		// Player one formation
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.subPlayerOneFormation));
		mPlayerOneFormationLabel = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, "2-3");
		mLocalPlayWidgets.add(mPlayerOneFormationLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", false,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.addPlayerOneFormation));
		mLocalPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", false,
						mDefaultPadding, Align.center, 1f, 1, ""));

		// Player two formation
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "leftarrow", false,
						mDefaultPadding, Align.right, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.subPlayerTwoFormation));
		mPlayerTwoFormationLabel = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, "2-3");
		mLocalPlayWidgets.add(mPlayerTwoFormationLabel);
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "rightarrow", true,
						mDefaultPadding, Align.left, "", TINY_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.addPlayerTwoFormation));

		// Standard buttons
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Start", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, nbColumns, GuiCommand.goToGame));
		mLocalPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Back", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, nbColumns, GuiCommand.goToMainMenu));
	}
	
	private void createNetworkPlay() {
		mNetworkPlayWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mTitlePadding, Align.center, 1f, 1, "Network Play"));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Host Game",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.goToHostNetwork));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Join Game",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, 2,
						GuiCommand.goToClientNetwork));
		mNetworkPlayWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Back", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.goToMainMenu));
	}
	
	private void createSettings() {
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mTitlePadding, Align.center, 1f, 2, "Settings"));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "aux", false,
						mDefaultPadding, Align.right, MEDIUM_FONT_SIZE, 1, "Sound: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true,
						mDefaultPadding, Configuration.getInstance().getSound(),
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1, GuiCommand.toggleSound));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "aux", false,
						mDefaultPadding, Align.right, MEDIUM_FONT_SIZE, 1, "Allow tilting: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true,
						mDefaultPadding, Configuration.getInstance().getTilting(),
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1, GuiCommand.toggleTilting));
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "aux", false,
						mDefaultPadding, Align.right, MEDIUM_FONT_SIZE, 1, "Powerups: "));
		mSettingsWidgets.add(new GuiCheckBox(mDefaultSkin, true, mDefaultPadding,
						Configuration.getInstance().getPowerups(),
						CHECKBOX_X_SIZE, CHECKBOX_Y_SIZE, 1,
						GuiCommand.togglePowerups));
		mSensitivityCounter = new GuiSlider(mDefaultSkin, true,
				mDefaultPadding, SLIDER_X_SIZE, SLIDER_Y_SIZE,
				TOUCHPAD_MIN_SENSITIVITY, TOUCHPAD_MAX_SENSITIVITY,
				(float) Configuration.getInstance().getSensitivity(), 1,
				GuiCommand.updateSensitivity);
		mSettingsWidgets.add(new GuiLabel(mDefaultSkin, "aux", false,
						mDefaultPadding, Align.right, MEDIUM_FONT_SIZE, 1, "Touchpad\nsensitivity: "));
		mSettingsWidgets.add(mSensitivityCounter);
		mSettingsWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Back", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 2, GuiCommand.goToMainMenu));
	}
	
	private void createCredits() {
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mTitlePadding, Align.center, 1f, 1, "Credits"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mDefaultPadding, Align.left, SMALL_FONT_SIZE, 1, "Development team"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "aux", true,
						mDefaultPadding, Align.center, SMALL_FONT_SIZE, 1,
						"Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\n"
						+ "Gaylor Bosson\nAdam Haefliger"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mDefaultPadding, Align.left, SMALL_FONT_SIZE, 1, "Programming language"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "aux", true,
						mDefaultPadding, Align.center, SMALL_FONT_SIZE, 1,
						"Java (using Libgdx)"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mDefaultPadding, Align.left, SMALL_FONT_SIZE, 1, "Special thanks"));
		mCreditsWidgets.add(new GuiLabel(mDefaultSkin, "aux", true,
						mDefaultPadding, Align.center, SMALL_FONT_SIZE, 1,
						"Calin Iorgulescu\nSander Kromwijk"));
		mCreditsWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Back", LARGE_BUTTON_X_SIZE,
						BUTTON_Y_SIZE, 1, GuiCommand.goToMainMenu));
	}
	
	private void createGameOver() {
		final int nbColumns = 3;
		mGameOverWidgets.add(new GuiLabel(mDefaultSkin, "default", true,
						mTitlePadding, Align.center, 1f, nbColumns, "Game Over"));
		mPlayerOneTeamFinal = new GuiImage(mDefaultSkin, "teamRed",
						TEAM_LOGO_X_SIZE, TEAM_LOGO_Y_SIZE, false, mDefaultPadding, 1);
		mGameOverWidgets.add(mPlayerOneTeamFinal);
		mFinalScore = new GuiLabel(mDefaultSkin, "default", false,
				mDefaultPadding, Align.center, 1f, 1, "0 - 0");
		mGameOverWidgets.add(mFinalScore);
		mPlayerTwoTeamFinal = new GuiImage(mDefaultSkin, "teamBlue",
				TEAM_LOGO_X_SIZE, TEAM_LOGO_Y_SIZE, true, mDefaultPadding, 1);
		mGameOverWidgets.add(mPlayerTwoTeamFinal);
		mGameOverWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Play again",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, nbColumns, GuiCommand.goToGame));
		mGameOverWidgets.add(new GuiButton(mDefaultSkin, "default", true,
						mDefaultPadding, Align.center, "Back to main menu",
						LARGE_BUTTON_X_SIZE, BUTTON_Y_SIZE, nbColumns,
						GuiCommand.goToMainMenu));
	}
}