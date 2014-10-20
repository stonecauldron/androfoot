package ch.epfl.sweng.androfoot.interfaces;

/**
 * 
 * @author Matvey
 *
 */
public interface SeparateThreadEngine {

	public void init();
	public void reset();
	public void pause();
	public void resume();
	public void start();
}
