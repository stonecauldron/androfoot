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

public class Player implements PlayerInterface {

	private static final float CIRCLERADIUS = 0.6f;
	private static final float BOXHALFWIDTH = 0.3f;
	private static final float BOXHALFLENGTH = 0.6f;
	private static final Vector2 OFFSETFACINGRIGHT = new Vector2(-0.3f, 0);
	private static final Vector2 OFFSETFACINGLEFT = new Vector2(0.3f, 0);
	private static final float PLAYERDENSITY = 0.5f;
	private static final float PLAYERFRICTION = 0.0f;
	private static final float PLAYERRESTITUTION = 1.0f;
	
	private Body playerBody;
	private BodyDef playerBodyDef = new BodyDef();
	
	private FixtureDef fixtureForCircle = new FixtureDef();
	
	//facingRight boolean in constructor for the entire bar or 
	//a method for individual player ?????????????
	//TODO
	public Player(World world, float initPosX, float initPosY, boolean facingRight) {
		playerBodyDef.type = BodyType.KinematicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		playerBody = world.createBody(playerBodyDef);
		
		createAttachCircleFixture();
		
		createAttachBoxFixture(world, facingRight);
	}
	
	private void createAttachCircleFixture() {
		CircleShape circle = new CircleShape();
		
		circle.setRadius(CIRCLERADIUS);
		fixtureForCircle.shape = circle;
		fixtureForCircle.density = PLAYERDENSITY;
		fixtureForCircle.friction = PLAYERFRICTION;
		fixtureForCircle.restitution = PLAYERRESTITUTION;
		
		playerBody.createFixture(fixtureForCircle);
		
		circle.dispose();
	}
	
	private void createAttachBoxFixture(World world, boolean facingRight) {
		PolygonShape boxShape = new PolygonShape();
		
		if(facingRight) {
			boxShape.setAsBox(BOXHALFWIDTH, BOXHALFLENGTH, OFFSETFACINGRIGHT, 0);
		} else {
			boxShape.setAsBox(BOXHALFWIDTH, BOXHALFLENGTH, OFFSETFACINGLEFT, 0);
		}
		
		playerBody.createFixture(boxShape, PLAYERDENSITY);
		
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

//	@Override
//	public int getZIndex() {
//		return 1;
//	}

}
