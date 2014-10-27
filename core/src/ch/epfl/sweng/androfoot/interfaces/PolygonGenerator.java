package ch.epfl.sweng.androfoot.interfaces;

import java.util.List;

import com.badlogic.gdx.physics.box2d.PolygonShape;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;

public interface PolygonGenerator {
	
	public float[] generateVertexesFloat();
	public float[] generateVertexesFloatInZPlane(float z);
	public List<ImmutablePoint<Float>> generatePointsList();
	
}
