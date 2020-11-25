/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;
import java.sql.*;
import java.util.*;
import org.sqlite.JDBC;
/**
 *
 * @author l-bishop
 */
public class DatabaseManagement 
{
    final Scanner scanner = new Scanner(System.in);
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
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();            
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void menu()
    {
        do
        {
            System.out.println("Please enter a valid menu option to proceed.\n");
            System.out.println("1. Add new database information\n2. Modify/Delete database information\n3. View all data in database");
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
        }
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
        String roadName;
        int roadLength;
        int roadCurvature;
        int trafficLevel;
        System.out.println("Please enter the name of the road.");
        roadName = scanner.nextLine();
        System.out.println("Please enter the length of the road.");
        roadLength = scanner.nextInt();
        System.out.println("Please enter the curvature of the road (0 for straight, 180 for U-Turn)");
        roadCurvature = scanner.nextInt();
        System.out.println("Please enter the traffic level of the road (0 empty, 100 for very busy))");
        trafficLevel = scanner.nextInt();
        System.out.println("Is this information all correct?\nRoad Name: "+roadName+"\nRoad Length: "+roadLength+"\nRoad Curvature: "+roadCurvature+"\nRoad Traffic Level: "+trafficLevel);
        Road road = new Road(roadName, roadLength, roadCurvature, trafficLevel);
    }
    
    private void addCorner()
    {
        boolean doneRoads = false;
        int cornerCurvature;
        System.out.println("Please enter the curvature of the road (0 for straight, 180 for U-Turn)");
        cornerCurvature = scanner.nextInt();
        while(doneRoads == false)
        {
            // ok this is the tricky part. basically after this it creates a new edge between a corner and a road, after checking if the road is part of an
            // array listing all current attachments.
        }
        System.out.println("Is this information all correct?\nCorner Curvature: "+cornerCurvature);
    }
    
    private void modifyData()
    {
        
    }
    
    private void dumpData()
    {
        
    }
}
