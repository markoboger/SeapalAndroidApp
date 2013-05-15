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
	private List<UUID> marks;
	private UUID routeEntryPoInteger;
	private Double distance;

	public Route() {
		setId(UUID.randomUUID().toString());
		marks = new LinkedList<UUID>();
	}
	
	public Route(IRoute r) {
		setId(r.getId());
		marks = new LinkedList<UUID>();
		
		this.user = r.getUser();

		this.name = r.getName();
		this.date = r.getDate();
		this.marks = r.getMarks();
		this.routeEntryPoInteger = r.getRouteEntryPoint();
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
		return marks;
	}

	@Override
	public void addMark(UUID mark) {
		this.marks.add(mark);
	}
	
	@Override
	public void deleteMark(UUID mark) {
		this.marks.remove(mark);
	}

	@Override
	public UUID getRouteEntryPoint() {
		return routeEntryPoInteger;
	}

	@Override
	public void setRouteEntryPoint(UUID mark) {
		this.routeEntryPoInteger = mark;
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
