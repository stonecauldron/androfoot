package ch.epfl.sweng.androfoot.box2dphysics;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getZIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getPositionX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPositionY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPowerUpPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public DefaultPowerUp clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
