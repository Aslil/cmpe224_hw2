
//-----------------------------------------------------
// Title: FlightGraph class
// Author: Aslı Algın
// Section: 02
// Assignment: 2
// Description: The FlightGraph represents a directed graph of flight routes between cities. 
// It uses an adjacency list to store the relationships between source cities and destination cities.
//-----------------------------------------------------
import java.util.*;

class FlightGraph {
	private Map<String, List<String>> adjacencyList;
	private static List<String> result;

	static class Edge {
		private final String source;
		private final String destination;

		public Edge(String source, String destination) {
			this.source = source;
			this.destination = destination;
		}

		public String getSource() {
			return source;
		}

		public String getDestination() {
			return destination;
		}
	}

	static class Graph {
		private final Map<String, List<Edge>> adjacencyList;

		public Graph() {
			this.adjacencyList = new HashMap<>();
		}

		public void addEdge(String source, String destination) {
			Edge edge = new Edge(source, destination);
			adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(edge);
		}

		public List<Edge> getEdges(String source) {
			return adjacencyList.getOrDefault(source, Collections.emptyList());
		}
	}

	public FlightGraph() {
		this.adjacencyList = new HashMap<>();
	}

	public void addRoute(String source, String destination) {
		adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
	}

	public List<String> getDestinations(String source) {
		return adjacencyList.getOrDefault(source, Collections.emptyList());
	}

	public void printAllRoutes(String source, int hops) {
		// --------------------------------------------------------
		// Summary: This method adds directed routes between cities, retrieve
		// destinations for a given source city.
		// Precondition: source is String, hops is integer.
		// Postcondition: It prints all routes from a specified source city within a
		// given number of hops using depth-first search, displaying the total number of
		// routes and their details.
		// --------------------------------------------------------
		List<String> currentPath = new ArrayList<>();
		currentPath.add(source);
		result = new ArrayList<>();
		dfs(source, currentPath, hops);
		System.out.println("\nNumber of total routes: " + result.size());
		System.out.println("Routes are:");
		Collections.sort(result);
		for (String res : result) {
			System.out.println(res);
		}
	}

	private void dfs(String city, List<String> currentPath, int hops) {
		// --------------------------------------------------------
		// Summary: This method recursively explores flight routes from a given city
		// within a specified number of hops.
		// Precondition: city is String, hops is integer, currentPath represents List of
		// String type.
		// Postcondition: It adds valid routes to the result list, and backtracking to
		// explore other paths while maintaining the current path's state.
		// --------------------------------------------------------
		if (currentPath.size() - 1 == hops) {
			result.add(String.join("-", currentPath));
			return;
		}

		List<String> destinations = getDestinations(city);
		for (String destination : destinations) {
			currentPath.add(destination);
			dfs(destination, currentPath, hops);
			currentPath.remove(currentPath.size() - 1);
		}
	}
}
