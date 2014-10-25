package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class BoardRenderer implements DrawableRenderer {
	
	private static final int MIDFIELD_COLOR_HEX = 0x2B2B2BFF;
	private static final Color MIDFIELD_COLOR = new Color(MIDFIELD_COLOR_HEX);
	private static final float MIDFIELD_THICKNESS = 0.1f;

	private Rectangle worldSize;
	
	public void setWorldSize(Rectangle worldSizeArg) {
		worldSize = worldSizeArg;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		batch.end();
		shapes.setColor(MIDFIELD_COLOR);
		shapes.begin(ShapeType.Filled);
		shapes.rect(worldSize.width/2 - MIDFIELD_THICKNESS/2, 0,
				MIDFIELD_THICKNESS, worldSize.height);
		shapes.end();
		batch.begin();

	}

}
