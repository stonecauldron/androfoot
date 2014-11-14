package ch.epfl.sweng.androfoot.players;

import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;

/**
 * Abstract class encapsulating functionality common to all the players.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractPlayer {
	
	// offset between the paddles
	private static final float PADDLE_OFFSET = Constants.WORLD_SIZE_X;
	// number of defensors
	private static final int DEFENSOR_NUMBER = 3;
	// number of attackers
	private static final int ATTACKER_NUMBER = 2;
	
	// all the paddles belonging to a player
	private List<GroupPaddle> paddles;
	
	private PlayerNumber playerNumber;
	
	public AbstractPlayer(PlayerNumber number) {
		playerNumber = number;
		
		initialisePaddleLayout();
	}
	
	protected List<GroupPaddle> getPaddles() {
		return paddles;
	}
	
	protected PlayerNumber getPlayerNumber() {
		return playerNumber;
	}
	
	private void initialisePaddleLayout() {
		float defenseXCoordinate = 0f;
		float attackXCoordinate = 0f;
		
		if (playerNumber == PlayerNumber.ONE) {
			defenseXCoordinate = PADDLE_OFFSET * 1.0f/6.0f;
			attackXCoordinate = PADDLE_OFFSET * 4.0f/6.0f;
		}
		else if (playerNumber == PlayerNumber.TWO) {
			defenseXCoordinate = PADDLE_OFFSET * 5.0f/6.0f;
			attackXCoordinate = PADDLE_OFFSET * 2.0f/6.0f;
		}
		GroupPaddle.createGroupPaddle(defenseXCoordinate, DEFENSOR_NUMBER, playerNumber);
		GroupPaddle.createGroupPaddle(attackXCoordinate, ATTACKER_NUMBER, playerNumber);
	}
}