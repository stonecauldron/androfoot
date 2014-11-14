package accelerometertracker;

import com.badlogic.gdx.Gdx;

public class AccelerometerTracker {
		
	private static float GRAVITY_EARTH = (float) 9.81;
	
	private float mXGrav = 1;
    private float mYGrav = 1;
    private float mZGrav = 1;
	
	public void pollGrav() {
		
		mXGrav = Gdx.input.getAccelerometerX() / GRAVITY_EARTH;
	    mYGrav = Gdx.input.getAccelerometerY() / GRAVITY_EARTH;;
	    mZGrav = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;;
	}
    
    
    
}
