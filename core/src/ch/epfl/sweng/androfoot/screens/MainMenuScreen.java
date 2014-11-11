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
public class MainMenuScreen implements Screen {
	private static final String BUTTON_STRING_LOCALPLAY = "Local Play";
	private static final String BUTTON_STRING_HOSTGAME = "Host Game";
	private static final String BUTTON_STRING_JOINGAME = "Join Game";
	private static final String BUTTON_STRING_SETTINGS = "Settings";
	private static final String BUTTON_STRING_CREDITS = "Credits";
	private static final String BUTTON_STRING_EXIT = "Exit";
	private static final String LABEL_TITLE = "AndroFoot";

	private static final int BUTTON_X_SIZE_PER_LETTER = 20;
	private static final int BUTTON_X_PADDING = 20;
	private static final int BUTTON_Y_SIZE = 40;
	private static final int WIDGET_Y_PADDING = 20;

	private Stage stage = new Stage();
	private Table table = new Table();
	private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
	private Skin skin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);

	private TextButton buttonLocalPlay = new TextButton(BUTTON_STRING_LOCALPLAY, skin);
	private TextButton buttonHost = new TextButton(BUTTON_STRING_HOSTGAME, skin);
	private TextButton buttonJoin = new TextButton(BUTTON_STRING_JOINGAME, skin);
	private TextButton buttonSettings = new TextButton(BUTTON_STRING_SETTINGS, skin);
	private TextButton buttonCredits = new TextButton(BUTTON_STRING_CREDITS, skin);
	private TextButton buttonExit = new TextButton(BUTTON_STRING_EXIT, skin);

	private Label title = new Label(LABEL_TITLE, skin);

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
		buttonLocalPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new LocalPlayScreen());
			}
		});
		buttonHost.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new HostScreen());
			}
		});
		buttonJoin.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new FindScreen());
			}
		});
		buttonSettings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new SettingsScreen());
			}
		});
		buttonCredits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsScreen());
			}
		});
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		// Display the elements (in the order we add them)
		table.add(title).padBottom(WIDGET_Y_PADDING * 2).row();
		table.add(buttonLocalPlay)
				.size(BUTTON_X_PADDING + BUTTON_STRING_LOCALPLAY.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.add(buttonHost)
				.size(BUTTON_X_PADDING + BUTTON_STRING_HOSTGAME.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.add(buttonJoin)
				.size(BUTTON_X_PADDING + BUTTON_STRING_JOINGAME.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.add(buttonSettings)
				.size(BUTTON_X_PADDING + BUTTON_STRING_SETTINGS.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.add(buttonCredits)
				.size(BUTTON_X_PADDING + BUTTON_STRING_CREDITS.length() * BUTTON_X_SIZE_PER_LETTER,
						BUTTON_Y_SIZE)
				.padBottom(WIDGET_Y_PADDING).row();
		table.add(buttonExit)
				.size(BUTTON_X_PADDING + BUTTON_STRING_EXIT.length() * BUTTON_X_SIZE_PER_LETTER,
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
