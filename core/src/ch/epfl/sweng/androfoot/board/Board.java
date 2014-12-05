package ch.epfl.sweng.androfoot.board;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Player;
import ch.epfl.sweng.androfoot.gamelogic.powerups.PowerUpManager;
import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;
import ch.epfl.sweng.androfoot.interfaces.PowerUpObserver;
import ch.epfl.sweng.androfoot.players.AbstractPlayer;
import ch.epfl.sweng.androfoot.players.PlayerFactory;
import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

/**
 * Class to represent the board object. It will contain all the elements
 * necessary for a game. It is a modified singleton where the BoardFactory can
 * set the correct instance of the class.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class Board implements GoalObserver, PlayerObserver, PowerUpObserver  {

	private static final float INITIAL_BALL_SPEED = 2f;

	private static Board mInstance;
	
	private AbstractPlayer mPlayerOne;
	private AbstractPlayer mPlayerTwo;

	private int playerOneScore;
	private int playerTwoScore;

	private int winningScore;

	private Ball mBall;

	/**
	 * Board constructor
	 * 
	 * @param p1
	 *            reference to the first player.
	 * @param p2
	 *            reference to the second player.
	 */
	Board(PlayerType playerOne, PlayerType playerTwo, int winScore) {
		setUpPlayers(playerOne, playerTwo);

		setUpScore(winScore);

		setUpBall();
		
		setUpUpperAndLowerWalls();
		setUpLeftAndRightWalls();
		setUpGoals();
		
		// Start observing events.
		EventManager.getEventManager().addGoalObserver(this);
		EventManager.getEventManager().addPlayerObserver(this);
		EventManager.getEventManager().addPowerUpContactObserver(this);
	}

	/**
	 * Get the board that is currently active.
	 * 
	 * @return the active board
	 */
	public static Board getInstance() {
		return mInstance;
	}

	@Override
	public void goal(DefaultGoal goal, DefaultBall ball) {
		PlayerNumber playerThatScored;
		if (goal.getTeam() == GoalTeam.ONE) {
			// player 2 scored
			playerThatScored = PlayerNumber.TWO;
		} else {
			// player 1 scored
			playerThatScored = PlayerNumber.ONE;
		}
		incrementScore(playerThatScored);
		
		// check if winning score was reached
		if (reachedWinningScore()) {
			resetBoard();
			GuiManager.getInstance().setFinalScore(playerOneScore, playerTwoScore);
			GuiManager.getInstance().executeCommand(GuiCommand.goToGameOver);
			return;
		}
		resetBall(playerThatScored);
	}

	@Override
	public void setBall(Player player, boolean teamFlag) {
		if (player.isAbleToControlBall()) {
			Ball ball = PhysicsWorld.getPhysicsWorld().getBall();
			if (teamFlag) {
				ball.setBallPosition(player.getPositionX()
						+ Constants.BALL_CONTROL_OFFSET - Constants.BALL_RADIUS
						/ 2, player.getPositionY());
				ball.setLinearVelocity(0, 0);
			} else {
				ball.setBallPosition(player.getPositionX()
						- Constants.BALL_CONTROL_OFFSET + Constants.BALL_RADIUS
						/ 2, player.getPositionY());
				ball.setLinearVelocity(0, 0);
			}
		}
	}

	static void setInstance(Board instance) {
		mInstance = instance;
	}

	/**
	 * Deletes the paddles and ball that populate the current board.
	 */
	public void resetBoard() {
		
		// Reset the PowerUpManager
		PowerUpManager.getInstance().reset();
		
		// destroy players
		mPlayerOne.destroy();
		mPlayerTwo.destroy();
		
		// Destroy all the physics objects
		PhysicsWorld.getPhysicsWorld().clear();
		
		// Reset Graphic Engine
		GraphicEngine.getEngine().reset();
		
	}
	
	void instantiateNewGame(PlayerType p1, PlayerType p2, int winScore) {
		setUpScore(winScore);
		
		setUpPlayers(p1, p2);
		
		setUpUpperAndLowerWalls();
		setUpLeftAndRightWalls();
		setUpGoals();
		
		setUpBall();
	}
	
	private void setUpPlayers(PlayerType p1, PlayerType p2) {
		PlayerFactory.resetPlayerNumber();
		mPlayerOne = PlayerFactory.createPlayer(p1);
		mPlayerTwo = PlayerFactory.createPlayer(p2);
	}

	
	private void setUpScore(int winScore) {
		resetScore();
		winningScore = winScore;
	}

	
	
	private void setUpBall() {
		mBall = PhysicsWorld.getPhysicsWorld().createBall(Constants.WORLD_SIZE_X / 2,
				Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
	
		java.util.Random random = new java.util.Random();
		float x = (float) Math.pow(-1, random.nextInt(2));
		mBall.setLinearVelocity(INITIAL_BALL_SPEED * x, 0);
	}

	private void setUpUpperAndLowerWalls() {
	    PhysicsWorld.getPhysicsWorld().createBorder(0, -Constants.BORDER_WIDTH,
                Constants.WORLD_SIZE_X, Constants.BORDER_WIDTH,
                BorderType.NO_TEAM);
        PhysicsWorld.getPhysicsWorld().createBorder(0, Constants.WORLD_SIZE_Y,
                Constants.WORLD_SIZE_X, Constants.BORDER_WIDTH,
                BorderType.NO_TEAM);
	}

	private void setUpLeftAndRightWalls() {
		PhysicsWorld.getPhysicsWorld().createBorder(Constants.WORLD_SIZE_X
				- Constants.BORDER_WIDTH, 0, Constants.BORDER_WIDTH,
				Constants.GOAL_HEIGHT, BorderType.TEAM_TWO);
		PhysicsWorld.getPhysicsWorld().createBorder(Constants.WORLD_SIZE_X
				- Constants.BORDER_WIDTH, Constants.WORLD_SIZE_Y
				- Constants.GOAL_HEIGHT, Constants.BORDER_WIDTH,
				Constants.GOAL_HEIGHT, BorderType.TEAM_TWO);
		
		PhysicsWorld.getPhysicsWorld().createBorder(0, 0, Constants.BORDER_WIDTH,
                Constants.GOAL_HEIGHT, BorderType.TEAM_ONE);
        PhysicsWorld.getPhysicsWorld().createBorder(0, Constants.WORLD_SIZE_Y
                - Constants.GOAL_HEIGHT, Constants.BORDER_WIDTH,
                Constants.GOAL_HEIGHT, BorderType.TEAM_ONE);
	}

	private void setUpGoals() {
		float xTeamOne = Constants.WORLD_ORIGIN_X - Constants.GOAL_WIDTH
				- Constants.GOAL_OFFSET;
		float yTeamOne = Constants.WORLD_ORIGIN_Y;
		PhysicsWorld.getPhysicsWorld().createGoal(xTeamOne, yTeamOne, Constants.GOAL_WIDTH,
				Constants.WORLD_SIZE_Y, GoalTeam.ONE);

		float xTeamTwo = Constants.WORLD_SIZE_X + Constants.GOAL_OFFSET;
		PhysicsWorld.getPhysicsWorld().createGoal(xTeamTwo, yTeamOne, Constants.GOAL_WIDTH,
				Constants.WORLD_SIZE_Y, GoalTeam.TWO);
	}

	private void incrementScore(PlayerNumber playerNumber) {
		if (playerNumber == PlayerNumber.ONE) {
			playerOneScore = playerOneScore + 1;
		} else if (playerNumber == PlayerNumber.TWO) {
			playerTwoScore = playerTwoScore + 1;
		}
		// update score counter on screen
		GraphicEngine.getEngine().setScore(playerOneScore, playerTwoScore);
	}

	private boolean reachedWinningScore() {
		return playerOneScore >= winningScore || playerTwoScore >= winningScore;
	}
	
	private void resetScore() {
		playerOneScore = 0;
		playerTwoScore = 0;
		GraphicEngine.getEngine().setScore(0, 0);
	}
	
	private void resetBall(PlayerNumber playerNumber) {
		mBall.setBallPosition(Constants.WORLD_SIZE_X / 2,
				Constants.WORLD_SIZE_Y / 2);

		// change speed in relation to who scored a goal
		if (playerNumber == PlayerNumber.ONE) {
			// give ball to player two
			mBall.setLinearVelocity(-INITIAL_BALL_SPEED, INITIAL_BALL_SPEED);
		} else if (playerNumber == PlayerNumber.TWO) {
			// give ball to player one
			mBall.setLinearVelocity(INITIAL_BALL_SPEED, INITIAL_BALL_SPEED);
		}
	}

	@Override
	public void applyPowerUp(DefaultPowerUp powerUp) {
		PhysicsWorld.getPhysicsWorld().destroy(powerUp);
	}
}
