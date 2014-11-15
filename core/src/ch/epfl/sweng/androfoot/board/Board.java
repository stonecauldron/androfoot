package ch.epfl.sweng.androfoot.board;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.players.AbstractPlayer;

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
	public void goal(boolean isTeamOne) {
		ball.setBallPosition(2, 2);
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
	}
	
	private void setUpUpperAndLowerWalls() {
		PhysicsWorld.createBorder(0, 0, Constants.GOAL_WIDTH,
				Constants.GOAL_HEIGHT, true);
		PhysicsWorld.createBorder(0, Constants.WORLD_SIZE_Y
				- Constants.GOAL_HEIGHT, Constants.GOAL_WIDTH,
				Constants.GOAL_HEIGHT, true);
	}
	
	private void setUpLeftAndRightWalls() {
		PhysicsWorld.createBorder(
				Constants.WORLD_SIZE_X - Constants.GOAL_WIDTH, 0,
				Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, false);
		PhysicsWorld.createBorder(
				Constants.WORLD_SIZE_X - Constants.GOAL_WIDTH,
				Constants.WORLD_SIZE_Y - Constants.GOAL_HEIGHT,
				Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, false);

		PhysicsWorld.createBorder(0, -Constants.GOAL_WIDTH,
				Constants.WORLD_SIZE_X, Constants.GOAL_WIDTH, true);
		PhysicsWorld.createBorder(0, Constants.WORLD_SIZE_Y,
				Constants.WORLD_SIZE_X, Constants.GOAL_WIDTH, true);
	}
	
	private void setUpGoals() {
		PhysicsWorld.createGoal(true);
		PhysicsWorld.createGoal(false);
	}
}
