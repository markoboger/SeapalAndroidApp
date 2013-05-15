package de.htwg.seapal.utils.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Observer class for the observer pattern.
 */
public class Observable implements IObservable {
	
	/**
	 * The subsribers.
	 */
	private List<IObserver> subscribers = new ArrayList<IObserver>(2);

	@Override
	public void addObserver(IObserver s) {
		subscribers.add(s);
	}

	@Override
	public void removeObserver(IObserver s) {
		subscribers.remove(s);
	}

	@Override
	public void removeAllObservers() {
		subscribers.clear();
	}

	@Override
	public void notifyObservers() {
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(Event e) {
		for (Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();) {
			IObserver observer = iter.next();
			observer.update(e);
		}
	}
}
