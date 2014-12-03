/**
 * 
 */
package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

/**
 * @author Sidney Barthe This class builds into a Screen (which is the libgdx
 *         equivalent of an Activity) used for the gui. You have to pass it an
 *         array of GuiWidgets as parameter (with the widgets in the order you
 *         want them to be displayed from top to bottom, left to right).
 */
public class GuiScreen implements Screen {
	private Stage mStage = new Stage();
	private Table mTable = new Table();
	private ArrayList<GuiWidget> mWidgets;

	public GuiScreen(ArrayList<GuiWidget> widgets) {
		mWidgets = widgets;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mStage.act();
		mStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		mStage.clear();
		mTable.clear();
		mStage = new Stage();
		mTable = new Table();
		mStage.getViewport().update(width, height, false);
		show();
	}

	@Override
	public void show() {
		for (GuiWidget widget : mWidgets) {
			widget.show(mTable, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		mTable.setFillParent(true);
		mStage.addActor(mTable);

		Gdx.input.setInputProcessor(mStage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		mStage.dispose();
	}

}
