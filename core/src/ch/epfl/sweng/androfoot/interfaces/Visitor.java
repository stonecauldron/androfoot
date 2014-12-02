package ch.epfl.sweng.androfoot.interfaces;

/**
 * Reprensent an object that can visit Visitable objects
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Visitor_pattern"> Visitor pattern
 *      on wikipedia </a>
 * @author Guillame Leclerc
 *
 */
public interface Visitor {

	void visit(Visitable visitable);

	void visit(DefaultBall ball);

	void visit(DefaultPlayer player);

	void visit(DrawableRectangle rectangle);

	/**
	 * Exception that report a {@link Visitable} is not handled by this class
	 * 
	 * @author Guillame Leclerc
	 *
	 */
	static class NotCompatibleVisitableException extends RuntimeException {
		/**
		 * the serial UID for serialization
		 */
		private static final long serialVersionUID = -8855835886568357842L;

		public NotCompatibleVisitableException() {
			super();
		}

		public NotCompatibleVisitableException(Throwable t) {
			super(t);
		}

		public NotCompatibleVisitableException(String message) {
			super(message);
		}

		public NotCompatibleVisitableException(String message, Throwable t) {
			super(message, t);
		}
	}

}
