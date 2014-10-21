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
	private BodyDef bodyDef = new BodyDef();
	private CircleShape circle = new CircleShape();
	private FixtureDef fixture = new FixtureDef();
	private float ballRadius;
	
	/**
	 * Contructor of the Ball class.
	 * @param world World to which the ball will be attached.
	 * @param initPosX Initial x coordinate of the ball.
	 * @param initPosY Initial y coordinate of the ball.
	 * @param radius Radius of the ball.
	 * @param density Density of the ball.
	 * @param friction Friction of the ball.
	 * @param restitution Restitution coefficient of the ball.
	 */
	public Ball(World world, float initPosX, float initPosY, float radius,
					float density, float friction, float restitution) {
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(initPosX, initPosY);
		
		ballRadius = radius;
		circle.setRadius(ballRadius);
		
		fixture.shape = circle;
		fixture.density = density;
		fixture.friction = friction;
		fixture.restitution = restitution;
		
		ballBody = world.createBody(bodyDef);
		ballBody.createFixture(fixture);
		
		circle.dispose();
		
		ballBody.setLinearVelocity(new Vector2(2, 2));
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

	@Override
	public int getZIndex() {
		return 0;
	}

}
