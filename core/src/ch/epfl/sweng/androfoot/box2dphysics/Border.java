package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Class that defines each individual border of the playfield.
 * @author Matvey
 *
 */
public class Border {

	private BodyDef borderDef = new BodyDef();
	private Body borderBody;
	private PolygonShape borderShape = new PolygonShape();
	
	/**
	 * Constructor of the Border class.
	 * @param world World which will contain the border.
	 * @param posX x coordinate of the polygon centre.
	 * @param posY y coordinate of the polygon centre.
	 * @param thicknessX Thickness of the polygon along the x axis.
	 * @param thicknessY Thickness of the polygon along the y axis.
	 */
	public Border(World world, float posX, float posY, float thicknessX, float thicknessY) {
		
		borderDef.type = BodyType.StaticBody;
		borderDef.position.set(new Vector2(posX, posY));
		
		borderBody = world.createBody(borderDef);
		
		borderShape.setAsBox(thicknessX/2, thicknessY/2);
		borderBody.createFixture(borderShape, 0.0f);
		
		borderShape.dispose();
	}
}
