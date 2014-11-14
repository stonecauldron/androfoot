package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.PaddleInterface;
import ch.epfl.sweng.androfoot.interfaces.PlayerInterface;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.PolygonMap;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleSimplifier;

/**
 * Class that defines an individual player.
 * @author Matvey
 *
 */
public class Player implements PlayerInterface {
	
	private static final int MAX_PLAYER_VERTEX = 11;
	private static int zIndexCounter = 1;
	private Body playerBody;
	private final BodyDef playerBodyDef = new BodyDef();
	
	private final FixtureDef fixtureForCircle = new FixtureDef();
	private final FixtureDef fixtureForBox = new FixtureDef();
	
	private final PolygonShape controlShape = new PolygonShape();
	private final PolygonShape shootingShape = new PolygonShape(); 
	
	private PolygonMap paddleGenerator;
	private boolean teamFlag;
	
	private final PaddleInterface parent;
	
	private int zIndex;
	
	/**
	 * Constructor of an individual player.
	 * @param world Instance of the physics world to which the player will be attached.
	 * @param initPosX x coordinate of the initial position of the player.
	 * @param initPosY y coordinate of the initial position of the player.
	 * @param teamOrientation If true the player is facing right, otherwise player is facing left.
	 * @param paddle 
	 */
	public Player(World world, float initPosX, float initPosY, boolean teamOrientation, Paddle paddle) {
		parent = paddle;
		teamFlag = teamOrientation;
		
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		playerBody = world.createBody(playerBodyDef);
		
		createPaddleShape(teamFlag);
		
		createAttachFixtureForCircle();
		createAttachFixtureForBox();
		
		if(teamFlag) {
			playerBody.setTransform(playerBody.getPosition(), (float) (-Math.PI/2));
		} else {
			playerBody.setTransform(playerBody.getPosition(), (float) (Math.PI/2));
		}
		
		zIndex = zIndexCounter;
		zIndexCounter++;
		
		PaddleContactListener.addPlayer(this);
		PlayersContactListener.addPlayer(this);
	}
	
	/**
	 * Auxiliary method used to create the paddle shape using the PolygonGenerator.
	 * @param teamFlag True for team one, else false.
	 */
	private void createPaddleShape(boolean teamFlag) {
		
		PaddleGenerator fullGenerator;
		if(teamFlag) {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam1();
		} else {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam2();
		}
		
		paddleGenerator = new PaddleSimplifier(fullGenerator, MAX_PLAYER_VERTEX);
		
		PolygonGenerator controlPolygonBuilder = paddleGenerator.get(PaddleGenerator.CONTROL_BLOCK_KEY);
		PolygonGenerator shootPolygonBuilder = paddleGenerator.get(PaddleGenerator.SHOOT_BLOCK_KEY);
		
		controlShape.set(controlPolygonBuilder.generateVertexesFloat());
		shootingShape.set(shootPolygonBuilder.generateVertexesFloat());
	}
	
	/**
	 * Creates and attaches the circular fixture to the player object.
	 */
	private void createAttachFixtureForCircle() {
		fixtureForCircle.shape = controlShape;
		fixtureForCircle.filter.categoryBits = Constants.CATEGORY_PLAYER;
		fixtureForCircle.filter.maskBits = Constants.CATEGORY_PADDLE | Constants.CATEGORY_BALL;
		
		playerBody.createFixture(fixtureForCircle);
	}
	
	/**
	 * Creates and attaches the rectangular fixture to the player object.
	 */
	private void createAttachFixtureForBox() {
		fixtureForBox.shape = shootingShape;
		fixtureForBox.filter.categoryBits = Constants.CATEGORY_PLAYER;
		fixtureForBox.filter.maskBits = Constants.CATEGORY_PADDLE | Constants.CATEGORY_BALL;
		
		playerBody.createFixture(fixtureForBox);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public float getPositionX() {
		return playerBody.getPosition().x;
	}

	@Override
	public float getPositionY() {
		return playerBody.getPosition().y;
	}

	@Override
	public void setPlayerVelocity(float x, float y) {
		playerBody.setLinearVelocity(x, y);
	}

	@Override
	public float getPlayerAngle() {
		return playerBody.getAngle();
	}

	@Override
	public boolean getTeam() {
		return teamFlag;
	}
	
	@Override
	public Body getBody() {
		return playerBody;
	}

	@Override
	public int getZIndex() {
		return zIndex;
	}

	@Override
	public boolean isAbleToControlBall() {
		return parent.isAbleToControlBall();
	}

}
