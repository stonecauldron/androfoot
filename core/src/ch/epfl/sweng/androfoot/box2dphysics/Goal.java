package ch.epfl.sweng.androfoot.box2dphysics;

import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

import com.badlogic.gdx.math.Vector2;
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
public class Goal implements DefaultGoal {
    /**
     * Team of the goal
     * @author Gaylor
     *
     */
    public enum GoalTeam {ONE, TWO};
    
    private final GoalTeam team;
    
    private final Body goalBody;
    private final BodyDef goalBodyDef;
    
    /**
     * Constructor of a physical goal object.
     * @param teamOne The team flag, true for team one, otherwise false.
     * @param zIndex The Z Index of the goal for rendering.
     */
    Goal(World world, float x, float y, float width, float height, GoalTeam goalTeam) {
    	
        team = goalTeam;
        
        /* Goal */
        goalBodyDef = new BodyDef();
        goalBodyDef.type = BodyType.StaticBody;
        goalBodyDef.position.set(x + width / 2, y + height / 2);
        
        goalBody = world.createBody(goalBodyDef);
        createNewGoalFixture(width, height);
        
        GoalContactListener.addGoal(this);
    }
    
    @Override
	public void changeFixture(float newWidth, float newHeight) {
		goalBody.getFixtureList().clear();
		createNewGoalFixture(newWidth, newHeight);
	}
    
    private void createNewGoalFixture(float newWidth, float newHeight) {
    	final FixtureDef goalFixture = new FixtureDef();
    	final PolygonShape goalShape = new PolygonShape();
    	
        goalFixture.isSensor = true;
        
        goalShape.setAsBox(newWidth / 2, newHeight / 2);
        goalFixture.shape = goalShape;
        goalBody.createFixture(goalFixture);
        
        goalShape.dispose();
    }
    
    public Body getBody() {
        return goalBody;
    }
    
    public GoalTeam getTeam() {
        return team;
    }
    
    @Override
    public Vector2 getPosition() {
        return goalBody.getPosition();
    }
    
    @Override
    public ImmutableGoal getStates() {
        return new ImmutableGoal(this);
    }

    @Override
    public int getZIndex() {
        return -1;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
