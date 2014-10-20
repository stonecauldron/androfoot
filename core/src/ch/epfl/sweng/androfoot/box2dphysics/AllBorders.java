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
		
		Border bottomBorder = new Border(world, screenLength/2, -0.1f, screenLength, 0.1f);
		Border leftBorder = new Border(world, -0.1f, screenWidth/2, 0.1f, screenWidth);
		Border upperBorder = new Border(world, screenLength/2, screenWidth + 0.1f, screenLength, 0.1f);
		Border rightBorder = new Border(world, screenLength + 0.1f, screenWidth/2, 0.1f, screenWidth);
	}
	
}
