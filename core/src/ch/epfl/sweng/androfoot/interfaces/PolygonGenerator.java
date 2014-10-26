package ch.epfl.sweng.androfoot.interfaces;

import java.awt.geom.Point2D;
import java.util.Set;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;

public interface PolygonGenerator {
	
	public float[] generateVertexesFloat();
	public float[] generateVertexesFloatInZPlane(float z);
	public Set<ImmutablePoint<Float>> generatePointsSet();
}
