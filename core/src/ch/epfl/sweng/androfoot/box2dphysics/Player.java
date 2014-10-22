package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.PlayerInterface;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

public class Player implements Drawable, PlayerInterface {

	private Body playerBody;
	private BodyDef playerBodyDef = new BodyDef();
	
	private CircleShape circle = new CircleShape();
	private float circleRadius = 0.6f;
	
	private PolygonShape boxShape = new PolygonShape();
	private float boxWidth;
	private float boxHeight;
	
	private FixtureDef fixtureForCircle = new FixtureDef();
	private FixtureDef fixtureForBox = new FixtureDef();
	
	public Player(World world, float initPosX, float initPosY, float density) {
		playerBodyDef.type = BodyType.KinematicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		circle.setRadius(circleRadius);
		fixtureForCircle.shape = circle;
		fixtureForCircle.density = density;
		fixtureForCircle.friction = 0.0f;
		fixtureForCircle.restitution = 1.0f;
		
		boxShape.setAsBox(0.3f, 0.6f, new Vector2(-0.3f, 0), 0);
		
		playerBody = world.createBody(playerBodyDef);
		playerBody.createFixture(fixtureForCircle);
		playerBody.createFixture(boxShape, 0.5f);
		
		circle.dispose();
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
	public int getZIndex() {
		return 1;
	}

}
