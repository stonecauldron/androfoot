package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.BallInterface;
import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerInterface;
import ch.epfl.sweng.androfoot.interfaces.ScoreDisplayer;
import ch.epfl.sweng.androfoot.interfaces.Visitable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GraphicEngine implements WorldRenderer, ScoreDisplayer, Visitor, GoalObserver {

	private static final int MAX_SHOCKWAVES = 10;
	private static final int DEFAULT_SCREEN_WIDTH = 300;
	private static final int DEFAULT_SCREEN_HEIGHT = 200;

	private static final int SCORE_COLOR_HEX = 0x2B2B2BFF;
	private static final Color SCORE_COLOR = new Color(SCORE_COLOR_HEX);
	private static final int FIELD_COLOR_HEX = 0x303030FF;
	private static final Color FIELD_COLOR = new Color(FIELD_COLOR_HEX);

	private final static GraphicEngine instance = new GraphicEngine();

	private final ShapeRenderer renderer = new ShapeRenderer();
	private final SpriteBatch batch = new SpriteBatch();
	private final BallRenderer ballRenderer = new BallRenderer();
	private final ScoreRenderer scoreRenderer = new ScoreRenderer(SCORE_COLOR);
	private final BoardRenderer boardRenderer = new BoardRenderer();
	private final RectangleRenderer rectangleRenderer = new RectangleRenderer();
	private final PlayerRenderer playerT1Renderer;
	private final PlayerRenderer playerT2Renderer;

	private DrawableWorld world = null;
	private Rectangle worldRegion = null;
	private boolean isBoundToWorld = false;

	private OrthographicCamera camera = null;
	private FitViewport viewport = null;

	private int screenWidth = DEFAULT_SCREEN_WIDTH;
	private int screenHeight = DEFAULT_SCREEN_HEIGHT;

	private final ShockwaveManager shockwaveManager = new ShockwaveManager(MAX_SHOCKWAVES);

	private GraphicEngine() {
		playerT1Renderer = new PlayerRenderer(PlayerCharacteristicsManager.getInstanceTeam1());
		playerT1Renderer.setColor(PlayerCharacteristicsManager.getColorTeam1());
		playerT2Renderer = new PlayerRenderer(PlayerCharacteristicsManager.getInstanceTeam1());
		playerT2Renderer.setColor(PlayerCharacteristicsManager.getColorTeam2());
		batch.enableBlending();
		EventManager.getEventManager().addGoalObserver(this);
		shockwaveManager.addShockWave(new ShockWave(new Vector2(1, 1), new Color(1.0f, 0f, 0f, 0.3f), 10f, 10f));
		shockwaveManager.addShockWave(new ShockWave(new Vector2(3, 0), new Color(1.0f, 0f, 0f, 0.3f), 10f, 10f));
	}

	public static GraphicEngine getEngine() {
		return instance;
	}

	Viewport getViewPort() {
		return viewport;
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

	public void enableBlending() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		renderer.setProjectionMatrix(camera.combined);

		renderer.begin(ShapeType.Filled);
		renderer.setColor(FIELD_COLOR);
		renderer.rect(0, 0, worldRegion.width, worldRegion.height);
		renderer.end();

		scoreRenderer.render(batch, renderer);
		boardRenderer.render(batch, renderer);
		shockwaveManager.render(batch, renderer);
		shockwaveManager.age(delta);
		for (Visitable v : world.toDraw()) {
			v.accept(this);
		}
	}

	@Override
	public void visit(BallInterface ball) {
		ballRenderer.setBall(ball).render(batch, renderer);
	}

	@Override
	public void visit(Visitable visitable) {
		String message = this.getClass().getName() + " cannot render ojects of type " + visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);
	}

	@Override
	public void visit(PlayerInterface player) {
		PlayerRenderer pr;
		if (player.getTeam()) {
			pr = playerT1Renderer;
		} else {
			pr = playerT2Renderer;
		}
		pr.setPosition(player.getPositionX(), player.getPositionY());
		pr.setScale(1);
		pr.setCanControl(player.isAbleToControlBall());
		pr.setRotation(player.getPlayerAngle());
		pr.render(batch, renderer);
	}

	@Override
	public void setScore(int player1, int player2) {
		scoreRenderer.setScore(player1, player2);
	}

	private void initCamera() {
		viewport = new FitViewport(worldRegion.width, worldRegion.height);
		camera = new OrthographicCamera(worldRegion.width, worldRegion.height);
		viewport.setCamera(camera);
		camera.position.set(worldRegion.width / 2 + worldRegion.x, worldRegion.height / 2 + worldRegion.y,
				camera.position.z);
		camera.update();
	}

	@Override
	public void setScreenSize(int width, int height) {
		System.out.println("screen set");
		screenHeight = height;
		screenWidth = width;
		if (isBoundToWorld) {
			viewport.update(screenWidth, screenHeight);
		}
	}

	@Override
	public void visit(DrawableRectangle rectangle) {
		rectangleRenderer.setColor(rectangle.getColor());
		rectangleRenderer.setRectangle(rectangle.getShape());
		rectangleRenderer.render(batch, renderer);
	}

	@Override
	public void goal(boolean isTeamOne) {
		float posX = PhysicsWorld.getBall().getPositionX();
		float posY = PhysicsWorld.getBall().getPositionY();
		Color c;
		if(isTeamOne){
			c = PlayerCharacteristicsManager.getColorTeam1();
		} else {
			c = PlayerCharacteristicsManager.getColorTeam2();
		}
		
		c.a = 0.4f;
		
		shockwaveManager.addShockWave(new ShockWave(new Vector2(posX, posY), c, 12, 10));
	}
}
