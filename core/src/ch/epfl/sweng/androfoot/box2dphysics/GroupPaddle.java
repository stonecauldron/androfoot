package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.GroupPaddleInterface;
import ch.epfl.sweng.androfoot.players.PlayerNumber;

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
    public GroupPaddle(float x, float width, int number, World world, float worldHeight, boolean facingRight) {
        paddles = new ArrayList<Paddle>();
        
        for (int i = 0; i < number; i++) {
            float height = worldHeight / number;
            float y = i * height;
            
            paddles.add(new Paddle(world, x, y, width, height, facingRight));
        }
    }
    
    public List<Paddle> getPaddles() {
        return paddles;
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
    
    /**
     * Create a groupPaddle
     * @param x the x coordinate of the group
     * @param number the number of players in the group
     * @param playerNumber whether the player is player one or player two
     * @return the newly created GroupPaddle
     */
    public static GroupPaddle createGroupPaddle(float x, int number, PlayerNumber playerNumber) {
    	boolean facingRight = false;
    	if (playerNumber == PlayerNumber.ONE) {
    		facingRight = true;
    	}
    	return new GroupPaddle(x - Constants.PADDLE_WIDTH/2,
    			Constants.PADDLE_WIDTH, number, PhysicsWorld.getPhysicsWorld().getBox2DWorld(),
    			Constants.WORLD_SIZE_Y, facingRight);
    }

}
