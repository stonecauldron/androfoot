package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.PolygonMap;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;

public class PlayerRenderer implements MeshRenderer{
	
	private final PolygonRenderer shootPartRenderer;
	private final PolygonRenderer controlPartRenderer;
	
	private Color currentColor = new Color();
	private boolean canControl = true;
	
	public PlayerRenderer(PolygonMap generator) {
		PolygonGenerator shootGenerator = generator.get(PaddleGenerator.SHOOT_BLOCK_KEY);
		PolygonGenerator controlGenerator = generator.get(PaddleGenerator.CONTROL_BLOCK_KEY);
		
		shootPartRenderer = new PolygonRenderer(shootGenerator);
		controlPartRenderer = new PolygonRenderer(controlGenerator);
	}
	
	private void setControlColor() {
		if(canControl) {
			controlPartRenderer.setColor(currentColor);
		}
	}
	
	public void setCanControl(boolean canControlArg){
		canControl = canControlArg;
		setControlColor();
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		shootPartRenderer.render(batch, shapes);
		controlPartRenderer.render(batch, shapes);
	}

	@Override
	public void setPosition(float x, float y) {
		shootPartRenderer.setPosition(x, y);
		controlPartRenderer.setPosition(x, y);
	}

	@Override
	public void setColor(Color c) {
		currentColor = c;
		shootPartRenderer.setColor(c);
		setControlColor();
	}

	@Override
	public void setRotation(float angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScale(float s) {
		shootPartRenderer.setScale(s);
		controlPartRenderer.setScale(s);
	}

	@Override
	public void setZindex(float z) {
		shootPartRenderer.setZindex(z);
		controlPartRenderer.setZindex(z);
	}

}
