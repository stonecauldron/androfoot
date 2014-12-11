package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Immutable implementation of a border to transmit the states in event
 * @author Gaylor
 *
 */
public class ImmutableBorder implements DefaultBorder {
    
    private Body body;
    private float width;
    private float height;
    private int zIndex;
    private BorderType type;
    private Vector2 position;
    
    protected ImmutableBorder(DefaultBorder border) {
        body = border.getBody();
        width = border.getWidth();
        height = border.getHeight();
        zIndex = border.getZIndex();
        type = border.getType();
        position = border.getPosition().cpy();
    }

    @Override
    public Body getBody() {
        return body;
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
    public BorderType getType() {
        return type;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public ImmutableBorder getStates() {
        return this;
    }

    @Override
    public void setWidth(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeight(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setPosition(Vector2 newPosition) {
        throw new UnsupportedOperationException();
    }

}
