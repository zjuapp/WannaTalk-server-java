package com.wannatalk.server.model;

public class MapPoint {
	public int lat;
	public int lon;
	public int distance(MapPoint another){
		return (int)Math.sqrt((another.lat - lat) * (another.lat - lat) + (another.lon - lon) * (another.lon - lon));
	}
	public MapPoint(int lat, int lon){
		this.lat = lat;
		this.lon = lon;
	}
}
