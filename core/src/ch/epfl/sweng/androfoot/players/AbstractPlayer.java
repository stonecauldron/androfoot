package ch.epfl.sweng.androfoot.players;

import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;

/**
 * Abstract class encapsulating functionality common to all the players.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractPlayer {
	
	private List<GroupPaddle> paddles;
	
	private PlayerNumber playerNumber;
	
	public AbstractPlayer(PlayerNumber number) {
		playerNumber = number;
	}
	
	protected List<GroupPaddle> getPaddles() {
		return paddles;
	}
	
	protected PlayerNumber getPlayerNumber() {
		return playerNumber;
	}
}
