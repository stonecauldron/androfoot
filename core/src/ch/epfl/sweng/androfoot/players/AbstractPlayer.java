package ch.epfl.sweng.androfoot.players;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;

/**
 * Abstract class encapsulating functionality common to all the players.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractPlayer {

	private static final float W_SIZE_X = Constants.WORLD_SIZE_X;

	// position offset
	private static final float POSITION_OFFSET_DEFENSE = 0.2f;
	private static final float POSITION_OFFSET_ATTACK = -0.5f;

	// index in list of attack and defense
	private static final int DEFENSORS_INDEX = 0;
	private static final int ATTACKERS_INDEX = 1;

	private static final float BOARD_DIVISION_FACTOR = 6f;

	// checkstyle hacks
	private static final float FOUR = 4f;
	private static final float FIVE = 5f;

	// all the paddles belonging to a player
	private List<GroupPaddle> paddles;

	// formation of players
	private int nbDefense;
	private int nbAttack;

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

	/**
	 * Destroy all the paddles belonging to the player.
	 */
	public void destroy() {
		for (GroupPaddle groupPaddle : paddles) {
			PhysicsWorld.getPhysicsWorld().destroy(groupPaddle);
		}
	}

	protected List<GroupPaddle> getPaddles() {
		return paddles;
	}

	protected GroupPaddle getDefensePaddles() {
		return paddles.get(DEFENSORS_INDEX);
	}

	protected GroupPaddle getAttackPaddles() {
		return paddles.get(ATTACKERS_INDEX);
	}

	protected PlayerNumber getPlayerNumber() {
		return playerNumber;
	}

	protected int getNumberOfAttackers() {
		return nbAttack;
	}

	protected int getNumberOfDefensors() {
		return nbDefense;
	}

	private void initialisePaddleLayout() {
		float defenseXCoordinate = 0f;
		float attackXCoordinate = 0f;

		initialiseFormations();

		if (playerNumber == PlayerNumber.ONE) {
			defenseXCoordinate = W_SIZE_X * 1.0f / BOARD_DIVISION_FACTOR
					+ POSITION_OFFSET_DEFENSE;
			attackXCoordinate = W_SIZE_X * FOUR / BOARD_DIVISION_FACTOR
					+ POSITION_OFFSET_ATTACK;

			paddles.add(
					DEFENSORS_INDEX,
					PhysicsWorld.getPhysicsWorld().createPaddle(
							defenseXCoordinate, Constants.PADDLE_WIDTH,
							nbDefense, true));

			paddles.add(
					ATTACKERS_INDEX,
					PhysicsWorld.getPhysicsWorld().createPaddle(
							attackXCoordinate, Constants.PADDLE_WIDTH,
							nbAttack, true));

		} else if (playerNumber == PlayerNumber.TWO) {
			defenseXCoordinate = W_SIZE_X * FIVE / BOARD_DIVISION_FACTOR
					- POSITION_OFFSET_DEFENSE;
			attackXCoordinate = W_SIZE_X * 2.0f / BOARD_DIVISION_FACTOR
					- POSITION_OFFSET_ATTACK;

			paddles.add(
					DEFENSORS_INDEX,
					PhysicsWorld.getPhysicsWorld().createPaddle(
							defenseXCoordinate, Constants.PADDLE_WIDTH,
							nbDefense, false));

			paddles.add(
					ATTACKERS_INDEX,
					PhysicsWorld.getPhysicsWorld().createPaddle(
							attackXCoordinate, Constants.PADDLE_WIDTH,
							nbAttack, false));
		}
	}

	private void initialiseFormations() {
		if (playerNumber == PlayerNumber.ONE) {
			nbDefense = Configuration.getInstance().getPlayerOneFormation()[1];
			nbAttack = Configuration.getInstance().getPlayerOneFormation()[0];
		} else if (playerNumber == PlayerNumber.TWO) {
			nbDefense = Configuration.getInstance().getPlayerTwoFormation()[1];
			nbAttack = Configuration.getInstance().getPlayerTwoFormation()[0];
		}
	}
}
