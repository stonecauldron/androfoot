package ch.epfl.sweng.androfoot.android.test;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import junit.framework.TestCase;

public class PaddleTest extends TestCase {
    
    @Test
    public final void testPaddleCreation() {
        World worldTest = PhysicsWorld.getPhysicsWorld().getWorld();
        
        Paddle paddle = new Paddle(worldTest, 1, 1, 1, 1, true);
        
        Array<Body> bodies = new Array<Body>();
        worldTest.getBodies(bodies);
        
        // World must contains the body
        assertTrue(bodies.contains(paddle.getLimitedArea(), true));
        
        // Test the initial position
        assertEquals(paddle.getLimitedArea().getPosition().x, 1.0f, 0);
    }
    
    @Test
    public final void testPaddleLimit() throws InterruptedException {
        World worldTest = PhysicsWorld.getPhysicsWorld().getWorld();
        @SuppressWarnings("unused")
        Paddle paddle = new Paddle(worldTest, 1, 1, 3, 3, true);
        
        Body circle;
        BodyDef bodyDef = new BodyDef();
        CircleShape circleShape = new CircleShape();
        FixtureDef fixture = new FixtureDef();
        
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(2, 2));
        
        circleShape.setRadius(0.2f);
        fixture.shape = circleShape;
        fixture.restitution = 0.0f;
        fixture.density = 1.0f;
        fixture.friction = 0.0f;
        fixture.filter.categoryBits = Constants.CATEGORY_PLAYER;
        
        circle = worldTest.createBody(bodyDef);
        circle.createFixture(fixture);
        
        circleShape.dispose();
        
        circle.setLinearVelocity(0, -0.5f);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(60);
        }
        
        assertTrue("Bottom limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 4);
        assertTrue("Bottom limit : position y", circle.getPosition().y >= 1 && circle.getPosition().y <= 1.3);
        
        circle.setLinearVelocity(0, 0.5f);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(60);
        }
        
        assertTrue("Top limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 4);
        assertTrue("Top limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
        
        circle.setLinearVelocity(0.5f, 0);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(60);
        }
        
        assertTrue("Right limit : position x", circle.getPosition().x >= 3.7 && circle.getPosition().x <= 4);
        assertTrue("Right limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
        
        circle.setLinearVelocity(-0.5f, 0);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(60);
        }
        
        assertTrue("Left limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 1.3);
        assertTrue("Left limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
    }
    
    @Test
    public void testPlayerInteractionWithLimit() {
        World worldTest = PhysicsWorld.getPhysicsWorld().getWorld();
        
        // Not in the same place than the other paddle
        Paddle paddle = new Paddle(worldTest, 5, 1, 3, 3, true);
        
        // Top limit
        paddle.setVelocity(0.0f, 1.0f);
        for(int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(30);
        }
        
        assertTrue("Player top position y limit", paddle.getPlayer().getPositionY() >= 3.9 - Constants.CIRCLERADIUS && 
                        paddle.getPlayer().getPositionY() <= 4.0);
        
        // Bottom limit
        paddle.setVelocity(0.0f, -1.0f);
        for(int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(30);
        }
        
        assertTrue("Player bottom position y limit", paddle.getPlayer().getPositionY() >= 1.0 && 
                        paddle.getPlayer().getPositionY() <= 1.1 + Constants.CIRCLERADIUS);
        
        // Left limit
        paddle.setVelocity(-1.0f, 0.0f);
        for(int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(30);
        }
        
        assertTrue("Player left position x limit", paddle.getPlayer().getPositionX() >= 5.0 && 
                        paddle.getPlayer().getPositionX() <= 5.1 + Constants.CIRCLERADIUS);
        
        // Right limit
        paddle.setVelocity(1.0f, 0.0f);
        for(int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(30);
        }
        
        assertTrue("Player right position x limit", paddle.getPlayer().getPositionX() >= 7.9 - Constants.CIRCLERADIUS && 
                        paddle.getPlayer().getPositionX() <= 8);
    }
}
