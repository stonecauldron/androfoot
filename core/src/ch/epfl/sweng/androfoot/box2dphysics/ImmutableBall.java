package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Immutable implementation of a ball to transmit the states in event
 * @author Gaylor
 *
 */
public class ImmutableBall implements DefaultBall {
    
    private Body body;
    private int zIndex;
    private float x;
    private float y;
    private float radius;
    private Vector2 velocity;
    
    public ImmutableBall(DefaultBall ball) {
        body = ball.getBody();
        zIndex = ball.getZIndex();
        x = ball.getPositionX();
        y = ball.getPositionY();
        radius = ball.getRadius();
        velocity = ball.getLinearVelocity().cpy();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
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
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public void setBallPosition(float posX, float posY) {
        // Do nothing
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLinearVelocity(float posX, float posY) {
        // Do nothing
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return velocity;
    }

    @Override
    public ImmutableBall getStates() {
        return this;
    }

}
