package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a object able to represent a DrawableWorld on the screen
 * @author Guillame Leclerc
 *
 */
public interface WorldRenderer extends Resettable{
	
	/**
	 * specify the world to render
	 * @param world the world
	 */
	void bindToWorld(DrawableWorld world);
	
	
	/**
	 * Render the world on the screen
	 */
	void render(float delta);
	
	/**
	 * define the screen size usable by this renderer
	 * @param width the screen width
	 * @param height the screen height
	 */
	void setScreenSize(int width, int height);
	
	/**
	 * Init the engine (just before the game start)
	 */
	void init();
}
