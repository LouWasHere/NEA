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
public class DatabaseManagement 
{
    Scanner scanner = new Scanner(System.in);
    private Connection conn = null;
    private int userSelection;
    public DatabaseManagement()
    {
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
    }
    
    public void menu()
    {
        boolean loop = true;
        while(loop)
        {
            do
            {
                System.out.println("Please enter a valid menu option to proceed.\n");
                System.out.println("1. Add new database information\n2. Modify/Delete database information\n3. View all data in database\n4. Exit to main menu");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                userSelection = scanner.nextInt();
                scanner.nextLine();
            }
            while (!(userSelection >= 1 && userSelection <=4));
            switch(userSelection)
            {
                case 1:
                    do
                    {
                        System.out.println("Please enter a valid menu option to proceed.\n");
                        System.out.println("1. Add new Road\n2. Add new Corner\n3. Add new racer\n4. Add new vehicle\n");
                        while (!scanner.hasNextInt()) 
                        {
                            System.out.println("That's not a number!");
                            scanner.next();
                        }
                    userSelection = scanner.nextInt();
                    scanner.nextLine();
                }
                while (!(userSelection >= 1 && userSelection <=4));
                switch(userSelection)
                {
                    case 1:
                        addRoad();
                    break;
                    case 2:
                        addCorner();
                    break;
                    case 3:
                        
                    break;
                    case 4:

                    break;
                }   
                break;
                case 2:

                break;
                case 3:

                break;
                case 4:
                    loop = false;
                break;
            }
        }
        close();
    }
    
    private void close() 
    {
        try
        {
            conn.close();
        } 
        catch (final SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    private void addRoad()
    {
        boolean tryAgain;
        Statement stmt = null;
        String roadName;
        int roadLength;
        int roadCurvature;
        int trafficLevel;
        do
        {
            tryAgain = false;
            System.out.println("Please enter the name of the road.");
            roadName = scanner.nextLine();
            System.out.println("Please enter the length of the road.");
            while (!scanner.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                scanner.next();
            }
            roadLength = scanner.nextInt();
            do
            {
                System.out.println("Please enter the curvature of the road (from 0 to 180))");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                roadCurvature = scanner.nextInt();
            }
            while (!(userSelection >= 0 && userSelection <=180));
            do
            {
                System.out.println("Please enter the traffic level of the road (from 1 to 10))");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                trafficLevel = scanner.nextInt();
            }
            while (!(userSelection >= 1 && userSelection <=10));
            System.out.println("Is this information all correct?\nRoad Name: "+roadName+"\nRoad Length: "+roadLength+"\nRoad Curvature: "+roadCurvature+"\nRoad Traffic Level: "+trafficLevel);
            System.out.println("Please enter Y for yes and any other character for no.");
            String selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y"))
            {
                tryAgain = true;
            }
        }
        while(tryAgain == true);
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO RoadInfo (RoadName, RoadLength, RoadCurvature, TrafficID) VALUES (\""+roadName+"\", "+roadLength+", "+roadCurvature+", "+trafficLevel+");");
            conn.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
    }
    
    private void addCorner()
    {
        Statement stmt = null;
        ResultSet rs = null;
        int cornerCurvature;
        int cornerID = 0;
        System.out.println("Please enter the curvature of the corner (0 for straight, 180 for U-Turn)");
        cornerCurvature = scanner.nextInt();
        scanner.nextLine();
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
        System.out.println("Please enter the ID of the first road connection.");
        int firstRoadID = scanner.nextInt();
        int secondRoadID;
        do
        {
            System.out.println("Please enter the second road connection. (MUST BE DIFFERNET TO FIRST CONNECTION!)");
            secondRoadID = scanner.nextInt();
        } 
        while(secondRoadID == firstRoadID);
        //create corner
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO CornerInfo (CornerCurvature) VALUES ("+cornerCurvature+");");
            conn.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        //read corner ID (autoincremented)
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT MAX(CornerID) AS MaxCornerID FROM CornerInfo;");
            while (rs.next())
            {
                cornerID = rs.getInt("MaxCornerID");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        //create edge
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EdgeInfo (RoadID, CornerID) VALUES ("+firstRoadID+", "+cornerID+");");
            conn.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        //create second edge
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EdgeInfo (RoadID, CornerID) VALUES ("+secondRoadID+", "+cornerID+");");
            conn.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        
    }
    
    private void addRacer()
    {
        boolean tryAgain;
        String forename;
        String surname;
        int vehicleID;
        String selection;
        do
        {
            tryAgain = false;
            System.out.println("Please enter the forename of the Racer to be added.");
            forename = scanner.nextLine();
            scanner.next();
            System.out.println("Please enter the surname of the Racer to be added.");
            surname = scanner.nextLine();
            scanner.next();
            System.out.println("Please enter the ID of the vehicle the driver is associated with (insert 0 for no vehicle)");
            while (!scanner.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                scanner.next();
            }
            vehicleID = scanner.nextInt();
            String contactNumber;
            do
            {
                System.out.println("Please enter the emergency contact number of this racer (must be 11 digits long)");
                contactNumber = scanner.nextLine();
            }
            while(contactNumber.length() != 11);
            System.out.println("Is this information all correct?\nName: "+forename+" "+surname+"\nVehicleID: "+vehicleID+"\n Emergency Contact: "+contactNumber);
            selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y"))
            {
                tryAgain = true;
            }
        }
        while(tryAgain = true);
    }
    
    private void addVehicle()
    {
        
    }
    
    private void modifyData()
    {
        
    }
    
    private void dumpData()
    {
        
    }
}
