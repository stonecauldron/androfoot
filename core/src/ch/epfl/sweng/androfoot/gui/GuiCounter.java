package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe This class represents a counter, which is a GuiLabel
 *         able to hold a value and display it.
 */
public class GuiCounter extends GuiLabel {
	private int mValue;
	
	public GuiCounter(Skin skin,
					boolean lineBreak,
					int[] padding,
					int colSpan,
					int value) {
		super(skin, lineBreak, padding, colSpan, Integer.toString(value));
		mValue = value;
	}
	
	public void add(int x) {
		mValue = mValue + x;
		super.mLabel.setText(Integer.toString(mValue));
	}
	
	public void sub(int x) {
		mValue = mValue - x;
		if(mValue < 1) {
			mValue = 1;
		}
		super.mLabel.setText(Integer.toString(mValue));
	}
	
	public int getValue() {
		return mValue;
	}

	public void show(Table table) {
		super.show(table);
	}
}
