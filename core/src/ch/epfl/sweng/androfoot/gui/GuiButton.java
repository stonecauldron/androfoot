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
	private TextButton mButton;
	private GuiCommand mCommand;
	private boolean mNewLine;
	private float[] mPadding;
	private String mText;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	
	public GuiButton(Skin skin,
					 String style,
					 boolean lineBreak,
					 float[] padding,
					 String text,
					 float xSize,
					 float ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
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
		int i = 0;
		table.add(mButton)
			.size(mXSize * width, mYSize * height)
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height);

		if (mNewLine) {
			table.row();
		}
	}
}
