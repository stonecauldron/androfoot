package ch.epfl.sweng.androfoot.box2dphysics;

import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that defines each individual border of the playfield.
 * @author Gaylor
 *
 */
public class Border implements DrawableRectangle, DefaultBorder {
    
    /**
     * Represent the type of the border
     * @author Gaylor
     *
     */
    public enum BorderType { TEAM_ONE, TEAM_TWO, NO_TEAM };

    private static int zIndexIncrement = Constants.BORDER_Z_INDEX;
    private final int zIndex;
    private final BorderType borderType;
    private final Body borderBody;
    private final BodyDef borderBodyDef;
    private PolygonShape shape;
    private Rectangle rectangle;
    private Fixture fixture;
    private float borderWidth;
    private float borderHeight;
    private float posX;
    private float posY;
	
	/**
	 * Represent a border of the board
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param teta
	 */
	protected Border(World world, float x, float y, float width, float height, BorderType type) {
		
	    zIndex = zIndexIncrement;
        zIndexIncrement++;
        borderWidth = width;
        borderHeight = height;
        posX = x;
        posY = y;
        borderType = type;
        
        borderBodyDef = new BodyDef();
        borderBodyDef.type = BodyType.StaticBody;
        // Position of the center of the rectangle
        borderBodyDef.position.set(x + (width / 2), y + (height / 2));
        
        borderBody = world.createBody(borderBodyDef);
        
        createFixture();
        
        BorderContactListener.addBorder(this);
	}
	
	@Override
	public void setWidth(float value) {
	    borderWidth = value;
	    
	    createFixture();
	}
	
	@Override
	public void setHeight(float value) {
	    borderHeight = value;
	    
	    createFixture();
	}
	
	@Override
	public void setPosition(Vector2 position) {
	    posX = position.x;
	    posY = position.y;
	    
	    borderBody.setTransform(position, 0);
	    rectangle = new Rectangle(posX, posY, borderWidth, borderHeight);
	}
	
	@Override
	public float getWidth() {
	    return borderWidth;
	}
	
	@Override
	public float getHeight() {
	    return borderHeight;
	}
	
	public Body getBody() {
	    return borderBody;
	}
	
	@Override
	public Vector2 getPosition() {
	    return borderBody.getPosition();
	}
	
	@Override
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
                return PlayerCharacteristicsManager.getColorTeam1();
            case TEAM_TWO:
                return PlayerCharacteristicsManager.getColorTeam2();
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
    
    @Override
    public ImmutableBorder getStates() {
        return new ImmutableBorder(this);
    }
    
    private void createFixture() {
        shape = new PolygonShape();
        shape.setAsBox(borderWidth / 2, borderHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = Constants.BORDER_DENSITY;
        fixtureDef.restitution = Constants.BORDER_RESTITUTION;
        fixtureDef.shape = shape;
        
        if (fixture != null) {
            borderBody.destroyFixture(fixture);
        }
        fixture = borderBody.createFixture(fixtureDef);
        
        shape.dispose();
        
        // For the graphic engine
        rectangle = new Rectangle(posX, posY, borderWidth, borderHeight);
    }
}
