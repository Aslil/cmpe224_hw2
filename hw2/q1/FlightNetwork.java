
//-----------------------------------------------------
// Title: FlightNeywork class
// Author: Aslı Algın
// Section: 02
// Assignment: 2
// Description: The FlightNeywork program reads input from the user for a file name, the maximum number of hops, and a source city, 
//  then constructs a FlightGraph and adds routes from the given file to it and prints all possible flight routes from the given source city
// within the specified number of hops.
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FlightNetwork {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("");
			String fileName = scanner.nextLine();

			System.out.print("");
			int hops = scanner.nextInt();
			scanner.nextLine();

			System.out.print("");
			String sourceCity = scanner.nextLine();

			FlightGraph flightGraph = new FlightGraph();
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] cities = line.split(",");
					flightGraph.addRoute(cities[0].trim(), cities[1].trim()); // adding a directed route from the first
																				// city to second city.
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			List<String> destinations = flightGraph.getDestinations(sourceCity);
			flightGraph.printAllRoutes(sourceCity, hops);
		}
	}
}