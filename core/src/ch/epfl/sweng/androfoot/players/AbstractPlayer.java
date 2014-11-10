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
	private static final float PADDLE_OFFSET = Constants.WORLD_SIZE_X/5.0f;
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
		// TODO create createPaddleGroup method in the physics Engine
		// will not have references to physics world and physics world height
		// createPaddleGroupOffense(x coordinate)
		// createPaddleGroupdDefense(x coordinate)
		
		// TODO BoardFactory
		// Creates a board object and defines wich actual player instances will
		// be created.
		float defenseXCoordinate = 0f;
		float attackXCoordinate = 0f;
		
		if (playerNumber == PlayerNumber.ONE) {
			defenseXCoordinate = PADDLE_OFFSET;
			attackXCoordinate = PADDLE_OFFSET * 3;
		}
		else if (playerNumber == PlayerNumber.TWO) {
			defenseXCoordinate = PADDLE_OFFSET * 4;
			attackXCoordinate = PADDLE_OFFSET * 2;
		}
		GroupPaddle.createGroupPaddle(defenseXCoordinate, DEFENSOR_NUMBER, playerNumber);
		GroupPaddle.createGroupPaddle(attackXCoordinate, ATTACKER_NUMBER, playerNumber);
	}
}
