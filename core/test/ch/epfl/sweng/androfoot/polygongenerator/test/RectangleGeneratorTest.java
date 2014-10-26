package ch.epfl.sweng.androfoot.polygongenerator.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

import com.badlogic.gdx.math.Rectangle;

import ch.epfl.sweng.androfoot.polygongenerator.RectangleGenerator;

public class RectangleGeneratorTest {
	
	private static float DELTA = 0.0001f;
	
	@Test
	public void testWidth() {
		Random rnd = new Random();
		float width = rnd.nextFloat();
		Rectangle rectangle = new Rectangle(rnd.nextFloat(), rnd.nextFloat(), width, rnd.nextFloat());
		RectangleGenerator generator = new RectangleGenerator(rectangle);
		float[] generated = generator.generateVertexesFloat();
		
		List<Float> xPos = new ArrayList<Float>();
		for(int i = 0 ; i < 4; i ++) {
			xPos.add(generated[2*i]);
		}
		Collections.sort(xPos);
		assertEquals("the width is incorrect", xPos.get(xPos.size()-1)-xPos.get(0), width, DELTA);
	}

	@Test
	public void testHeight() {
		Random rnd = new Random();
		float height = rnd.nextFloat();
		Rectangle rectangle = new Rectangle(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), height);
		RectangleGenerator generator = new RectangleGenerator(rectangle);
		float[] generated = generator.generateVertexesFloat();
		
		List<Float> yPos = new ArrayList<Float>();
		for(int i = 0 ; i < 4; i ++) {
			yPos.add(generated[2*i + 1]);
		}
		Collections.sort(yPos);
		assertEquals("the height is incorrect", yPos.get(yPos.size()-1)-yPos.get(0), height, DELTA);
	}
	
	@Test
	public void testY() {
		Random rnd = new Random();
		float height = Math.abs(rnd.nextFloat());
		float y = rnd.nextFloat();
		Rectangle rectangle = new Rectangle(rnd.nextFloat(), y, rnd.nextFloat(), height);
		RectangleGenerator generator = new RectangleGenerator(rectangle);
		float[] generated = generator.generateVertexesFloat();
		
		List<Float> yPos = new ArrayList<Float>();
		for(int i = 0 ; i < 4; i ++) {
			yPos.add(generated[2*i + 1]);
		}
		Collections.sort(yPos);
		assertEquals("the minimum value for y is invalid", yPos.get(0), y-height, DELTA);
		assertEquals("the maximum value for y is invalid", yPos.get(yPos.size()-1), y, DELTA);
		assertEquals("the height is incorrect", yPos.get(yPos.size()-1)-yPos.get(0), height, DELTA);
	}

	@Test
	public void testX() {
		Random rnd = new Random();
		float width = Math.abs(rnd.nextFloat());
		float x = rnd.nextFloat();
		Rectangle rectangle = new Rectangle(x, rnd.nextFloat(), width, rnd.nextFloat());
		RectangleGenerator generator = new RectangleGenerator(rectangle);
		float[] generated = generator.generateVertexesFloat();
		
		List<Float> xPos = new ArrayList<Float>();
		for(int i = 0 ; i < 4; i ++) {
			xPos.add(generated[2*i]);
		}
		Collections.sort(xPos);
		assertEquals("the minimum value for y is invalid", xPos.get(0), x, DELTA);
		assertEquals("the maximum value for y is invalid", xPos.get(xPos.size()-1), x+width, DELTA);
		assertEquals("the height is incorrect", xPos.get(xPos.size()-1)-xPos.get(0), width, DELTA);
	}

}
