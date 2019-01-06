package graph;

import java.util.ArrayList;

/** The Driver class for project Dijkstra */
public class Driver {
    public static void main(String[] args) {
            // Initialize a graph
            Graph graph = new Graph();

            // Create an instance of the Dijkstra class
            Dijkstra dijkstra = new Dijkstra("USA.txt", graph);

            // Create a graphical user interface and wait for user to click
            // on two cities:
            GUIApp app = new GUIApp(dijkstra, graph);
            HashTable table = new HashTable(5);
        //  table.insert("San Francisco", 0);
          table.insert("SanFrancisco", 9);
          table.insert("Denver", 1);
          table.insert("San Jose", 2);
          table.insert("Davis", 3);
          table.insert("Seattle", 4);
          table.insert("Orlando", 5);
          table.insert("WashingtonDC", 6);
          table.insert("Portland", 7);
          table.insert("Minneapolis", 8);
//           table.insert("SanFrancisco",1);
//           table.insert("Das",2);

//           table.insert("bbb",4);
//           table.insert("ggds",5);
//           table.insert("sdjskjdk",6);
      //  table.insert("SanFrancisco",3);


        //    System.out.println(table.find("SanFrancisco"));

    }
}
