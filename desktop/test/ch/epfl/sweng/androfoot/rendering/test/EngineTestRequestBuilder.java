package ch.epfl.sweng.androfoot.rendering.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class EngineTestRequestBuilder {
	
	private static final float DELTA_RENDER = 0.16f;
	
	private final List<Drawable> toRender = new ArrayList<Drawable>();
	private final Rectangle region;
	
	public EngineTestRequestBuilder(Rectangle regionArg) {
		region = regionArg;
	}
	
	public void addDrawable(Drawable draw) {
		toRender.add(draw);
		Collections.sort(toRender, Drawable.DRAWABLE_COMPARATOR);
	}
	
	public GraphicTester.TestRequest build() {
		final DrawableWorld world = new DrawableWorld() {
			
			@Override
			public List<Drawable> toDraw() {
				return toRender;
			}
			
			@Override
			public Rectangle regionToDraw() {
				return region;
			}
		};
		Runnable code = new Runnable() {
			
			@Override
			public void run() {
				GraphicEngine engine = GraphicEngine.getEngine();
				engine.bindToWorld(world);
				engine.setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				engine.render(DELTA_RENDER);
			}
		};
		
		return new GraphicTester.TestRequest(code);
	}


}
