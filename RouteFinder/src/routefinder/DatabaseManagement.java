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
        
    }
    public void close() 
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
}
