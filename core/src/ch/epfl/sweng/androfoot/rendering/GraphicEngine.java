package ch.epfl.sweng.androfoot.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ch.epfl.sweng.androfoot.gamelogic.PlayerShapeManager;
import ch.epfl.sweng.androfoot.interfaces.BallInterface;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.PlayerInterface;
import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;
import ch.epfl.sweng.androfoot.interfaces.ScoreDisplayer;
import ch.epfl.sweng.androfoot.interfaces.Visitable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;
import ch.epfl.sweng.androfoot.polygongenerator.CircleGenerator;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;

public class GraphicEngine implements WorldRenderer, ScoreDisplayer, Visitor{
	
	private static final int DEFAULT_SCREEN_WIDTH = 300;
	private static final int DEFAULT_SCREEN_HEIGHT = 200;

	private static final int SCORE_COLOR_HEX = 0x2B2B2BFF;
	private static final Color SCORE_COLOR = new Color(SCORE_COLOR_HEX);
	private static final int FIELD_COLOR_HEX = 0x303030FF;
	private static final Color FIELD_COLOR = new Color(FIELD_COLOR_HEX);

	private final static GraphicEngine instance = new GraphicEngine();

	private final ShapeRenderer renderer  = new ShapeRenderer();
	private final SpriteBatch batch = new SpriteBatch();
	private final BallRenderer ballRenderer = new BallRenderer();
	private final ScoreRenderer scoreRenderer = new ScoreRenderer(SCORE_COLOR);
	private final BoardRenderer boardRenderer = new BoardRenderer();
	private final PolygonRenderer playerT1Renderer;
	private final PolygonRenderer playerT2Renderer;
	
	private DrawableWorld world = null;
	private Rectangle worldRegion = null;
	private boolean isBoundToWorld = false;
	
	private OrthographicCamera camera = null;
	private FitViewport viewport = null;
	
	private int screenWidth = DEFAULT_SCREEN_WIDTH;
	private int screenHeight = DEFAULT_SCREEN_HEIGHT;
	
	private GraphicEngine() {
		playerT1Renderer = new PolygonRenderer(PlayerShapeManager.getInstanceTeam1());
		playerT2Renderer = new PolygonRenderer(PlayerShapeManager.getInstanceTeam1());
	}
	
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
		boardRenderer.setWorldSize(worldRegion);
		initCamera();
	}
	
	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		renderer.setProjectionMatrix(camera.combined);

		renderer.setColor(FIELD_COLOR);
		renderer.begin(ShapeType.Filled);
		renderer.rect(0, 0, worldRegion.width, worldRegion.height);
		renderer.end();
		
		batch.begin();
		scoreRenderer.render(batch, renderer);
		boardRenderer.render(batch, renderer);
		for(Visitable v : world.toDraw()) {
			v.accept(this);
		}
		batch.end();
	}
	
	@Override
	public void visit(BallInterface ball) {
		ballRenderer.setBall(ball).render(batch, renderer);
	}
	
	@Override
	public void visit(Visitable visitable) {
		String message = this.getClass().getName() + " cannot render ojects of type " + 
				visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);
	}
	
	@Override
	public void visit(PlayerInterface player) {
		PolygonRenderer pr;
		if(player.getTeam())
		{
			pr = playerT1Renderer;
		} else {
			pr = playerT2Renderer;
		}
		pr.setPosition(player.getPositionX(), player.getPositionY());
		pr.setScale(1);
		pr.setRotation(player.getPlayerAngle());
		pr.render(batch, renderer);
	}

	@Override
	public void setScore(int player1, int player2) {
		scoreRenderer.setScore(player1, player2);
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
