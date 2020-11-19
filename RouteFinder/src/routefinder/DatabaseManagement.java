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
    private Connection conn = null;
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
        final Scanner scanner = new Scanner(System.in);
        int userSelection;
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
        }
        while (!(userSelection >= 1 && userSelection <=3));
        switch(userSelection)
        {
            case 1:
                
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
    
    private void addData()
    {
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            String sql = "INSERT INTO CustomerAddress (AddressID,HouseNo,Street,Town,County,PostCode) "
                    + "VALUES (6, 10, 'Downing Street', 'Westminster', 'London', 'SW1A 2AA');";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.commit();
        } 
        catch (final Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    private void modifyData()
    {
        
    }
    
    private void dumpData()
    {
        
    }
}
