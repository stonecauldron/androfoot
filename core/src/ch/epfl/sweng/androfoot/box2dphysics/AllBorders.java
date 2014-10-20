package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.physics.box2d.World;

public class AllBorders {
	
	public AllBorders(World world, float screenLength, float screenWidth) {
		
		Border bottomBorder = new Border(world, screenLength/2, 0, screenLength, 0.1f);
		Border leftBorder = new Border(world, 0, screenWidth/2, 0.1f, screenWidth);
		Border upperBorder = new Border(world, screenLength/2, screenWidth, screenLength, 0.1f);
		Border rightBorder = new Border(world, screenLength, screenWidth/2, 0.1f, screenWidth);
	}
	
}
