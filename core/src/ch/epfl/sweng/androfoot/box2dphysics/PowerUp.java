package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

public class PowerUp implements DefaultPowerUp {
	
	private Body powerUpBody;
	private final BodyDef bodyDef = new BodyDef();
	private final CircleShape circle = new CircleShape();
	private final FixtureDef fixture = new FixtureDef();
	private float hitBoxRadius;
	
	public PowerUp(World world, float initPosX, float initPosY, float hBoxRadius) {
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(initPosX, initPosY);
		
		hitBoxRadius = hBoxRadius;
		circle.setRadius(hitBoxRadius);
		
		fixture.shape = circle;
		fixture.filter.categoryBits = Constants.CATEGORY_POWERUP;
		
		circle.dispose();
		
		powerUpBody = world.createBody(bodyDef);
		powerUpBody.createFixture(fixture);
		
		//PowerUpContactListener.addPowerUp(this);
	}

	@Override
	public Body getBody() {
		return powerUpBody;
	}

	@Override
	public int getZIndex() {
		return Constants.POWERUP_Z_INDEX;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public float getPositionX() {
		return powerUpBody.getPosition().x;
	}

	@Override
	public float getPositionY() {
		return powerUpBody.getPosition().y;
	}
	
	@Override
	public float getHitBoxRadius() {
		return hitBoxRadius;
	}

	@Override
	public void setPowerUpPosition(float x, float y) {
		powerUpBody.setTransform(x, y, 0);
	}

	@Override
	public DefaultPowerUp clone() {
		return new DefaultPowerUp() {
			
			private Vector2 position = powerUpBody.getPosition().cpy();
	        private float radius = getHitBoxRadius();
			
			@Override
			public Body getBody() {
				throw new UnsupportedOperationException();
			}

			@Override
			public int getZIndex() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void accept(Visitor visitor) {
				throw new UnsupportedOperationException();
			}

			@Override
			public float getPositionX() {
				return position.x;
			}

			@Override
			public float getPositionY() {
				return position.y;
			}
			
			@Override
			public float getHitBoxRadius() {
				return radius;
			}

			@Override
			public void setPowerUpPosition(float x, float y) {
				throw new UnsupportedOperationException();
			}
		};
	}

}
