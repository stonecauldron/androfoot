package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Class that defines the goals as physical objects.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Goal {
    
    private int zIndexIncrement;
    private final boolean isTeamOne;
    private World world;
    private GoalBorder[] goalBorders;
    
    private final Body goalBody;
    private final BodyDef goalBodyDef;
    private final EdgeShape goalShape;
    private final FixtureDef goalFixture;
    
    /**
     * Constructor of a physical goal object.
     * @param teamOne The team flag, true for team one, otherwise false.
     * @param physicsWorld The physics world to which the goal belongs.
     * @param zIndex The Z Index of the goal for rendering.
     */
    public Goal(boolean teamOne, World physicsWorld, int zIndex) {
        zIndexIncrement = zIndex;
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
        
        /* Borders */
        goalBorders = new GoalBorder[2];
        float y = Constants.WORLD_SIZE_Y - Constants.GOAL_HEIGHT;
        if (isTeamOne) {
            goalBorders[0] = new GoalBorder(Constants.WORLD_ORIGIN_X, Constants.WORLD_ORIGIN_Y, 
                    Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, 0);
            goalBorders[1] = new GoalBorder(Constants.WORLD_ORIGIN_X, y, 
                    Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, 0);
        } else {
            float x = Constants.WORLD_SIZE_X - Constants.GOAL_WIDTH;
            goalBorders[0] = new GoalBorder(x, 0, Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, 0);
            goalBorders[1] = new GoalBorder(x, y, Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT, 0);
        }
    }
    
    public GoalBorder[] getBorders() {
        return goalBorders;
    }
    
    public Body getBody() {
        return goalBody;
    }
    
    public boolean isTeamOne() {
        return isTeamOne;
    }
    
    /**
     * Represent the border between the goal and the top/bottom of the field
     * @author Gaylor
     *
     */
    class GoalBorder implements DrawableRectangle {
        
        private int zIndex;
        private final Body borderBody;
        private final BodyDef borderBodyDef;
        private final PolygonShape shape;
        private final Rectangle rectangle;
        private final FixtureDef fixture;
        
        public GoalBorder(float x, float y, float width, float height, float teta) {
            zIndex = zIndexIncrement;
            zIndexIncrement++;
            
            borderBodyDef = new BodyDef();
            borderBodyDef.type = BodyType.StaticBody;
            // Position of the center of the rectangle
            borderBodyDef.position.set(x + (width / 2), y + (height / 2));
            
            borderBody = world.createBody(borderBodyDef);
            
            fixture = new FixtureDef();
            fixture.density = Constants.GOAL_DENSITY;
            fixture.restitution = Constants.GOAL_RESTITUTION;
            
            shape = new PolygonShape();
            shape.setAsBox(width / 2, height / 2, new Vector2(0, 0), (float) Math.toRadians(teta));
            fixture.shape = shape;
            borderBody.createFixture(fixture);
            shape.dispose();
            
            // For the graphic engine
            rectangle = new Rectangle(x, y, width, height);
        }

        @Override
        public int getZIndex() {
            return zIndex;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public Color getColor() {
            if (isTeamOne) {
                return Constants.GOAL_COLOR_TEAM1;
            } else {
                return Constants.GOAL_COLOR_TEAM2;
            }
        }

        @Override
        public Rectangle getShape() {
            return rectangle;
        }
        
        
    }
}
