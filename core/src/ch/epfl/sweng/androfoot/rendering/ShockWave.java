package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.RectangleGenerator;
import ch.epfl.sweng.androfoot.rendering.shaders.ShockwaveShader;
import ch.epfl.sweng.androfoot.rendering.shaders.SimpleShaderBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ShockWave implements DrawableRenderer{

	private final Vector2 origin;
	private final Color color;
	private final float lifeTime;
	private final Renderer renderer;
	private final float speed;
	private float age = 0f;
	
	public ShockWave(Vector2 originArg, Color colorArg, float lifTimeArg, float speedArg) {
		super();
		speed = speedArg;
		lifeTime = lifTimeArg;
		color = colorArg;
		origin = originArg;
		renderer = new Renderer(new RectangleGenerator(PhysicsWorld.getPhysicsWorld().regionToDraw()));
		renderer.setZindex(10);
		renderer.setColor(color);
		renderer.setPosition(0, 0);
		renderer.setRotation(0);
		renderer.setScale(1);
	}
	
	protected ShockwaveShader createShader() {
		return new ShockwaveShader();
	}
	
	private class Renderer extends PolygonRenderer {
		
		ShockwaveShader shader;
		Vector2 origin = new Vector2();
		float radius = 0f;

		public Renderer(PolygonGenerator generator) {
			super(generator);
		}
		
		@Override
		protected SimpleShaderBuilder getShader() {
			shader = createShader();
			return shader;
		}
		
		public void setCenter(Vector2 originArg) {
			origin = originArg;
		}
		
		public void setRadius(float radiusArg) {
			radius = radiusArg;
		}
		
		@Override
		protected void setShaderArguments(SimpleShaderBuilder shaderArg) {
			super.setShaderArguments(shaderArg);
			shader.setCenter(new Vector3(origin, 0));
			shader.setRadius(radius);
		}
		
	}

	
	public boolean isEnded() {
		return age >= lifeTime;
	}
	
	public void age(float delta) {
		age += delta*speed;
	}
	
	protected Color getColorNow() {
		return color;
	}
	
	protected float getLiferatio() {
		return age/lifeTime;
	}
	
	@Override
	public void render(SpriteBatch batch , ShapeRenderer shapeRender) {
		GraphicEngine.getEngine().enableBlending();
		renderer.setColor(getColorNow());
		renderer.setCenter(origin);
		renderer.setRadius(age/1);
		renderer.render(batch, shapeRender);
	}
}
