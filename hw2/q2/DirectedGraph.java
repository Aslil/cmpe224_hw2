
//-----------------------------------------------------
// Title: DirectedGraph class
// Author: Aslı Algın
// Section: 02
// Assignment: 2
// Description: The DirectedGraph class defines a directed graph represented using an adjacency list. 
//Also, there is a separate DirectedEdge class that represents a directed edge between two vertices with integer coordinates 
//(sourceI, sourceJ, targetI, targetJ).  
//-----------------------------------------------------
import java.util.*;

public class DirectedGraph {
	private Map<String, List<String>> adjacencyList;

	public DirectedGraph() {
		this.adjacencyList = new HashMap<>();
	}

	public void addEdge(String source, String target) {
		adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(target);
	}

	public List<String> getNeighbors(String vertex) {
		return adjacencyList.getOrDefault(vertex, Collections.emptyList());
	}
}

class DirectedEdge {

	int sourceI, sourceJ, targetI, targetJ;

	public DirectedEdge(int sourceI, int sourceJ, int targetI, int targetJ) {
		// --------------------------------------------------------
		// Summary:this class defines a DirectedEdge class representing a directed edge
		// in a graph,
		// with integer coordinates for the source and target vertices
		// Precondition: sourceI, sourceJ, targetI, targetJ are integers.
		// Postcondition: It returns toString method for a readable string
		// representation of the edge.
		// --------------------------------------------------------
		this.sourceI = sourceI;
		this.sourceJ = sourceJ;
		this.targetI = targetI;
		this.targetJ = targetJ;
	}

	@Override
	public String toString() {
		return sourceI + "" + sourceJ + " -> " + targetI + "" + targetJ;
	}
}
