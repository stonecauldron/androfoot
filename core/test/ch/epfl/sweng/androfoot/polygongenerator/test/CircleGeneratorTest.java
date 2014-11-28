package ch.epfl.sweng.androfoot.polygongenerator.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import ch.epfl.sweng.androfoot.polygongenerator.AngleType;
import ch.epfl.sweng.androfoot.polygongenerator.CircleGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.polygongenerator.PolygonUtils;

public class CircleGeneratorTest {
	private static final double DELTA = 0.0001;
	private static final int SEGMENTS = 5;

	@Test
	public void testAngleConverter() {
		assertEquals("The converter failed", Math.PI, PolygonUtils.degreeToRadian(180f), DELTA);
		assertEquals("The converter failed", 0d, PolygonUtils.degreeToRadian(0f), DELTA);
		assertEquals("The converter failed", Math.PI/2, PolygonUtils.degreeToRadian(90f), DELTA);
		assertEquals("The converter failed", 4*Math.PI, PolygonUtils.degreeToRadian(720f), DELTA);
	}
	
	@Test
	public void testAngleSimplifier() {
		assertEquals("The simplifier failed", 0d, PolygonUtils.simplifyRadianAngle((float) (Math.PI*8)), DELTA);
		assertEquals("The simplifier failed", 0d, PolygonUtils.simplifyRadianAngle((float) (Math.PI*4)), DELTA);
		assertEquals("The simplifier failed", Math.PI, PolygonUtils.simplifyRadianAngle((float) (Math.PI*5)), DELTA);
		assertEquals("The simplifier failed", Math.PI*3d/2d, PolygonUtils.simplifyRadianAngle((float) (-Math.PI/2)), DELTA);
	}
	
	@Test
	public void testRoundCircle() {
		CircleGenerator generator = new CircleGenerator(SEGMENTS);
		float[] vertexes = generator.generateVertexesFloat();
		assertEquals("The amount of vertex is false", SEGMENTS + 2, (vertexes.length) / 2);
		assertEquals("The last vertex is not the same as the first one", vertexes[0], vertexes[SEGMENTS*2], DELTA);
		assertEquals("The last vertex is not the same as the first one", vertexes[1], vertexes[SEGMENTS*2 + 1], DELTA);
	}
	
	@Test 
	public void testEquivalentRepresentations() {
		Random rnd = new Random();
		CircleGenerator generator = new CircleGenerator(SEGMENTS, 
				rnd.nextFloat(), rnd.nextFloat(), AngleType.RADIAN, rnd.nextFloat());
		
		float zPlane = rnd.nextFloat();
		
		float[] vertexes = generator.generateVertexesFloat();
		float[] vertexes3D = generator.generateVertexesFloatInZPlane(zPlane);
		List<ImmutablePoint<Float>> points = generator.generatePointsList();
		
		for(int i = 0 ; i <= SEGMENTS ; i++) {
			assertEquals("xVertex and xVertex3D different", vertexes[2*i], vertexes3D[3*i], DELTA);
			assertEquals("yVertex and yVertex3D different", vertexes[2*i + 1], vertexes3D[3*i + 1], DELTA);
			assertEquals("vertex3D not in Zplane", zPlane, vertexes3D[3*i + 2], DELTA);
			assertEquals("xVertex and xListPoint different", vertexes[2*i], points.get(i).x, DELTA);
			assertEquals("yVertex and yListPoint different", vertexes[2*i + 1], points.get(i).y, DELTA);
		}
	}
	
	@Test
	public void testPointsArroundCircle() {
		Random rnd = new Random();
		float radius = Math.abs(rnd.nextFloat() + rnd.nextInt(5));
		CircleGenerator generator = new CircleGenerator(SEGMENTS, 
				rnd.nextFloat(), rnd.nextFloat(), AngleType.RADIAN, radius);
		
		for(ImmutablePoint<Float> p : generator.generatePointsList()) {
			assertEquals("the point is not on the circle", radius, Math.sqrt(p.x*p.x + p.y*p.y), DELTA);
		}
	}
	
	@Test
	public void testEquivalentPortions() {
		Random rnd = new Random();
		float from = rnd.nextFloat();
		float to = rnd.nextFloat();
		CircleGenerator generator = new CircleGenerator(SEGMENTS, from, to, AngleType.RADIAN);
		CircleGenerator generator2 = new CircleGenerator(SEGMENTS, to, from, AngleType.RADIAN);
		
		assertArrayEquals("the two arrays are different",
				generator.generateVertexesFloat(), generator2.generateVertexesFloat(), (float)DELTA);
	}
	
	@Test 
	public void testRadiansDegreeSame() {
		Random rnd = new Random();
		float from = rnd.nextFloat() + rnd.nextInt(360);
		float to = rnd.nextFloat() + rnd.nextInt(360);
		CircleGenerator generator = new CircleGenerator(SEGMENTS, 
				from, to, AngleType.DEGREE);

		CircleGenerator generator2 = new CircleGenerator(SEGMENTS, 
				PolygonUtils.degreeToRadian(from), PolygonUtils.degreeToRadian(to),
				AngleType.RADIAN);

		assertArrayEquals("the two arrays are different", 
				generator.generateVertexesFloat(), generator2.generateVertexesFloat(), (float)DELTA);
	}
	
	@Test 
	public void testSimplifiedAngleSame() {
		Random rnd = new Random();
		float from = rnd.nextFloat() + rnd.nextInt(360);
		float to = rnd.nextFloat() + rnd.nextInt(360);

		CircleGenerator generator = new CircleGenerator(SEGMENTS, 
				PolygonUtils.simplifyRadianAngle(from), PolygonUtils.simplifyRadianAngle(to),
				AngleType.RADIAN);

		CircleGenerator generator2 = new CircleGenerator(SEGMENTS, 
				PolygonUtils.simplifyRadianAngle(from), PolygonUtils.simplifyRadianAngle(to),
				AngleType.RADIAN);

		assertArrayEquals("the two arrays are different", 
				generator.generateVertexesFloat(), generator2.generateVertexesFloat(), (float)DELTA);
		
	}
}
