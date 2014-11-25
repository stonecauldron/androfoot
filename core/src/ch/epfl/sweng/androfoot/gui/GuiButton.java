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
	private int[] mPadding;
	private String mText;
	private int mXSizePerLetter;
	private int mYSize;
	private int mColSpan;
	
	public GuiButton(Skin skin,
					 boolean lineBreak,
					 int[] padding,
					 String text,
					 int xSizePerLetter,
					 int ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mText = text;
		mButton = new TextButton(mText, skin);
		mXSizePerLetter = xSizePerLetter;
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

	public void show(Table table) {
		table.add(mButton)
		.size(mText.length() * mXSizePerLetter, mYSize)
		.colspan(mColSpan)
		.padRight(mPadding[0])
		.padTop(mPadding[1])
		.padLeft(mPadding[2])
		.padBottom(mPadding[3]);
		
		if (mNewLine) {
			table.row();
		}
	}
}
