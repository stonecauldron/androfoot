package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.rendering.shaders.BoomShockwaveShader;
import ch.epfl.sweng.androfoot.rendering.shaders.ShockwaveShader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class BoomShockwave extends ShockWave {

	public BoomShockwave(Vector2 originArg, Color colorArg, float lifTimeArg, float speedArg) {
		super(originArg, colorArg, lifTimeArg, speedArg);
	}
	
	@Override
	protected ShockwaveShader createShader() {
		// TODO Auto-generated method stub
		return new BoomShockwaveShader();
	}

}
