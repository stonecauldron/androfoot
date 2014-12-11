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
	private static final int DEFAULT_PLAYER_ONE_TEAM = 0;
	private static final int DEFAULT_PLAYER_TWO_TEAM = 1;
	private static final int DEFAULT_PLAYER_ONE_TYPE = 0;
	private static final int DEFAULT_PLAYER_TWO_TYPE = 0;
	private static final int NUMBER_OF_PLAYER_TYPES = 5;
	private static final int NUMBER_OF_PLAYERS_PER_TEAM = 5;
	private static final int NUMBER_OF_TEAMS = 8;
	private int mScoreLimit;
	private int mSensitivity;
	private int mPlayerOneTypeIndex;
	private int mPlayerTwoTypeIndex;
	private int mPlayerOneNbAttackers;
	private int mPlayerTwoNbAttackers;
	private int mPlayerOneTeam;
	private int mPlayerTwoTeam;
	private boolean mSoundOn;
	private boolean mTiltingOn;
	private boolean mPowerupsOn;
	private Preferences mPrefs;
	private boolean mNetworkMode;

	private Configuration() {
		if (ONEINSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		} else {
			mPrefs = Gdx.app.getPreferences("default");
			mScoreLimit = mPrefs.getInteger("SCORE_LIMIT", DEFAULT_SCORE_LIMIT);
			mSensitivity = mPrefs
					.getInteger("SENSITIVITY", DEFAULT_SENSITIVITY);
			mPlayerOneTypeIndex = mPrefs.getInteger("PLAYER_ONE_TYPE", DEFAULT_PLAYER_ONE_TYPE);
			mPlayerTwoTypeIndex = mPrefs.getInteger("PLAYER_TWO_TYPE", DEFAULT_PLAYER_TWO_TYPE);
			mPlayerOneNbAttackers = mPrefs.getInteger(
					"PLAYER_ONE_NB_ATTACKERS", DEFAULT_NB_OF_ATTACKERS);
			mPlayerTwoNbAttackers = mPrefs.getInteger(
					"PLAYER_TWO_NB_ATTACKERS", DEFAULT_NB_OF_ATTACKERS);
			mPlayerOneTeam = mPrefs.getInteger(
					"PLAYER_ONE_TEAM", DEFAULT_PLAYER_ONE_TEAM);
			mPlayerTwoTeam = mPrefs.getInteger(
					"PLAYER_TWO_TEAM", DEFAULT_PLAYER_TWO_TEAM);
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
		mPrefs.putInteger("PLAYER_ONE_TYPE", mPlayerOneTypeIndex % NUMBER_OF_PLAYER_TYPES);
		mPrefs.putInteger("PLAYER_TWO_TYPE", mPlayerTwoTypeIndex % NUMBER_OF_PLAYER_TYPES);
		mPrefs.putInteger("PLAYER_ONE_NB_ATTACKERS", mPlayerOneNbAttackers);
		mPrefs.putInteger("PLAYER_TWO_NB_ATTACKERS", mPlayerTwoNbAttackers);
		mPrefs.putInteger("PLAYER_ONE_TEAM", mPlayerOneTeam);
		mPrefs.putInteger("PLAYER_TWO_TEAM", mPlayerTwoTeam);
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

	public void addPlayerOneType() {
		mPlayerOneTypeIndex = (mPlayerOneTypeIndex + 1)
				% NUMBER_OF_PLAYER_TYPES;
	}
	
	public void subPlayerOneType() {
		mPlayerOneTypeIndex = (mPlayerOneTypeIndex + (NUMBER_OF_PLAYER_TYPES - 1))
				% NUMBER_OF_PLAYER_TYPES;
	}
	
	public void addPlayerTwoType() {
		mPlayerTwoTypeIndex = (mPlayerTwoTypeIndex + 1)
				% NUMBER_OF_PLAYER_TYPES;
	}
	
	public void subPlayerTwoType() {
		mPlayerTwoTypeIndex = (mPlayerTwoTypeIndex + (NUMBER_OF_PLAYER_TYPES - 1))
				% NUMBER_OF_PLAYER_TYPES;
	}

	public void setPlayerOneType(PlayerType type) {
		switch (type) {
			case LOCAL_PLAYER:
				mPlayerOneTypeIndex = 0;
				break;
			case RANDOM_AI_PLAYER:
				mPlayerOneTypeIndex = 1;
				break;
			case EASY_AI_PLAYER:
				mPlayerOneTypeIndex = 2;
				break;
			case MEDIUM_AI_PLAYER:
				mPlayerOneTypeIndex = 3;
				break;
			case HARD_AI_PLAYER:
				mPlayerOneTypeIndex = 4;
				break;
			case REMOTE_PLAYER:
				mPlayerOneTypeIndex = 5;
				break;
			default:
				break;
		}
	}

	public void setPlayerTwoType(PlayerType type) {
		switch (type) {
			case LOCAL_PLAYER:
				mPlayerTwoTypeIndex = 0;
				break;
			case RANDOM_AI_PLAYER:
				mPlayerTwoTypeIndex = 1;
				break;
			case EASY_AI_PLAYER:
				mPlayerTwoTypeIndex = 2;
				break;
			case MEDIUM_AI_PLAYER:
				mPlayerTwoTypeIndex = 3;
				break;
			case HARD_AI_PLAYER:
				mPlayerTwoTypeIndex = 4;
				break;
			case REMOTE_PLAYER:
				mPlayerTwoTypeIndex = 5;
				break;
			default:
				break;
		}
	}

	public PlayerType getPlayerOneType() {
		switch (mPlayerOneTypeIndex) {
			case 0:
				return PlayerType.LOCAL_PLAYER;
			case 1:
				return PlayerType.RANDOM_AI_PLAYER;
			case 2:
				return PlayerType.EASY_AI_PLAYER;
			case 3:
				return PlayerType.MEDIUM_AI_PLAYER;
			case 4:
				return PlayerType.HARD_AI_PLAYER;
			case 5:
				return PlayerType.REMOTE_PLAYER;
			default:
				break;
		}
		return PlayerType.LOCAL_PLAYER;
	}

	public PlayerType getPlayerTwoType() {
		switch (mPlayerTwoTypeIndex) {
			case 0:
				return PlayerType.LOCAL_PLAYER;
			case 1:
				return PlayerType.RANDOM_AI_PLAYER;
			case 2:
				return PlayerType.EASY_AI_PLAYER;
			case 3:
				return PlayerType.MEDIUM_AI_PLAYER;
			case 4:
				return PlayerType.HARD_AI_PLAYER;
			case 5:
				return PlayerType.REMOTE_PLAYER;
			default:
				break;
		}
		return PlayerType.LOCAL_PLAYER;
	}

	public void addPlayerOneFormation() {
		mPlayerOneNbAttackers = (mPlayerOneNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerOneFormation() {
		mPlayerOneNbAttackers = ((mPlayerOneNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}

	public int[] getPlayerOneFormation() {
		return new int[] {mPlayerOneNbAttackers,
			NUMBER_OF_PLAYERS_PER_TEAM - mPlayerOneNbAttackers };
	}

	public void addPlayerTwoFormation() {
		mPlayerTwoNbAttackers = (mPlayerTwoNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerTwoFormation() {
		mPlayerTwoNbAttackers = ((mPlayerTwoNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}
	
	public void addPlayerOneTeam() {
		mPlayerOneTeam = (mPlayerOneTeam + 1) % NUMBER_OF_TEAMS;
		if (mPlayerOneTeam == mPlayerTwoTeam) {
			addPlayerOneTeam();
		}
	}

	public void subPlayerOneTeam() {
		mPlayerOneTeam = (mPlayerOneTeam + (NUMBER_OF_TEAMS - 1)) % NUMBER_OF_TEAMS;
		if (mPlayerOneTeam == mPlayerTwoTeam) {
			subPlayerOneTeam();
		}
	}
	
	public int getPlayerOneTeam() {
		return mPlayerOneTeam;
	}
	
	public void addPlayerTwoTeam() {
		mPlayerTwoTeam = (mPlayerTwoTeam + 1) % NUMBER_OF_TEAMS;
		if (mPlayerTwoTeam == mPlayerOneTeam) {
			addPlayerTwoTeam();
		}
	}

	public void subPlayerTwoTeam() {
		mPlayerTwoTeam = (mPlayerTwoTeam + (NUMBER_OF_TEAMS - 1)) % NUMBER_OF_TEAMS;
		if (mPlayerTwoTeam == mPlayerOneTeam) {
			subPlayerTwoTeam();
		}
	}
	
	public int getPlayerTwoTeam() {
		return mPlayerTwoTeam;
	}

	public void setDefaultConfig() {
		mPlayerOneNbAttackers = 2;
		mPlayerTwoNbAttackers = 2;
		mScoreLimit = 11;
		if (!mTiltingOn) {
			toggleTilting();
		}
		mNetworkMode = true;
	}

	public int[] getPlayerTwoFormation() {
		return new int[] {mPlayerTwoNbAttackers,
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

	public boolean ismNetworkMode() {
		return mNetworkMode;
	}

	public void setNetworkMode(boolean networkMode) {
		this.mNetworkMode = networkMode;
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
