package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.BallInterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BallRenderer extends Actor {
	
	private static final int NB_SEGMENTS = 100;
	private BallInterface ball;
	private final ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	public BallRenderer(BallInterface ballToRender) {
		ball = ballToRender;
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.circle(ball.getPositionX(), ball.getPositionY(), ball.getRadius(), NB_SEGMENTS);
		shapeRenderer.end();
		batch.end();
	}
	
	

}
