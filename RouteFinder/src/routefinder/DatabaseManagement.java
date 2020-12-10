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
    private Scanner scanner;
    private Connection conn = null;
    private int userSelection;
    public DatabaseManagement()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:programDatabase.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            //System.out.println("Opened database successfully");
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
            scanner = new Scanner(System.in);
            do
            {
                System.out.println("Please enter a valid menu option to proceed.\n");
                System.out.println("1. Add new database information\n2. Delete database information\n3. Exit to main menu");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                userSelection = scanner.nextInt();
                scanner.nextLine();
            }
            while (!(userSelection >= 1 && userSelection <=3));
            switch(userSelection)
            {
                case 1:
                    do
                    {
                        System.out.println("Please enter a valid menu option to proceed.\n");
                        System.out.println("1. Add new Road\n2. Add new Corner\n3. Add new racer\n4. Add new vehicle\n5. Cancel\n");
                        while (!scanner.hasNextInt()) 
                        {
                            System.out.println("That's not a number!");
                            scanner.next();
                        }
                    userSelection = scanner.nextInt();
                    scanner.nextLine();
                    }
                    while (!(userSelection >= 1 && userSelection <=5));
                    switch(userSelection)
                    {
                        case 1:
                            addRoad();
                        break;
                        case 2:
                            addCorner();
                        break;
                        case 3:
                            addRacer();
                        break;
                        case 4:
                            addVehicle();
                        break;
                        case 5:

                        break;
                    }   
                break;
                case 2:
                    deleteData();
                break;
                case 3:
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
        scanner = new Scanner(System.in);
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
            scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y"))
            {
                tryAgain = true;
            }
            else
            {
                System.out.println("Road added succesfully.");
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
        scanner = new Scanner(System.in);
        Statement stmt = null;
        ResultSet rs = null;
        int cornerCurvature;
        int cornerID = 0;
        int firstRoadID;
        int secondRoadID;
        boolean tryAgain = false;
        do
        {
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
            while (!scanner.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                scanner.next();
            }
            firstRoadID = scanner.nextInt();
            do
            {
                System.out.println("Please enter the second road connection. (MUST BE DIFFERNET TO FIRST CONNECTION!)");
                while (!scanner.hasNextInt()) 
                {
                System.out.println("That's not a number!");
                scanner.next();
                }
                secondRoadID = scanner.nextInt();
            } 
            while(secondRoadID == firstRoadID);
            System.out.println("Is this all correct? Please enter Y for yes and any other character for no.");
            scanner = new Scanner(System.in);
            String selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y"))
            {
                tryAgain = true;
            }
        }
        while(tryAgain == true);
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
        scanner = new Scanner(System.in);
        boolean tryAgain;
        String forename;
        String surname;
        int vehicleID;
        String selection;
        String contactNumber;
        Statement stmt = null;
        ResultSet rs = null;
        do
        {
            tryAgain = false;
            System.out.println("Please enter the forename of the Racer to be added.");
            forename = scanner.nextLine();
            System.out.println("Please enter the surname of the Racer to be added.");
            surname = scanner.nextLine();
            try
            {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT VehicleID, VehicleModel FROM VehicleInfo;");
                while (rs.next())
                {
                    String VehicleModel = rs.getString("VehicleModel");
                    int vehicleIDList = rs.getInt("VehicleID");

                    System.out.print("Vehicle Model = " + VehicleModel);
                    System.out.println(", RoadID = " + vehicleIDList + "\n");
                }
            }
            catch(SQLException e)
            {
                System.out.println("Error: "+e);
            }
            System.out.println("Please enter the ID of the vehicle the driver is associated with (insert 0 for no vehicle)");
            while (!scanner.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                scanner.next();
            }
            vehicleID = scanner.nextInt();
            scanner = new Scanner(System.in);
            do
            {
                System.out.println("Please enter the emergency contact number of this racer (must be 11 digits long)");
                contactNumber = scanner.nextLine();
            }
            while(contactNumber.length() != 11);
            System.out.println("Is this information all correct?\nName: "+forename+" "+surname+"\nVehicleID: "+vehicleID+"\nEmergency Contact: "+contactNumber);
            System.out.println("Please enter Y for yes, or any other character for no.");
            scanner = new Scanner(System.in);
            selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y")) //THIS IS BROKEN DUMBASS FIX THIS
            {
                tryAgain = true;
            }
            else
            {
                System.out.println("Added successfully.");
            }
        }
        while(tryAgain == true);
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO RacerInfo (VehicleID, EmergencyNo, RacerForename, RacerSurname) VALUES ("+vehicleID+", \""+contactNumber+"\", \""+forename+"\", \""+surname+"\");");
            conn.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
    }
    
    private void addVehicle()
    {
        scanner = new Scanner(System.in);
        boolean tryAgain;
        String model;
        String selection;
        Statement stmt = null;
        do
        {
            tryAgain = false;
            System.out.println("Please enter the model of car to be added into the database.");
            model = scanner.nextLine();
            System.out.println("Are you sure this is correct? Enter Y for yes, or any other character for no.");
            selection = scanner.nextLine();
            if(!selection.equals("Y")&&!selection.equals("y"))
            {
                tryAgain = true;
            }
            else
            {
                System.out.println("Attempting to add data...");
            }
        }
        while(tryAgain == true);
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO VehicleInfo (VehicleModel) VALUES (\""+model+"\");");
            conn.commit();
            System.out.println("Success!");
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
    }
    
    private void deleteData()
    {
        scanner = new Scanner(System.in);
        String selection;
        Statement stmt = null;
        int roadID;
        String RoadName = null;
        do
        {
            System.out.println("Please enter a valid menu option to proceed.\n");
            System.out.println("What data would you like to delete?\n1. Road\n2. Corner\n3. Racer\n4. Vehicle");
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
                System.out.println("Please note that deleting a road will delete any corners that this road was attached to. If you wish to just remove a join\nbetween two roads please use \"Delete Corner\".");
                try
                {
                    ResultSet rs = null;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT RoadID, RoadName FROM RoadInfo;");
                    while (rs.next())
                    {
                        RoadName = rs.getString("RoadName");
                        int RoadID = rs.getInt("RoadID");
                        System.out.print("Road Name = " + RoadName);
                        System.out.println(", RoadID = " + RoadID + "\n");
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("Error: "+e);
                }
                System.out.println("Please enter the ID of the road you wish to delete.");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                roadID = scanner.nextInt();
                scanner.nextLine();
                try
                {
                    ResultSet rs = null;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT RoadID, RoadName FROM RoadInfo WHERE RoadID="+roadID+";");
                    RoadName = rs.getString("RoadName");
                }
                catch(SQLException e)
                {
                    System.out.println("Error: "+e);
                }
                System.out.println("Road Name: "+RoadName+" will be deleted, along with any corners that attach it to other roads. Do you wish to proceed? Enter Y to confirm.");
                selection = scanner.nextLine();
                if(selection.equals("Y")||selection.equals("y"))
                {
                    System.out.println("Confirmed. Deleting records...");
                    try
                    {
                        ResultSet rs = null;
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery("SELECT RoadID, CornerID FROM EdgeInfo WHERE RoadID="+roadID+";");
                        while(rs.next())
                        {
                            stmt = conn.createStatement();
                            stmt.executeUpdate("DELETE FROM CornerInfo WHERE CornerID="+rs.getInt("CornerID")+";");
                            conn.commit();
                            stmt.executeUpdate("DELETE FROM EdgeInfo WHERE CornerID="+rs.getInt("CornerID")+";");
                            conn.commit();
                        }
                        stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM RoadInfo WHERE RoadID="+roadID+";");
                        conn.commit();
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error: "+e);
                    }
                    System.out.println("Deleted Records.");
                }
            break;
            case 2:
                int cornerID;
                try
                {
                    ResultSet rs = null;
                    ResultSet rs2 = null;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT CornerID FROM CornerInfo;");
                    while (rs.next())
                    {
                        cornerID = rs.getInt("CornerID");
                        stmt = conn.createStatement();
                        rs2 = stmt.executeQuery("SELECT RoadInfo.RoadName AS RoadName FROM EdgeInfo, RoadInfo WHERE EdgeInfo.CornerID="+cornerID+" AND EdgeInfo.RoadID=RoadInfo.RoadID;");
                        System.out.println("Corner "+cornerID+" connects Roads:");
                        while(rs2.next())
                        {
                            System.out.println(rs2.getString("RoadName"));
                        }
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("Error: "+e);
                }
                System.out.println("Please enter the ID of the corner you wish to delete.");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                cornerID = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Corner "+cornerID+" will be deleted. The roads it connects will remain, so that a corner may be reinstated if desired. Proceed? Enter Y to confirm.");
                selection = scanner.nextLine();
                if(selection.equals("Y")||selection.equals("y"))
                {
                    System.out.println("Confirmed. Deleting records...");
                    try
                    {
                        stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM EdgeInfo WHERE CornerID="+cornerID+";");
                        conn.commit();
                        stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM CornerInfo WHERE CornerID="+cornerID+";");
                        conn.commit();
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error: "+e);
                    }
                    System.out.println("Deleted Records.");
                }
                else
                {
                    System.out.println("Cancelled.");
                }
            break;
            case 3:
                int racerID;
                try
                {
                    ResultSet rs = null;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT RacerID, RacerForename, RacerSurname FROM RacerInfo;");
                    while (rs.next())
                    {
                        System.out.println("RacerID: "+rs.getInt("RacerID")+", Name: "+rs.getString("RacerForename")+", "+rs.getString("RacerSurname"));
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("Error: "+e);
                }
                System.out.println("Please enter the ID of the racer you wish to delete.");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                racerID = scanner.nextInt();
                scanner.nextLine();
                scanner = new Scanner(System.in);
                System.out.println("Racer "+racerID+" will be deleted. Proceed? Enter Y to confirm.");
                selection = scanner.nextLine();
                if(selection.equals("Y")||selection.equals("y"))
                {
                    System.out.println("Confirmed. Deleting records...");
                    try
                    {
                        stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM RacerInfo WHERE RacerID="+racerID+";");
                        conn.commit();
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error: "+e);
                    }
                    System.out.println("Deleted Records.");
                }
                else
                {
                    System.out.println("Cancelled.");
                }
            break;
            case 4:
                System.out.println("Deleting a vehicle will set all drivers associated with this vehicle to the status of NO VEHICLE.");
                int vehicleID;
                try
                {
                    ResultSet rs = null;
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT VehicleID, VehicleModel FROM VehicleInfo;");
                    rs.next();
                    while (rs.next())
                    {
                        System.out.println("VehicleID: "+rs.getInt("VehicleID")+", Model: "+rs.getString("VehicleModel"));
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("Error: "+e);
                }
                System.out.println("Please enter the ID of the vehicle you wish to delete.");
                while (!scanner.hasNextInt()) 
                {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                vehicleID = scanner.nextInt();
                scanner = new Scanner(System.in);
                System.out.println("Vehicle "+vehicleID+" will be deleted. Proceed? Enter Y to confirm.");
                selection = scanner.nextLine();
                if(selection.equals("Y")||selection.equals("y"))
                {
                    System.out.println("Confirmed. Deleting records...");
                    try
                    {
                        stmt = conn.createStatement();
                        stmt.executeUpdate("UPDATE RacerInfo SET VehicleID=0 WHERE VehicleID="+vehicleID+";");
                        conn.commit();
                        stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM VehicleInfo WHERE VehicleID="+vehicleID+";");
                        conn.commit();
                    }
                    catch(SQLException e)
                    {
                        System.out.println("Error: "+e);
                    }
                    System.out.println("Deleted Records.");
                }
            break;
        }
    }
    public void displayRacerInfo()
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT RacerInfo.RacerID, RacerInfo.RacerForename, RacerInfo.RacerSurname, RacerInfo.EmergencyNo, VehicleInfo.VehicleModel FROM VehicleInfo, RacerInfo WHERE RacerInfo.VehicleID=VehicleInfo.VehicleID;");
            while (rs.next())
            {
                System.out.println("Racer Name: "+rs.getString("RacerForename")+" "+rs.getString("RacerSurname"));
                System.out.println("Emergency Contact Number: "+rs.getString("EmergencyNo"));
                System.out.println("Vehicle: "+rs.getString("VehicleModel")+"\n");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
    }
}
