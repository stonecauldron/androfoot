package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class PolygonRenderer extends AbstractMeshRenderer {
	
	public PolygonRenderer(PolygonGenerator generator) {
		super(new PolygonToMesh(generator).build());
	}
}
