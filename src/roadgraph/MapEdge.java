package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	GeographicPoint start;
	GeographicPoint end;
	String name;
	Double distance;
	
	public MapEdge(GeographicPoint start, GeographicPoint end,
			String name, Double distance) {
		this.start = start;
		this.end = end;
		this.name = name;
		this.distance = distance;
	}
	public GeographicPoint getStart() {
		return start;
	}
	public void setStart(GeographicPoint start) {
		this.start = start;
	}
	public GeographicPoint getEnd() {
		return end;
	}
	public void setEnd(GeographicPoint end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
}
