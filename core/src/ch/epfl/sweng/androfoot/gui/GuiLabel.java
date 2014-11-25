package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe This class represents a label, which is a string that
 *         can be displayed directly on a screen.
 */
public class GuiLabel extends GuiWidget {
	protected boolean mNewLine;
	protected int[] mPadding;
	protected Label mLabel;
	protected int mColSpan;
	
	public GuiLabel(Skin skin,
					boolean lineBreak,
					int[] padding,
					int colSpan,
					String text) {
		mNewLine = lineBreak;
		mPadding = padding;
		mLabel = new Label(text, skin);
		mColSpan = colSpan;
	}

	public void show(Table table) {
		table.add(mLabel)
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
