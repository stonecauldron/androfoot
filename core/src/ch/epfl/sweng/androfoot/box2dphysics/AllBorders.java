package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that defines and constructs all the four borders around the playfield.
 * @author Matvey
 *
 */
public class AllBorders {
	
	private static final float SCREENOFFSET = 0.1f;
	/**
	 * Constructor of the AllBorders class.
	 * @param world World which will contain the borders.
	 * @param screenLength Length of the screen (x axis).
	 * @param screenWidth Width of the screen (y axis).
	 */
	public AllBorders(World world, float screenLength, float screenWidth) {
		
		new Border(world, screenLength/2, -SCREENOFFSET, screenLength, SCREENOFFSET);
		new Border(world, -SCREENOFFSET, screenWidth/2, SCREENOFFSET, screenWidth);
		new Border(world, screenLength/2, screenWidth + SCREENOFFSET, screenLength, SCREENOFFSET);
		new Border(world, screenLength + SCREENOFFSET, screenWidth/2, SCREENOFFSET, screenWidth);
	}
	
}
