package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This class represents a button with a range of parameters to be able to display
 * it efficiently in different manners.
 * 
 * @author Sidney Barthe
 * 
 */
public class GuiButton extends GuiWidget {
	private static final float FONTS_X_REFERENCE_SIZE = 640f;
	private static final float FONTS_Y_REFERENCE_SIZE = 480f;
	private TextButton mButton;
	private GuiCommand mCommand;
	private boolean mNewLine;
	private float[] mPadding;
	private int mAlign;
	private String mText;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	
	/**
	 * Builds the widget.
	 * 
	 * @param skin  the skin in which the widget will be searched
	 * @param style  name of the button style (rectangle, arrow...)
	 * @param lineBreak
	 * 			set to true if the next widget has to be added on a new line
	 * @param padding
	 * 			an array containing the size of the empty spaces around the widget
	 * @param align
	 * 			specifies if the widget has to be aligned somewhere (center, left, right...)
	 * @param text  the label displayed in the button
	 * @param xSize  relative x size (compared to x screen size) of the button
	 * @param ySize  relative y size (compared to y screen size) of the button
	 * @param colSpan
	 * 			specifies how many horizontal grid cells this widget will occupy
	 * @param command
	 * 			the gui command that will be executed when pressing the button
	 * 
	 */
	public GuiButton(Skin skin,
					 String style,
					 boolean lineBreak,
					 float[] padding,
					 int align,
					 String text,
					 float xSize,
					 float ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mAlign = align;
		mText = text;
		mButton = new TextButton(mText, skin, style);
		mXSize = xSize;
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

	/**
	 * Displays the widget.
	 * 
	 * @param table  the table in which the widget will be displayed
	 * @param width  width of the screen
	 * @param height height of the screen
	 * 
	 */
	public void show(Table table, int width, int height) {
		mButton.getLabel().setFontScale(Math.min(
						(float) (Math.min(FONTS_X_REFERENCE_SIZE, width) / FONTS_X_REFERENCE_SIZE),
						(float) (Math.min(FONTS_Y_REFERENCE_SIZE, height) / FONTS_Y_REFERENCE_SIZE)));

		int i = 0;
		table.add(mButton)
			.size(mXSize * width, mYSize * height)
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
}
