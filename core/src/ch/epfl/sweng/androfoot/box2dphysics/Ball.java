package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.BallInterface;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Class that defines the ball of the game.
 * @author Matvey
 *
 */
public class Ball implements Drawable, BallInterface {
	
	private Body ballBody;
	private final BodyDef bodyDef = new BodyDef();
	private final CircleShape circle = new CircleShape();
	private final FixtureDef fixture = new FixtureDef();
	private float ballRadius;
	
	/**
	 * Contructor of the Ball class.
	 * @param initPosX Initial x coordinate of the ball.
	 * @param initPosY Initial y coordinate of the ball.
	 * @param radius Radius of the ball.
	 * @param density Density of the ball.
	 * @param friction Friction of the ball.
	 * @param restitution Restitution coefficient of the ball.
	 */
	public Ball(float initPosX, float initPosY, float radius,
					float density, float friction, float restitution) {
		//Get the instance of the physics world.
		World world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(initPosX, initPosY);
		
		ballRadius = radius;
		circle.setRadius(ballRadius);
		
		fixture.shape = circle;
		fixture.density = density;
		fixture.friction = friction;
		fixture.restitution = restitution;
		fixture.filter.categoryBits = Constants.CATEGORY_BALL;
		fixture.filter.maskBits = Constants.CATEGORY_OTHERS | Constants.CATEGORY_PLAYER;
		
		ballBody = world.createBody(bodyDef);
		ballBody.createFixture(fixture);
		
		circle.dispose();
		
		PaddleContactListener.addBall(this);
		
		ballBody.setLinearVelocity(new Vector2(-3, 0)); //For testing purposes
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public float getPositionX() {
		return ballBody.getPosition().x;
	}

	@Override
	public float getPositionY() {
		return ballBody.getPosition().y;
	}

	@Override
	public float getRadius() {
		return ballRadius;
	}
	
	public Body getBody() {
	    return ballBody;
	}
	
	@Override
	public void setBallPosition(float x, float y) {
		ballBody.setTransform(x, y, 0);
	}
	
	@Override
	public void setLinearVelocity(float x, float y) {
	    ballBody.setLinearVelocity(x, y);
	}
	
	@Override
	public Vector2 getLinearVelocity() {
		return ballBody.getLinearVelocity();
	}

	@Override
	public int getZIndex() {
		return 0;
	}

}
