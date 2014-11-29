package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe This class represents a label, which is a string that
 *         can be displayed directly on a screen.
 */
public class GuiLabel extends GuiWidget {
	private boolean mNewLine;
	private float[] mPadding;
	private Label mLabel;
	private int mColSpan;
	
	public GuiLabel(Skin skin,
					String style,
					boolean lineBreak,
					float[] padding,
					int colSpan,
					String text) {
		mNewLine = lineBreak;
		mPadding = padding;
		mLabel = new Label(text, skin, style);
		mColSpan = colSpan;
	}

	public void show(Table table, int width, int height) {
		//mLabel.setFontScale(1.2f);
		
		table.add(mLabel)
		.colspan(mColSpan)
		.padRight(mPadding[0] * width)
		.padTop(mPadding[1] * height)
		.padLeft(mPadding[2] * width)
		.padBottom(mPadding[3] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
	
	public void setText(String text) {
		mLabel.setText(text);
	}
}
