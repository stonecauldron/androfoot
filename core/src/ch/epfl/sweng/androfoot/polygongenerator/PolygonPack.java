package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.HashMap;

import com.sun.org.apache.bcel.internal.classfile.Code;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.PolygonMap;

/**
 * A polygonGenerator aggregator 
 * It is a kind of {@code Map<String, PolygonGenerator>}
 * You can't add a new sub polygon after the vertexes have been generated
 * @author Guillame Leclerc
 *
 */
 class PolygonPack extends AbstractPolygonGenerator implements PolygonMap{
	
	private final HashMap<String, PolygonGenerator> generators = new HashMap<String, PolygonGenerator>();
	private float[] vertexes;
	private boolean isGenerated = false;

	@Override
	protected float[] generate() {
		if(isGenerated) {
			assert vertexes != null;
			return vertexes;
		}
		
		int nbVertexes = 0;
		for(PolygonGenerator generator : generators.values()) {
			nbVertexes += generator.generatePointsList().size();
		}
		
		vertexes = new float[2 * nbVertexes];
		int currIndex = 0;
		for(PolygonGenerator generator : generators.values()) {
			for(float f : generator.generateVertexesFloat()) {
				vertexes[currIndex++] = f;
			}
		}
		
		isGenerated = true;
		return vertexes;
	}
	
	/**
	 * Add a new PolygonGenerator to the PolygonPack
	 * @param key the key of the Polygon
	 * @param generator the generator of the polygon
	 * @throws RuntimeException when the polygon has already been generated at least onece.
	 */
	protected void add(String key, PolygonGenerator generator) {
		if(isGenerated) {
			throw new RuntimeException("Impossible to add to this PolygonPack, it has already been generated");
		}
		generators.put(key, generator);
	}

	@Override
	public PolygonGenerator get(String key) {
		if ( ! generators.containsKey(key)) {
			throw new RuntimeException("key " + key + "Not found");
		}
		return generators.get(key);
	}

}
