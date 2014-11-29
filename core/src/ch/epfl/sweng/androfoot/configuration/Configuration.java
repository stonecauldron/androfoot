package ch.epfl.sweng.androfoot.configuration;

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
	private int mSensibility;

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
		if(mScoreLimit < 1) {
			mScoreLimit = 1;
		}
	}
	
	public void setSensibility(int x) {
		mSensibility = x;
	}
	
	public int getSensibility() {
		return mSensibility;
	}
}
