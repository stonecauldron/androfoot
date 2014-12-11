package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ShakeRenderer implements DrawableRenderer {

	private final Color olverlayColor;
	private float time = -1;
	private final float timeToMax;
	private final float maxEndTime;
	private final float endAnimTime;
	private final RectangleRenderer renderer;

	public ShakeRenderer(Color color, float durationArg, float animDurationArg) {
		if (2 * animDurationArg > durationArg) {
			throw new IllegalArgumentException(
					"The animation duration must be smaller than the half of the duration of the notifier");
		}
		olverlayColor = new Color(color);
		timeToMax = animDurationArg;
		maxEndTime = durationArg - animDurationArg;
		endAnimTime = durationArg;
		renderer = new RectangleRenderer();
		renderer.setRectangle(new Rectangle(Constants.WORLD_ORIGIN_X,
				Constants.WORLD_ORIGIN_Y, Constants.WORLD_SIZE_X,
				Constants.WORLD_SIZE_Y));
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		float opacity;
		if (time < 0) {
			opacity = 0;
		} else if (time < timeToMax) {
			opacity = time / timeToMax;
		} else if (time < maxEndTime) {
			opacity = 1.0f;
		} else if (time < endAnimTime) {
			opacity = 1f - (time - maxEndTime) / (endAnimTime - maxEndTime);
		} else {
			opacity = 0;
		}
		
		System.out.println(opacity);
		
		Color realColor = new Color(olverlayColor);
		realColor.a *= opacity;
		GraphicEngine.getEngine().enableBlending();
		renderer.setColor(realColor);
		renderer.render(batch, shapes);
	}

	public void age(float delta) {
		if (time >= 0) {
			time += delta;
		}
	}

	public void start() {
		time = 0;
	}

}
