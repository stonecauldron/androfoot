package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This class represents a label with a range of parameters to be able to display
 * it efficiently in different manners. A label is an element of text that is
 * displayed.
 * 
 * @author Sidney Barthe
 * 
 */
public class GuiLabel extends GuiWidget {
	private static final float FONTS_X_REFERENCE_SIZE = 640f;
	private static final float FONTS_Y_REFERENCE_SIZE = 480f;
	private boolean mNewLine;
	private float[] mPadding;
	private int mAlign;
	private float mSize;
	private Label mLabel;
	private int mColSpan;
	
	/**
	 * Builds the widget.
	 * 
	 * @param skin  the skin in which the widget will be searched
	 * @param style  name of the label style
	 * @param lineBreak
	 * 			set to true if the next widget has to be added on a new line
	 * @param padding
	 * 			an array containing the size of the empty spaces around the widget
	 * @param align
	 * 			specifies if the widget has to be aligned somewhere (center, left, right...)
	 * @param size  relative size of the font
	 * @param colSpan
	 * 			specifies how many horizontal grid cells this widget will occupy
	 * @param text  the displayed text
	 * 
	 */
	public GuiLabel(Skin skin,
					String style,
					boolean lineBreak,
					float[] padding,
					int align,
					float size,
					int colSpan,
					String text) {
		mNewLine = lineBreak;
		mPadding = padding;
		mAlign = align;
		mSize = size;
		mLabel = new Label(text, skin, style);
		mColSpan = colSpan;
	}

	/**
	 * Displays the widget.
	 * 
	 * @param table  the table in which the widget will be displayed
	 * @param width  width of the screen
	 * @param height height of the screen
	 * 
	 */
	public void show(Table table, int width, int height) {
		mLabel.setFontScale(mSize*Math.min(
						(float) (Math.min(FONTS_X_REFERENCE_SIZE, width) / FONTS_X_REFERENCE_SIZE),
						(float) (Math.min(FONTS_Y_REFERENCE_SIZE, height) / FONTS_Y_REFERENCE_SIZE)));

		int i = 0;
		table.add(mLabel)
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height)
			.align(mAlign);
		
		if (mNewLine) {
			table.row();
		}
	}
	
	/**
	 * Changes the current displayed text.
	 * 
	 * @param text  the new text to be displayed
	 * 
	 */
	public void setText(String text) {
		mLabel.setText(text);
	}
}
