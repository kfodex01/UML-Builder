
/**
 * This is the class that houses the main method.
 *
 * @author Kevin Fode
 * @version 1.0
 */

// imports
import umlbuilder.*;

public class UmlStepper
{
    
    public static void main(String[] args){
        
        // variables
        MainMenu thisMainMenu = new MainMenu();
        int action;
        EditMode thisEditMode = new EditMode();
        SteppingMode thisSteppingMode = new SteppingMode();
        UmlFileProvider thisUmlFileProvider = new UmlFileProvider();
        
        // displays the menu
        boolean looping = true;
        while(looping == true)
        {
            action = thisMainMenu.execute();
            switch(action)
            {
                case MainMenu.EXIT:
                looping = false;
                break;
                
                case MainMenu.EDIT:
                thisEditMode.execute();
                break;
                
                case MainMenu.STEP:
                thisSteppingMode.execute(thisEditMode.getStartNode());
                break;
                
                case MainMenu.SAVE:
                thisUmlFileProvider.saveFile(thisEditMode);
                
                case MainMenu.LOAD:
                thisUmlFileProvider.loadFile(thisEditMode);
                
            }
        }
    }
    
}
