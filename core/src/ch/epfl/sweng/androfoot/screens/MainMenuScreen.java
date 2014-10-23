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

    private Stage stage = new Stage();
    private Table table = new Table();

    private Skin skin = new Skin(Gdx.files.internal("skins/UI_menus.json"),
        new TextureAtlas(Gdx.files.internal("skins/UI_menus.pack")));

    private TextButton
    	buttonLocalPlay = new TextButton("Local Play", skin),
    	buttonNetworkPlay = new TextButton("Network Play", skin),
    	buttonSettings = new TextButton("Settings", skin),
    	buttonCredits = new TextButton("Credits", skin),
        buttonExit = new TextButton("Exit", skin);
    private Label title = new Label("AndroFoot",skin);

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
    	buttonLocalPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new SplashScreen());
            }
        });
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Display the elements (in the order we add them)
        table.add(title).padBottom(40).row();
        table.add(buttonLocalPlay).size(200,60).padBottom(20).row();
        table.add(buttonNetworkPlay).size(250,60).padBottom(20).row();
        table.add(buttonSettings).size(150,60).padBottom(20).row();
        table.add(buttonCredits).size(150,60).padBottom(20).row();
        table.add(buttonExit).size(100,60).padBottom(20).row();

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
