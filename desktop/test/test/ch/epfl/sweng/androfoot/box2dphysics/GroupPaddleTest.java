package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import junit.framework.TestCase;

public class GroupPaddleTest extends TestCase {
    
    private World world = new World(new Vector2(0, 0), false);
    
    @Test
    public void testGroupPaddleCreation() {
        GroupPaddle paddles = new GroupPaddle(world, 0, 1, 2, true);
        
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        
        // World must contains the body
        for (Paddle paddle : paddles.getPaddles()) {
            assertTrue(bodies.contains(paddle.getLimitedArea(), true));
        }
    }

}
