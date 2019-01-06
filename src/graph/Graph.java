package graph;

/**
 * A class that represents a graph where nodes are cities (of type CityNode)
 * and edges connect them and the cost of each edge is the distance between
 * the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a HashTable and a PriorityQueue from scratch.
 */
import java.util.*;
import java.io.*;
import java.awt.Point;

public class Graph {
    public final int EPS_DIST = 5;

    private CityNode[] nodes; // nodes of the graph
	private int numNodes;     // total number of nodes
	private int numEdges; // total number of edges
	private Edge[] adjacencyList = new Edge[22]; // adjacency list; for each vertex stores a linked list of edges
	HashTable Table = new HashTable(40);
    // Your HashTable that maps city names to node ids should probably be here as well

	/**
	 * Read graph info from the given file, and create nodes and edges of
	 * the graph.
	 *
	 * @param filename name of the file that has nodes and edges
	 */
	public void loadGraph(String filename) {


		try (FileReader f = new FileReader(filename);
			 BufferedReader br = new BufferedReader(f)) {
			String line;
			boolean check = false;
			boolean check1 = false;

 			int ID =0;
			while ((line = br.readLine()) != null) {
				if (line.equals("NODES"))
				{
					line = br.readLine();
					int num = Integer.parseInt(line);
					nodes = new CityNode[num];
					check = true;
					line = br.readLine();
				}
				if (line.equals("ARCS") )
				{
					check = false;
					check1=true;
					line = br.readLine();

				}
				if(check == true)
				{
					String split1[] = line.split(" ");
					CityNode city = new CityNode(split1[0],Double.parseDouble(split1[1]),Double.parseDouble(split1[2]));
					this.addNode(city);
					Table.insert(split1[0],ID);
					ID++;


				}
				if(check1 ==true)
				{


					String split[] = line.split(" ");
					String city1 = split[0];
					String city2 = split[1];
					int dis = Integer.parseInt(split[2]);

					Edge newEdge = new Edge(Table.find(city2),dis);
					Edge newEdge1 = new Edge(Table.find(city1),dis);

					this.addEdge(Table.find(city1),newEdge);
					this.addEdge(Table.find(city2),newEdge1 );


				}

			}


            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");

//			for (int i=0 ;i<20;i++)
//			{
//                Edge head = adjacencyList[i];
//
//				while (head !=null)
//				{
//					System.out.println(nodes[head.getNeighbor()].getCity()+"   "+head.getCost());
//					head = head.getNext();
//				}
//				System.out.println("*****************");
//			}
		}
		catch (IOException e) {
			System.out.println("No such file");
		}




		// FILL IN CODE

	}

	/**
	 * Add a node to the array of nodes.
	 * Increment numNodes variable.
     * Called from loadGraph.
	 *
	 * @param node a CityNode to add to the graph
	 */
	public void addNode(CityNode node) {

		nodes[numNodes] = node;
		//System.out.println(nodes[numNodes].getCity()+"   "+numNodes);
		numNodes++;



		// FILL IN CODE
	}

	/**
	 * Return the number of nodes in the graph
	 * @return number of nodes
	 */
	public int numNodes() {
		return numNodes;
	}

	/**
	 * Adds the edge to the linked list for the given nodeId
	 * Called from loadGraph.
     *
	 * @param nodeId id of the node
	 * @param edge edge to add
	 */
	public void addEdge(int nodeId, Edge edge)
	{

         if (adjacencyList[nodeId]==null)
		 {
		 	adjacencyList[nodeId]=edge;
			// System.out.println(nodeId+"   "+edge.getCost()+"  "+edge.getNeighbor()+"aaa");
		 }
		 else
		 {
			 edge.setNext(adjacencyList[nodeId]);
			 adjacencyList[nodeId]=edge;
		     //System.out.println(nodeId+"   "+edge.getCost()+"  "+edge.getNeighbor());

		 }
		 numEdges++;

//         System.out.println(nodeId);
//         System.out.println( adjacencyList[nodeId].getCost());


		// FILL IN CODE
	}

	/**
	 * Returns an integer id of the given city node
	 * @param city node of the graph
	 * @return its integer id
	 */
	public int getId(CityNode city) {
        return Table.find(city.getCity());
        // Don't forget to change this
    }

	/**
	 * Return the edges of the graph as a 2D array of points.
	 * Called from GUIApp to display the edges of the graph.
	 *
	 * @return a 2D array of Points.
	 * For each edge, we store an array of two Points, v1 and v2.
	 * v1 is the source vertex for this edge, v2 is the destination vertex.
	 * This info can be obtained from the adjacency list
	 */
	public Point[][] getEdges() {
		int i ;
		int j = 0;
		Point[][] edges2D = new Point[numEdges][2];

		for ( i=0 ;i<20;i++)
		{
			Edge head = adjacencyList[i];

			while (head !=null)
			{

				edges2D[j][0]= nodes[i].getLocation();
			    edges2D[j][1]= nodes[head.getNeighbor()].getLocation();
//				System.out.println(nodes[head.getNeighbor()].getCity()+"   "+head.getCost());
//			    System.out.println(edges2D[j][0]+ "      "+edges2D[j][1]);

				head = head.getNext();

				j++;
			}

		}



		// FILL IN CODE

		return edges2D;
	}

	/**
	 * Get the nodes of the graph as a 1D array of Points.
	 * Used in GUIApp to display the nodes of the graph.
	 * @return a list of Points that correspond to nodes of the graph.
	 */
	public Point[] getNodes() {
	    if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
		Point[] pnodes = new Point[this.nodes.length];
       for (int x =0 ; x<nodes.length;x++)
	   {
	     	pnodes[x]=nodes[x].getLocation();
	   }

		// FILL IN CODE

		return pnodes;
	}

	/**
	 * Used in GUIApp to display the names of the airports.
	 * @return the list that contains the names of cities (that correspond
	 * to the nodes of the graph)
	 */
	public String[] getCities() {
        if (this.nodes == null) {
            System.out.println("Graph has no nodes. Write loadGraph method first. ");
            return null;
        }
		String[] labels = new String[nodes.length];
        for(int i= 0;i<nodes.length;i++)
		{
			labels[i]=nodes[i].getCity();
		}


		// FILL IN CODE


		return labels;

	}
	public Edge getHead(int i)
	{
		return  adjacencyList[i];
	}

	/** Take a list of node ids on the path and return an array where each
	 * element contains two points (an edge between two consecutive nodes)
	 * @param pathOfNodes A list of node ids on the path
	 * @return array where each element is an array of 2 points
	 */
	public Point[][] getPath(List<Integer> pathOfNodes) {
		int i;
		System.out.println(pathOfNodes.size()+"   ************************");
		Point[][] edges2D = new Point[pathOfNodes.size()-1][2];
		Point pre;
		Point current;
		pre = nodes[pathOfNodes.get(0)].getLocation();

		for( i=0; i<pathOfNodes.size()-1;i++)
		{
			String pre1 = nodes[pathOfNodes.get(i)].getCity();
			System.out.println("pre  "+pre1);
           current = nodes[pathOfNodes.get(i+1)].getLocation();
           edges2D[i][0]= pre;
		   edges2D[i][1]=current;
		   pre = current;
		}


        // Each "edge" is an array of size two (one Point is origin, one Point is destination)
        // FILL IN CODE

        return edges2D;
	}

	/**
	 * Return the CityNode for the given nodeId
	 * @param nodeId id of the node
	 * @return CityNode
	 */
	public CityNode getNode(int nodeId) {
		return nodes[nodeId];
	}

	/**
	 * Take the location of the mouse click as a parameter, and return the node
	 * of the graph at this location. Needed in GUIApp class.
	 * @param loc the location of the mouse click
	 * @return reference to the corresponding CityNode
	 */
	public CityNode getNode(Point loc) {
		for (CityNode v : nodes) {
			Point p = v.getLocation();
			if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
				return v;
		}
		return null;
	}
}