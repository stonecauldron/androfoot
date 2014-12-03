package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe This class represents a label, which is a string that
 *         can be displayed directly on a screen.
 */
public class GuiLabel extends GuiWidget {
	private static final float DEFAULT_X_SCREEN_SIZE = 640f;
	private static final float DEFAULT_Y_SCREEN_SIZE = 480f;
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
		mLabel.setFontScale(Math.min((float) (Math.min(DEFAULT_X_SCREEN_SIZE, width) / DEFAULT_X_SCREEN_SIZE),
				(float) (Math.min(DEFAULT_Y_SCREEN_SIZE, height) / DEFAULT_Y_SCREEN_SIZE)));

		int i = 0;
		table.add(mLabel)
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
	
	public void setText(String text) {
		mLabel.setText(text);
	}
}
