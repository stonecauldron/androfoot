package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This class represents a checkbox with a range of parameters to be able to display
 * it efficiently in different manners. A checkbox is a clickable square that holds
 * a boolean.
 * 
 * @author Sidney Barthe
 * 
 */
public class GuiCheckBox extends GuiWidget {
	private CheckBox mCheckBox;
	private GuiCommand mCommand;
	private boolean mNewLine;
	private float[] mPadding;
	private float mXSize;
	private float mYSize;
	private int mColSpan;
	
	/**
	 * Builds the widget.
	 * 
	 * @param skin  the skin in which the widget will be searched
	 * @param lineBreak
	 * 			set to true if the next widget has to be added on a new line
	 * @param padding
	 * 			an array containing the size of the empty spaces around the widget
	 * @param currentState
	 * 			the state (checked/unchecked) of the checkbox (true for checked)
	 * @param xSize  relative x size (compared to x screen size) of the button
	 * @param ySize  relative y size (compared to y screen size) of the button
	 * @param colSpan
	 * 			specifies how many horizontal grid cells this widget will occupy
	 * @param command
	 * 			the gui command that will be executed when checking/unchecking
	 * 
	 */
	public GuiCheckBox(Skin skin,
					 boolean lineBreak,
					 float[] padding,
					 boolean currentState,
					 float xSize,
					 float ySize,
					 int colSpan,
					 GuiCommand command) {
		
		mNewLine = lineBreak;
		mPadding = padding;
		mCheckBox = new CheckBox("", skin);
		mCheckBox.setChecked(currentState);
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

	/**
	 * Displays the widget.
	 * 
	 * @param table  the table in which the widget will be displayed
	 * @param width  width of the screen
	 * @param height height of the screen
	 * 
	 */
	public void show(Table table, int width, int height) {
		mCheckBox.getImageCell().size(Math.min(mXSize * width, mYSize * height),
						Math.min(mXSize * width, mYSize * height));
		int i = 0;
		table.add(mCheckBox)
			.size(Math.min(mXSize * width, mYSize * height), Math.min(mXSize * width, mYSize * height))
			.colspan(mColSpan)
			.padRight(mPadding[i++] * width)
			.padTop(mPadding[i++] * height)
			.padLeft(mPadding[i++] * width)
			.padBottom(mPadding[i++] * height);
		
		if (mNewLine) {
			table.row();
		}
	}
}
