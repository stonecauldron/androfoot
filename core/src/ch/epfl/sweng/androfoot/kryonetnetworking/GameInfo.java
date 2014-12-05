package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class used to abstract general game settings that player must send to
 *         each other to have synchronous board
 */
public class GameInfo {

	private int mAttackerCount;
	private int mDefensorCount;
	
	private int mMaxScore;
	private boolean mTiltOn;
	
	/**
	 * @param attackCount # of attacker
	 * @param defCount # of defensor
	 * @param maxScore maximum score
	 * @param tilOn if shaking is enabled
	 * 
	 */
	public GameInfo(int attackCount, int defCount, int maxScore, boolean tiltOn) {
		this.mAttackerCount = attackCount;
		this.mDefensorCount = defCount;
		this.mMaxScore = maxScore;
		this.mTiltOn = tiltOn;
		
	}
	
	public int getmMaxScore() {
		return mMaxScore;
	}

	public boolean ismTiltOn() {
		return mTiltOn;
	}

	public int getmAttackerCount() {
		return mAttackerCount;
	}

	public int getmDefensorCount() {
		return mDefensorCount;
	}

	public GameInfo() {
		
	}
}
