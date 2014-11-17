package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that defines the goals as physical objects.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Goal {
    /**
     * Team of the goal
     * @author Gaylor
     *
     */
    public enum GoalTeam {ONE, TWO};
    
    private final GoalTeam team;
    private World world;
    
    private final Body goalBody;
    private final BodyDef goalBodyDef;
    private final PolygonShape goalShape;
    private final FixtureDef goalFixture;
    
    /**
     * Constructor of a physical goal object.
     * @param teamOne The team flag, true for team one, otherwise false.
     * @param zIndex The Z Index of the goal for rendering.
     */
    public Goal(float x, float y, float width, float height, GoalTeam goalTeam) {
    	//Retrieve the instance of the Physics World.
    	World physicsWorld = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
    	
        team = goalTeam;
        world = physicsWorld;
        
        /* Goal */
        goalBodyDef = new BodyDef();
        goalBodyDef.type = BodyType.StaticBody;
        goalBodyDef.position.set(x + width / 2, y + height / 2);
        
        goalBody = world.createBody(goalBodyDef);
        
        goalFixture = new FixtureDef();
        goalFixture.isSensor = true;
        
        goalShape = new PolygonShape();
        goalShape.setAsBox(width / 2, height / 2);
        goalFixture.shape = goalShape;
        goalBody.createFixture(goalFixture);
        goalShape.dispose();
        
        GoalContactListener.addGoal(this);
    }
    
    public Body getBody() {
        return goalBody;
    }
    
    public GoalTeam getTeam() {
        return team;
    }
}
