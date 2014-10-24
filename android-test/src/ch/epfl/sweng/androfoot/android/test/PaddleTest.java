package ch.epfl.sweng.androfoot.android.test;

import org.junit.Test;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

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
        fixture.filter.categoryBits = Paddle.CATEGORY_PLAYER;
        
        circle = worldTest.createBody(bodyDef);
        circle.createFixture(fixture);
        
        circleShape.dispose();
        
        circle.setLinearVelocity(0, -0.5f);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(1);
        }
        Log.d("bottom limit", String.valueOf(circle.getPosition().y));
        
        assertTrue("Bottom limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 4);
        assertTrue("Bottom limit : position y", circle.getPosition().y >= 1 && circle.getPosition().y <= 1.3);
        
        circle.setLinearVelocity(0, 0.5f);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(1);
        }
        
        assertTrue("Top limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 4);
        assertTrue("Top limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
        
        circle.setLinearVelocity(0.5f, 0);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(1);
        }
        
        assertTrue("Right limit : position x", circle.getPosition().x >= 3.7 && circle.getPosition().x <= 4);
        assertTrue("Right limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
        
        circle.setLinearVelocity(-0.5f, 0);
        
        for (int i = 0; i < 50; i++) {
            PhysicsWorld.getPhysicsWorld().phyStep(1);
        }
        
        assertTrue("Left limit : position x", circle.getPosition().x >= 1 && circle.getPosition().x <= 1.3);
        assertTrue("Left limit : position y", circle.getPosition().y >= 3.7 && circle.getPosition().y <= 4);
    }
}
