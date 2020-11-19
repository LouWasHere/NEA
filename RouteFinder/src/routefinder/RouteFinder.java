/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;
import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class RouteFinder 
{
    public static void main(String[] args) 
    {
        System.out.println("MULTI - CRITERIA ROUTE FINDER");
        System.out.println("By BishopSoft\n");
        try
        {
            TimeUnit.SECONDS.sleep(1);
        } 
        catch(InterruptedException e)
        {
            System.out.println();
        }
        switch(displayMainMenu())
        {
            case 1:
                DatabaseManagement dbManagement = new DatabaseManagement();
                dbManagement.menu();
            break;
            case 2:
            
            break;
            case 3:
            
            break;
        }
    }
    private static int displayMainMenu()
    {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a valid menu option to proceed.\n");
        System.out.println("1. Manage Database Information\n2. View Stored Database Info\n3. Calculate Route");
        return scanner.nextInt();
    }
    
}
