package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe
 * This class represents a button with a range of parameters to be able to display it
 * efficiently in different manners.
 */
public class GuiCheckBox extends GuiWidget {
	private CheckBox mCheckBox;
	private GuiCommand mCommand;
	private boolean mNewLine;
	private int[] mPadding;
	private int mXSize;
	private int mYSize;
	private int mColSpan;
	
	public GuiCheckBox(Skin skin,
					 boolean lineBreak,
					 int[] padding,
					 int xSize,
					 int ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mCheckBox = new CheckBox("", skin);
		mXSize = xSize;
		mYSize = ySize;
		mCommand = command;
		mColSpan = colSpan;
		mCheckBox.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GuiManager.getInstance().executeCommand(mCommand);
			}
		});
	}

	public void show(Table table) {
		table.add(mCheckBox)
		.size(mXSize, mYSize)
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
