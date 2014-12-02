package ch.epfl.sweng.androfoot.interfaces;

import java.util.List;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;

/**
 * Represent a class able to retrieve a set of two dimensional vertexes(points)
 * @author Guillame Leclerc
 *
 */
public interface PolygonGenerator {
	
	/**
	 * (compute) and retrieve the list of  two dimensional vertexes
	 * @return vertexes in an array of floats (x values in even indexes and Y in odd)
	 */
	float[] generateVertexesFloat();

	/**
	 * (compute) and retrieve the list of  three dimensional vertexes
	 * @param z the zCoordinates for all the vertexes
	 * @return vertexes in an array of floats (in the format : x1,y1,z1,...,xn,yn,zn)
	 */
	float[] generateVertexesFloatInZPlane(float z);
	
	/**
	 * (compute) and retrieve the list of  two dimensional vertexes
	 * @return vertexes in a list of {@link ImmutablePoint}
	 */
	List<ImmutablePoint<Float>> generatePointsList();
	
}
