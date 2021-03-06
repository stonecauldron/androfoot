package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.DefaultPaddle;

/**
 * @see DefaultPaddle
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Paddle implements DefaultPaddle {
    
    private final float posX;
    private final float posY;
    private final float width;
    private final float height;
    private final boolean teamFlag;
    
    private Player player;
    
    /**
     * Constructor
     * @param x position
     * @param y position
     * @param width of the limited area
     * @param height of the limited area
     */
    protected Paddle(World world, float x, float y, float paddleWidth, float paddleHeight, boolean facingRight) {
    	
        posX = x;
        posY = y;
        width = paddleWidth;
        height = paddleHeight;
        
        teamFlag = facingRight;
        
        player = new Player(world, x + (width / 2), y + (height / 2), facingRight, this);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    void changePlayerFixture() {
    	getPlayer().createPaddleShape(teamFlag);
    }
    
    public void checkPosition() {
        if (player.getPositionX() > posX + width) {
            player.setPlayerVelocity(0, player.getPlayerVelocity().y);
            player.setPosition(posX + width, player.getPositionY());
        } else if (player.getPositionX() < posX) {
            player.setPlayerVelocity(0, player.getPlayerVelocity().y);
            player.setPosition(posX, player.getPositionY());
        }
        
        float semiHeight = player.getSemiHeight();
        
        if (player.getPositionY() + semiHeight > posY + height) {
            player.setPlayerVelocity(player.getPlayerVelocity().x, 0);
            player.setPosition(player.getPositionX(), 
                    posY + height - semiHeight);
        } else if (player.getPositionY() - semiHeight < posY) {
            player.setPlayerVelocity(player.getPlayerVelocity().x, 0);
            player.setPosition(player.getPositionX(), 
                    posY + semiHeight);
        }
    }

    @Override
    public void setPlayerVelocity(float x, float y) {
        player.setPlayerVelocity(x, y);
    }
    
	@Override
	public void setPlayerXVelocity(float x) {
		player.setPlayerVelocity(x, player.getPlayerVelocity().y);
	}

	@Override
	public void setPlayerYVelocity(float y) {
		player.setPlayerVelocity(player.getPlayerVelocity().x, y);
	}
	
	@Override
	public void setPosition(Vector2 position) {
	    player.getBody().setTransform(position, player.getPlayerAngle());
	}
	
	@Override
	public Vector2 getPosition() {
	    return player.getBody().getPosition();
	}
	
	@Override
	public float getWidth() {
	    return width;
	}
	
	@Override
	public float getHeight() {
	    return height;
	}

	@Override
	public boolean isAbleToControlBall() {
		boolean res = true;
		
		float ballRadius = Constants.BALL_RADIUS;
		float paddleCenterX = posX + (width / 2);
		
		if (teamFlag) {
			if ((player.getPositionX() + Constants.BALL_CONTROL_OFFSET)
					>= (paddleCenterX + width/2 + ballRadius)) {
				res = false;
			}
			
		} else {
			if ((player.getPositionX() - Constants.BALL_CONTROL_OFFSET)
					<= (paddleCenterX - width/2 - ballRadius)) {
				res = false;
			}
		}
		
		return res;
	}
}
