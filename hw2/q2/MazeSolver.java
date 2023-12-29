
//-----------------------------------------------------
// Title: MazeSolver class
// Author: Aslı Algın
// Section: 02
// Assignment: 2
// Description: The MazeSolver class reads a maze from a file, searches paths to find treasures marked with lowercase letters or 'E', 
// and prints the number of treasures found along with the corresponding paths using depth-first search (DFS) 
// and a directed graph representation. 
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MazeSolver {
	private static char[][] maze;
	private static List<String> treasures;
	private static Set<String> visitedPaths;
	private static List<DirectedEdge> directedEdges;
	private static DirectedGraph directedGraph;
	private static int rows;
	private static int cols;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("");
			String fileName = scanner.nextLine();
			readMazeFromFile(fileName);
		}

		findTreasures();

		int pathNumber = 1;
		System.out.println(treasures.size() + " treasures are found.");
		System.out.println("Paths are:");
		for (String treasurePath : treasures) {
			System.out.println(pathNumber++ + ") " + treasurePath);
		}
	}

	private static void readMazeFromFile(String fileName) {
		// --------------------------------------------------------
		// Summary:This method reads a maze from a file, initializes data structures for
		// storing treasures, visited paths, directed edges, and a directed graph
		// with integer coordinates for the source and target vertices. 2D array for
		// represent the maze.
		// Precondition: fileName is String.
		// Postcondition:If an IOException occurs during the file read operation, it
		// prints the stack trace.
		// --------------------------------------------------------
		treasures = new ArrayList<>();
		visitedPaths = new HashSet<>();
		directedEdges = new ArrayList<>();
		directedGraph = new DirectedGraph();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			List<String> mazeLines = new ArrayList<>();
			String line;

			while ((line = br.readLine()) != null) {
				mazeLines.add(line);
			}

			rows = mazeLines.size();
			cols = mazeLines.get(0).length();
			maze = new char[rows][cols];

			for (int i = 0; i < rows; i++) {
				maze[i] = mazeLines.get(i).toCharArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void findTreasures() {
		// --------------------------------------------------------
		// Summary:This Java method iterates through each cell in a maze, identifying
		// starting points marked with lowercase letters or 'E'. For each valid starting
		// point, it initiates a depth-first search (DFS) pass, marking the paths
		// visited and collecting treasures found along the way.
		// Postcondition:The collected treasures are then sorted according to ascending
		// order(their lenght).
		// --------------------------------------------------------

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (Character.isLowerCase(maze[i][j]) || maze[i][j] == 'E') {
					String startVertex = i + "" + j;
					if (maze[i][j] != '+' && !visitedPaths.contains(startVertex)) {
						dfs(i, j, new ArrayList<>());
					}
				}
			}
		}

		Collections.sort(treasures, Comparator.comparingInt(String::length));
	}

	private static List<DirectedEdge> getValidDirectedEdges(int i, int j) {
		// --------------------------------------------------------
		// Summary: This method creates a list of directed edges that are valid for a
		// specific maze cell position. It traverses through a predefined set of valid
		// letters iteratively, checking adjacent cells in all four directions (left,
		// right, up, and down).
		// Precondition: i and j are int.
		// Postcondition: If the neighboring cell contains a valid move according to the
		// isValidMove method, a directed edge is created and added to the list of
		// directed edges.
		// --------------------------------------------------------
		List<DirectedEdge> directedEdges = new ArrayList<>();

		char[] validLetters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'E' };

		for (char letter : validLetters) {
			if (i > 0 && isValidMove(maze[i - 1][j])) {
				directedEdges.add(new DirectedEdge(i, j, i - 1, j));
			}
			if (i < rows - 1 && isValidMove(maze[i + 1][j])) {
				directedEdges.add(new DirectedEdge(i, j, i + 1, j));
			}
			if (j > 0 && isValidMove(maze[i][j - 1])) {
				directedEdges.add(new DirectedEdge(i, j, i, j - 1));
			}
			if (j < cols - 1 && isValidMove(maze[i][j + 1])) {
				directedEdges.add(new DirectedEdge(i, j, i, j + 1));
			}
		}

		return directedEdges;
	}

	private static boolean isValidMove(char cell) {
		// determines whether a given character represents a valid move in a maze by
		// checking if it is a lowercase letter or the letter 'E'.
		return Character.isLowerCase(cell) || cell == 'E';
	}

	private static void dfs(int i, int j, List<String> path) {
		// --------------------------------------------------------
		// Summary:This method performs a depth-first search (DFS) traversal on a maze
		// starting from a given position (i, j). It updates the set of visited paths,
		// constructs a path list containing lowercase letters and 'E', and collects
		// treasures encountered.
		// Precondition: i and j are int, path represents List of String type .
		// Postcondition: The path gets added to the list of treasures if the traversal
		// reaches a treasure marked by 'E'.
		// --------------------------------------------------------
		visitedPaths.add(i + "" + j);

		if (Character.isLowerCase(maze[i][j]) || maze[i][j] == 'E') {
			path.add(Character.toString(maze[i][j]));
		}

		if (maze[i][j] == 'E') {
			treasures.add(String.join("", path));
		}

		List<DirectedEdge> edges = getValidDirectedEdges(i, j);
		for (DirectedEdge edge : edges) {
			int neighborI = edge.targetI;
			int neighborJ = edge.targetJ;

			if (!visitedPaths.contains(neighborI + "" + neighborJ)) {
				dfs(neighborI, neighborJ, new ArrayList<>(path));
				directedEdges.add(edge);
				directedGraph.addEdge(i + "" + j, neighborI + "" + neighborJ);
			}
		}
	}
}
