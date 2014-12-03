package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe
 * This class represents a button with a range of parameters to be able to display it
 * efficiently in different manners.
 */
public class GuiButton extends GuiWidget {
	private static final float DEFAULT_X_SCREEN_SIZE = 640f;
	private static final float DEFAULT_Y_SCREEN_SIZE = 480f;
	private TextButton mButton;
	private GuiCommand mCommand;
	private boolean mNewLine;
	private float[] mPadding;
	private int mAlign;
	private String mText;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	
	public GuiButton(Skin skin,
					 String style,
					 boolean lineBreak,
					 float[] padding,
					 int align,
					 String text,
					 float xSize,
					 float ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mAlign = align;
		mText = text;
		mButton = new TextButton(mText, skin, style);
		mXSize = xSize;
		mYSize = ySize;
		mCommand = command;
		mColSpan = colSpan;
		mButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GuiManager.getInstance().executeCommand(mCommand);
			}
		});
	}

	public void show(Table table, int width, int height) {
		mButton.getLabel().setFontScale(Math.min((float) (Math.min(DEFAULT_X_SCREEN_SIZE, width) / DEFAULT_X_SCREEN_SIZE),
				(float) (Math.min(DEFAULT_Y_SCREEN_SIZE, height) / DEFAULT_Y_SCREEN_SIZE)));

		int i = 0;
		table.add(mButton)
			.size(mXSize * width, mYSize * height)
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height)
			.align(mAlign);

		if (mNewLine) {
			table.row();
		}
	}
}
