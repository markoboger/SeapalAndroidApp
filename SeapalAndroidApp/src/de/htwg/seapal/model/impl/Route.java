package de.htwg.seapal.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.model.ModelDocument;

public class Route extends ModelDocument implements IRoute {

	/**
	 * Serial version UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String user; // UUID user

	private String name;
	private Long date;
	private List<String> marks;
	private String routeEntryPoint;
	private Double distance;

	public Route() {
		setId(UUID.randomUUID().toString());
		marks = new LinkedList<String>();
		this.user = "";
		this.name = "";
		this.date = 0L;
		this.routeEntryPoint = null; 
		this.distance = 0D;
	}

	public Route(IRoute r) {
		setId(r.getId());
		this.user = r.getUser();
		this.name = r.getName();
		this.date = r.getDate();
		List<String> list = new LinkedList<String>();
		for (UUID id : r.getMarks()) {
			list.add(id.toString());
		}
		this.marks = null;
		this.routeEntryPoint = r.getRouteEntryPoint().toString();
		this.distance = r.getDistance();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Long getDate() {
		return date;
	}

	@Override
	public void setDate(Long date) {
		this.date = date;
	}

	@Override
	public List<UUID> getMarks() {
		List<UUID> list = new LinkedList<UUID>();
		for (String id : marks) {
			list.add(UUID.fromString(id));
		}
		return list;
	}

	@Override
	public void addMark(UUID mark) {
		this.marks.add(mark.toString());
	}

	@Override
	public void deleteMark(UUID mark) {
		this.marks.remove(mark.toString());
	}

	@Override
	public UUID getRouteEntryPoint() {
		if(routeEntryPoint == null) 
			return null;
		return UUID.fromString(routeEntryPoint);
	}

	@Override
	public void setRouteEntryPoint(UUID mark) {
		this.routeEntryPoint = mark.toString();
	}

	@Override
	public Double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}
}
