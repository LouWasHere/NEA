/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

import java.sql.*;
import java.util.*;
/**
 *
 * @author l-bishop
 */
public class DjikstraOperations 
{
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
                System.out.println("1. Calculate Shortest Route\n2. Calculate Straightest Route\n3. Calculate Quietest Route\n4. Calculate Quietest+Straightest Route\n5. Exit");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                userSelection = scanner.nextInt();
            }
            while (!(userSelection >= 1 && userSelection <=3));
            switch(userSelection)
            {
                case 1:
                    buildTree(0);
                break;
                case 2:
                    buildTree(1);
                break;
                case 3:
                    buildTree(2);
                break;
                case 4:
                    buildTree(3);
                break;
                case 5:
                    loop = false;
                break;
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
        HashMap<Integer,Node> Nodes = new HashMap<Integer,Node>();
        ArrayList<Integer> Indexes = new ArrayList<Integer>();
        int roadID = 0;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT RoadID, RoadName, RoadLength, RoadCurvature, TrafficID FROM RoadInfo;");
            while (rs.next())
            {
                roadID = rs.getInt("RoadID");
                Road road = new Road(rs.getString("RoadName"), rs.getInt("RoadLength"), rs.getInt("RoadCurvature"), rs.getInt("TrafficID"));
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
                Corner corner = new Corner(rs2.getInt("CornerCurvature"));
                Indexes.add(cornerID + maxRoadID + 1);
                Nodes.put(cornerID + maxRoadID + 1, corner);
            }
            rs2.close();
            stmt.close();
            
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
