/**
 * The EditMode class contains all the functionality for editing UML diagrams.
 *
 * @author Kevin Fode
 * @version 1.0
 */

 package umlbuilder;
 
 //imports
 import java.util.Scanner;

public class SteppingMode
{
    
    /**
     * Starts the Stepping mode.
     * @param startNode The first node in the chain.
     */
    public void execute(Node startNode)
    {
        
        //if no UML has been created.
        if(startNode==null)
        {
            System.out.println("No UML detected. Returning to main menu.");
            return;
        }
        
        System.out.println("Welcome to Stepping Mode");
        System.out.println();
        System.out.println("Executing UML...");
        System.out.println();
        
        //steeping loop
        Node currentNode = startNode;
        boolean looping = true;
        while(looping)
        {
            System.out.println(currentNode.getText());
            System.out.println();
            int numberOfNextNodes = currentNode.getNumberOfNextNodes();
            if(numberOfNextNodes == 0)
            {
                System.out.println("*** UML Chain Complete. Press any key to continue. ***");
                looping = false;
                this.waitForKeyPress();
            } else if (numberOfNextNodes == 1)
            {
                System.out.println("*** Press any key to continue. ***");
                this.waitForKeyPress();
                currentNode = currentNode.getNextNode();
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        
    }
    
    /**
     * For hitting any key.
     */
    private void waitForKeyPress()
    {
        
        Scanner reader = new Scanner(System.in);
        reader.nextLine();
        reader.close();
        
    }
    
}