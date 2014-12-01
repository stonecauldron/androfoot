package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe
 */
public class GuiSlider extends GuiWidget {
	private Slider mSlider;
	private boolean mNewLine;
	private float[] mPadding;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	private GuiCommand mCommand;
	
	public GuiSlider(Skin skin,
					 boolean lineBreak,
					 float[] padding,
					 float xSize,
					 float ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mXSize = xSize;
		mYSize = ySize;
		mSlider = new Slider(1, 1000, 1, false, skin);
		mColSpan = colSpan;
		mCommand = command;
		mSlider.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(mSlider.getValue());
				GuiManager.getInstance().executeCommand(mCommand);
				return true;
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				GuiManager.getInstance().executeCommand(mCommand);
			}
		});
	}

	public void show(Table table, int width, int height) {
		table.add(mSlider)
		.size(mXSize * width, mYSize * height)
		.colspan(mColSpan)
		.padRight(mPadding[0] * width)
		.padTop(mPadding[1] * height)
		.padLeft(mPadding[2] * width)
		.padBottom(mPadding[3] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
	
	public int getValue() {
		return (int) mSlider.getValue();
	}
}
