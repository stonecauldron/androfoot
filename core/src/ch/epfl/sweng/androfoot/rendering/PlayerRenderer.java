package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.PolygonMap;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;
import ch.epfl.sweng.androfoot.rendering.shaders.ConcreteDrawToTexture;

/**
 * Class to render MeshRenderer
 * @author Guillame Leclerc
 *
 */
public class PlayerRenderer implements MeshRenderer{
	
	private static final float CANT_CONTROL_FACTOR = 0.6f;
	
	private final PolygonRenderer shootPartRenderer;
	private final PolygonRenderer controlPartRenderer;
	
	private Color currentColor = new Color();
	private boolean canControl = true;
	
	/**
	 * Init the renderer from a paddle ({@link PolygonMap})
	 * @param generator the paddle generator
	 */
	public PlayerRenderer(PolygonMap generator) {
		PolygonGenerator shootGenerator = generator.get(PaddleGenerator.SHOOT_BLOCK_KEY);
		PolygonGenerator controlGenerator = generator.get(PaddleGenerator.CONTROL_BLOCK_KEY);
		
		shootPartRenderer = new PolygonRenderer(shootGenerator);
		controlPartRenderer = new PolygonRenderer(controlGenerator);
	}
	
	/**
	 * Set the color according to the control state
	 */
	private void setControlColor() {
		if(canControl) {
			controlPartRenderer.setColor(currentColor);
			shootPartRenderer.setColor(currentColor);
		} else {
			Color transformedColor = new Color(currentColor);
			transformedColor.mul(CANT_CONTROL_FACTOR);
			controlPartRenderer.setColor(transformedColor);
			shootPartRenderer.setColor(transformedColor);
		}
	}
	
	/**
	 * Set the CanControl state
	 * @param canControlArg
	 */
	public void setCanControl(boolean canControlArg){
		canControl = canControlArg;
		setControlColor();
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		controlPartRenderer.render(batch, shapes);
		shootPartRenderer.render(batch, shapes);
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
		shootPartRenderer.setRotation(angle);
		controlPartRenderer.setRotation(angle);
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
