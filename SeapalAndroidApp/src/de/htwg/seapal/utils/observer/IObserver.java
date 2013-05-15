package de.htwg.seapal.utils.observer;

/**
 * Observer interface for the observer pattern.
 */
public interface IObserver {
	/**
	 * Updates the observer.
	 * @param event The update event.
	 */
	void update(Event event);
}
