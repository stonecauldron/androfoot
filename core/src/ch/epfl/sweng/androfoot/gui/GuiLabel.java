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
	private int mXPadding;
	private int mYPadding;
	private Label mLabel;
	
	public GuiLabel(Skin skin,
					boolean lineBreak,
					int xPadding,
					int yPadding,
					String text) {
		mNewLine = lineBreak;
		mXPadding = xPadding;
		mYPadding = yPadding;
		mLabel = new Label(text, skin);
	}

	public void show(Table table) {
		table.add(mLabel).padBottom(mYPadding).padRight(mXPadding);
		if (mNewLine) {
			table.row();
		}
	}
}
