package ch.epfl.sweng.androfoot.polygongenerator;

public class ImmutablePoint<T> {
	
	public final T x;
	public final T y;
	
	public ImmutablePoint(T xArg, T yArg) {
		x = xArg;
		y = yArg;
	}
}
