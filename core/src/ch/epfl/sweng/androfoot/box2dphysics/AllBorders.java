package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that defines and constructs all the four borders around the playfield.
 * @author Matvey
 *
 */
public class AllBorders {
	
	/**
	 * Constructor of the AllBorders class.
	 * @param world World which will contain the borders.
	 * @param screenLength Length of the screen (x axis).
	 * @param screenWidth Width of the screen (y axis).
	 */
	public AllBorders(World world, float screenLength, float screenWidth) {
		
		new Border(world, screenLength/2, -Constants.SCREENOFFSET, screenLength, Constants.SCREENOFFSET);
		new Border(world, screenLength/2, screenWidth + Constants.SCREENOFFSET, screenLength, Constants.SCREENOFFSET);
	}
	
}
