/** GUIApp - a class that deals with the graphical user interface of the project.
 *  Creates the window with the panel that displays the map of the US,
 *  with major cities as nodes and edges connecting them.
 *  Allows the user to click on two cities, and shows the shortest path
 *  between these two cities in blue.
 *  Please do not change anything in this class.
 */
package graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUIApp extends JFrame {
    private MapPanel panel;

    /**
     * Constructor of GUIApp
     * @param algo Reference to Dijkstra object
     * @param graph Reference to the graph
     */
    public GUIApp(Dijkstra algo, Graph graph) {
        // Creating a window
        JFrame frame = new JFrame("USA Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // Creating a panel that will contains the "map" and buttons
        panel = new MapPanel(algo, graph);
        // Adding the panel to the window
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /** The panel for the GUI */
    private class MapPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        public final static int RAD = 3;

        private Dijkstra algo; // Dijkstra's object
        private Graph graph; // Graph
        private CityNode origin, destination; // two vertices clicked by the user
        private JButton buttonReset, buttonQuit; // buttons
        private BufferedImage image; // for showing the image of the US map
        private boolean oneClicked = false; // whether the user already clicked no one city

        /**
         * Constructor for MapPanel class
         * @param algo Reference to Dijkstra's
         * @param graph Reference to the graph
         */
        public MapPanel(Dijkstra algo, Graph graph) {
            this.algo = algo;
            this.graph = graph;
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(580, 290));
            this.setBackground(Color.lightGray);
            this.addMouseListener(new MyListener());

            // buttons
            buttonReset = new JButton("Reset");
            buttonReset.addActionListener(new ButtonListener());
            buttonQuit = new JButton("Quit");
            buttonQuit.addActionListener(new ButtonListener());
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.add(buttonReset);
            buttonPanel.add(buttonQuit);
            this.add(buttonPanel, BorderLayout.EAST);

            try { // load the image of the map of the USA
                image = ImageIO.read(new File("USA.bmp"));
            } catch (IOException ex) {
                System.out.println("Could not load the image. " + ex);
            }
            repaint(); // draw everything
        }

        /**
         * The method is responsible for drawing everything on the panel.
         * Do NOT call it explicitly. Instead, call repaint() when
         * something changes and needs to be repainted.
         * @param g Graphics
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
            drawNodes(g);
            drawEdges(g, Color.lightGray);
            drawShortestPath(g);
        }

        /**
         * Draws a little circle at the given location of the node; Uses the
         * given color; "city" parameter is used to draw a label next to the
         * circle.
         * @param g Graphics
         * @param location point that contains x and y coordinates of the city on the image
         * @param col color
         * @param city name of the city
         */
        public void drawNode(Graphics g, Point location, Color col, String city) {
            g.setColor(col);
            g.fillOval(location.x - RAD, location.y - RAD, 2 * RAD, 2 * RAD);
            g.setColor(Color.black);
            g.setFont(new Font("SANS_SERIF", Font.PLAIN, 11));
            g.drawString(city, location.x + 2, location.y - 2);
        }

        /**
         * Display the nodes of the graph as little circles on the map
         * @param g Graphics
         */
        public void drawNodes(Graphics g) {
            Point[] nodes = graph.getNodes();
            if (nodes == null)
                return;
            String[] labels = graph.getCities();
            for (int i = 0; i < nodes.length; i++) {
                if ((origin != null && i == graph.getId(origin)) ||
                        (destination != null) && (i == graph.getId(destination)))
                    drawNode(g, nodes[i], Color.BLUE, labels[i]);
                    // the ones that the user clicked will be shown in blue
                else
                    drawNode(g, nodes[i], Color.BLACK, labels[i]);
            }

        }

        /**
         * Draw edges of the graph
         * edges is an array, where each value is an array of two Points
         * @param g Graphics
         * @param color Color in which to draw the edges
         */
        public void drawEdges(Graphics g, Color color) {
            Point[][] edges = graph.getEdges();
            g.setColor(color);

            for (int i = 0; i < edges.length; i++) {
                Point[] edge = edges[i];
                assert(edge.length == 2); // should contain two vertices
                Point p1 = edge[0];
                Point p2 = edge[1];
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        } // drawEdges

        /**
         * Draw the edges of the shortest path in blue.
         * @param g Graphics
         */
        public void drawShortestPath(Graphics g) {
            Point[][] pathEdges = algo.getPath();
            g.setColor(Color.BLUE);
            if (pathEdges == null)
                return;
            for (int i = 0; i < pathEdges.length; i++) {
                Point[] edge = pathEdges[i];
                assert(edge.length == 2); // should contain two vertices
                Point p1 = edge[0];
                Point p2 = edge[1];
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        } // drawShortestPath

        /** Inner class MyListener that listens for mouse clicks */
        class MyListener implements MouseListener {

            /** Handles mouse clicks
             * @param e mouse event */
            public void mouseClicked(MouseEvent e) {

                Point center = e.getPoint();
                CityNode v = graph.getNode(center);
                if (v == null) {
                    System.out.println("You did not click on any node");
                    return;
                }
                repaint();

                if (!oneClicked) { // first click
                    System.out.println("First node clicked: " + v.getCity());
                    origin = v;
                    oneClicked = true;
                } else { // it's the second click
                    System.out.println("Second  node clicked: " + v.getCity());
                    System.out.println(
                            "Call Dijkstra's shortestPath() method to compute the shortest path between selected cities");
                    destination = v;
                    algo.computeShortestPath(origin, destination);
                    oneClicked = false;
                    repaint();
                } // if oneClicked is true

            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }
        }

        /** Inner class that handles button presses */
        class ButtonListener implements ActionListener {
            /**
             * Will be called automatically when the user clicks on a button.
             * @param e ActionEvent
             */
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buttonQuit) {
                    System.exit(0);
                } else {
                    oneClicked = false;
                    origin = null;
                    destination = null;
                    if (graph != null) {
                        algo.resetPath();
                    }
                    repaint();
                }
            }
        } // inner class ButtonListener

    } // MapPanel
} // GUIApp