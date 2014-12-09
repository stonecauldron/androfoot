package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Class that defines the ball of the game.
 * @author Matvey
 *
 */
public class Ball implements DefaultBall {
	
	private Body ballBody;
	private final BodyDef bodyDef = new BodyDef();
	private float ballRadius;
	
	/**
	 * Constructor of the Ball class.
	 * @param world The Box2D world in which the object is located.
	 * @param initPosX Initial x coordinate of the ball.
	 * @param initPosY Initial y coordinate of the ball.
	 * @param radius Radius of the ball.
	 * @param density Density of the ball.
	 * @param friction Friction of the ball.
	 * @param restitution Restitution coefficient of the ball.
	 */
	Ball(World world, float initPosX, float initPosY, float radius,
					float density, float friction, float restitution) {
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(initPosX, initPosY);
		
		ballBody = world.createBody(bodyDef);
		createNewBallFixture(radius, density, friction, restitution);
		
		PaddleContactListener.addBall(this);
		BorderContactListener.addBall(this);
		GoalContactListener.addBall(this);
	}
	
	public void createNewBallFixture(float radius, float density, float friction, float restitution) {
		
		final CircleShape circle = new CircleShape();
		final FixtureDef fixture = new FixtureDef();
		
		ballRadius = radius;
		circle.setRadius(ballRadius);
		
		fixture.shape = circle;
		fixture.density = density;
		fixture.friction = friction;
		fixture.restitution = restitution;
		fixture.filter.categoryBits = Constants.CATEGORY_BALL;
		fixture.filter.maskBits = Constants.CATEGORY_OTHERS | Constants.CATEGORY_PLAYER;
		
		ballBody.createFixture(fixture);
		
		circle.dispose();
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
	public void changeFixture(float newRadius, float newDensity, float newFriction, float newRestitution) {
		
		if (ballBody.getFixtureList().size != 0) {
			while (ballBody.getFixtureList().size > 0){
				ballBody.destroyFixture(ballBody.getFixtureList().first());
			}
		}
		createNewBallFixture(newRadius, newDensity, newFriction, newRestitution);
	}
	
	@Override
	public Vector2 getLinearVelocity() {
		return ballBody.getLinearVelocity();
	}

	@Override
	public int getZIndex() {
		return 0;
	}
	
	@Override
	public ImmutableBall getStates() {
	    return new ImmutableBall(this);
	}

	@Override
	public void changeFixture(float newRadius) {
		changeFixture(newRadius, Constants.BALL_DENSITY, Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
	}

}
