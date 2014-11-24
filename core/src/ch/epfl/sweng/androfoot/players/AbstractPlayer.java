package ch.epfl.sweng.androfoot.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

/**
 * Abstract class encapsulating functionality common to all the players.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractPlayer {
	
	// offset between the paddles
	private static final float W_SIZE_X = Constants.WORLD_SIZE_X;
	// number of defensors
	private static final int DEFENSOR_NUMBER = 3;
	// number of attackers
	private static final int ATTACKER_NUMBER = 2;
	
	// all the paddles belonging to a player
	private List<GroupPaddle> paddles;
	
	private PlayerNumber playerNumber;
	
	public AbstractPlayer(PlayerNumber number) {
		// HACK to create a mock Player object for testing purposes
		if (number == PlayerNumber.MOCK) {
			return;
		}
		
		playerNumber = number;
		
		paddles = new ArrayList<GroupPaddle>();
		
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
			defenseXCoordinate = W_SIZE_X * 1.0f/6.0f;
			attackXCoordinate = W_SIZE_X * 4.0f/6.0f;
		}
		else if (playerNumber == PlayerNumber.TWO) {
			defenseXCoordinate = W_SIZE_X * 5.0f/6.0f;
			attackXCoordinate = W_SIZE_X * 2.0f/6.0f;
		}
		
		paddles.add(PhysicsWorld.createPaddle(defenseXCoordinate, Constants.PADDLE_WIDTH, DEFENSOR_NUMBER, playerNumber == PlayerNumber.ONE));
		paddles.add(PhysicsWorld.createPaddle(attackXCoordinate, Constants.PADDLE_WIDTH, ATTACKER_NUMBER, playerNumber == PlayerNumber.ONE));
	}
}
