package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a visitable object 
 * @see <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern </a>
 * @author Guillame Leclerc
 *
 */
public interface Visitable {
	public void accept(Visitor visitor);
}
