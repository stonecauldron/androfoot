package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ch.epfl.sweng.androfoot.interfaces.PlayerInterface;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Class that defines an individual player.
 * @author Matvey
 *
 */
public class Player implements PlayerInterface {
	
	private Body playerBody;
	private BodyDef playerBodyDef = new BodyDef();
	
	private FixtureDef fixtureForCircle = new FixtureDef();
	private FixtureDef fixtureForBox = new FixtureDef();
	
	/**
	 * Constructor of an individual player.
	 * @param world Instance of the physics world to which the player will be attached.
	 * @param initPosX x coordinate of the initial position of the player.
	 * @param initPosY y coordinate of the initial position of the player.
	 * @param facingRight If true the player is facing right, otherwise player is facing left.
	 */
	public Player(World world, float initPosX, float initPosY, boolean facingRight) {
		playerBodyDef.type = BodyType.DynamicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		playerBody = world.createBody(playerBodyDef);
		
		createAttachCircleFixture();
		
		createAttachBoxFixture(facingRight);
	}
	
	/**
	 * Creates and attaches the circle fixture to the physics body.
	 */
	private void createAttachCircleFixture() {
		CircleShape circle = new CircleShape();
		
		circle.setRadius(Constants.CIRCLERADIUS);
		fixtureForCircle.shape = circle;
		fixtureForCircle.density = Constants.PLAYERDENSITY;
		fixtureForCircle.friction = Constants.PLAYERFRICTION;
		fixtureForCircle.restitution = Constants.PLAYERRESTITUTION;
		fixtureForCircle.filter.maskBits = Constants.CATEGORY_PLAYER;
		
		playerBody.createFixture(fixtureForCircle);
		
		circle.dispose();
	}
	
	/**
	 * Creates and attaches the box fixture to the physics body.
	 * @param facingRight If true the player is facing right, otherwise player is facing left.
	 */
	private void createAttachBoxFixture(boolean facingRight) {
		PolygonShape boxShape = new PolygonShape();
		
		if (facingRight) {
			boxShape.setAsBox(Constants.BOXHALFWIDTH, Constants.BOXHALFLENGTH, Constants.OFFSETFACINGRIGHT, 0);
		} else {
			boxShape.setAsBox(Constants.BOXHALFWIDTH, Constants.BOXHALFLENGTH, Constants.OFFSETFACINGLEFT, 0);
		}
		
		fixtureForBox.shape = boxShape;
		fixtureForBox.density = Constants.PLAYERDENSITY;
		fixtureForBox.restitution = Constants.PLAYERRESTITUTION;
		fixtureForBox.filter.maskBits = Constants.CATEGORY_PLAYER;
		
		playerBody.createFixture(fixtureForBox);
		
		boxShape.dispose();
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

//	@Override
//	public int getZIndex() {
//		return 1;
//	}

}