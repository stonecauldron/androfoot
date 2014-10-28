package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * List of all the constants use in the physic package
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class Constants {
	
	/* Goals */
	public static final float GOAL_WIDTH = 0.2f;
	public static final float GOAL_HEIGHT = 2f;
	public static final int GOAL_COLOR_TEAM1_HEX = 0x2ECC71FF;
	public static final int GOAL_COLOR_TEAM2_HEX = 0xBF55ECFF;
	public static final Color GOAL_COLOR_TEAM1 = new Color(GOAL_COLOR_TEAM1_HEX);
	public static final Color GOAL_COLOR_TEAM2 = new Color(GOAL_COLOR_TEAM2_HEX);
	
    /* Paddle */
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_OTHERS = 0x0002;
    
    /* Border */
    public static final float SCREENOFFSET = 0.1f;
    
    /* PhysicsWorld */
    public static final int POSITION_ITERATIONS = 1;
    public static final int VELOCITY_ITERATIONS = 1;
    //public static final float TIME_STEP = 1/30f;
    public static final float BALL_RESTITUTION = 1.0f;
    public static final float BALL_FRICTION = 0.0f;
    public static final float BALL_DENSITY = 0.0001f;
    public static final float BALL_RADIUS = 0.2f;
    public static final float BALL_INIT_POS_Y = 3.4f;
    public static final float BALL_INIT_POS_X = 5.0f;
    public static final float WORLD_ORIGIN_Y = 0;
    public static final float WORLD_ORIGIN_X = 0;
    public static final float WORLD_SIZE_Y = 6.0f;
    public static final float WORLD_SIZE_X = 10.0f;
    
    /* Player */
    public static final float CIRCLERADIUS = 0.6f;
    public static final float BOXHALFWIDTH = 0.3f;
    public static final float BOXHALFLENGTH = 0.6f;
    public static final Vector2 OFFSETFACINGRIGHT = new Vector2(-0.3f, 0);
    public static final Vector2 OFFSETFACINGLEFT = new Vector2(0.3f, 0);
    public static final float PLAYERDENSITY = 0.5f;
    public static final float PLAYERFRICTION = 0.0f;
    public static final float PLAYERRESTITUTION = 0.0f;
}
