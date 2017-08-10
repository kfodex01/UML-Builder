
/**
 * The Main Menu for the UML Stepper.
 *
 * @author Kevin Fode
 * @version 1.0
 */

//imports
import umlbuilder.Menu;

public class MainMenu
{
    // variables
    private String[] choices = {"Enter Edit Mode", "Enter Stepping Mode",
        "Load UML from File", "Save UML to File", "Exit"};
    public static final int EDIT = 1;
    public static final int STEP = 2;
    public static final int LOAD = 3;
    public static final int SAVE = 4;
    public static final int EXIT = 5;
    
    public int execute()
    {
        
        System.out.println("Welcome to the UML Stepper");
        return new Menu(choices).provideMenu();
        
    }
    
}
