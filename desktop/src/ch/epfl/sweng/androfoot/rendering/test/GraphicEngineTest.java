/**
 * 
 */
package ch.epfl.sweng.androfoot.rendering.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.sweng.androfoot.AndroGame;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Guillame Leclerc
 *
 */
public class GraphicEngineTest {
	private static GraphicTester tester;
	private static final Rectangle WORLD_REGION = new Rectangle(0, 0, 10, 6);
	private static final Rectangle OUTSIDE_RECTANGLE = new Rectangle(600,0,600,600);
	private static final Color BACKGROUND_COLOR = new Color(0x303030ff);

	@BeforeClass
	public static void setup() {
		tester = new GraphicTester();
	}

	@Test
	public void testOpenGLandScreenshotTaker() throws InterruptedException {
		GraphicTester.TestRequest request = new GraphicTester.TestRequest(
				new Runnable() {
					@Override
					public void run() {
						Gdx.gl.glClearColor(1f, 0f, 1f, 1f);
						Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					}
				});

		tester.scheduleTest(request);

		Pixmap screenShot = request.getResult();

		Color c = new Color(screenShot.getPixel(0, 0));
		assertEquals(new Color(1f, 0f, 1f, 1f), c);
	}

	@Test
	public void testDrawRedRectangle() {
		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, Color.RED, 0));

		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap screenShot = request.getResult();

		Set<Color> colors = getColorsFromPixMap(screenShot);
		colors.remove(Color.BLACK);
		for (Color c : colors) {
			c = setAlphaTo(c, 1f);
			assertTrue("is black or Red (value :" + c.toString() + ")",
					c.equals(Color.BLACK) || c.equals(Color.RED));
		}
	}

	@Test
	public void testZIndex() {
		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, Color.RED, 0));
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, Color.BLUE, 1));
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, Color.GREEN, -1));
		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap result = request.getResult();
		Set<Color> colors = getColorsFromPixMap(result);
		colors.remove(Color.BLACK);
		for(Color c : colors) {
			assertEquals(Color.BLUE, c);
		}
	}
	
	
	@Test
	public void testOutOfBounds() {
		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, BACKGROUND_COLOR, 0));
		builder.addDrawable(new SimpleRectangle(OUTSIDE_RECTANGLE, Color.RED, 0));
		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap result = request.getResult();
		Set<Color> colors = getColorsFromPixMap(result);
		colors.remove(Color.BLACK);
		for(Color c : colors) {
			assertEquals(BACKGROUND_COLOR, c);
		}
	}

	private static Color setAlphaTo(Color c, float alpha) {
		return new Color(c.r, c.g, c.b, alpha);
	}

	private static Set<Color> getColorsFromPixMap(Pixmap image) {
		HashSet<Color> colors = new HashSet<Color>();
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color c = new Color(image.getPixel(i, j));
				c = setAlphaTo(c, 1f);
				colors.add(c);
			}
		}
		return colors;
	}

	@AfterClass
	public static void tearDown() {
		tester.dispose();
	}

	private static class SimpleRectangle implements DrawableRectangle {
		
		private final int zIndex;
		private final Color color;
		private final Rectangle region;
		
		public SimpleRectangle(Rectangle regionArg, Color colorArg, int zArg) {
			zIndex = zArg;
			color = colorArg;
			region = regionArg;
		}

		@Override
		public int getZIndex() {
			return zIndex;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}

		@Override
		public Color getColor() {
			return color;
		}

		@Override
		public Rectangle getShape() {
			return region;
		}
	
	}
}
