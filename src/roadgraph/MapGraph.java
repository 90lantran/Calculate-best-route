/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

import geography.GeographicPoint;
import util.GraphLoader;


/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

	private Map<GeographicPoint, MapNode> vertices;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		vertices = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return vertices.values().size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{

		Set<GeographicPoint> verticesLocations = new HashSet<GeographicPoint>();
		for(MapNode node : vertices.values()){
			verticesLocations.add(node.getLocation());
		}
		return verticesLocations;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int numEdges = 0;
		for(MapNode node : vertices.values()){
			numEdges += node.getEdges().size();
		}		
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if (location == null || vertices.containsKey(location)){
		return false;
		} else {
			vertices.put(location, new MapNode(location)); // maybe wrong the constructor 
			return true; // Wrong idea, to make a node, you need location and edges
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		vertices.get(from).getEdges().add(new MapEdge(from, to, roadType, length));
		
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 2
		MapNode begin = vertices.get(start);
		MapNode end = vertices.get(goal);
		
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>(); 
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		toExplore.add(begin);
		nodeSearched.accept(begin.getLocation());
		boolean found = false;
		
		while (!toExplore.isEmpty()){
			MapNode curr = toExplore.remove();
			if (curr == end){
				found = true;
				break;
			}
			visited.add(curr);
			List<MapEdge> neighbors = curr.getEdges();
			for(MapEdge e : neighbors){
				MapNode next = vertices.get(e.getEnd());
				if (!visited.contains(next)){
					visited.add(next);
					toExplore.add(next);
					nodeSearched.accept(e.getEnd());
					parentMap.put(next, curr);
				}				
			}
			
		}
		
		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<GeographicPoint>();
		}
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = end;
		while (curr != begin) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
		
		
		
		
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		//return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		PriorityQueue<MapNode> pq = new PriorityQueue<>(new Comparator<MapNode>() {
			@Override
			public int compare(MapNode o1, MapNode o2) {
				return o1.compareTo(o2);
	
			}	
		});
		
		HashSet<MapNode> visited = new HashSet<>();
		Map<MapNode, MapNode> parentMap = new HashMap<>();
		MapNode begin = vertices.get(start);
		MapNode end = vertices.get(goal);
		boolean found = false;
		// initialize distances
		for(GeographicPoint point : getVertices()){
			vertices.get(point).setFromStartPoint(Double.MAX_VALUE);
		}
		begin.setFromStartPoint(0.0);
		
		pq.add(begin);
		
		while(!pq.isEmpty()){
			MapNode curr = pq.remove();
			nodeSearched.accept(curr.getLocation());
			if (!visited.contains(curr)){
				visited.add(curr);
				if (curr == end){
					//return parentMap;
					found = true;
					break;
				}
				
				List<MapEdge> neighors = curr.getEdges();
				for(MapEdge n : neighors){
					if(!visited.contains(n.getEnd())){
						Double newDistance = curr.getFromStartPoint() + n.getDistance();
						if (newDistance < vertices.get(n.getEnd()).getFromStartPoint()){
							vertices.get(n.getEnd()).setFromStartPoint(newDistance);
							parentMap.put(vertices.get(n.getEnd()),curr);
							pq.add(vertices.get(n.getEnd()));
						}					
					}
				}
			}
		}
		
		
		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<GeographicPoint>();
		}
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = end;
		while (curr != begin) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		PriorityQueue<MapNode> pq = new PriorityQueue<>(new Comparator<MapNode>() {
			@Override
			public int compare(MapNode o1, MapNode o2) {
				return o1.compareTo(o2);
	
			}	
		});
		
		HashSet<MapNode> visited = new HashSet<>();
		Map<MapNode, MapNode> parentMap = new HashMap<>();
		MapNode begin = vertices.get(start);
		MapNode end = vertices.get(goal);
		boolean found = false;
		// initialize distances
		for(GeographicPoint point : getVertices()){
			vertices.get(point).setFromStartPoint(Double.MAX_VALUE);
		}
		begin.setFromStartPoint(0.0);
		
		pq.add(begin);
		
		while(!pq.isEmpty()){
			MapNode curr = pq.remove();
			nodeSearched.accept(curr.getLocation());
			if (!visited.contains(curr)){
				visited.add(curr);
				if (curr == end){
					//return parentMap;
					found = true;
					break;
				}
				
				List<MapEdge> neighors = curr.getEdges();
				for(MapEdge n : neighors){
					if(!visited.contains(n.getEnd())){
						Double newDistance = curr.getFromStartPoint() + n.getDistance()
						+ n.getEnd().distance(goal);
						if (newDistance < vertices.get(n.getEnd()).getFromStartPoint()){
							vertices.get(n.getEnd()).setFromStartPoint(newDistance);
							parentMap.put(vertices.get(n.getEnd()),curr);
							pq.add(vertices.get(n.getEnd()));
						}					
					}
				}
			}
		}
		
		
		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<GeographicPoint>();
		}
		// reconstruct the path
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = end;
		while (curr != begin) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
		
		
		
		
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
//		GeographicPoint start = new GeographicPoint(1, 1);
//		GeographicPoint goal = new GeographicPoint(8, -1);
//		List<GeographicPoint> ans = theMap.bfs(start, goal);
//		for(GeographicPoint p : ans){
//		System.out.println(p);
//		}
		
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz */
//		MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");
		
		// for simple 
		GeographicPoint start = new GeographicPoint(1, 1);
		GeographicPoint end = new GeographicPoint(8, -1);
		
		// For utc
//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);

		for(GeographicPoint p : route){
		System.out.println(p);
		}
		System.out.println("\nFor A*");
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		for(GeographicPoint p : route2){
			System.out.println(p);
			}
		
		
	}
	
}
