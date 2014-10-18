package ch.epfl.sweng.androfoot.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.Visitable;
import ch.epfl.sweng.androfoot.interfaces.Visitor;
import ch.epfl.sweng.androfoot.interfaces.WorldRenderer;

public class GraphicEngine implements WorldRenderer, Visitor{
	
	private DrawableWorld world = null;

	@Override
	public void bindToWorld(DrawableWorld worldArg) {
		assert worldArg != null;
		world = worldArg;
	}

	@Override
	public void render() {
		List<Drawable> toDraw = new ArrayList<Drawable>(world.toDraw());
		Comparator<Drawable> comparator = new Comparator<Drawable>() {
			
			@Override
			public int compare(Drawable o1, Drawable o2) {
				return o1.getZIndex() - o2.getZIndex();
			}
		};
		Collections.sort(toDraw, comparator); 
		for(Drawable element : toDraw) {
			element.accept(this);
		}
	}

	@Override
	public void visit(Visitable visitable) {
		String message = this.getClass().getName() + " cannot render ojects of type " + 
				visitable.getClass().getName();
		throw new Visitor.NotCompatibleVisitableException(message);
	}
}
