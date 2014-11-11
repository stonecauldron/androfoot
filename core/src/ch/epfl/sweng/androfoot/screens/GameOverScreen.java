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
public class GameOverScreen implements Screen {
	private String winningPlayer;
	private int score1;
	private int score2;

	GameOverScreen(String playerName, int s1, int s2) {
		this.winningPlayer = playerName;
		this.score1 = s1;
		this.score2 = s2;
	}

	private static final String BUTTON_STRING_BACK = "Back to main menu";
	private static final String LABEL_TITLE = "Game Over!";

	private static final int BUTTON_X_SIZE_PER_LETTER = 20;
	private static final int BUTTON_X_PADDING = 20;
	private static final int BUTTON_Y_SIZE = 40;
	private static final int WIDGET_Y_PADDING = 20;

	private Stage stage = new Stage();
	private Table table = new Table();
	private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gui/gui.pack"));
	private Skin skin = new Skin(Gdx.files.internal("gui/gui.json"), atlas);

	private TextButton buttonBack = new TextButton(BUTTON_STRING_BACK, skin);
	private Label title = new Label(LABEL_TITLE, skin);
	private Label winner = new Label(winningPlayer
									+ " won!\n"
									+ score1
									+ "-"
									+ score2, skin);

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
		buttonBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
			}
		});

		// Display the elements (in the order we add them)
		table.add(title).padBottom(WIDGET_Y_PADDING * 2).row();
		table.add(winner).padBottom(WIDGET_Y_PADDING * 2).row();
		table.add(buttonBack)
				.size(BUTTON_X_PADDING + BUTTON_STRING_BACK.length()
						* BUTTON_X_SIZE_PER_LETTER, BUTTON_Y_SIZE)
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
