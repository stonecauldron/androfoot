package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

/**
 * Immutable implementation of a goal to transmit states in events
 * @author Gaylor
 *
 */
public class ImmutableGoal implements DefaultGoal {
    
    private Body body;
    private int zIndex;
    private GoalTeam team;
    private Vector2 position;
    
    protected ImmutableGoal(DefaultGoal goal) {
        body = goal.getBody();
        zIndex = goal.getZIndex();
        team = goal.getTeam();
        position = goal.getPosition().cpy();
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
    public GoalTeam getTeam() {
        return team;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public ImmutableGoal getStates() {
        return this;
    }

    @Override
    public void changeFixture(float newWidth, float newHeight) {
        throw new UnsupportedOperationException();
    }

}
