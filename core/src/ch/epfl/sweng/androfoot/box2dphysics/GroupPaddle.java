package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.GroupPaddleInterface;

/**
 * @see GroupPaddleInterface
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class GroupPaddle implements GroupPaddleInterface {
    
    private List<Paddle> paddles;
    
    /**
     * Constructor
     * @param x position
     * @param width of the limited area
     * @param number of paddles
     * @param world the physic world
     * @param worldHeight height of the world
     */
    public GroupPaddle(float x, float width, int number, World world, float worldHeight) {
        paddles = new ArrayList<Paddle>();
        
        for (int i = 0; i < number; i++) {
            float height = worldHeight / number;
            float y = i * height;
            
            paddles.add(new Paddle(world, x, y, width, height));
        }
    }

    /**
     * Change the velocity of all the paddle
     */
    @Override
    public void setVelocity(float x, float y) {
        for (Paddle paddle : paddles) {
            paddle.setVelocity(x, y);
        }
    }

}
