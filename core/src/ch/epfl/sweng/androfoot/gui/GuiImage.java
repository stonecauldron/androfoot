package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Sidney Barthe
 */
public class GuiImage extends GuiWidget {
	private boolean mNewLine;
	private float[] mPadding;
	private Image mImage;
	private int mColSpan;
	private float mXSize;
	private float mYSize;
	
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
	
	public void changeImage(Skin skin, String name) {
		mImage.setDrawable(skin, name);
	}
}
