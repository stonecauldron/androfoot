package ch.epfl.sweng.androfoot.configuration;

import ch.epfl.sweng.androfoot.players.PlayerType;

/**
 * @author Sidney Barthe This singleton class contains all configuration
 *         variables of the game (the score limit, whether sounds are activated
 *         or not...). They will be set by some of the screens (settingsScreen,
 *         localPlayScreen...) using setters and they can be accessed by any
 *         class in the project using getters.
 * 
 *         If you need a functionality implemented here, you can contact me by
 *         email, slack, facebook or add a TODO comment here.
 */
public final class Configuration {
	private static final int DEFAULT_SCORE_LIMIT = 5;

	private static final Configuration ONEINSTANCE = new Configuration();
	private int mScoreLimit;
	private int mSensitivity;
	private PlayerType mPlayerOneType;
	private PlayerType mPlayerTwoType;

	private Configuration() {
		if (ONEINSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		} else {
			mScoreLimit = DEFAULT_SCORE_LIMIT;
		}
	}

	public static Configuration getInstance() {
		return ONEINSTANCE;
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
}
