package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.PolygonMap;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;
import ch.epfl.sweng.androfoot.rendering.shaders.BlurTextureTransformer;
import ch.epfl.sweng.androfoot.rendering.shaders.ConcreteDrawToTexture;
import ch.epfl.sweng.androfoot.rendering.shaders.TextureTransformer;

public class PlayerRenderer implements MeshRenderer{
	
	private static final Color CANT_CONTROL_COLOR = Color.RED;
	
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
		} else {
			controlPartRenderer.setColor(CANT_CONTROL_COLOR);
		}
	}
	
	public void setCanControl(boolean canControlArg){
		canControl = canControlArg;
		setControlColor();
	}
	ConcreteDrawToTexture texturer = new ConcreteDrawToTexture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		controlPartRenderer.render(batch, shapes);
		shootPartRenderer.render(batch, shapes);
		/*texturer.beginCapture();
		GraphicEngine.getEngine().getViewPort().apply();
		texturer.endCapture();
		TextureRegion texture = texturer.getOutput();
		//texture = transformer.transform(texture);
		batch.draw(texture.getTexture(), 0, 0, Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y, 0, 0, texture.getRegionWidth(), texture.getRegionHeight(), false, true);
		GraphicEngine.getEngine().getViewPort().apply();*/
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
