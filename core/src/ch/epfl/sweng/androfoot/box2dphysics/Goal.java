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
    private final PolygonShape goalShape;
    private final FixtureDef goalFixture;
    
    /**
     * Constructor of a physical goal object.
     * @param teamOne The team flag, true for team one, otherwise false.
     * @param zIndex The Z Index of the goal for rendering.
     */
    public Goal(World world, float x, float y, float width, float height, GoalTeam goalTeam) {
    	
        team = goalTeam;
        
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
    
    @Override
    public Vector2 getPosition() {
        return goalBody.getPosition();
    }
    
    @Override
    public DefaultGoal clone() {
        return new DefaultGoal() {
            private GoalTeam teamGoal = team;
            private Vector2 position = goalBody.getPosition();
            
            @Override
            public GoalTeam getTeam() {
                return teamGoal;
            }

            @Override
            public Vector2 getPosition() {
                return position;
            }
            
            public DefaultGoal clone() {
                return null;
            }

            @Override
            public Body getBody() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getZIndex() {
                return -1;
            }

            @Override
            public void accept(Visitor visitor) {
                visitor.visit(this);
            }
        };
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
