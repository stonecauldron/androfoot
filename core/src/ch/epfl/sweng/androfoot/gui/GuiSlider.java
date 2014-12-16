package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This class represents a slider with a range of parameters to be able to display
 * it efficiently in different manners. A slider is a bar equipped with a knob
 * that can be moved on it to tweak a value.
 * 
 * @author Sidney Barthe
 * 
 */
public class GuiSlider extends GuiWidget {
	private Slider mSlider;
	private boolean mNewLine;
	private float[] mPadding;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	private GuiCommand mCommand;
	
	/**
	 * Builds the widget.
	 * 
	 * @param skin  the skin in which the widget will be searched
	 * @param lineBreak
	 * 			set to true if the next widget has to be added on a new line
	 * @param padding
	 * 			an array containing the size of the empty spaces around the widget
	 * @param xSize  relative x size (compared to x screen size) of the slider
	 * @param ySize  relative y size (compared to y screen size) of the slider
	 * @param minValue  minimum value that the slider can hold (leftmost value)
	 * @param maxValue  maximum value that the slider can hold (rightmost value)
	 * @param currentValue  the current value and resulting knob position
	 * @param colSpan
	 * 			specifies how many horizontal grid cells this widget will occupy
	 * @param command
	 * 			the gui command that will be executed when moving the knob
	 * 
	 */
	public GuiSlider(Skin skin,
					 boolean lineBreak,
					 float[] padding,
					 float xSize,
					 float ySize,
					 float minValue,
					 float maxValue,
					 float currentValue,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mXSize = xSize;
		mYSize = ySize;
		mSlider = new Slider(minValue, maxValue, 1, false, skin);
		mSlider.setValue(currentValue);
		mColSpan = colSpan;
		mCommand = command;
		mSlider.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				GuiManager.getInstance().executeCommand(mCommand);
				return true;
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
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
		mSlider.getStyle().knob.setMinHeight(mYSize * height);
		mSlider.getStyle().knob.setMinWidth(mYSize * height);
		mSlider.getStyle().background.setMinHeight((mYSize * height) / 2);
		
		int i = 0;
		table.add(mSlider)
			.size(mXSize * width, mYSize * height)
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
	
	/**
	 * Returns the value tweaked by the slider.
	 * 
	 */
	public int getValue() {
		return (int) mSlider.getValue();
	}
}
