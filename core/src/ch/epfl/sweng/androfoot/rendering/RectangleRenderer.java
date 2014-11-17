package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class RectangleRenderer implements DrawableRenderer{
	private Rectangle rectangle;
	private Color color;
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void setRectangle(Rectangle rectangleArg) {
		rectangle = rectangleArg;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		shapes.begin(ShapeType.Filled);
		shapes.setColor(color);
		shapes.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		shapes.end();
	}

}
