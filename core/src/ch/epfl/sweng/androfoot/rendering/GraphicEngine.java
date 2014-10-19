package ch.epfl.sweng.androfoot.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.ScoreDisplayer;
import ch.epfl.sweng.androfoot.interfaces.Visitable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;

public class GraphicEngine implements WorldRenderer, ScoreDisplayer, Visitor{
	
	private final int DEFAULT_SCREEN_WIDTH = 300;
	private final int DEFAULT_SCREEN_HEIGHT = 200;
	
	private final static GraphicEngine instance = new GraphicEngine();
	
	private DrawableWorld world = null;
	private int currentScorePlayer1 = 0;
	private int currentScorePlayer2 = 0;
	private Stage stage = new Stage();
	private Rectangle worldRegion = null;
	private OrthographicCamera camera = null;
	private Viewport viewport = null;
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
		List<Drawable> toDraw = new ArrayList<Drawable>(world.toDraw());
		Comparator<Drawable> comparator = new Comparator<Drawable>() {
			
			@Override
			public int compare(Drawable o1, Drawable o2) {
				return o1.getZIndex() - o2.getZIndex();
			}
		};
		Collections.sort(toDraw, comparator); 
		stage = new Stage();
		stage.setViewport(viewport);
		for(Drawable element : toDraw) {
			element.accept(this);
		}
		stage.draw();
	}

	@Override
	public void visit(Visitable visitable) {
		String message = this.getClass().getName() + " cannot render ojects of type " + 
				visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);
	}

	@Override
	public void setScore(int player1, int player2) {
		currentScorePlayer1 = player1;
		currentScorePlayer2 = player2;
	}
	
	private void initCamera() {
		camera = new OrthographicCamera(worldRegion.width,worldRegion.height);
		viewport = new FitViewport(screenWidth, screenHeight, camera);
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
