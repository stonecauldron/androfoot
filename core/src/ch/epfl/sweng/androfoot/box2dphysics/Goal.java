package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that defines the goals as physical objects.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Goal {
    private final boolean isTeamOne;
    private World world;
    
    private final Body goalBody;
    private final BodyDef goalBodyDef;
    private final EdgeShape goalShape;
    private final FixtureDef goalFixture;
    
    /**
     * Constructor of a physical goal object.
     * @param teamOne The team flag, true for team one, otherwise false.
     * @param zIndex The Z Index of the goal for rendering.
     */
    public Goal(boolean teamOne) {
    	//Retrieve the instance of the Physics World.
    	World physicsWorld = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
    	
        isTeamOne = teamOne;
        world = physicsWorld;
        
        /* Goal */
        goalBodyDef = new BodyDef();
        goalBodyDef.type = BodyType.StaticBody;
        if (isTeamOne) {
            goalBodyDef.position.set(Constants.WORLD_ORIGIN_X - 0.2f, Constants.WORLD_ORIGIN_Y);
        } else {
            goalBodyDef.position.set(Constants.WORLD_SIZE_X + 0.2f, Constants.WORLD_ORIGIN_Y);
        }
        
        goalBody = world.createBody(goalBodyDef);
        
        goalFixture = new FixtureDef();
        goalFixture.isSensor = true;
        
        goalShape = new EdgeShape();
        goalShape.set(0, 0, 0, Constants.WORLD_SIZE_Y);
        goalFixture.shape = goalShape;
        goalBody.createFixture(goalFixture);
        goalShape.dispose();
        
        GoalContactListener.addGoal(this);
    }
    
    public Body getBody() {
        return goalBody;
    }
    
    public boolean isTeamOne() {
        return isTeamOne;
    }
}
