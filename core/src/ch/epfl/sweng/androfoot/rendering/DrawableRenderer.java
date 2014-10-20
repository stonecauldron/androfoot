package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface DrawableRenderer {
	
	public void render(SpriteBatch batch, ShapeRenderer shapes) ;
}
