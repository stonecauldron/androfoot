package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
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
	private BodyDef playerBodyDef = new BodyDef();
	
	private FixtureDef fixtureForCircle = new FixtureDef();
	private FixtureDef fixtureForBox = new FixtureDef();
	
	private PolygonShape controlShape = new PolygonShape();
	private PolygonShape shootingShape = new PolygonShape(); 
	
	private PolygonMap paddleGenerator;
	private boolean teamFlag;
	
	private int zIndex;
	
	/**
	 * Constructor of an individual player.
	 * @param world Instance of the physics world to which the player will be attached.
	 * @param initPosX x coordinate of the initial position of the player.
	 * @param initPosY y coordinate of the initial position of the player.
	 * @param teamOrientation If true the player is facing right, otherwise player is facing left.
	 */
	public Player(World world, float initPosX, float initPosY, boolean teamOrientation) {
		teamFlag = teamOrientation;
		PaddleGenerator fullGenerator;
		if(teamOrientation) {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam1();
		} else {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam2();
		}
		
		paddleGenerator = new PaddleSimplifier(fullGenerator, MAX_PLAYER_VERTEX);
		
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		playerBody = world.createBody(playerBodyDef);
		
		PolygonGenerator controlPolygonBuilder = paddleGenerator.get(PaddleGenerator.CONTROL_BLOCK_KEY);
		PolygonGenerator shootPolygonBuilder = paddleGenerator.get(PaddleGenerator.SHOOT_BLOCK_KEY);
		
		controlShape.set(controlPolygonBuilder.generateVertexesFloat());
		shootingShape.set(shootPolygonBuilder.generateVertexesFloat());
		
		fixtureForCircle.shape = controlShape;
		fixtureForCircle.filter.categoryBits = Constants.CATEGORY_PLAYER;
		fixtureForCircle.filter.maskBits = Constants.CATEGORY_PADDLE | Constants.CATEGORY_BALL;
		fixtureForBox.shape = shootingShape;
		fixtureForBox.filter.categoryBits = Constants.CATEGORY_PLAYER;
		fixtureForBox.filter.maskBits = Constants.CATEGORY_PADDLE | Constants.CATEGORY_BALL;
		
		playerBody.createFixture(fixtureForCircle);
		playerBody.createFixture(fixtureForBox);
		
		if(teamOrientation) {
			playerBody.setTransform(playerBody.getPosition(), (float) (-Math.PI/2));
		} else {
			playerBody.setTransform(playerBody.getPosition(), (float) (Math.PI/2));
		}
		
		zIndex = zIndexCounter;
		zIndexCounter++;
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
	public int getZIndex() {
		return zIndex;
	}

}
