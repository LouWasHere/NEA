/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routefinder;

import java.util.Scanner;

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
    }
}
