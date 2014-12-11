package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class used to abstract general game settings that player must send to
 *         each other to have synchronous board
 */
public class GameInfo {

	private boolean mTiltOn;
	
	/**
	 * @param attackCount # of attacker
	 * @param defCount # of defensor
	 * @param maxScore maximum score
	 * @param tilOn if shaking is enabled
	 * 
	 */
	public GameInfo(boolean tiltOn) {
		this.mTiltOn = tiltOn;
	}
	

	public boolean ismTiltOn() {
		return mTiltOn;
	}

	public GameInfo() {
		
	}
}
