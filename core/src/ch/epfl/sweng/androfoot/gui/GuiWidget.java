package ch.epfl.sweng.androfoot.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * This abstract class represents a widget, which is an element of the GUI (such as
 * a button, a label, a checkbox...), it is supposed to contain a real widget from
 * scene2d and additional fields (like padding, skin...) needed to display it.
 * 
 * @author Sidney Barthe
 * 
 */
abstract public class GuiWidget {
	abstract void show(Table table, int width, int height);
}
