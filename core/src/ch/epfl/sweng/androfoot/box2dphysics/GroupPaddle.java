package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.List;

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
     * @param worldHeight height of the world
     */
    public GroupPaddle(float x, float width, int number, boolean facingRight) {
        paddles = new ArrayList<Paddle>();
        
        for (int i = 0; i < number; i++) {
            float height = Constants.WORLD_SIZE_Y / number;
            float y = i * height;
            
            paddles.add(new Paddle(x, y, width, height, facingRight));
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
            paddle.setPlayerVelocity(x, y);
        }
    }

	@Override
	public void setXVelocity(float x) {
		for (Paddle paddle : paddles) {
			paddle.setPlayerXVelocity(x);
		}
	}

	@Override
	public void setYVelocity(float y) {
		for (Paddle paddle : paddles) {
			paddle.setPlayerYVelocity(y);
		}
	}

}
