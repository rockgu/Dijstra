package graph;

/** Class Dijkstra. Implementation of Dijkstra's
 *  algorithm on the graph for finding the shortest path.
 *  Fill in code. You may add additional helper methods or classes.
 */

import java.util.*;
import java.awt.Point;

public class Dijkstra {
	private Graph graph; // stores the graph of CityNode-s and edges connecting them
    private List<Integer> shortestPath = null; // nodes that are part of the shortest path

    /** Constructor
	 *
	 * @param filename name of the file that contains info about nodes and edges
     * @param graph graph
	 */
	public Dijkstra(String filename, Graph graph) {
	    this.graph = graph;
		graph.loadGraph(filename);
	}

	/**
	 * Returns the shortest path between the origin vertex and the destination vertex.
	 * The result is stored in shortestPathEdges.
	 * This function is called from GUIApp, when the user clicks on two cities.
	 * @param origin source node
	 * @param destination destination node
     * @return the ArrayList of nodeIds (of nodes on the shortest path)
	 */
	public List<Integer> computeShortestPath(CityNode origin, CityNode destination) {

	    // FILL IN CODE
		PriorityQueue heap = new PriorityQueue(graph.numNodes());
		int [][] Dijkstra = new int[graph.numNodes()][2];
		for (int i=0; i<graph.numNodes();i++)
		{
			Dijkstra[i][0]=Integer.MAX_VALUE;
			Dijkstra[i][1]=-1;
		}

		int origin1 = graph.getId(origin);
		Dijkstra[origin1][0]= 0;
		Dijkstra[origin1][1]=-1;
		int destination1 = graph.getId(destination);
		for (int i= 0 ;i<graph.numNodes();i++) {
			heap.insert(i, Dijkstra[i][0]);
		}

		while (!heap.isempty())
		{
				int head1 = heap.removeMin();
				Edge head = graph.getHead(head1);
				while (head != null)
				{
//					System.out.println("head   "+Dijkstra[head.getNeighbor()][0]);
//					System.out.println("head111   "+Dijkstra[head1][0]+head.getCost());
					if(Dijkstra[head.getNeighbor()][0]>=(Dijkstra[head1][0]+head.getCost())) {

						heap.reduceKey(head.getNeighbor(), head.getCost()+Dijkstra[head1][0]);
						Dijkstra[head.getNeighbor()][0] = head.getCost()+Dijkstra[head1][0];
						Dijkstra[head.getNeighbor()][1] = head1;
					}
					head = head.getNext();
				}
		}


//		for (int i=0;i<graph.numNodes();i++)
//		{
//
//			System.out.print("the cost        "+Dijkstra[i][0]+"   ");
//			System.out.println("the position    "+Dijkstra[i][1]);
//
//
//		}
		int index=Dijkstra[destination1][1];
		//System.out.println("index"+index);
		shortestPath = new ArrayList<Integer>();
		shortestPath.add(destination1);
		while(origin1!=index)
		{
			shortestPath.add(index);
         //     System.out.println("path  "+list.get(index));
              index = Dijkstra[index][1];
		}
		shortestPath.add(origin1);
		System.out.println("size  "+shortestPath.size());
		for(int i=0; i<shortestPath.size();i++)
		{
			System.out.println("  path   "+shortestPath.get(i));
		}


//		System.out.println("list");
//		for(int i=0;i<list.size();i++)
//		{
//			System.out.print(list.get(i));
//		}
		return shortestPath;




        // Create and initialize Dijkstra's table
        // Create and initialize a Priority Queue

        // Run Dijkstra

        // Compute the nodes on the shortest path by "backtracking" using the table

        // The result should be in an instance variable called "shortestPath" and
        // should also be returned by the method
	     // don't forget to change it
    }

    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
    public Point[][] getPath() {
        if (shortestPath == null)
            return null;
        return graph.getPath(shortestPath); // delegating this task to the Graph class
    }

    /** Set the shortestPath to null.
     *  Called when the user presses Reset button.
     */
    public void resetPath() {
        shortestPath = null;
    }

}