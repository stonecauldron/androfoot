package ch.epfl.sweng.androfoot.interfaces;

public interface Visitor {
	
	public void visit(Visitable visitable);
	
	public static class NotCompatibleVisitableException extends RuntimeException {
		/**
		 * the serial UID for serialization
		 */
		private static final long serialVersionUID = -8855835886568357842L;

		public NotCompatibleVisitableException() {
			super();
		}
		
		public NotCompatibleVisitableException(Throwable t){
			super(t);
		}
		
		public NotCompatibleVisitableException(String message){
			super(message);
		}
		
		public NotCompatibleVisitableException(String message, Throwable t){
			super(message, t);
		}
	}

}
