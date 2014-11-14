package ch.epfl.sweng.androfoot.box2dphysics;

import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Class that defines each individual border of the playfield.
 * @author Matvey
 *
 */
public class Border implements DrawableRectangle {

    private int zIndexIncrement;
    private int zIndex;
    private boolean isTeamOne;
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
	public Border(float x, float y, float width, float height, float teta, boolean teamOne) {
		
	    zIndex = zIndexIncrement;
        zIndexIncrement++;
        isTeamOne = teamOne;
        
        borderBodyDef = new BodyDef();
        borderBodyDef.type = BodyType.StaticBody;
        // Position of the center of the rectangle
        borderBodyDef.position.set(x + (width / 2), y + (height / 2));
        
        borderBody = PhysicsWorld.getPhysicsWorld().getBox2DWorld().createBody(borderBodyDef);
        
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
