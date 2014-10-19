package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a object able to represent a DrawableWorld on the screen
 * @author Guillame Leclerc
 *
 */
public interface WorldRenderer {
	
	/**
	 * specify the world to render
	 * @param world the world
	 */
	public void bindToWorld(DrawableWorld world);
	
	
	/**
	 * Render the world on the screen
	 */
	public void render();
	
	/**
	 * define the screen size usable by this renderer
	 * @param width the screen width
	 * @param height the screen height
	 */
	public void setScreenSize(int width, int height);

}
