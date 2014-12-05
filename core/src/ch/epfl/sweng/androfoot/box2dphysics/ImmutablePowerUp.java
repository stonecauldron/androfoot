package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Immutable implementation of a power up to transmit states in events
 * @author Gaylor
 *
 */
public class ImmutablePowerUp implements DefaultPowerUp {
    
    private Body body;
    private int zIndex;
    private Vector2 position;
    private float hitBoxRadius;
    
    public ImmutablePowerUp(DefaultPowerUp powerup) {
        body = powerup.getBody();
        zIndex = powerup.getZIndex();
        position = new Vector2(powerup.getPositionX(), powerup.getPositionY());
        hitBoxRadius = powerup.getHitBoxRadius();
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
    public float getPositionX() {
        return position.x;
    }

    @Override
    public float getPositionY() {
        return position.y;
    }

    @Override
    public float getHitBoxRadius() {
        return hitBoxRadius;
    }

    @Override
    public void setPowerUpPosition(float x, float y) {
        // The power up is immutable !
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutablePowerUp getStates() {
        return this;
    }

}
