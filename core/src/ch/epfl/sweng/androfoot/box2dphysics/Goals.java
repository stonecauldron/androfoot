package ch.epfl.sweng.androfoot.box2dphysics;

import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.Visitor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Goals {
	
	private final float worldWidth;
	private final float worldHeight;
	private final World world;
	private final GoalsBorder[] borders;

	private static int zIndex = -1;
	
	public Goals(World worldArg, float worldWidthArg, float worldHeightArg) {
		world = worldArg;
		worldHeight = worldHeightArg;
		worldWidth = worldWidthArg;
		
		borders = new GoalsBorder[4];

		borders[0] = new GoalsBorder(true, true);
		borders[1] = new GoalsBorder(true, false);
		borders[2] = new GoalsBorder(false, false);
		borders[3] = new GoalsBorder(false, true);
		//borders[1] = new GoalsBorder(true, true);
		//borders[2] = new GoalsBorder(false, false);
		//borders[3] = new GoalsBorder(false, true);
	}
	
	public GoalsBorder[] getBorders() {
		return borders;
	}
	
	class GoalsBorder implements DrawableRectangle{
		private final BodyDef borderDef = new BodyDef();
		private final Body borderBody;
		private final boolean isTeam1;
		private final Rectangle shape;
		private final int currentZindex;
		

		private GoalsBorder(boolean isTeam1Arg, boolean isTop) {
			isTeam1 = isTeam1Arg;
			currentZindex = zIndex--;
			float posX, posY;

			if(isTeam1){
				posX = 0f;
			}else {
				posX = worldWidth - Constants.GOAL_WIDTH;
			}
			
			if(isTop) {
				posY = 0f;
			} else {
				posY = worldHeight - Constants.GOAL_HEIGHT;
			}
			
			borderDef.type = BodyType.StaticBody;
			borderDef.position.set(new Vector2(posX, posY));
 
			borderBody = world.createBody(borderDef);
			final PolygonShape borderShape = new PolygonShape();
			borderShape.setAsBox(Constants.GOAL_HEIGHT, Constants.GOAL_WIDTH, new Vector2(0f,0f), 0f);
			borderBody.createFixture(borderShape, 0f);
			
			shape = new Rectangle(posX, posY, Constants.GOAL_WIDTH, Constants.GOAL_HEIGHT);
			System.out.println(shape);
		}

		@Override
		public int getZIndex() {
			return currentZindex;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}

		@Override
		public Color getColor() {
			if(isTeam1) {
				return Constants.GOAL_COLOR_TEAM1;
			} else{
				return Constants.GOAL_COLOR_TEAM2;
			}
		}

		@Override
		public Rectangle getShape() {
			return shape;
		} 	
	}

}
