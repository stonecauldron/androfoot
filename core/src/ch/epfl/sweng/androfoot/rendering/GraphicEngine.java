package ch.epfl.sweng.androfoot.rendering;

import ch.epfl.sweng.androfoot.accelerometer.AccelerometerTracker;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.gamelogic.powerups.PowerUpCharacteristicsManger;
import ch.epfl.sweng.androfoot.interfaces.AccelerometerObserver;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.DrawableRectangle;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerShapeListener;
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

/**
 * The graphic engine of the game
 * 
 * @author Guillaume
 *
 */
final public class GraphicEngine implements WorldRenderer, ScoreDisplayer,
				Visitor, GoalObserver, BorderObserver, PlayerShapeListener,
				AccelerometerObserver {

	private static final int BORDER_COLLISION_SHOCKWAVE_SPEED = 8;
	private static final int BORDER_COLLISION_SHOCKWAVE_LIFETIME = 2;
	private static final int GOAL_SHOCKWAVE_SPEED = 9;
	private static final int GOAL_SHOCKWAVE_LIFETIME = 12;
	private static final float GOAL_SHOCKWAVE_OPACITY = 0.4f;
	private static final int MAX_SHOCKWAVES = 3;
	private static final int DEFAULT_SCREEN_WIDTH = 300;
	private static final int DEFAULT_SCREEN_HEIGHT = 200;
	private static final int TILT_COLOR_HEX = 0xFF000060;
	private static final Color TILT_COLOR = new Color(TILT_COLOR_HEX);

	private static final int FIELD_COLOR_HEX = 0x303030FF;
	private static final Color FIELD_COLOR = new Color(FIELD_COLOR_HEX);

	private static final float SHAKE_TIME = 0.5f;
	private static final float SHAKE_ANIMATION_TIME = 0.2f;

	private static GraphicEngine instance = new GraphicEngine();

	private final ShapeRenderer renderer = new ShapeRenderer();
	private final SpriteBatch batch = new SpriteBatch();
	private final BallRenderer ballRenderer = new BallRenderer();
	private final ScoreRenderer scoreRenderer = new ScoreRenderer();
	private final BoardRenderer boardRenderer = new BoardRenderer();
	private final RectangleRenderer rectangleRenderer = new RectangleRenderer();
	private final ShakeRenderer shakeRenderer = new ShakeRenderer(TILT_COLOR,
					SHAKE_TIME, SHAKE_ANIMATION_TIME);
	private PlayerRenderer playerT1Renderer;
	private PlayerRenderer playerT2Renderer;
	private final PowerUpRender powerUpRender;

	private DrawableWorld world = null;
	private Rectangle worldRegion = null;
	private boolean isBoundToWorld = false;

	private OrthographicCamera camera = null;
	private FitViewport viewport = null;

	private int screenWidth = DEFAULT_SCREEN_WIDTH;
	private int screenHeight = DEFAULT_SCREEN_HEIGHT;

	private final ShockwaveManager shockwaveManager = new ShockwaveManager(
					MAX_SHOCKWAVES);

	/**
	 * Init the singleton engine
	 */
	private GraphicEngine() {
		shapeHasChanged();
		powerUpRender = new PowerUpRender(
						PowerUpCharacteristicsManger.getPowerUpShape());
		powerUpRender.setColor(PowerUpCharacteristicsManger.getPowerUpColor());
		batch.enableBlending();
		EventManager.getEventManager().addGoalObserver(this);
		EventManager.getEventManager().addBorderContactObserver(this);
		AccelerometerTracker.getInstance().addObserverShaker(this);
	}

	/**
	 * Get the engine Singleton pattern
	 * 
	 * @return the engine
	 */
	public static GraphicEngine getEngine() {
		return instance;
	}

	/**
	 * get the currentViewport
	 * 
	 * @return the viewport
	 */
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

	/**
	 * Enable simple alpha blending
	 */
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
		powerUpRender.rotate(delta);
		for (Visitable v : world.toDraw()) {
			v.accept(this);
		}
		shakeRenderer.age(delta);
		shakeRenderer.render(batch, renderer);
	}

	@Override
	public void visit(DefaultBall ball) {
		ballRenderer.setBall(ball).render(batch, renderer);
	}

	@Override
	public void visit(Visitable visitable) {
		String message = this.getClass().getName()
						+ " cannot render ojects of type "
						+ visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);
	}

	@Override
	public void visit(DefaultPlayer player) {
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

	/**
	 * Init the camera and the viewport according the the settings of the world
	 */
	private void initCamera() {
		viewport = new FitViewport(worldRegion.width, worldRegion.height);
		camera = new OrthographicCamera(worldRegion.width, worldRegion.height);
		viewport.setCamera(camera);
		camera.position.set(worldRegion.width / 2 + worldRegion.x,
						worldRegion.height / 2 + worldRegion.y,
						camera.position.z);
		camera.update();
	}

	@Override
	public void setScreenSize(int width, int height) {
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
	public void goal(DefaultGoal goal, DefaultBall ball) {
		float posX = ball.getPositionX();
		float posY = ball.getPositionY();
		Color c;
		if (goal.getTeam() == GoalTeam.ONE) {
			c = PlayerCharacteristicsManager.getColorTeam1();
		} else {
			c = PlayerCharacteristicsManager.getColorTeam2();
		}

		c.a = GOAL_SHOCKWAVE_OPACITY;

		shockwaveManager.addShockWave(new ShockWave(new Vector2(posX, posY), c,
						GOAL_SHOCKWAVE_LIFETIME, GOAL_SHOCKWAVE_SPEED));
	}

	@Override
	public void borderContact(DefaultBorder border, DefaultBall ball) {
		float posX = ball.getPositionX();
		float posY = ball.getPositionY();
		Color c = BallRenderer.BALL_COLOR;

		shockwaveManager.addShockWave(new BoomShockwave(
						new Vector2(posX, posY), c,
						BORDER_COLLISION_SHOCKWAVE_LIFETIME,
						BORDER_COLLISION_SHOCKWAVE_SPEED));
	}

	@Override
	public void reset() {
		shockwaveManager.reset();
	}

	@Override
	public void shapeHasChanged() {
		playerT1Renderer = new PlayerRenderer(
						PlayerCharacteristicsManager.getInstanceTeam1());
		playerT1Renderer.setColor(PlayerCharacteristicsManager.getColorTeam1());
		playerT2Renderer = new PlayerRenderer(
						PlayerCharacteristicsManager.getInstanceTeam2());
		playerT2Renderer.setColor(PlayerCharacteristicsManager.getColorTeam2());
	}

	@Override
	public void visit(DefaultPowerUp powerup) {
		powerUpRender.setPosition(powerup.getPositionX(),
						powerup.getPositionY());
		powerUpRender.setScale(powerup.getHitBoxRadius());
		powerUpRender.render(batch, renderer);
	}

	@Override
	public void isShaking() {
		shakeRenderer.start();
	}

	@Override
	public void init() {
		shapeHasChanged();
		shockwaveManager.reset();
	}
}
