package ch.epfl.sweng.androfoot.box2dphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Border {

	private BodyDef borderDef = new BodyDef();
	private Body borderBody;
	private PolygonShape borderShape = new PolygonShape();
	
	public Border(World world, float posX, float posY, float thicknessX, float thicknessY) {
		
		borderDef.type = BodyType.StaticBody;
		borderDef.position.set(new Vector2(posX, posY));
		
		borderBody = world.createBody(borderDef);
		
		borderShape.setAsBox(thicknessX/2, thicknessY/2);
		borderBody.createFixture(borderShape, 0.0f);
		
		borderShape.dispose();
	}
}
