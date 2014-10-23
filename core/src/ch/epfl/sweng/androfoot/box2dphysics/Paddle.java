package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.PaddleInterface;

/**
 * @see PaddleInterface
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Paddle implements PaddleInterface {
    
    public static final short CATEGORY_PLAYER = 0x0001;
    
    // Limited area definition
    private Body limitedArea;
    private BodyDef areaBodyDef;
    private List<EdgeShape> areasShape;
    private FixtureDef areaFixture;
    
    /**
     * Constructor
     * @param world the physic world
     * @param x position
     * @param y position
     * @param width of the limited area
     * @param height of the limited area
     */
    public Paddle(World world, float x, float y, float width, float height) {
        areasShape = new ArrayList<EdgeShape>();
        
        // Static definition for the 4 egdes
        areaBodyDef = new BodyDef();
        areaBodyDef.type = BodyType.StaticBody;
        areaBodyDef.position.set(new Vector2(x, y));
        
        limitedArea = world.createBody(areaBodyDef);
        
        // 4 edges for the limited area of the paddle
        List<Vector2> vertices = new ArrayList<Vector2>();
        vertices.add(new Vector2(0, 0));
        vertices.add(new Vector2(0, 0 + height));
        vertices.add(new Vector2(0 + width, 0 + height));
        vertices.add(new Vector2(0 + width, 0));
        
        areaFixture = new FixtureDef();
        areaFixture.filter.maskBits = CATEGORY_PLAYER;
        areaFixture.density = 1.0f;
        areaFixture.friction = 0.0f;
        // We want the player stops himself after the collision
        areaFixture.restitution = 0.0f;
        
        // Creation of the 4 edges
        for (int i = 0; i < vertices.size(); i++) {
            EdgeShape edge = new EdgeShape();
            areasShape.add(edge);
            edge.set(vertices.get(i), vertices.get((i + 1) % vertices.size()));
            
            areaFixture.shape = edge;
            
            limitedArea.createFixture(areaFixture);
            
            edge.dispose();
        }
        
        // TODO Add player when the class will be created
    }
    
    public Body getLimitedArea() {
        return limitedArea;
    }

    @Override
    public void setVelocity(float x, float y) {
        // TODO set the velocity of the player
        
    }

}
