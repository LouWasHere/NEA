/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

import java.sql.*;
import java.util.*;
import java.util.Collections;
/**
 *
 * @author l-bishop
 */
public class DjikstraOperations 
{
    private HashMap<Integer,Node> Nodes = new HashMap<Integer,Node>();
    private ArrayList<Integer> Indexes = new ArrayList<Integer>();
    public void menu()
    {
        System.out.println("Here you can calculate various routes between two points using a variety of conditions.");
        Scanner scanner = new Scanner(System.in);
        int userSelection;
        boolean loop = true;
        do
        {
            do
            {
                System.out.println("Please enter a valid menu option to proceed.\n");
                System.out.println("1. Calculate Shortest Route\n2. Calculate Straightest Route\n3. Calculate Quietest Route\n4. Exit");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                userSelection = scanner.nextInt();
            }
            while (!(userSelection >= 1 && userSelection <=4));
            if(userSelection == 4)
            {
                loop = false;
            }
            else
            {
                buildTree(userSelection);
            }
        }
        while(loop == true);
    }
    private void buildTree(int mode)
    {
        Connection conn = null;
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:programDatabase.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        int roadID = 0;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT RoadID, RoadName, RoadLength, RoadCurvature, TrafficID FROM RoadInfo;");
            while (rs.next())
            {
                roadID = rs.getInt("RoadID");
                String roadName = rs.getString("RoadName");
                int roadLength = rs.getInt("RoadLength");
                int roadCurvature = rs.getInt("RoadCurvature");
                int trafficID = rs.getInt("TrafficID");
                Road road = new Road(roadName, roadLength, roadCurvature, trafficID);
                switch(mode)
                {
                    case 1:
                        road.setComparableValue(roadLength);
                    break;
                    case 2:
                        road.setComparableValue(roadCurvature);
                    break;
                    case 3:
                        road.setComparableValue(trafficID);
                    break;
                }
                Indexes.add(roadID);
                Nodes.put(roadID, road);
            }
            rs.close();
            stmt.close(); 
            int maxRoadID = roadID;
            stmt = conn.createStatement();
            rs2 = stmt.executeQuery("SELECT CornerID, CornerCurvature FROM CornerInfo;");
            while (rs2.next())
            {
                int cornerID = rs2.getInt("CornerID");
                int cornerCurvature = rs2.getInt("CornerCurvature");
                Corner corner = new Corner(cornerCurvature);
                switch(mode)
                {
                    case 1:
                        corner.setComparableValue(0);
                    break;
                    case 2:
                        corner.setComparableValue(cornerCurvature);
                    break;
                    case 3:
                        corner.setComparableValue(0);
                    break;
                }
                Indexes.add(cornerID + maxRoadID + 1);
                Nodes.put(cornerID + maxRoadID + 1, corner);
            }
            rs2.close();
            stmt.close();
            stmt = conn.createStatement();
            rs2 = stmt.executeQuery("SELECT CornerID, RoadID FROM EdgeInfo;");
            while (rs2.next())
            {
                int cornerID = rs2.getInt("CornerID");
                roadID = rs2.getInt("RoadID");
                Edge edge = new Edge();
                edge.setSourceNode(Nodes.get(roadID));
                edge.setTargetNode(Nodes.get(cornerID + maxRoadID + 1));
                Nodes.get(roadID).addNeighbour(edge);
                Nodes.get(cornerID + maxRoadID + 1).addNeighbour(edge);
                Edge otherEdge = new Edge();
                otherEdge.setSourceNode(Nodes.get(cornerID + maxRoadID + 1));
                otherEdge.setTargetNode(Nodes.get(roadID));
                Nodes.get(roadID).addNeighbour(otherEdge);
                Nodes.get(cornerID + maxRoadID + 1).addNeighbour(otherEdge);
            }
            rs2.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Please enter the ID road you are starting from:");
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT RoadID, RoadName FROM RoadInfo;");
            while (rs.next())
            {
                String RoadName = rs.getString("RoadName");
                int RoadID = rs.getInt("RoadID");
                System.out.print("Road Name = " + RoadName);
                System.out.println(", RoadID = " + RoadID + "\n");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) 
        {
                System.out.println("That's not a number!");
                scanner.next();
        }
        calculateShortestPath(scanner.nextInt());
        System.out.println("Please enter the ID road you wish to end at.");
        scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) 
        {
                System.out.println("That's not a number!");
                scanner.next();
        }
        int destinationRoadID = scanner.nextInt();
        System.out.println("--------------------------------------");
        System.out.println("Calculating Route...");
        System.out.println("--------------------------------------");
          
        System.out.println("Shortest Path start to finish: ");
        ArrayList<Node> nodes = getShortestPathTo(Nodes.get(destinationRoadID));
        for(int i = 0; i<nodes.size()-1; i=i+2)
        {
            System.out.println("Travel along "+nodes.get(i).getName());
            System.out.println("Turn onto "+nodes.get(i+2).getName());
        }
        System.out.println("Travel along "+nodes.get(nodes.size()-1).getName()+" to finish.");
    }
    private void calculateShortestPath(int sourceNodeID)
    {
        Connection conn = null;
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:programDatabase.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        Node sourceNode = Nodes.get(sourceNodeID);
        sourceNode.setDistance(0);
        final PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        priorityQueue.add(sourceNode);
        sourceNode.setVisited(true);
        while (!priorityQueue.isEmpty())
        {
            // Find the minimum distance node using priority queue
            final Node actualNode = priorityQueue.poll();
            // I found you can iterate over a collection using a colon like this. I know we
            // didn't learn this but I found it in the documentation!
            for (Edge edge : actualNode.getAdjacenciesList()) 
            {
                Node n = edge.getTargetNode();
                if (!n.isVisited()) 
                {
                    // Increment the total distance if needed.
                    final double newDistance = actualNode.getDistance() + edge.getTargetNode().getComparableValue();
                    // Now if the distance found is more efficent, that becomes the new "smallest"
                    // distance, and the node being worked on is removed from the Priority Queue,
                    // then added to the back.
                    if (newDistance < n.getDistance()) 
                    {
                        priorityQueue.remove(n);
                        n.setDistance(newDistance);
                        n.setPredecessor(actualNode);
                        priorityQueue.add(n);
                    }
                }
            }
            actualNode.setVisited(true);
        }
    }
    public ArrayList<Node> getShortestPathTo(final Node targetNode)
    {
        final ArrayList<Node> path = new ArrayList<>();
        for(Node node=targetNode; node!=null; node=node.getPredecessor())
        {   
	        path.add(node);
        }
        Collections.reverse(path);
        return path;
    }
}
