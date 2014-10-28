package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public interface DrawableRectangle extends Drawable {
	public Color getColor();
	public Rectangle getShape();
}
