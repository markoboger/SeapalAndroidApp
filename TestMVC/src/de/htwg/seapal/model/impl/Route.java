package de.htwg.seapal.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IRoute;

public class Route implements IRoute {

	private String id;
	private String name;
	private long date;
	private List<UUID> marks;
	private UUID routeEntryPoint;
	private double distance;

	public Route() {
		id = UUID.randomUUID().toString();
		marks = new LinkedList<UUID>();
	}

	@Override
	public UUID getId() {
		return UUID.fromString(id);
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
	public long getDate() {
		return date;
	}

	@Override
	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public List<UUID> getMarks() {
		return marks;
	}

	@Override
	public void setMark(UUID mark) {
		this.marks.add(mark);
	}

	@Override
	public UUID getRouteEntryPoint() {
		return routeEntryPoint;
	}

	@Override
	public void setRouteEntryPoint(UUID mark) {
		this.routeEntryPoint = mark;
	}

	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
