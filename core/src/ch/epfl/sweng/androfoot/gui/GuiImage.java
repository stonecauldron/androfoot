package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This class represents an image with a range of parameters to be able to display
 * it efficiently in different manners.
 * 
 * @author Sidney Barthe
 * 
 */
public class GuiImage extends GuiWidget {
	private boolean mNewLine;
	private float[] mPadding;
	private Image mImage;
	private int mColSpan;
	private float mXSize;
	private float mYSize;
	
	/**
	 * Builds the widget.
	 * 
	 * @param skin  the skin in which the widget will be searched
	 * @param style  name of the image
	 * @param xSize  relative x size (compared to x screen size) of the button
	 * @param ySize  relative y size (compared to y screen size) of the button
	 * @param lineBreak
	 * 			set to true if the next widget has to be added on a new line
	 * @param padding
	 * 			an array containing the size of the empty spaces around the widget
	 * @param colSpan
	 * 			specifies how many horizontal grid cells this widget will occupy
	 * 
	 */
	public GuiImage(Skin skin,
					String style,
					float xSize,
					float ySize,
					boolean lineBreak,
					float[] padding,
					int colSpan) {
		mNewLine = lineBreak;
		mPadding = padding;
		mXSize = xSize;
		mYSize = ySize;
		mImage = new Image(skin, style);
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
		int i = 0;
		table.add(mImage)
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
	 * Changes the current image.
	 * 
	 * @param skin  the skin in which the image will be searched
	 * @param name  name of the image
	 * 
	 */
	public void changeImage(Skin skin, String name) {
		mImage.setDrawable(skin, name);
	}
}
