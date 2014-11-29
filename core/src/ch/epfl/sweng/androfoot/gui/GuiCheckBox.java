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
	private float[] mPadding;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	
	public GuiCheckBox(Skin skin,
					 boolean lineBreak,
					 float[] padding,
					 float xSize,
					 float ySize,
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

	public void show(Table table, int width, int height) {
		mCheckBox.getImageCell().size(Math.min(mXSize * width, mYSize * height),
						Math.min(mXSize * width, mYSize * height));
		
		table.add(mCheckBox)
		.size(Math.min(mXSize * width, mYSize * height), Math.min(mXSize * width, mYSize * height))
		.colspan(mColSpan)
		.padRight(mPadding[0] * width)
		.padTop(mPadding[1] * height)
		.padLeft(mPadding[2] * width)
		.padBottom(mPadding[3] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
}
