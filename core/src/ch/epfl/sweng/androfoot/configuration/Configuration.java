package ch.epfl.sweng.androfoot.configuration;

import ch.epfl.sweng.androfoot.players.PlayerType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @author Sidney Barthe This singleton class contains all configuration
 *         variables of the game (the score limit, whether sounds are activated
 *         or not...). They will be set in the settings and before starting a
 *         game. They can be accessed from anywhere using getters. All these
 *         values are loaded as preferences when the first instance is created
 *         and can be saved using the savePreferences method.
 */
public final class Configuration {
	private static final Configuration ONEINSTANCE = new Configuration();
	private static final int DEFAULT_SCORE_LIMIT = 5;
	private static final int DEFAULT_SENSITIVITY = 60;
	private static final int DEFAULT_NB_OF_ATTACKERS = 2;
	private static final PlayerType DEFAULT_PLAYER_ONE_TYPE = PlayerType.LOCAL_PLAYER;
	private static final PlayerType DEFAULT_PLAYER_TWO_TYPE = PlayerType.LOCAL_PLAYER;
	private static final int NUMBER_OF_PLAYERS_PER_TEAM = 5;
	private int mScoreLimit;
	private int mSensitivity;
	private PlayerType mPlayerOneType;
	private PlayerType mPlayerTwoType;
	private int mPlayerOneNbAttackers;
	private int mPlayerTwoNbAttackers;
	private boolean mSoundOn;
	private boolean mTiltingOn;
	private boolean mPowerupsOn;
	private Preferences mPrefs;

	private Configuration() {
		if (ONEINSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		} else {
			mPrefs = Gdx.app.getPreferences("default");
			mScoreLimit = mPrefs.getInteger("SCORE_LIMIT", DEFAULT_SCORE_LIMIT);
			mSensitivity = mPrefs
					.getInteger("SENSITIVITY", DEFAULT_SENSITIVITY);
			switch (mPrefs.getInteger("PLAYER_ONE_TYPE")) {
			case 0:
				mPlayerOneType = PlayerType.LOCAL_PLAYER;
				break;
			case 1:
				mPlayerOneType = PlayerType.RANDOM_AI_PLAYER;
				break;
			default:
				mPlayerOneType = DEFAULT_PLAYER_ONE_TYPE;
				break;
			}
			switch (mPrefs.getInteger("PLAYER_TWO_TYPE")) {
			case 0:
				mPlayerTwoType = PlayerType.LOCAL_PLAYER;
				break;
			case 1:
				mPlayerTwoType = PlayerType.RANDOM_AI_PLAYER;
				break;
			default:
				mPlayerTwoType = DEFAULT_PLAYER_TWO_TYPE;
				break;
			}
			mPlayerOneNbAttackers = mPrefs.getInteger(
					"PLAYER_ONE_NB_ATTACKERS", DEFAULT_NB_OF_ATTACKERS);
			mPlayerTwoNbAttackers = mPrefs.getInteger(
					"PLAYER_TWO_NB_ATTACKERS", DEFAULT_NB_OF_ATTACKERS);
			mSoundOn = mPrefs.getBoolean("SOUND", true);
			mTiltingOn = mPrefs.getBoolean("TILTING", true);
			mPowerupsOn = mPrefs.getBoolean("POWERUPS", true);
		}
	}

	public static Configuration getInstance() {
		return ONEINSTANCE;
	}

	public void savePreferences() {
		mPrefs.putInteger("SCORE_LIMIT", mScoreLimit);
		mPrefs.putInteger("SENSITIVITY", mSensitivity);
		switch (mPlayerOneType) {
		case LOCAL_PLAYER:
			mPrefs.putInteger("PLAYER_ONE_TYPE", 0);
			break;
		case RANDOM_AI_PLAYER:
			mPrefs.putInteger("PLAYER_ONE_TYPE", 1);
			break;
		default:
			mPrefs.putInteger("PLAYER_ONE_TYPE", 0);
			break;
		}
		;
		switch (mPlayerTwoType) {
		case LOCAL_PLAYER:
			mPrefs.putInteger("PLAYER_TWO_TYPE", 0);
			break;
		case RANDOM_AI_PLAYER:
			mPrefs.putInteger("PLAYER_TWO_TYPE", 1);
			break;
		default:
			mPrefs.putInteger("PLAYER_TWO_TYPE", 0);
			break;
		}
		;
		mPrefs.putInteger("PLAYER_ONE_NB_ATTACKERS", mPlayerOneNbAttackers);
		mPrefs.putInteger("PLAYER_TWO_NB_ATTACKERS", mPlayerTwoNbAttackers);
		mPrefs.putBoolean("SOUND", mSoundOn);
		mPrefs.putBoolean("TILTING", mTiltingOn);
		mPrefs.putBoolean("POWERUPS", mPowerupsOn);
		mPrefs.flush();
	}

	public int getScoreLimit() {
		return mScoreLimit;
	}

	public void addScoreLimit(int x) {
		mScoreLimit = mScoreLimit + x;
	}

	public void subScoreLimit(int x) {
		mScoreLimit = mScoreLimit - x;
		if (mScoreLimit < 1) {
			mScoreLimit = 1;
		}
	}

	public void setSensivity(int x) {
		mSensitivity = x;
	}

	public int getSensitivity() {
		return mSensitivity;
	}

	public void setPlayerOneType(PlayerType type) {
		mPlayerOneType = type;
	}

	public void setPlayerTwoType(PlayerType type) {
		mPlayerTwoType = type;
	}

	public PlayerType getPlayerOneType() {
		return mPlayerOneType;
	}

	public PlayerType getPlayerTwoType() {
		return mPlayerTwoType;
	}

	public void addPlayerOneFormation() {
		mPlayerOneNbAttackers = (mPlayerOneNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerOneFormation() {
		mPlayerOneNbAttackers = ((mPlayerOneNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}

	public int[] getPlayerOneFormation() {
		return new int[] { mPlayerOneNbAttackers,
				NUMBER_OF_PLAYERS_PER_TEAM - mPlayerOneNbAttackers };
	}

	public void addPlayerTwoFormation() {
		mPlayerTwoNbAttackers = (mPlayerTwoNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerTwoFormation() {
		System.out.println("reached");
		mPlayerTwoNbAttackers = ((mPlayerTwoNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}

	public void setDefaultConfig() {
		mPlayerOneNbAttackers = 2;
		mPlayerTwoNbAttackers = 2;
		if (!mTiltingOn) {
			toggleTilting();
		}
	}

	public int[] getPlayerTwoFormation() {
		return new int[] { mPlayerTwoNbAttackers,
				NUMBER_OF_PLAYERS_PER_TEAM - mPlayerTwoNbAttackers };
	}

	public void toggleSound() {
		if (mSoundOn == false) {
			mSoundOn = true;
		} else {
			mSoundOn = false;
		}
	}

	public boolean getSound() {
		return mSoundOn;
	}

	public void toggleTilting() {
		if (mTiltingOn == false) {
			mTiltingOn = true;
		} else {
			mTiltingOn = false;
		}
	}

	public boolean getTilting() {
		return mTiltingOn;
	}

	public void togglePowerups() {
		if (mPowerupsOn == false) {
			mPowerupsOn = true;
		} else {
			mPowerupsOn = false;
		}
	}

	public boolean getPowerups() {
		return mPowerupsOn;
	}
}
