/**
 * Generates a menu from a selection of provided items.
 *
 * @author Kevin Fode
 * @version 1.0
 */

package umlbuilder;

// imports
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu
{
    // variable declaration
    private String[] items;
    public static final String[] YES_NO = {"Yes", "No"};
    public static final int YES = 1;
    public static final int NO = 2;
    
    /**
     * Constructor for Menu class.
     * 
     * @param items A String array containing each menu item.
     */
    public Menu(String[] items)
    {
        this.items = items;
    }
    
    /**
     * Prints menu items.
     * 
     * @return the number of the menu option selected. Returns 0 with empty menu.
     */
    public int provideMenu()
    {
        // variables
        int menuSize = items.length;
        int choice = 0;
        
        // kills method when given an empty menu
        if (menuSize < 1)
        {
            return 0;
        }
        
        // prints menu items
        System.out.println();
        for(int i = 1; i <= menuSize; i++){
            System.out.println(i + ". " + items[i - 1]);
        }
        
        // gets user input
        while(choice == 0){
            choice = this.getChoice(menuSize);
        }
        
        return choice;
        
    }
    
    /**
     * Gets choice picked.
     * @return An int of the chosen thing.
     */
    private int getChoice(int menuSize)
    {
        
        //variables
        Scanner reader = new Scanner(System.in);
        int choice = 0;
        
        System.out.println("-->");
        try
        {
            choice = reader.nextInt();
            if (choice < 1 || choice > menuSize){ //if choice is outside menu range
                choice = 0;
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Input must be a number. Try again.");
        }
        reader.close();
        return choice;
        
    }
    
    /**
     * Adds menu items to the beginning of the menu
     * 
     * @return Returns the Menu.
     */
    public Menu addToStart(String addon)
    {
        
        String[] newItems = new String[items.length + 1];
        newItems[0] = addon;
        for(int i = 0; i < items.length; i++)
        {
            newItems[i + 1] = items[i];
        }
        items = newItems;
        return this;
        
    }
    
    /**
     * Adds menu items to the end of the menu
     * @return Returns the Menu.
     */
    public Menu addToEnd(String addon)
    {
        
        String[] newItems = new String[items.length + 1];
        for(int i = 0; i < items.length; i++)
        {
            newItems[i] = items[i];
        }
        newItems[items.length] = addon;
        items = newItems;
        return this;
        
    }
    
}
