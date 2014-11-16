package ch.epfl.sweng.androfoot.box2dphysics;

import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Class that defines each individual border of the playfield.
 * @author Gaylor
 *
 */
public class Border implements DrawableRectangle {
    
    public enum BorderType { TEAM_ONE, TEAM_TWO, NO_TEAM };

    private static int zIndexIncrement = 500;
    private int zIndex;
    private BorderType borderType;
    private final Body borderBody;
    private final BodyDef borderBodyDef;
    private final PolygonShape shape;
    private final Rectangle rectangle;
    private final FixtureDef fixture;
	
	/**
	 * Represent a border of the board
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param teta
	 */
	public Border(float x, float y, float width, float height, BorderType type) {
		
	    zIndex = zIndexIncrement;
        zIndexIncrement++;
        borderType = type;
        
        borderBodyDef = new BodyDef();
        borderBodyDef.type = BodyType.StaticBody;
        // Position of the center of the rectangle
        borderBodyDef.position.set(x + (width / 2), y + (height / 2));
        
        borderBody = PhysicsWorld.getPhysicsWorld().getBox2DWorld().createBody(borderBodyDef);
        
        fixture = new FixtureDef();
        fixture.density = Constants.GOAL_DENSITY;
        fixture.restitution = Constants.GOAL_RESTITUTION;
        
        shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixture.shape = shape;
        borderBody.createFixture(fixture);
        shape.dispose();
        
        // For the graphic engine
        rectangle = new Rectangle(x, y, width, height);
        
        BorderContactListener.addBorder(this);
	}
	
	public Body getBody() {
	    return borderBody;
	}
	
	public BorderType getType() {
	    return borderType;
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
        switch (borderType) {
        case TEAM_ONE:
            return Constants.GOAL_COLOR_TEAM1;
        case TEAM_TWO:
            return Constants.GOAL_COLOR_TEAM2;
        case NO_TEAM:
            return new Color(0, 0, 0, 1);
        default:
            return new Color(0, 0, 0, 1);
        }
    }

    @Override
    public Rectangle getShape() {
        return rectangle;
    }
}
