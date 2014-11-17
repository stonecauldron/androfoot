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
	private int mXPadding;
	private int mYPadding;
	private String mText;
	private int mXSizePerLetter;
	private int mYSize;
	
	public GuiButton(Skin skin,
					 boolean lineBreak,
					 int xPadding,
					 int yPadding,
					 String text,
					 int xSizePerLetter,
					 int ySize,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mXPadding = xPadding;
		mYPadding = yPadding;
		mText = text;
		mButton = new TextButton(mText, skin);
		mXSizePerLetter = xSizePerLetter;
		mYSize = ySize;
		mCommand = command;
	}

	public void show(Table table) {
		mButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GuiManager.getInstance().executeCommand(mCommand);
			}
		});
		
		table.add(mButton)
		.size(mXPadding + mText.length() * mXSizePerLetter, mYSize).padBottom(mYPadding);
		
		if (mNewLine) {
			table.row();
		}
	}
}
