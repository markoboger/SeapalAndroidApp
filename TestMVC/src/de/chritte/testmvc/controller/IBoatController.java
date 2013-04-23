package de.chritte.testmvc.controller;

import de.chritte.testmvc.observer.IObservable;

public interface IBoatController extends IObservable {
	
	String getBoatName(String id);
	void setBoatName(String id, String  boatName);

}
