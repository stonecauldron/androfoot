package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.DefaultPaddle;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
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
public class Player implements DefaultPlayer {
	
	private static final int MAX_PLAYER_VERTEX = 11;
	private static int zIndexCounter = 1;
	private Body playerBody;
	private final BodyDef playerBodyDef = new BodyDef(); 
	
	private boolean teamFlag;
	
	private final DefaultPaddle parent;
	
	private int zIndex;
	
	/**
	 * Constructor of an individual player.
	 * @param world The Box2D world in which the object is located.
	 * @param initPosX x coordinate of the initial position of the player.
	 * @param initPosY y coordinate of the initial position of the player.
	 * @param teamOrientation If true the player is facing right, otherwise player is facing left.
	 * @param paddle 
	 */
	Player(World world, float initPosX, float initPosY, boolean teamOrientation, Paddle paddle) {
		
		parent = paddle;
		teamFlag = teamOrientation;
		
		playerBodyDef.type = BodyType.KinematicBody;
		playerBodyDef.position.set(new Vector2(initPosX, initPosY));
		
		playerBody = world.createBody(playerBodyDef);
		playerBody.setLinearVelocity(0, 0);
		
		createPaddleShape(teamFlag);
		
		if (teamFlag) {
			playerBody.setTransform(playerBody.getPosition(), (float) (-Math.PI/2));
		} else {
			playerBody.setTransform(playerBody.getPosition(), (float) (Math.PI/2));
		}
		
		zIndex = zIndexCounter;
		zIndexCounter++;
		
		PaddleContactListener.addPlayer(this);
		PlayerContactListener.addPlayer(this);
	}
	
	/**
	 * Auxiliary method used to create the paddle shape using the PolygonGenerator.
	 * @param teamOne True for team one, else false.
	 */
	private void createPaddleShape(boolean teamOne) {
		
		PolygonMap paddleGenerator;
		PaddleGenerator fullGenerator;
		
		final PolygonShape controlShape = new PolygonShape();
		final PolygonShape shootingShape = new PolygonShape();
		
		if (teamOne) {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam1();
		} else {
			fullGenerator = PlayerCharacteristicsManager.getInstanceTeam2();
		}
		
		paddleGenerator = new PaddleSimplifier(fullGenerator, MAX_PLAYER_VERTEX);
		
		PolygonGenerator controlPolygonBuilder = paddleGenerator.get(PaddleGenerator.CONTROL_BLOCK_KEY);
		PolygonGenerator shootPolygonBuilder = paddleGenerator.get(PaddleGenerator.SHOOT_BLOCK_KEY);
		
		controlShape.set(controlPolygonBuilder.generateVertexesFloat());
		createAttachFixture(controlShape);
		shootingShape.set(shootPolygonBuilder.generateVertexesFloat());
		createAttachFixture(shootingShape);
	}
	
	/**
	 * Creates and attaches the circular fixture to the player object.
	 */
	private void createAttachFixture(PolygonShape shape) {
		final FixtureDef fixtureForCircle = new FixtureDef();
		
		fixtureForCircle.shape = shape;
		
		playerBody.createFixture(fixtureForCircle);
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
	public Vector2 getPlayerVelocity() {
		return playerBody.getLinearVelocity();
	}

	@Override
	public void setPlayerVelocity(float x, float y) {
		playerBody.setLinearVelocity(x, y);
	}
	
	public void setPosition(float x, float y) {
	    playerBody.setTransform(x, y, playerBody.getAngle());
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
	
	@Override
	public DefaultPlayer clone() {
	    return new DefaultPlayer() {
	        private Vector2 position = playerBody.getPosition().cpy();
	        private Vector2 velocity = playerBody.getLinearVelocity().cpy();
	        private float angle = getPlayerAngle();
	        private boolean team = teamFlag;
	        
            @Override
            public int getZIndex() {
                // Forgotten
                throw new UnsupportedOperationException();
            }

            @Override
            public void accept(Visitor visitor) {
                // Forgotten
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
            public float getPlayerAngle() {
                return angle;
            }

            @Override
            public boolean getTeam() {
                return team;
            }

            @Override
            public Body getBody() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Vector2 getPlayerVelocity() {
                return velocity;
            }

            @Override
            public void setPlayerVelocity(float x, float y) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isAbleToControlBall() {
                return parent.isAbleToControlBall();
            }

            @Override
            public DefaultPlayer clone() {
                throw new UnsupportedOperationException();
            }
	        
	    };
	}

}
