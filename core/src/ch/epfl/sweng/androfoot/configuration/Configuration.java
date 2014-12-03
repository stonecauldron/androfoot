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
	private static final int DEFAULT_SENSITIVITY = 60;
	private static final int DEFAULT_NB_OF_ATTACKERS = 2;
	private static final PlayerType DEFAULT_PLAYER_ONE_TYPE = PlayerType.LOCAL_PLAYER;
	private static final PlayerType DEFAULT_PLAYER_TWO_TYPE = PlayerType.LOCAL_PLAYER;
	private static final int NUMBER_OF_PLAYERS_PER_TEAM = 5;

	private static final Configuration ONEINSTANCE = new Configuration();
	private int mScoreLimit;
	private int mSensitivity;
	private PlayerType mPlayerOneType;
	private PlayerType mPlayerTwoType;
	private int mPlayerOneNbAttackers;
	private int mPlayerTwoNbAttackers;

	private Configuration() {
		if (ONEINSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		} else {
			mScoreLimit = DEFAULT_SCORE_LIMIT;
			mSensitivity = DEFAULT_SENSITIVITY;
			mPlayerOneType = DEFAULT_PLAYER_ONE_TYPE;
			mPlayerTwoType = DEFAULT_PLAYER_TWO_TYPE;
			mPlayerOneNbAttackers = DEFAULT_NB_OF_ATTACKERS;
			mPlayerTwoNbAttackers = DEFAULT_NB_OF_ATTACKERS;
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
	
	public void addPlayerOneFormation() {
		mPlayerOneNbAttackers = (mPlayerOneNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerOneFormation() {
		mPlayerOneNbAttackers = ((mPlayerOneNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}
	
	public int[] getPlayerOneFormation() {
		return new int[]{mPlayerOneNbAttackers, NUMBER_OF_PLAYERS_PER_TEAM - mPlayerOneNbAttackers};
	}
	
	public void addPlayerTwoFormation() {
		mPlayerTwoNbAttackers = (mPlayerTwoNbAttackers % (NUMBER_OF_PLAYERS_PER_TEAM - 1)) + 1;
	}

	public void subPlayerTwoFormation() {
		mPlayerTwoNbAttackers = ((mPlayerTwoNbAttackers - 1) + (NUMBER_OF_PLAYERS_PER_TEAM - 2))
				% (NUMBER_OF_PLAYERS_PER_TEAM - 1) + 1;
	}
	
	public int[] getPlayerTwoFormation() {
		return new int[]{mPlayerTwoNbAttackers, NUMBER_OF_PLAYERS_PER_TEAM - mPlayerTwoNbAttackers};
	}
}
