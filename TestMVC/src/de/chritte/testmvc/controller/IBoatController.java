package de.chritte.testmvc.controller;

import java.util.UUID;

import de.chritte.testmvc.observer.IObservable;

public interface IBoatController extends IObservable {
	
	String getBoatName(UUID id);
	void setBoatName(UUID id, String  boatName);

}
