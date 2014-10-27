package ch.epfl.sweng.androfoot.polygongenerator;

public class PaddleSimplifier extends PolygonPack {
	
	public PaddleSimplifier(PaddleGenerator paddle, int nbVertexes) {
		if(nbVertexes < 7){
			throw new AssertionError("cannot be less than 7 vertex");
		}
		int nbVertexCircle = nbVertexes-4;
		
		PaddleGenerator outPadGen = new PaddleGenerator(paddle, nbVertexCircle);
		add(PaddleGenerator.CONTROL_BLOCK_KEY, new NonCiclicPolygon(outPadGen.get(PaddleGenerator.CONTROL_BLOCK_KEY)));
		add(PaddleGenerator.SHOOT_BLOCK_KEY, new NonCiclicPolygon(outPadGen.get(PaddleGenerator.SHOOT_BLOCK_KEY)));
	}

}
