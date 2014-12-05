package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

public class ImmutablePlayer implements DefaultPlayer {
    
    private int zIndex;
    private Body body;
    private Vector2 position;
    private float angle;
    private boolean team;
    private boolean ableToControl;
    private Vector2 velocity;
    
    public ImmutablePlayer(DefaultPlayer player) {
        zIndex = player.getZIndex();
        body = player.getBody();
        position = new Vector2(player.getPositionX(), player.getPositionY());
        angle = player.getPlayerAngle();
        team = player.getTeam();
        ableToControl = player.isAbleToControlBall();
        velocity = player.getPlayerVelocity().cpy();
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
    public float getPlayerAngle() {
        return angle;
    }

    @Override
    public boolean getTeam() {
        return team;
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public Vector2 getPlayerVelocity() {
        return velocity;
    }

    @Override
    public void setPlayerVelocity(float x, float y) {
        // The player is immutable !
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbleToControlBall() {
        return ableToControl;
    }

    @Override
    public ImmutablePlayer getStates() {
        return this;
    }

}
