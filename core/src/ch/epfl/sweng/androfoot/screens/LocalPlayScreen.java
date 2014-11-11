package ch.epfl.sweng.androfoot.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author Sidney Barthe
 *
 */
public class LocalPlayScreen implements Screen {
	private static final String BUTTON_STRING_START = "Start!";
	private static final int BUTTON_X_SIZE_PER_LETTER = 20;
	private static final int BUTTON_X_PADDING = 20;
	private static final int BUTTON_Y_SIZE = 40;
	private static final int WIDGET_Y_PADDING = 20;

	private Stage stage = new Stage();
	private Table table = new Table();
	private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
	private Skin skin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);

	private TextButton buttonStart = new TextButton(BUTTON_STRING_START, skin);

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		buttonStart.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
			}
		});

		// Display the elements (in the order we add them)
		table.add(buttonStart)
				.size(BUTTON_X_PADDING + BUTTON_STRING_START.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}
