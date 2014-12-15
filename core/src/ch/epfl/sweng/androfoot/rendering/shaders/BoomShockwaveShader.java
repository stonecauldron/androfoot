package ch.epfl.sweng.androfoot.rendering.shaders;

/**
 * Shockwave shader for contacts between ball and borders
 * 
 * @author Guillaume
 *
 */
public class BoomShockwaveShader extends ShockwaveShader {

	@Override
	protected String getFragmentShader() {
		return "#ifdef GL_ES\n"
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
						+ "float comp = clamp((-0.1 / (x-u_radius)),0.1,1.0);"
						+ "resu.a*=comp;" + "resu.a -= 50.0/256.0;"
						+ "gl_FragColor = resu;" + "}";
	}
}
