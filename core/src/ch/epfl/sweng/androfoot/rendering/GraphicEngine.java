package ch.epfl.sweng.androfoot.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ch.epfl.sweng.androfoot.interfaces.BallInterface;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.ScoreDisplayer;
import ch.epfl.sweng.androfoot.interfaces.Visitable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;

public class GraphicEngine implements WorldRenderer, ScoreDisplayer, Visitor{
	
	private final int DEFAULT_SCREEN_WIDTH = 300;
	private final int DEFAULT_SCREEN_HEIGHT = 200;

	ShapeRenderer renderer  = new ShapeRenderer();
	SpriteBatch batch = new SpriteBatch();
	
	private final static GraphicEngine instance = new GraphicEngine();
	private static final float WORLD_WIDTH = 1500;
	static final float WORLD_HEIGHT = 900;
	
	private DrawableWorld world = null;
	private int currentScorePlayer1 = 0;
	private int currentScorePlayer2 = 0;
	private Rectangle worldRegion = null;
	private OrthographicCamera camera = null;
	private FitViewport viewport = null;
	private boolean isBoundToWorld = false;
	private int screenWidth = DEFAULT_SCREEN_WIDTH;
	private int screenHeight = DEFAULT_SCREEN_HEIGHT;
	
	private GraphicEngine() { }
	
	public static GraphicEngine getEngine() {
		return instance;
	}
	
	@Override
	public void bindToWorld(DrawableWorld worldArg) {
		assert worldArg != null;
		assert worldArg.regionToDraw() != null;
		world = worldArg;
		worldRegion = world.regionToDraw();
		isBoundToWorld = true;
		initCamera();
	}
	
	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		renderer.setProjectionMatrix(camera.combined);

		renderer.setColor(new Color(0x303030));
		renderer.begin(ShapeType.Filled);
		renderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		renderer.end();

		batch.begin();
		for(Visitable v : world.toDraw()) {
			v.accept(this);
		}
		batch.end();
	}
	
	@Override
	public void visit(BallInterface ball) {
		new BallRenderer(ball).render(batch, renderer);
	}
	
	@Override
	public void visit(Visitable visitable) {
		return;
		/*String message = this.getClass().getName() + " cannot render ojects of type " + 
				visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);*/
	}

	@Override
	public void setScore(int player1, int player2) {
		currentScorePlayer1 = player1;
		currentScorePlayer2 = player2;
	}
	
	private void initCamera() {
		viewport = new FitViewport(worldRegion.width, worldRegion.height);
		camera = new OrthographicCamera(worldRegion.width,worldRegion.height);
		viewport.setCamera(camera);
		camera.position.set(worldRegion.width/2 + worldRegion.x,
				worldRegion.height/2 + worldRegion.y,
				camera.position.z);
		camera.update();
	}

	@Override
	public void setScreenSize(int width, int height) {
		screenHeight = height;
		screenWidth = width;
		if(isBoundToWorld) {
			viewport.update(screenWidth, screenHeight);
		}
	}
}
