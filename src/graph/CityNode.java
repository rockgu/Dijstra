/** A class that represents a node of the graph.
 *  Contains the name of the city and the location  (x, y coordinates) on the "map".
 *  Do not modify this class.
 */
package graph;
import java.awt.Point;

public class
CityNode  {
	private final String city;
	private Point location;

	/** Create a node of the graph for the given city (and x and coordinates)
	 * @param cityName
	 * @param x
	 * @param y
	 */
	public CityNode(String cityName, double x, double y) {
		// Do not change this method
		this.city = cityName;
		int xint = (int) (507*x / 7.0);
		int yint = (int) (289 - 289*y/4.0);
		this.location = new Point(xint, yint);
	}

	/**
	 * Getter for location
	 * @return Point that has x and y; location of the node on the image
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Getter for the city
	 * @return the name of the city
	 */
	public String getCity() {
		return city;
	}
}