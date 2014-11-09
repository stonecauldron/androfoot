/**
 * 
 */
package ch.epfl.sweng.androfoot.rendering.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Request;

import ch.epfl.sweng.androfoot.AndroGame;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;
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
import com.badlogic.gdx.physics.box2d.World;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Guillame Leclerc
 *
 */
public class GraphicEngineTest {
	private static GraphicTester tester;
	private static final Rectangle WORLD_REGION = new Rectangle(0, 0, 10, 6);
	private static final Rectangle OUTSIDE_RECTANGLE = new Rectangle(600, 0, 600, 600);
	private static final Color BACKGROUND_COLOR = new Color(0x303030ff);
	private static final Color MIDFIELD_COLOR = new Color(0x2B2B2BFF);
	private static final float DELTA = 0.001f;

	@BeforeClass
	public static void setup() {
		tester = new GraphicTester();
	}

	@Test
	public void testOpenGLandScreenshotTaker() throws InterruptedException {
		GraphicTester.TestRequest request = new GraphicTester.TestRequest(new Runnable() {
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

		Set<Color> colors = getColorSet(getAllPixels(screenShot));
		colors.remove(Color.BLACK);
		for (Color c : colors) {
			c = setAlphaTo(c, 1f);
			assertTrue("is black or Red (value :" + c.toString() + ")", c.equals(Color.BLACK) || c.equals(Color.RED));
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
		Set<Color> colors = getColorSet(getAllPixels(result));
		colors.remove(Color.BLACK);
		for (Color c : colors) {
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
		Set<Color> colors = getColorSet(getAllPixels(result));
		colors.remove(Color.BLACK);
		for (Color c : colors) {
			assertEquals(BACKGROUND_COLOR, c);
		}
	}

	@Test
	public void testPercentageOccupied() {
		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, BACKGROUND_COLOR, 0));
		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap result = request.getResult();
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		float screenRatio = width / (float) height;
		float worldRatio = WORLD_REGION.width / WORLD_REGION.height;
		float expectedPourcentage = worldRatio / screenRatio;
		if (screenRatio < worldRatio) {
			expectedPourcentage = 1 / expectedPourcentage;
		}
		expectedPourcentage *= 100;
		expectedPourcentage = 100 - expectedPourcentage;

		HashMap<Color, Float> colors = getPixelCountPercentage(getAllPixels(result));
		assertEquals("The pourcentage of borders is not correct", expectedPourcentage, colors.get(Color.BLACK), DELTA);
	}

	@Test
	public void testEmptyBoardAppearence() {
		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap result = request.getResult();
		Set<Color> colors = getColorSet(getVerticalPixels(result,
				result.getWidth()/2));
		colors.remove(Color.BLACK);
		for(Color c : colors) {
			assertEquals("The color should be the midfield Line", MIDFIELD_COLOR, c );
		}
	}

	@Test
	public void testHalfRectangle() {
		Rectangle halfRectanglePart1 = new Rectangle(0, 0, WORLD_REGION.width / 2, WORLD_REGION.height);
		Rectangle halfRectanglePart2 = new Rectangle(WORLD_REGION.width / 2, 0, WORLD_REGION.width / 2,
				WORLD_REGION.height);

		EngineTestRequestBuilder builder = new EngineTestRequestBuilder(WORLD_REGION);
		builder.addDrawable(new SimpleRectangle(WORLD_REGION, BACKGROUND_COLOR, 0));
		builder.addDrawable(new SimpleRectangle(halfRectanglePart1, Color.RED, 2));
		builder.addDrawable(new SimpleRectangle(halfRectanglePart2, Color.BLUE, 1));

		GraphicTester.TestRequest request = builder.build();
		tester.scheduleTest(request);
		Pixmap result = request.getResult();

		HashMap<Color, Float> colors = getPixelCountPercentage(getAllPixels(result));
		assertEquals("The two rectangle should have the same area", colors.get(Color.RED), colors.get(Color.BLUE),
				DELTA);
		assertFalse("the background should be covered by the two rectangles", colors.keySet()
				.contains(BACKGROUND_COLOR));
	}

	private static Color setAlphaTo(Color c, float alpha) {
		return new Color(c.r, c.g, c.b, alpha);
	}

	private static Set<Color> getColorSet(List<Color> colorsInput) {
		HashSet<Color> colors = new HashSet<Color>();
		for (Color c : colorsInput) {
			c = setAlphaTo(c, 1f);
			colors.add(c);
		}
		return colors;
	}

	private static HashMap<Color, Float> getPixelCountPercentage(List<Color> colorsInput) {
		HashMap<Color, Float> colors = new HashMap<Color, Float>();
		for (Color c : colorsInput) {
			c = setAlphaTo(c, 1f);
			if (colors.containsKey(c)) {
				colors.put(c, colors.get(c) + 1f);
			} else {
				colors.put(c, 1f);
			}
		}

		final int nbPixels = colorsInput.size();

		for (Color c : colors.keySet()) {
			colors.put(c, colors.get(c) * 100f / nbPixels);
		}
		return colors;
	}

	private static List<Color> getVerticalPixels(Pixmap image, int x) {
		List<Color> colors = new ArrayList<Color>();
		for (int y = 0; y < image.getHeight(); y++) {
			colors.add(new Color(image.getPixel(x, y)));
		}
		return colors;
	}

	private static List<Color> getAllPixels(Pixmap image) {
		List<Color> colors = new ArrayList<Color>();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				colors.add(new Color(image.getPixel(x, y)));
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
