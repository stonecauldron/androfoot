package ch.epfl.sweng.androfoot.rendering.test;

import java.util.SortedSet;
import java.util.TreeSet;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class EngineTestRequestBuilder {
	
	private final SortedSet<Drawable> toRender = new TreeSet<Drawable>(Drawable.DRAWABLE_COMPARATOR);
	private final Rectangle region;
	
	public EngineTestRequestBuilder(Rectangle regionArg) {
		region = regionArg;
	}
	
	public void addDrawable(Drawable draw) {
		toRender.add(draw);
	}
	
	public GraphicTester.TestRequest build() {
		final DrawableWorld world = new DrawableWorld() {
			
			@Override
			public SortedSet<Drawable> toDraw() {
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
				engine.render();
			}
		};
		
		return new GraphicTester.TestRequest(code);
	}


}
