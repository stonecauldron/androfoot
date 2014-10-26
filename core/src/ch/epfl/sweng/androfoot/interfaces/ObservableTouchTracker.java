package ch.epfl.sweng.androfoot.interfaces;


/**
 * @author Ahaeflig
 * 
 * This class is used to implement the observable touch tracker "subject".
 * Use TouchTrackerObserver to start tracking user input.
 */
public interface ObservableTouchTracker {
	void addObserver(TouchTrackerObserver obs);

	boolean removeObserver(TouchTrackerObserver obs);
}
