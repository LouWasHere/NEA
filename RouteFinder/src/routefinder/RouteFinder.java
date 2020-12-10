/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;
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
        boolean loop = true;
        while(loop)
        {
            DatabaseManagement dbManagement = new DatabaseManagement();
            switch(displayMainMenu())
            {
                case 1:
                    dbManagement.menu();
                break;
                case 2:
                    DjikstraOperations dOperations = new DjikstraOperations();
                    dOperations.menu();
                break;
                case 3:
                    dbManagement.displayRacerInfo();
                break;
                case 4:
                    loop = false;
                break;
            }
        }
    }
    private static int displayMainMenu()
    {
        Scanner scanner = new Scanner(System.in);
        int userSelection;
        do
        {
            System.out.println("Please enter a valid menu option to proceed.\n");
            System.out.println("1. Manage Database Information\n2. Calculate Route\n3. Display Racer and Vehicle Information\n4. Exit");
            while (!scanner.hasNextInt()) 
            {
                System.out.println("That's not a number!");
                scanner.next();
            }
            userSelection = scanner.nextInt();
        }
        while (!(userSelection >= 1 && userSelection <=4));
        return userSelection;
    }
    
}
