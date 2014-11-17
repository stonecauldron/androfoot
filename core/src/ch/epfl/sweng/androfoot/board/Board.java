package ch.epfl.sweng.androfoot.board;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.players.AbstractPlayer;
import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

/**
 * Class to represent the board object. It will contain all the elements
 * necessary for a game. It is a modified singleton where the BoardFactory
 * can set the correct instance of the class.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class Board implements GoalObserver {

	private static final String ERROR_MESSAGE = "Board was not created.";
	private final float INITIAL_BALL_SPEED = 2f;

	private static Board mInstance;

	private AbstractPlayer playerOne;
	private AbstractPlayer playerTwo;

	private int playerOneScore;
	private int playerTwoScore;

	private Ball ball;

	/**
	 * Board constructor
	 * 
	 * @param p1 reference to the first player.
	 * @param p2
	 *           reference to the second player.
	 */
	Board(AbstractPlayer p1, AbstractPlayer p2) {
		playerOne = p1;
		playerTwo = p2;

		playerOneScore = 0;
		playerTwoScore = 0;
		
		setUpBall();
		
		setUpUpperAndLowerWalls();
		setUpLeftAndRightWalls();
		setUpGoals();
		
		// start observing goal events
		EventManager.getEventManager().addGoalObserver(this);
	}

	/**
	 * Get the board that is currently active.
	 * 
	 * @return the active board
	 */
	public static Board getInstance() {
		if (mInstance == null) {
			throw new UnsupportedOperationException(ERROR_MESSAGE);
		}
		return mInstance;
	}

	static void setInstance(Board instance) {
		mInstance = instance;
	}

	@Override
	public void goal(GoalTeam team) {
		if (team == GoalTeam.ONE) {
			// player 2 scored
			incrementScore(PlayerNumber.TWO);
			resetBall(PlayerNumber.TWO);
		}
		else {
			// player 1 scored
			incrementScore(PlayerNumber.ONE);
			resetBall(PlayerNumber.ONE);
		}
	}

	/* FIXME Method does not work as supposed
	@Override
	public void setBall(Player player, boolean teamFlag) {
		System.out.println("Yes!");

		Ball ball = PhysicsWorld.getBall();
		if (teamFlag) {
			ball.setBallPosition(player.getPositionX()
					+ Constants.BALL_CONTROL_OFFSET, player.getPositionY());
			ball.setLinearVelocity(0, 0);
		} else {
			ball.setBallPosition(player.getPositionX()
					- Constants.BALL_CONTROL_OFFSET, player.getPositionY());
			ball.setLinearVelocity(0, 0);
		}
	}
	*/
	
	private void setUpBall() {
		ball = PhysicsWorld.createBall(Constants.WORLD_SIZE_X / 2,
				Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
		
		java.util.Random random = new java.util.Random();
		float x = (float) Math.pow(-1, random.nextInt(2));
		ball.setLinearVelocity(3 * x, 0);
	}
	
	private void setUpUpperAndLowerWalls() {
		PhysicsWorld.createBorder(0, 0, Constants.BORDER_WIDTH,
				Constants.GOAL_HEIGHT, BorderType.TEAM_ONE);
		PhysicsWorld.createBorder(0, Constants.WORLD_SIZE_Y
				- Constants.GOAL_HEIGHT, Constants.BORDER_WIDTH,
				Constants.GOAL_HEIGHT, BorderType.TEAM_ONE);
	}
	
	private void setUpLeftAndRightWalls() {
		PhysicsWorld.createBorder(
				Constants.WORLD_SIZE_X - Constants.BORDER_WIDTH, 0,
				Constants.BORDER_WIDTH, Constants.GOAL_HEIGHT, BorderType.TEAM_TWO);
		PhysicsWorld.createBorder(
				Constants.WORLD_SIZE_X - Constants.BORDER_WIDTH,
				Constants.WORLD_SIZE_Y - Constants.GOAL_HEIGHT,
				Constants.BORDER_WIDTH, Constants.GOAL_HEIGHT, BorderType.TEAM_TWO);

		PhysicsWorld.createBorder(0, -Constants.BORDER_WIDTH,
				Constants.WORLD_SIZE_X, Constants.BORDER_WIDTH, BorderType.NO_TEAM);
		PhysicsWorld.createBorder(0, Constants.WORLD_SIZE_Y,
				Constants.WORLD_SIZE_X, Constants.BORDER_WIDTH, BorderType.NO_TEAM);
	}
	
	private void setUpGoals() {
	    float xTeamOne = Constants.WORLD_ORIGIN_X - Constants.GOAL_WIDTH - Constants.GOAL_OFFSET;
	    float yTeamOne = Constants.WORLD_ORIGIN_Y;
		PhysicsWorld.createGoal(xTeamOne, yTeamOne, Constants.GOAL_WIDTH, Constants.WORLD_SIZE_Y, GoalTeam.ONE);
		
		float xTeamTwo = Constants.WORLD_SIZE_X + Constants.GOAL_OFFSET;
		PhysicsWorld.createGoal(xTeamTwo, yTeamOne, Constants.GOAL_WIDTH, Constants.WORLD_SIZE_Y, GoalTeam.TWO);
	}
	
	private void incrementScore(PlayerNumber playerNumber) {
		if (playerNumber == PlayerNumber.ONE) {
			playerOneScore = playerOneScore + 1;
		}
		else if (playerNumber == PlayerNumber.TWO) {
			playerTwoScore = playerTwoScore + 1;
		}
		// update score counter on screen
		GraphicEngine.getEngine().setScore(playerOneScore, playerTwoScore);
	}
	
	private void resetBall(PlayerNumber playerNumber) {
		ball.setBallPosition(Constants.WORLD_SIZE_X/2, Constants.WORLD_SIZE_Y/2);
		
		// change speed in relation to who scored a goal
		if (playerNumber == PlayerNumber.ONE) {
			// give ball to player two
			ball.setLinearVelocity(-INITIAL_BALL_SPEED, INITIAL_BALL_SPEED);
		}
		else if (playerNumber == PlayerNumber.TWO) {
			// give ball to player one
			ball.setLinearVelocity(INITIAL_BALL_SPEED, INITIAL_BALL_SPEED);
		}
	}
}
