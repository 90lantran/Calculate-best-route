package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode>{
	private GeographicPoint location;
	private List<MapEdge> edges;
	private Double fromStartPoint ;// = new Double(Double.MAX_VALUE); // the distance from the start point to this Node
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		edges = new ArrayList<MapEdge>();
	}
	
	public MapNode(GeographicPoint location, List<MapEdge> edges) {
		this.location = location;
		this.edges = edges;
	}
	public GeographicPoint getLocation() {
		return location;
	}
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}
	public List<MapEdge> getEdges() {
		return edges;
	}
	public void setEdges(List<MapEdge> edges) {
		this.edges = edges;
	}

	public Double getFromStartPoint() {
		return fromStartPoint;
	}

	public void setFromStartPoint(Double fromStartPoint) {
		this.fromStartPoint = fromStartPoint;
	}

	
	@Override
	public int compareTo(MapNode o) {
		
		return this.getFromStartPoint().compareTo(o.getFromStartPoint());
	}

	
	
	
	
}
