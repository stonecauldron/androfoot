package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class to render a {@link Rectangle}
 * @author Guillame Leclerc
 *
 */
public class RectangleRenderer implements DrawableRenderer{
	private Rectangle rectangle;
	private Color color;
	
	/**
	 * Set the color to fill the rectangle with
	 * @param c the color
	 */
	public void setColor(Color c) {
		color = c;
	}
	
	/**
	 * Redefine the shape of the rectangle
	 * @param rectangleArg the new rectangle to draw
	 */
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
