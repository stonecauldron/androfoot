package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GroupPaddleTest {
    
    private World world;
    private GroupPaddle group;
    
    @Before
    public void init() {
        PhysicsWorld.getPhysicsWorld().clear();
        world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
        group = PhysicsWorld.getPhysicsWorld().createPaddle(1, 0, 3, true);
    }

    @Test
    public void testGroupPaddleCreation() {
        
        int playerNumber = 0;
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        
        for (Body body : bodies) {
            for (Paddle paddle : group.getPaddles()) {
                if (body == paddle.getPlayer().getBody()) {
                    playerNumber++;
                }
            }
        }
        
        assertEquals(3, playerNumber);
    }
    
    @Test
    public void testAssignedVelocity() {
        group.setVelocity(2, 3);
        
        for (Paddle paddle : group.getPaddles()) {
            assertEquals(2.0, paddle.getPlayer().getPlayerVelocity().x, 0);
            assertEquals(3.0, paddle.getPlayer().getPlayerVelocity().y, 0);
        }
    }
    
    @Test
    public void testGeneralSyncInteraction() {
        group.setVelocity(2, 3);
        
        multiplePhyStep();
        
        for (Paddle paddle : group.getPaddles()) {
            assertEquals(0.0, paddle.getPlayer().getPlayerVelocity().x, 0);
            assertEquals(0.0, paddle.getPlayer().getPlayerVelocity().y, 0);
        }
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 200; i++) {
            world.step(1 / 60.0f, 4, 10);
            
            for (Paddle paddle : group.getPaddles()) {
                paddle.checkPosition();
            }
        }
    }
}
