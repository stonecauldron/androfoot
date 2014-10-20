package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.BallInterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BallRenderer implements DrawableRenderer {
	
	private static final int NB_SEGMENTS = 100;
	private BallInterface ball;
	
	public BallRenderer(BallInterface ballToRender) {
		ball = ballToRender;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.end();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(ball.getPositionX(), ball.getPositionY(), ball.getRadius(), NB_SEGMENTS);
		shapeRenderer.end();
		batch.begin();
	}
	
	

}
