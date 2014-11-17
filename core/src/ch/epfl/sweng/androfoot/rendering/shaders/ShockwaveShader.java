package ch.epfl.sweng.androfoot.rendering.shaders;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ShockwaveShader extends ConcreteSimpleShaderBuilder {
	
	private final int centerArgPosition;
	private final int radiusArgPosition;
	
	public ShockwaveShader() {
		super();
		centerArgPosition = shader.getUniformLocation("u_center");
		radiusArgPosition = shader.getUniformLocation("u_radius");
	}
	
	@Override
	protected String getVertexShader() {
		return "#ifdef GL_ES\n" 
                + "precision mediump float;\n"  
                + "#endif\n" 
				+ "attribute vec4 a_position;"
				+ "uniform vec4 u_color;"
				+ "uniform vec3 u_center;"
				+ "uniform mat4 u_projMatrix;"
				+ "uniform mat4 u_transMatrix;"
				+ "varying vec4 v_color;"
				+ "varying vec4 v_originalPoint;"
				+ "void main() {"
				+ "v_color = u_color;"
				+ "v_originalPoint = u_transMatrix * a_position;"
				+ "gl_Position = u_projMatrix * u_transMatrix * a_position;"
				+ "}";
	}
	
	@Override
	protected String getFragmentShader() {
		return 
				"#ifdef GL_ES\n" 
                + "precision mediump float;\n"  
                + "#endif\n" 
				+ "varying vec4 v_originalPoint;"
				+ "varying vec4 v_color;"
				+ "uniform vec3 u_center;"
				+ "uniform float u_radius;"
				+ "void main() {"
				+ "vec2 op = vec2(v_originalPoint.x, v_originalPoint.y);"
				+ "vec2 ref = vec2(u_center.x, u_center.y);"
				+ "float x = distance(ref, op);"
				+ "vec4 resu = v_color;"
				//+ "resu.a*=(-5.0 * (x-u_radius)*(x-u_radius) + 1);
				+"float comp = clamp((-0.1 / (x-u_radius)),0.1,1.0);"
				+ "resu.a*=comp * (cos(50 * x - u_radius)+1)/2;"
				+ "resu.a -= 8.0/256.0;"
				+ "gl_FragColor = resu;"
				+ "}";
	}
	
	public void setCenter(Vector3 center) {
		shader.setUniformf(centerArgPosition, center);
	}
	
	public void setRadius(float radius) {
		shader.setUniformf(radiusArgPosition, radius);
	}
}
