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
public class CreditsScreen implements Screen {
	private static final String
		BUTTON_STRING_BACK = "Back",
		LABEL_CREDITS = "Credits",
		LABEL_MEMBERS = "Guillaume Leclerc\nMathvey Khokhlov\nPedro Caldeira\nSidney Barthe\nAurelien Farine\nGaylor Bosson\nAdam Haefliger\nEmma Laure";
	private static final int
		BUTTON_X_SIZE_PER_LETTER = 20,
		BUTTON_X_PADDING = 20,
		BUTTON_Y_SIZE = 40,
		WIDGET_Y_PADDING = 20;

    private Stage stage = new Stage();
    private Table table = new Table();
    private TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skins/UI_menus.pack"));
    private Skin skin = new Skin(Gdx.files.internal("skins/UI_menus.json"), atlas);

    private TextButton
        buttonBack = new TextButton(BUTTON_STRING_BACK, skin);
    private Label
    	title = new Label(LABEL_CREDITS,skin),
    	members = new Label(LABEL_MEMBERS,skin);

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    	buttonBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        // Display the elements (in the order we add them)
        table.add(title).padBottom(WIDGET_Y_PADDING*2).row();
        table.add(members).padBottom(WIDGET_Y_PADDING*2).row();
        table.add(buttonBack).size(BUTTON_X_PADDING+BUTTON_STRING_BACK.length()*BUTTON_X_SIZE_PER_LETTER,BUTTON_Y_SIZE).padBottom(WIDGET_Y_PADDING).row();
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
